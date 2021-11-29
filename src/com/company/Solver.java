package com.company;

import java.util.*;

public class Solver {
    // لیست صف اولویت
    protected List<Node> queue = new ArrayList<>();
    // لیست تاریخچه گره های انتخاب شده
    protected List<Node> history = new ArrayList<>();

    // گرفتن تاریخچه
    public List<Node> getHistory() {
        return history;
    }
    // متد سازنده
    public Solver(int[] p) {
        queue.add(new Node(new Puzzle(p)));
    }

    // حل کردن معما که در نهایت حالت نهایی را برمیگرداند.
    public Node solve() {
        boolean flag = true;
        do {
            if (queue.get(0).puzzle.cost == 0) {
                history.add(queue.get(0));
                flag = false;
            } else {
                Node temp = queue.remove(0);
                history.add(temp);
                queue.addAll(temp.explore());
            }
            queue.sort((n1, n2) -> {
                int x1 = n1.puzzle.cost + n1.depth, x2 = n2.puzzle.cost + n2.depth;
                // مرتب کردن بر اساس تابع f
                if (x1 > x2) return 1;
                if (x1 < x2) return -1;
                // در صورت برابر بودن f، بر اساس هزینه گره مرتب می شوند.
                if (n1.puzzle.cost > n2.puzzle.cost) return 1;
                if (n1.puzzle.cost < n2.puzzle.cost) return -1;
                // اگر هنوز هم برابر بودند، بر اساس عمق مرتب میکند و همچنین برابری را چک میکند.
                return Integer.compare(n1.depth, n2.depth);
            });
        } while (flag);
        return queue.get(0);
    }
}
