# Square-1

Small project for visualization of Square-1 puzzle in JavaFX.

<img src="images/Square1.png" alt="Square 1" width="300" height="323">

See this [Wikipedia article](https://en.wikipedia.org/wiki/Square-1_%28puzzle%29) for a description of Square-1 cube.

# Pieces

The Square-1 is built from different pieces:

+ 8 Corner pieces
+ 8 Edge pieces
+ 2 Middle pieces

<img src="images/Square1Assemble.png" alt="Square 1 Assemble" width="300" height="323">

## Corner piece

There are 8 corner pieces used in a Square-1 cube. A corner piece has six sides: top, left rear, left front, right front, right rear, bottom.  Three of them are colored.


| Type   | Orientation | Name | Colors | Image |
|--------|-------------|------|--------|-------|
| Corner Piece | Top         | A    | White, Yellow, Orange | <img src="images/corner1.png" alt="Corner 1" width="100" height="136"> |
| Corner Piece | Top         | B    | White, Red, Yellow | <img src="images/corner4.png" alt="Corner 4" width="100" height="136"> |
| Corner Piece | Top         | C    | White, Blue, Red | <img src="images/corner3.png" alt="Corner 3" width="100" height="136"> |
| Corner Piece | Top         | D    | White, Orange, Blue | <img src="images/corner2.png" alt="Corner 2" width="100" height="136"> |
| Corner Piece | Bottom      | E    | Green, Blue, Orange | <img src="images/corner6.png" alt="Corner 6" width="100" height="136"> |
| Corner Piece | Bottom      | F    | Green, Red, Blue | <img src="images/corner7.png" alt="Corner 7" width="100" height="136"> |
| Corner Piece | Bottom      | G    | Green, Yellow, Red | <img src="images/corner8.png" alt="Corner 8" width="100" height="136"> |
| Corner Piece | Bottom      | H    | Green, Orange, Yellow | <img src="images/corner5.png" alt="Corner 5" width="100" height="136"> |

## Edge piece

There are 8 edge pieces used in a Square-1 cube. An edge piece has five sides: top, left, front, right, bottom. Two of them are colored.

| Type   | Orientation | Name | Colors | Image |
|--------|-------------|------|--------|-------|
| Edge Piece   | Top         | 1    | White, Yellow | <img src="images/edge1.png" alt="Edge 1" width="77" height="119"> |
| Edge Piece   | Top         | 2    | White, Red |<img src="images/edge4.png" alt="Edge 4" width="77" height="119"> |
| Edge Piece   | Top         | 3    | White, Blue |<img src="images/edge3.png" alt="Edge 3" width="77" height="119"> |
| Edge Piece   | Top         | 4    | White, Orange |<img src="images/edge2.png" alt="Edge 2" width="77" height="119"> |
| Edge Piece   | Bottom      | 5    | Green, Orange |<img src="images/edge6.png" alt="Edge 6" width="77" height="119"> |
| Edge Piece   | Bottom      | 6    | Green, Blue |<img src="images/edge7.png" alt="Edge 7" width="77" height="119"> |
| Edge Piece   | Bottom      | 7    | Green, Red |<img src="images/edge8.png" alt="Edge 8" width="77" height="119"> |
| Edge Piece   | Bottom      | 8    | Green, Yellow |<img src="images/edge5.png" alt="Edge 5" width="77" height="119"> |

## Middle piece

There are 2 middle pieces used in a Square-1 cube. A middle piece has six sides: top, left, front, right, rear, bottom. Three of them are colored.

| Type   | Name | Colors | Image |
|--------|------|--------|-------|
| Middle Piece     | M | Red, Orange, Yellow | <img src="images/middle1.png" alt="Middle 1" width="180" height="145"> |
| Middle Piece     | N | Orange, Blue, Red | <img src="images/middle2.png" alt="Middle 2" width="180" height="145"> |

# Releases

Releases can be found here for download

* [Linux](https://github.com/treimers/square-1/releases/tag/Linux)
* [Mac OS X](https://github.com/treimers/square-1/releases/tag/MacOSX)
* [Windows](https://github.com/treimers/square-1/releases/tag/WindowsX64)

# Usage

Application supports viewing and position editing currently. Solving the Square-1 is coming next. 

You can use the mouse to rotate the Square-1 cube and see all sides. Keyboard keys can be used to control different rotations as well.

**File Menu**

+ **ShortCut L**: load position from file
+ **ShortCut S**: save position to file
+ **ShortCut Q**: quit application

**Square-1 Menu**

+ **ShortCut C**: change colors of Square-1 sides
+ **ShortCut P**: enter a position
+ **ShortCut V**: enter current position

**View Menu**

+ **ShortCut B**: rotate in positive direction along x-axis
+ **Alt ShortCut B**: rotate in negative direction along x-axis
+ **ShortCut N**: rotate in positive direction along y-axis
+ **Alt ShortCut N**: rotate in negative direction along y-axis
+ **ShortCut M**: rotate in positive direction along z-axis
+ **Alt ShortCut M**: rotate in negative direction along z-axis
+ **ShortCut X**: toggle visibility of x-axis, y-axis and z-axis
+ **ShortCut R**: run an animated rotation (360 degrees) once
+ **Alt ShortCut R**: reset all rotations (x, y and z) to 0

**Help Menu**
+ **ShortCut K**: show hot keys
+ **ShortCut A**: show about screen
