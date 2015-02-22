## Breadth-First Search and A* Search (A-star Search)

This is a generic implementation of the Breadth First Search(BFS) and A* search algorithms. There are two examples provided: `Terrain.java` (`terrain.py`) and `Layton.java` (`layton.py`).

## Usage.

The implementation is available in Java and Python. Although they are nearly identical ports, Java implementation runs 20x-50x faster with larger problems.

### Java
Create a class that implements `Searchable.java` and then simply:
```java
boolean debug = true;
Searchable start = ... ;
Search search = new Search(debug);
Search.Result bfsResult = search.bfs(start);
System.out.println("bfs: " + bfsResult);
Search.Result astarResult = search.astar(start);
System.out.println("astar: " + astarResult);
```

### Python
Create a class that implements the following method:
```python
# Check if the state is considered a final state.
def done(self):

# The distance (cost) between the current state and the target state.
def distance(self, target):

# Heuristic for the A* algorithm.
def heuristic (self):

# Possible new states from the current state.
def moves(self):

# Check if the current state is the same an another.
def __eq__(self, o):

# Hash code of the current state.
def __hash__(self):

...

from search import (bfs, astar)

start = ...
goal, cost, iters = bfs(start)
print('bfs: {}, {}, {}'.format(goal, cost, iters))
goal, cost, iters = astar(start)
print('astar: {}, {}, {}'.format(goal, cost, iters))
```

## Examples

### Layton Puzzle (`Layton.java`, `layton.py`)
This puzzle is derived from one of the minigames in the *Professor Layton* series. The objective is to move the red square into the a position adjacent to the top row (that is, where the orange shape is currently located). The black squares cannot be moved. Each colored shape may be moved one unit left, right, up or down. The shapes cannot be rotated. No shapes may ever overlap.

<div align="center">
<img src="https://cloud.githubusercontent.com/assets/1221480/6317087/01e5f594-ba07-11e4-9057-ac9a3e53938e.png">
<br/>
<i>puzzle.png</i>
</div>

Here's a text-based version:
```
##########
###  aa###
##   a9 ##
#00 #99  #
#00##67  #
#12266778#
#112 6788#
## 53344##
###5534###
##########
```

For the heuristic for A* search, I used the manhattan distance between the red square and the top-right square.

Here are the number of priority queue pops (iterations) for BFS and A* for this problem:
```bash
$ javac *.java
$ java -d64 -Xms2g -Xmx4g Layton

$ # To do a full run for the puzzle (~3 mins for BFS, ~1 min for A*):
$ # java -d64 -Xms2g -Xmx4g Layton run debug

bfs=3615884
astar=2841892
```

### Terrain (`Terrain.java`, `terrain.py`)
Consider the following terrain map:

<div align="center">
<img src="https://cloud.githubusercontent.com/assets/1221480/5770089/20223c9a-9ceb-11e4-8d7c-75abfd63f901.png">
<br/>
<i>terrain.png</i>
</div>

Let's treat each pixel as a possible state. Let's say that each pixel can be directly reached only from its 4 adjacent neighbor pixels. (Moving diagonally is not allowed.) Let's say the cost of transitioning to any pixel is the value (from 0-255) in the green color channel of the destination pixel. The objective is to find the path with the lowest cost from pixel location (100, 100) to (400, 400).

For the heuristic for A* search, I used the manhattan distance from the current position the to goal position (400, 400).

Here are the number of priority queue pops (iterations) for BFS and A* for this problem:
```bash
$ javac *.java
$ java Terrain

bfs=225928
astar=219260
```
