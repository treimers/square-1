# Implementation

Implementation of the Square-1 pieces in JavaFX is done using MeshViews. A MeshView is a collection of faces or surfaces that form a 3D figure in space.

## Corner Piece

<img src="images/corner.png" alt="Square 1 Position Dialog" width="151" height="203">

A corner piece has three visible, colored faces: left front, right front and bottom or top. All other faces are not visible by default and colored light or dark gray.

The piece is created using 8 corner points named A to H. All faces are build and colored using triangles based on the corners.

    bottom: D, C, B, A
    left rear: F, E, A, B
    left front: C, G, F, B
    right front: C, D, H, G
    right rear: A, E, H, D
    top: E, F, G, H

There are 8 corner pieces in a Square-1.

## Edge Piece

<img src="images/edge.png" alt="Square 1 Position Dialog" width="131" height="178">

An edge piece has two visible, colored faces: top and front. All other faces are not visible by default and colored light or dark gray.

The piece is created using 6 corner points named A to F. All faces are build and colored using triangles based on the corners.

    bottom: C, B, A
    left: A, B, E, D
    front: B, C, F, E
    right 1: D, F, C, A
    top: D, E, F

There are 8 edge pieces in a Square-1.

## Middle Piece

<img src="images/middle.png" alt="Square 1 Position Dialog" width="234" height="310">

A middle piece has three visible, colored faces: left, front and rear. All other faces are not visible by default and colored light or dark gray.

The piece is created using 8 corner points named A to H. All faces are build and colored using triangles based on the corners.

    bottom: D, C, B, A
    left: F, E, A, B
    front: C, G, F, B
    right: C, D, H, G
    rear: E, H, D, A
    top: E, F, G, H

There are 2 middle pieces in a Square-1.
