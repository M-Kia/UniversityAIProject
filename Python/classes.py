import enum
import math
from functools import cmp_to_key


class Action(enum.Enum):
    Up = 1
    Down = 2
    Left = 3
    Right = 4

    def opposite(self):
        match self.value:
            case 1:
                return self.Down
            case 2:
                return self.Up
            case 3:
                return self.Right
            case 4:
                return self.Left


class Puzzle:
    goal_state = [1, 2, 3, 4, 5, 6, 7, 8, 0]

    def set_goal(self, state: list):
        self.goal_state = state.copy()

    def __init__(self, state: list):
        self.state = state.copy()

    def move(self, action: Action):
        try:
            zero_index = self.state.index(0)
        except:
            pass
        match action:
            case Action.Up:
                self.state[zero_index], self.state[zero_index - 3] = self.state[zero_index - 3], self.state[zero_index]
            case Action.Down:
                self.state[zero_index], self.state[zero_index + 3] = self.state[zero_index + 3], self.state[zero_index]
            case Action.Right:
                self.state[zero_index], self.state[zero_index + 1] = self.state[zero_index + 1], self.state[zero_index]
            case Action.Left:
                self.state[zero_index], self.state[zero_index - 1] = self.state[zero_index - 1], self.state[zero_index]

    def movement_ability(self):
        try:
            zero_index = self.state.index(0)
        except:
            pass
        l = list()
        if math.floor(zero_index / 3) != 0:
            l.append(Action.Up)
        if math.floor(zero_index / 3) != 2:
            l.append(Action.Down)
        if zero_index % 3 != 0:
            l.append(Action.Left)
        if zero_index % 3 != 2:
            l.append(Action.Right)
        return l

    def calculate_cost(self):
        s = 0
        try:
            for i in range(0, 9):
                goal_index = self.goal_state.index(i)
                index = self.state.index(i)
                if goal_index == index:
                    continue
                s += abs(math.floor(goal_index / 3) - math.floor(index / 3)) + abs((goal_index % 3) - (index % 3))
        except:
            pass
        return s


class Node:
    child_nodes = dict()

    def __init__(self, puzzle: Puzzle, parent_node=None, last_action=None):
        self.puzzle = Puzzle(puzzle.state)
        self.parent_node = parent_node
        self.last_action = last_action
        self.depth = 0 if parent_node is None else parent_node.depth + 1
        self.cost = self.puzzle.calculate_cost()

    def explore(self):
        temp = list()
        actions = self.puzzle.movement_ability()
        try:
            actions.remove(Action.opposite(self.last_action))
        except:
            pass
        for action in actions:
            puzzle = Puzzle(self.puzzle.state)
            puzzle.move(action)
            n = Node(puzzle, self, action)
            temp.append(n)
            self.child_nodes[action.name] = n
        return temp

    def __str__(self):
        string_format = """\n      {state[0]}    {state[1]}    {state[2]}
      {state[3]}    {state[4]}    {state[5]}
      {state[6]}    {state[7]}    {state[8]}\n"""
        node = self
        res = string_format.format(state=node.puzzle.state)
        while node.parent_node is not None:
            node = node.parent_node
            res = string_format.format(state=node.puzzle.state) + res
        return res


class Solver:
    history = list()
    queue = list()

    def __init__(self, state):
        self.queue.append(Node(Puzzle(state)))

    @staticmethod
    def sort_queue(n1: Node, n2: Node):
        if n1.cost == 0:
            return -1
        if n2.cost == 0:
            return 1
        if n1.cost + n1.depth > n2.cost + n2.depth:
            return 1
        if n1.cost + n1.depth < n2.cost + n2.depth:
            return -1
        if n1.cost > n2.cost:
            return 1
        if n1.cost < n2.cost:
            return -1
        if n1.depth > n2.depth:
            return -1
        if n1.depth < n2.depth:
            return 1
        return 0

    def filter(self, nodes: list[Node]):
        temp = nodes.copy()
        for node in nodes:
            for n in self.queue:
                if node.puzzle.state == n.puzzle.state:
                    temp.remove(node)
        return temp

    def solve(self):
        while self.queue[0].cost != 0:
            node = self.queue.pop(0)
            self.history.append(node)
            self.queue.extend(self.filter(node.explore()))
            self.queue.sort(key=cmp_to_key(Solver.sort_queue))
        self.history.append(self.queue[0])