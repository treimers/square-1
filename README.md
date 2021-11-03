# Square-1

Small project for visualization of Square-1 puzzle in JavaFX.

![Square-1](images/Square1.png)

See this [Wikipedia article](https://en.wikipedia.org/wiki/Square-1_%28puzzle%29) for a description of Square-1 cube.

# Pieces

The Square-1 is built from different pieces

+ Corners pieces
+ Edges pieces
+ Middle pieces

## Corner piece

A corner piece has six sides: top, left rear, left front, right rear, right front, bottom. In order to model a corner piece in JavaFX 3D the eight points A to H are used:

![Corner Piece](images/corner.png)

There are 8 corner pieces used in a Square-1 cube. They have three colored sides.




| Orientation | Name             | Image                           |
| ----------- | --------------   | ------------------------------- |
| Top         | Corner Piece 1-4 | <img src="images/corner1.png" alt="Corner 1" width="115" height="154"> <img src="images/corner2.png" alt="Corner 2" width="115" height="154"> <img src="images/corner3.png" alt="Corner 3" width="115" height="154"> <img src="images/corner4.png" alt="Corner 4" width="115" height="154">|
| Bottom      | Corner Piece 5-8 | <img src="images/corner5.png" alt="Corner 5" width="115" height="154"> <img src="images/corner6.png" alt="Corner 6" width="115" height="154"> <img src="images/corner7.png" alt="Corner 7" width="115" height="154"> <img src="images/corner8.png" alt="Corner 8" width="115" height="154">|

## Edge piece

An edge piece has five sides: top, left, front, right, bottom. In order to model an edge piece in JavaFX 3D the six points A to F are used:

![Edge Piece](images/edge.png)

There are 8 edge pieces used in a Square-1 cube. They have two colored sides.

| Orientation | Name           | Image                           |
| ----------- | ------------   | ------------------------------- |
| Top         | Edge Piece 1-4 | <img src="images/edge1.png" alt="Edge 1" width="61" height="85"> <img src="images/edge2.png" alt="Edge 2" width="61" height="85"> <img src="images/edge3.png" alt="Edge 3" width="61" height="85"> <img src="images/edge4.png" alt="Edge 4" width="61" height="85">|
| Bottom      | Edge Piece 5-8 | <img src="images/edge5.png" alt="Edge 5" width="61" height="85"> <img src="images/edge6.png" alt="Edge 6" width="61" height="85"> <img src="images/edge7.png" alt="Edge 7" width="61" height="85"> <img src="images/edge8.png" alt="Edge 8" width="61" height="85">|

## Middle piece

A middle piece has six sides: top, left, front, right, rear, bottom. In order to model a middle piece in JavaFX 3D the eight points A to H are used:

![Middle Piece](images/middle.png)

There are 2 middle pieces used in a Square-1 cube. They have three colored sides.

| Name             | Image                           |
| ---------------- | ------------------------------- |
| Middle Piece 1-2 | <img src="images/middle1.png" alt="Middle 1" width="143" height="149"> <img src="images/middle2.png" alt="Middle 2" width="143" height="149"> |

# Usage

Currently the application supports viewing only. You can use the mouse to rotate the Square-1 cube and see all sides.

Keyboard keys can be used to toggle visibility of the pieces and do rotations:

+ Keys A - H: toggle visibility of a corner (named from A=1 to H=8)
+ Keys 1 - 8: toggle visibility of an edge (numbered from 1 to 8)
+ Keys M - N: toggle visibility of a middle piece (M = middle piece 1, N = middle piece 2)
+ Key 0: hide all nodes (edges, corners and middle pieces)
+ Key 9: show all nodes (edges, corners and middle pieces)
+ Keys X: toggle visibility of x-axis, y-axis and z-axis
+ Key I: rotate in positive direction along x-axis
+ Key J: rotate in positive direction along x-axis
+ Key O: rotate in positive direction along y-axis
+ Key K: rotate in negative direction along y-axis
+ Key P: rotate in positive direction along z-axis (not possible with mouse)
+ Key L: rotate in negative direction along z-axis (not possible with mouse)
+ Key R: reset all rotations (x, y and z) to 0
