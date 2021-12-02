from classes import Solver, Puzzle

print("Please Enter Goal State(like '1 2 3 4 5 6 7 8 0')")
temp = [int(i) for i in input().replace(" ", "").split("")]
print("Please Enter First State(like '7 2 4 5 0 6 8 3 1')")
Puzzle.set_goal([int(i) for i in input().replace(" ", "").split("")])
solver = Solver(temp)

solver.solve()

print(solver.queue[0])


