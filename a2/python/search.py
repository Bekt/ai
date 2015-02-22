from heapq import (heappush, heappop)


def astar(start, debug=False):
    return _search(start, astar=True, debug=debug)


def bfs(start, debug=False):
    return _search(start, astar=False, debug=debug)


def _search(start, astar=False, debug=False):
    _reset()
    pq = []
    g = {start: 0}
    hpush(pq, start, start.heuristic())
    i = 0
    while True:
        i += 1
        node = hpop(pq)
        if node is None or node.done():
            return node, g.get(node, None), i
        if debug and i % 20000 == 0:
            print(i, g[node], len(pq))
        for move in node.moves():
            new_cost = g[node] + node.distance(move)
            if move not in g or new_cost < g[move]:
                g[move] = new_cost
                h = move.heuristic() if astar else 0
                hpush(pq, move, g[move] + h)


# Internal.
entries = {}
removed = set()


def hpush(pq, item, pr):
    if item in entries:
        entry = entries.pop(item)
        removed.add(entry)
    entry = (pr, id(item), item)
    entries[item] = entry
    heappush(pq, entry)


def hpop(pq):
    while pq:
        entry = heappop(pq)
        if entry not in removed:
            item = entry[2]
            del entries[item]
            return item


def _reset():
    entries = {}
    removed = set()
