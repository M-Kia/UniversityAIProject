package com.company;

import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please Enter Goal State(like '1 2 3 4 5 6 7 8 0')");
                String input = sc.nextLine();
                Puzzle.setGoalState(makeArray(input.replace(" ", "").toCharArray()));
                System.out.println("Please Enter First State(like '7 2 4 5 0 6 8 3 1')");
                input = sc.nextLine();
                int[] temp = makeArray(input.replace(" ", "").toCharArray());
                Solver solver = new Solver(temp);
                solver.solve();
                System.out.println();
                System.out.println(solver);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Finish");
    }

    public static int[] makeArray(char[] arr)throws Exception{
        if(arr.length != 9) throw new Exception("Wrong Input, try again.");
        int[] temp = new int[9];
        int t;
        for (int i = 0; i < 9; i++) {
        t = Character.getNumericValue(arr[i]);
        if (t > 8 || t < 0) throw new Exception("Wrong Input, try again.");
            temp[i] = t;
        }
        return temp;
    }
}

