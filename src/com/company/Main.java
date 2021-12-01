package com.company;

import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//        int[] temp = new int[]{7, 2, 4, 5, 0, 6, 8, 3, 1};
//        int[] temp = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
//        int[] temp = new int[]{5, 7, 4, 2, 0, 3, 1, 8, 6};
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter First State(like '7 2 4 5 0 6 8 3 1'");
        String input = sc.nextLine();
        int[] temp = makeArray(input.replace(" ", "").toCharArray());
        System.out.println("Please Enter Goal State");
        input = sc.nextLine();
        Puzzle.setGoalState(makeArray(input.replace(" ", "").toCharArray()));
        System.out.println("Finish");
    }

    public static int[] makeArray(char[] arr){
        int[] temp = new int[9];
        for(int i = 0; i < 9; i++){
            temp[i] = Character.getNumericValue(arr[i]);
        }
        return temp;
    }
}

