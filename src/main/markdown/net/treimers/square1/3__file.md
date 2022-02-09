# File Operations

A Square-1 can be saved to a data file or reloaded from a data file.

Save will write the current coloring scheme and the current position to a text file.

A saved Square-1 with coloring scheme and position can be restored later or after program restart via load.

The data file contains the color scheme and the position encoded as Hjson (human readable and human editable Json). An example output from a solved Square-1 with standard color scheme is given below.

```
{
  colorStrings:
  [
    0xFFFFFFFF
    0xFFFF00FF
    0xFFA500FF
    0x00008BFF
    0xFF0000FF
    0x008000FF
    0x808080FF
    0x000000FF
  ]
  positionString: A1B2C3D45E6F7G8H-
}
```