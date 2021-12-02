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

    public List<Node> filterChildren(List<Node> children) {
        List<Node> temp = new ArrayList<>(children);
        List<Node> t = new ArrayList<>(queue);
        for (Node node : children) {
            for (Node n : t) {
                if (Arrays.equals(node.puzzle.state, n.puzzle.state)) {
                    try {
                        temp.remove(node);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
        return temp;
    }

    // حل کردن معما که در نهایت حالت نهایی را برمیگرداند.
    public void solve() {
        while (queue.get(0).cost != 0) {
            Node temp = queue.remove(0);
            history.add(temp);
            queue.addAll(filterChildren(temp.explore()));
            queue.sort((n1, n2) -> {
                if (n1.cost == 0) return -1;
                if (n2.cost == 0) return 1;
                int x1 = n1.cost + n1.depth, x2 = n2.cost + n2.depth;
                // مرتب کردن بر اساس تابع f
                if (x1 > x2) return 1;
                if (x1 < x2) return -1;
                // در صورت برابر بودن f، بر اساس هزینه گره مرتب می شوند.
                if (n1.cost > n2.cost) return 1;
                if (n1.cost < n2.cost) return -1;
                // اگر هنوز هم برابر بودند، بر اساس عمق مرتب میکند و همچنین برابری را چک میکند.
                return Integer.compare(n1.depth, n2.depth);
            });
        }
        history.add(queue.get(0));
    }

    @Override
    public String toString() {
        String arrayFormat = "\t\t%d\t%d\t%d\n\t\t%d\t%d\t%d\n\t\t%d\t%d\t%d\n\n";
        Node node = queue.get(0);
        int[] state = node.puzzle.state;
        String res = String.format(arrayFormat, state[0], state[1], state[2], state[3], state[4], state[5], state[6],
                state[7], state[8]);
        while (node.parentNode != null) {
            node = node.parentNode;
            state = node.puzzle.state;
            res = String.format(arrayFormat, state[0], state[1], state[2], state[3], state[4], state[5], state[6],
                    state[7], state[8]) + res;
        }
        return res;
    }
}
