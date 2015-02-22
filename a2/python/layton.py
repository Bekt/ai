import numpy as np
from search import (bfs, astar)


class Layton(object):

    def __init__(self, board=None, shapes=None, current=None, goal=None):
        self.board = board
        self.shapes = shapes
        self.current = current
        self.goal = goal
        self._hash = None
        self._h = None

    def __eq__(self, o):
        return np.array_equal(self.board, o.board)

    def __hash__(self):
        self._hash = self._hash or hash(self.board.tostring())
        return self._hash

    def __str__(self):
        return str(self.board)

    def distance(self, target):
        return 1

    def done(self):
        return self.current == self.goal

    def heuristic(self):
        if self._h is None:
            p, q = self.current[0], self.goal[0]
            self._h = abs(p[0] - q[0]) + abs(p[1] - q[1])
        return self._h

    def moves(self):
        for ind, shape in enumerate(self.shapes):
            up = self._move_up(ind)
            if up: yield up
            down = self._move_down(ind)
            if down: yield down
            left = self._move_left(ind)
            if left: yield left
            right = self._move_right(ind)
            if right: yield right

    def _move_up(self, shape_ind):
        return self._move(shape_ind, lambda c: (c[0]+1, c[1]))

    def _move_down(self, shape_ind):
        return self._move(shape_ind, lambda c: (c[0]-1, c[1]))

    def _move_left(self, shape_ind):
        return self._move(shape_ind, lambda c: (c[0], c[1]-1))

    def _move_right(self, shape_ind):
        return self._move(shape_ind, lambda c: (c[0], c[1]+1))

    def _move(self, shape_ind, step_handler, d=False):
        shape = self.shapes[shape_ind]
        for coor in shape:
            step = step_handler(coor)
            if self.board[step] != -1 and step not in shape:
                return
        nboard = np.array(self.board, copy=True)
        nshapes = list(self.shapes)
        nshape = []
        moved = set()
        for coor in shape:
            step = step_handler(coor)
            if coor not in moved:
                nboard[coor] = -1
            nboard[step] = self.board[coor]
            nshape.append(step)
            moved.add(step)
        nshapes[shape_ind] = nshape
        ncurrent = nshape if shape == self.current else self.current
        return Layton(nboard, nshapes, ncurrent, self.goal)


def gen_start():
    board = np.array([
        np.full(10, -2),
        [-2, -2, -2, -1, -1, 10, 10, -2, -2, -2],
        [-2, -2, -1, -1, -1, 10, 9, -1, -2, -2],
        [-2, 0, 0, -1, -2, 9, 9, -1, -1, -2],
        [-2, 0, 0, -2, -2, 6, 7, -1, -1, -2],
        [-2, 1, 2, 2, 6, 6, 7, 7, 8, -2],
        [-2, 1, 1, 2, -1, 6, 7, 8, 8, -2],
        [-2, -2, -1, 5, 3, 3, 4, 4, -2, -2],
        [-2, -2, -2, 5, 5, 3, 4, -2, -2, -2],
        np.full(10, -2),
    ])
    shapes = [
        [(1, 5), (1, 6), (2, 5)],
        [(2, 6), (3, 5), (3, 6)],
        [(3, 1), (3, 2), (4, 1), (4, 2)],
        [(5, 1), (6, 1), (6, 2)],
        [(5, 2), (5, 3), (6, 3)],
        [(5, 4), (4, 5), (5, 5), (6, 5)],
        [(4, 6), (5, 6), (6, 6), (5, 7)],
        [(5, 8), (6, 8), (6, 7)],
        [(7, 3), (8, 3), (8, 4)],
        [(7, 4), (7, 5), (8, 5)],
        [(7, 6), (7, 7), (8, 6)],
    ]
    return Layton(board=board,
                  shapes=shapes,
                  current=[(3, 1), (3, 2), (4, 1), (4, 2)],
                  goal=[(1, 5), (1, 6), (2, 5), (2, 6)])


if __name__ == '__main__':
    start = gen_start()
    # Each of these take 20-30 mins.
    # Use the Java version which the the exact port
    # but finishes in <3 minutes.
    goal, cost, iters = bfs(start, debug=True)
    print('bfs2-cost: %d' % cost)
    print('bfs2-pops: %d' % iters)

    goal, cost, iters = astar(start, debug=True)
    print('astar2-cost: %d' % cost)
    print('astar2-pops: %d' % iters)

