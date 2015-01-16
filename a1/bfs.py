#!/usr/bin/env python3

from heapq import (heappush, heappop)
from PIL import Image
from types import SimpleNamespace as Node


def bfs(start, goal, img):
    pq = []
    heappush(pq, (start.cost, id(start), start))
    pix = img.load()
    visited = {(start.x, start.y)}
    i = 0
    while pq:
        item = heappop(pq)[2]
        if item.x == goal[0] and item.y == goal[1]:
            return item, i
        if i % 5000 < 1000:
            pix[item.y, item.x] = (0, 255, 0, 255)
        for step in [(item.x+1, item.y), (item.x-1, item.y),
                     (item.x, item.y+1), (item.x, item.y-1)]:
            x, y = step
            if (0 <= y < img.size[0] and 0 <= x < img.size[1]
                    and step not in visited):
                visited.add(step)
                node = Node(parent=item, x=x, y=y,
                            cost=item.cost + pix[y, x][2])
                heappush(pq, (node.cost, id(node), node))
            i += 1


def path(node, img):
    pix = img.load()
    while node.parent:
        pix[node.y, node.x] = (255, 0, 0, 255)
        node = node.parent


def main(filename, s_x, s_y, e_x, e_y):
    img = Image.open(filename)
    start = Node(parent=None, x=s_x, y=s_y, cost=0)
    goal, iters = bfs(start, (e_x, e_y), img)
    path(goal, img)
    img.save('path.png')
    print('Cost: %d' % goal.cost)
    print('Iterations: %d' % iters)
    print('Output: path.png')

if __name__ == '__main__':
    from sys import argv
    main(argv[1], int(argv[2]), int(argv[3]), int(argv[4]), int(argv[5]))
