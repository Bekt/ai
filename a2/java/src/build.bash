#!/usr/bin/env bash

javac *.java
# <1 sec.
java Terrain
java -d64 -Xms2g -Xmx4g Layton

# To do a full run for the puzzle (~3 mins for BFS, ~1 min for A*):
#time java -d64 -Xms2g -Xmx4g Layton run debug

rm -f *.class
