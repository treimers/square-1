# Developer documentation

This file describes the structure of the Square-1 application for someone who wants to change the code. Usage is in the [README](README.md). Piece names and colours are in [internals.md](internals.md). The vertices and triangles of the 3D pieces are in [implementation.md](implementation.md).

## Overview

The application shows a Square-1 position in JavaFX, lets you enter, scramble, and solve it, and plays the solution step by step.

The code is split into two packages:

- `net.treimers.square1` is the application: model, user interface, persistence, and scramble.
- `net.jaapsch.square1` is Jaap Scherphuis's solver. The application calls it and applies the returned move sequence to its own model.

Data flow when solving:

1. `Square1Controller` holds the current `Position`.
2. The solve dialog passes its notation to `Solver.solve`.
3. The solver returns a move sequence as text.
4. `MoveSequence` parses the text, and `Position.move` produces the intermediate positions.
5. `MeshGroup` draws the position selected by the slider in the solve dialog.

## Project layout and startup

Java 17, JavaFX 17, Maven. The entry class is `net.treimers.square1.Square1`. It sets the locale to English and starts `Square1App`. The application loads `square1panel.fxml` and passes the stage to `Square1Controller`.

```
mvn javafx:run
mvn test
```

`mvn package` also builds an installer with a bundled JRE (`javapackager`).

| Path | Contents |
|------|----------|
| `src/main/java/net/treimers/square1` | Application |
| `src/main/java/net/jaapsch/square1` | Solver |
| `src/main/resources/net/treimers/square1` | FXML, images, pruning tables (`.dat`) |
| `src/main/markdown/net/treimers/square1` | Help texts as Markdown |
| `src/test/java/net/treimers/square1` | JUnit test and search utilities |

During the build, `markdown-page-generator-plugin` converts the help texts to HTML. `HelpController` loads those HTML files into a `WebView`. The Markdown sources are the texts to edit.

## Architecture

`Square1Controller` drives the main window. It implements `Initializable`, `ColorBean`, `PropertyChangeListener`, and `MenuHandler`. Dialogs have their own controllers and share the base class `Square1Dialog`.

| FXML | Controller | Role |
|------|------------|------|
| `square1panel.fxml` | `Square1Controller` | Main window, menu, 3D view |
| `positionpanel.fxml` | `PositionController` | Enter a position |
| `solvepanel.fxml` | `SolveController` | Search for a solution and play it |
| `helppanel.fxml` | `HelpController` | Help |
| `keyboardshortcutpanel.fxml` | `KeyboardShortcutController` | Keyboard shortcuts |

Packages under `net.treimers.square1`:

| Package | Role |
|---------|------|
| `controller` | Flow and dialog control |
| `model` | Position, move, colours, stored data |
| `model.persistence` | File and preferences |
| `view.piece` | Piece meshes and layers |
| `view.dialog` | Dialog windows |
| `view.misc` | Scene group, images |
| `solver` | `Scrambler` |
| `exception` | `Square1Exception` |
| `util` | Colour as text |

Colours and piece visibility travel through `PropertyChangeSupport`. `Square1Controller` is the `ColorBean`. Each `AbstractPiece` listens for colour changes and repaints its material. `MeshGroup` reports which pieces are currently in the scene so the check items in the **Pieces** menu stay in step.

## Domain model

### Position

`Position` stores the pieces as numbers in an array of length 24. A corner occupies two entries (60°), an edge one (30°). The top layer uses indices 0 to 11, the bottom layer 12 to 23. The middle layer is a separate character: `-` for square, `/` for kite. The solved position is `A1B2C3D45E6F7G8H-`.

`toString()` appends the top and bottom pieces and then the middle layer. A complete position has 17 characters. Shorter strings are partial positions from the position dialog. `Square1Controller.doSolvePosition` refuses to solve while the length is not 17.

`getPieces()` returns the pieces by `Layer` (`TOP`, `BOTTOM`, `MIDDLE`). The names and colours of pieces A–H, 1–8, and the middle pieces M and N are in [internals.md](internals.md).

### Move

A `Move` has a top rotation, a bottom rotation, and a twist flag. The numbers are multiples of 30°, positive clockwise. `3,0/` turns the top layer by 90° and then slices. `0,-1-` turns only the bottom layer by 30° anticlockwise, without a slice.

`Move.PATTERN` and `MoveSequence` read sequences such as `(3,0)/(-1,1)/` and the more compact form `3,0/-1,1/` that the solver emits. `toString()` of a move ends with `/` when it slices, otherwise with `-`.

`Position.move` first rotates both layers in the array of 24. A move is illegal if a corner then lies across the front or right slice: the two half-entries of that corner would be split. A twist swaps the right half of the top layer with the right half of the bottom layer and flips the middle layer between `-` and `/`.

`Position.move(MoveSequence)` returns the starting position and the position after every move. That list feeds the slider in the solve dialog.

## 3D view

The main view is a `SubScene` with a `PerspectiveCamera` and a white `AmbientLight`. Inside it sits a `SmartGroup` (mouse rotation) and inside that the `MeshGroup`. The coordinate axes are a separate `Group` and can be shown or hidden from the menu. Keyboard and menu rotate the group by 10° at a time.

`MeshGroup.setContent` rebuilds the scene from the `Position`. Corners are placed as `CornerPiece` at steps of 60°, edges as `EdgePiece` at steps of 30°. The bottom layer is also flipped about the X axis. The middle layer always consists of `MiddlePiece` M and N. For `-`, N is rotated 180° about Z; for `/`, 180° about X and 150° about Z.

`AbstractPiece` is a `MeshView` with a `TriangleMesh`. Colours do not come from one material per face. They come from a one-row `WritableImage`: each `Side` is one pixel, and the texture coordinates in `Constants` point at the centre of that pixel. A colour change replaces only that image row. `CullFace` is `NONE`, so the inner faces stay visible.

The vertices and triangles of the corner, edge, and middle piece are in [implementation.md](implementation.md).

## User interface

The **File** menu loads and saves through `FileStore`. The **Edit** menu opens colours, position, scramble, and solve.

`PositionDialog` places pieces in a grid (`GridEntry`). `PositionController` uses `canAdd` and `isAvailable` to check that the piece is still free and that the layer can still take it.

`SolveDialog` shows the notation, the move sequence, and a slider. **Solve** calls `Solver.solve`, applies the sequence, and sets the slider to the intermediate positions. **Enter Move** does the same with a sequence typed by hand. The arrows and the slider call `MeshGroup.setContent` for the selected position. If the dialog closes with a position, the main window takes the last position under the slider.

`ColorDialog` changes the eight entries of `Side`: six outer colours and two inner colours (`INNER_VERTICAL`, `INNER_HORIZONTAL`). The defaults in the controller are white, yellow, orange, dark blue, red, green, grey, and black.

`Scrambler` produces up to six random slices. Top and bottom rotation lie between −6 and +5. Illegal moves are discarded. `doScramble` plays the resulting positions 500 ms apart.

Help is a tree of the generated HTML pages. Keyboard shortcuts are shown by `KeyboardShortcutController`.

## Persistence

`Square1Data` bundles the colour scheme, the position string, and the `MoveSequence`. `DataStore` has two implementations:

- `FileStore` writes Hjson. Jackson serialises to JSON, and Hjson formats the file and reads it back.
- `PreferencesStore` stores colours, position, and solution under `Preferences.userNodeForPackage(Square1.class)`. The keys are `color.<side>`, `position`, and `solution`.

Colours are stored as text through `Utils`.

## Solver

`SolveController` creates a `Solver` when it starts. The constructor builds the tables once. `solve(String)` expects the position notation, throws `Square1Exception` for an invalid or non-sliceable position, and returns the move sequence.

Before the search, `FullPosition.alignToSolvableShape` turns the layers into an orientation that occurs in the shape tables. That turn is prepended to the solution, with `-` instead of `/`, because it does not slice.

The search itself is `SimpPosition.solve`. It is IDA*: a depth-bounded search whose bound increases until a solution is found. It does not check the full colouring. It checks three simpler colourings (top/bottom, plus two half-colourings that share the same tables). A move sequence that solves all three also solves the normal position. The idea and the notation are described in `package-info.java` of `net.jaapsch.square1`. Leave that text unchanged: the licence requires the bundled documentation to be distributed without changes.

| Class | Role |
|-------|------|
| `FullPosition` | Full position, 24 entries, middle layer |
| `Sq1Shape` | List of possible shapes, including parity |
| `ShapeTranTable` | Shape after a move |
| `ShpColTranTable` | Corner or edge colouring after a move |
| `ChoiceTable` | Maps a colouring to a table index |
| `PrunTable` | Lower bound on the remaining moves for a colouring |
| `SimpPosition` | Encoded position and the search |
| `Layer`, `HalfLayer`, `ShapeColPos` | Helper representations inside the solver |

There are two metrics. In the twist metric only a slice counts as a move. In the turn metric every layer turn and every slice counts. The `turnMetric` flag in the `Solver` constructor is `false`. The search therefore runs in the twist metric: layer turns do not reduce the search depth, and a square middle layer is searched at even slice counts only. The pruning files for that metric are `sq1p1w.dat` and `sq1p2w.dat`. If they are missing as a resource or as a file, the tables are calculated on first start. The comment next to the flag speaks of the turn metric; the value `false` and the branches in `SimpPosition` and `PrunTable` decide.

`net.jaapsch.square1` is the ported solver. Changes to the search there should leave the bundled comments and `package-info.java` untouched. The external entry point is `Solver.solve`.

## Tests

`ScrambleSolveTest` is the JUnit test. It scrambles a solved position with a fixed seed, solves the result through `Solver`, and checks that applying the sequence returns to `A1B2C3D45E6F7G8H-`. Every scrambled position must be 17 characters long.

`Square1Search`, `Square1Shapes`, and `SquareOnePositions` have a `main` method and are not JUnit tests. They count shapes and search for move sequences at the shape level (corner as `C`, edge as `E`), independent of the user interface.

## Making changes

A new dialog action belongs in the matching FXML, its controller, and, when it is reached from the menu, in `Square1Controller`.

The move rules live in one place: `Position.move`. Reading and writing notation lives in `Move` and `MoveSequence`. The 3D layout lives in `MeshGroup`, the mesh geometry in `CornerPiece`, `EdgePiece`, and `MiddlePiece`.

A different solver metric is the `turnMetric` flag in the `Solver` constructor. The application passes the position string and reads a move sequence; it does not know the tables.

The solver returns one finished sequence. It searches until it finds the first solution at the current bound, then stops. That does not guarantee a shorter sequence in the other metric.
