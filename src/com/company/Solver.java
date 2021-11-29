package com.company;

import jdk.jfr.Label;

import java.util.*;

public class Solver {
    protected List<Node> queue = new ArrayList<>();
    protected List<Node> history = new ArrayList<>();

    // گرفتن تاریخچه
    public List<Node> getHistory() {
        return history;
    }

    // گرفتن حرکات
    public List<Node> getQueue() {
        return queue;
    }

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
                // مرتب کردن بر اساس تابع f
                if ((n1.puzzle.cost + n1.depth) > (n2.puzzle.cost + n2.depth)) return 1;
                if ((n1.puzzle.cost + n1.depth) < (n2.puzzle.cost + n2.depth)) return -1;
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
