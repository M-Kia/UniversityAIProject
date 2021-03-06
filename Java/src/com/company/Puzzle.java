package com.company;

import java.util.*;

public class Puzzle {
    protected static Map<Integer, Map<String, Integer>> goalState = Map.of(
            0, Map.of("index", 8, "column", 1, "row", 2),
            1, Map.of("index", 0, "column", 0, "row", 0),
            2, Map.of("index", 1, "column", 1, "row", 0),
            3, Map.of("index", 2, "column", 2, "row", 0),
            4, Map.of("index", 3, "column", 0, "row", 1),
            5, Map.of("index", 4, "column", 1, "row", 1),
            6, Map.of("index", 5, "column", 2, "row", 1),
            7, Map.of("index", 6, "column", 0, "row", 2),
            8, Map.of("index", 7, "column", 1, "row", 2)
    );

    public static void setGoalState(int[] goal) {
        Map<Integer, Map<String, Integer>> map = new HashMap<>();
        Map<String, Integer> temp;
        for (int i = 0; i < goal.length; i++) {
            if (goal[i] < 0 || goal[i] > 8 || map.get(goal[i]) != null)
                return;
            temp = new HashMap<>();
            temp.put("index", i);
            temp.put("column", i % 3);
            temp.put("row", Math.floorDiv(i, 3));
            map.put(goal[i], temp);
        }
        goalState = map;
    }

    public int[] state;

    // متد سازنده 1
    public Puzzle(int[] p) {
        this.state = Arrays.copyOf(p, p.length);
    }

    // متد سازنده 2
    public Puzzle(Puzzle p) {
        this(p.state);
    }

    // متد حرکت دادن و جابه جا کردن کاشی صفر با کاشی اطرافش
    public void Move(ActionSource action, int... num) {
        int zeroPlace;
        if (num.length == 0) {
            zeroPlace = findZero();
        } else zeroPlace = num[0];
        int j;
        switch (action) {
            case Up -> j = zeroPlace - 3;
            case Down -> j = zeroPlace + 3;
            case Right -> j = zeroPlace + 1;
            case Left -> j = zeroPlace - 1;
            default -> j = -1;
        }
        int temp = this.state[zeroPlace];
        this.state[zeroPlace] = this.state[j];
        this.state[j] = temp;
    }

    // پیدا کردن اندیس کاشی صفر
    public int findZero() {
        for (int i = 0; i < this.state.length; i++) {
            if (this.state[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // تابع محاسبه حداقل هزینه رسیدن پازل به حالت نهایی
    public int calculateCost() {
        int sum = 0;
        for (int i = 0; i < this.state.length; i++) {
            sum += findMinMove(this.state[i], i);
        }
        return sum;
    }

    // تابع محاسبه هزینه رسیدن کاشی به هحل اصلی اش
    public static int findMinMove(int value, int index) {
        if (goalState.get(value).get("index") == index) return 0;
        int rowIndex = Math.floorDiv(index, 3);
        int temp = goalState.get(value).get("row") - rowIndex;
        int h = Math.abs(temp);
        temp = index + (temp * 3);
        temp = goalState.get(value).get("index") - temp;
        h += Math.abs(temp);
        return h;
    }

    // چک کردن حرکات های موجود برای اندیس مربوطه
    public List<ActionSource> checkMovementAbility(int... num) {
        int zeroPlace;
        if (num.length == 0) {
            zeroPlace = findZero();
        } else zeroPlace = num[0];
        List<ActionSource> result = new ArrayList<>();
        if (Math.floorDiv(zeroPlace, 3) != 0) result.add(ActionSource.Up);
        if (Math.floorDiv(zeroPlace, 3) != 2) result.add(ActionSource.Down);
        if (zeroPlace % 3 != 0) result.add(ActionSource.Left);
        if (zeroPlace % 3 != 2) result.add(ActionSource.Right);
        return result;
    }

    // تابع برای قابل نمایشی کردن حالت
    @Override
    public String toString() {
        return String.format("%d %d %d %n%d %d %d %n%d %d %d%n", this.state[0], this.state[1], this.state[2], this.state[3], this.state[4], this.state[5], this.state[6], this.state[7], this.state[8]);
    }
}
