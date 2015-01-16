## Breadth-First Search
This is a basic BFS implementation using priority queues. To illustrate it works, the program loads an image and find the minimum cost to get from point A to point B. A cost is the value of R**G**B of the pixel.

## Instructions
Load the following terrain map:

<div align="center">
<img src="https://cloud.githubusercontent.com/assets/1221480/5770089/20223c9a-9ceb-11e4-8d7c-75abfd63f901.png">
<br/>
*terrain.png*
</div>

Let's treat each pixel as a possible state. Let's say that each pixel can be
directly reached only from its 4 adjacent neighbor pixels.
(Moving diagonally is not allowed.)
Let's say the cost of transitioning to any pixel is the value (from 0-255) in
the green color channel of the destination pixel. Find the path with the lowest
cost from pixel location (100, 100) to (400, 400). Keep track of the number of
times you iterate through the "while" loop, iter.
When iter % 5000 < 1000, set the pixel at the current state, s, to green.
When the goal state is reached, draw the shortest path in red.
Your visualization should look similar to this, except it should not contain
any superfluous primates:

<div align="center">
<img src="https://cloud.githubusercontent.com/assets/1221480/5770098/400ceb0e-9ceb-11e4-8486-8a419d803208.png">
<br/>
*contour.png*
</div>


The path with the lowest cost has a cost of **65844**.


## Results
```bash
$ python3 bfs.py terrain.png 100 100 400 400
Cost: 65844
Iterations: 903708
Output: path.png
```
<div align="center">
<img src="https://cloud.githubusercontent.com/assets/1221480/5770090/254ae21c-9ceb-11e4-88df-af74e7de5044.png">
<br/>
*path.png*
</div>
