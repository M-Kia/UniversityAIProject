package com.company;

import com.company.ActionSource;

import java.util.*;

public class Puzzle {
    protected static final Map<Integer, Map<String, Integer>> defaultPlaces = Map.of(
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

    public int[] state;
    public int cost;

//    // چک کردن برابر بودن حالت های یک پازل
//    public boolean equalsTo(Puzzle p) {
//        return Arrays.equals(this.state, p.state);
//    }

    // متد سازنده 1
    public Puzzle(int[] p) {
        this.state = Arrays.copyOf(p, p.length);
        cost = this.calculateCost();
    }

    // متد سازنده 2
    public Puzzle(Puzzle p) {
        this(p.state);
    }

    // متد حرکت دادن و جابه جا کردن کاشی صفر با کاشی اطرافش
    public void Move(ActionSource action, int... num) {
        int zeroPlace = -1;
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

//    public int missingPuzzlesCount() {
//        int result = 0;
//        for (int i = 0; i < this.state.length; i++) {
//            if (i != defaultPlaces.get(this.state[i]).get("index")) result++;
//        }
//        return result;
//    }

    // تابع محاسبه هزینه رسیدن کاشی به هحل اصلی اش
    public static int findMinMove(int value, int index) {
        if (defaultPlaces.get(value).get("index") == index) return 0;
        int rowIndex = Math.floorDiv(index, 3);
        int temp = defaultPlaces.get(value).get("row") - rowIndex;
        int h = Math.abs(temp);
        temp = index + (temp * 3);
        temp = defaultPlaces.get(value).get("index") - temp;
        h += Math.abs(temp);
        return h;
    }

    // چک کردن حرکات های موجود برای اندیس مربوطه
    public List<ActionSource> checkMovementAbility(int... num) {
        int zeroPlace = -1;
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
