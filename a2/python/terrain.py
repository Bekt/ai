#!/usr/bin/env python3
from search import (bfs, astar)
from PIL import Image


class Terrain(object):

    def __init__(self, img, current, goal):
        self.img = img
        self.current = current
        self.goal = goal
        self.pix = img.load()
        self._h = None

    def distance(self, target):
        p = target.current
        # Green pixel value.
        return self.pix[p[0], p[1]][2]

    def done(self):
        return self.current == self.goal

    def heuristic(self):
        self._h = self._h or (14 * (abs(self.current[0] - self.goal[0])
                                 + abs(self.current[1] - self.goal[1])))
        return self._h

    def moves(self):
        p = self.current
        for step in [(p[0]+1, p[1]), (p[0]-1, p[1]),
                     (p[0], p[1]+1), (p[0], p[1]-1)]:
            y, x = step
            if 0 <= y < self.img.size[0] and 0 <= x < img.size[1]:
                yield Terrain(self.img, step, self.goal)

    def __eq__(self, o):
        return self.current == o.current

    def __hash__(self):
        return hash(self.current)


def do_astar(terrain):
    goal, cost, iters = astar(terrain)
    print('astar1=%d' % iters)


def do_bfs(terrain):
    goal, cost, iters = bfs(terrain)
    print('bfs1=%d' % iters)


if __name__ == '__main__':
    img = Image.open('terrain.png')
    start = Terrain(img, (100, 100), (400, 400))
    do_bfs(start)
    do_astar(start)
