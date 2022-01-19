# Notation

Jaap Scherphuis introduced a notation to model a Square-1 position.

A Square-1 consists of 8 corner pieces, 8 edge pieces and 2 middle pieces. All corner pieces are named by letters from 'A' to 'H', all edge pieces are named by digits from '0' to '9'.

The middle layer has two pieces with possible positions:

 - the middle layer is in original state (square) or
 - the middle layer is twisted (kite-shape).

An original middle layer is reflected in this notation by a '-' character while a twisted middle layer is identified by a '/' character.

Hold the Square-1 so that a long side of the middle layer is on the left side. You should see a gap in the front with a smaller face on to the left and a longer one to the right.

The pieces in the top layer are now counted starting from the position left to this gap. The following pieces are order clockwise (from top view). On a solved Square-1 the top layer is denoted by A1B2C3D4.

The pieces in the bottom layer are counted starting from the position right to the gap. Next pieces are arranged clockwise (from bottom view). The bottom layer of a solved Square-1 consist of pieces 5E6F7G8H.

The start pieces of top and bottom layer are illustrated in the next picture:

<img src="images/notation.png" alt="Notation" width="323" height="291">

All these are put together into a position string with top layer pieces, bottom layer pieces and middle layer position. This gives

	A1B2C3D45E6F7G8H-

for a solved Square-1.

The Square-1 with a twisted middle layer is represented by this position string

	A1B2C3D45E6F7G8H/

<img src="images/notation2.png" alt="Notation" width="338" height="347">
