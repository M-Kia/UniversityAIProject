package com.company;

import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//        int[] temp = new int[]{7, 2, 4, 5, 0, 6, 8, 3, 1};
//        int[] temp = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
        int[] temp = new int[]{1, 2, 0, 7, 8, 6, 5, 4, 3};
        Solver solver = new Solver(temp);
//        List<Node> history = solver.getHistory();
        Node node = solver.solve();
        List<Node> history = solver.getHistory();


        String filename = "C:\\Users\\Kia\\Desktop\\statistics";
        if (false) {
            String arrayFormat = "\"%s\": [\n\t\t\t%d, %d, %d, \n\t\t\t%d, %d, %d, \n\t\t\t%d, %d, %d\n\t\t]";
            String sFormat = "\n\t\"%d\": {\n\t\t%s, \n\t\t\"action\": \"%s\", \n\t\t%s\n\t}";
            int[] t1, t2;
            String s1 = "{", st1, st2;
            for (int i = 0; i < history.size(); i++) {
                if (i != 0) s1 += ",";
                t1 = (history.get(i).parentNode != null) ? history.get(i).parentNode.puzzle.state : null;
                t2 = history.get(i).puzzle.state;
                if (t1 != null)
                    st1 = String.format(arrayFormat, "parentState", t1[0], t1[1], t1[2], t1[3], t1[4], t1[5], t1[6], t1[7], t1[8]);
                else
                    st1 = "\"parentState\": [\n\t\t\t\"N\",\n\t\t\t   \"U\",\n\t\t\t      \"LL\"\n\t\t]";
                st2 = String.format(arrayFormat, "state",t2[0], t2[1], t2[2], t2[3], t2[4], t2[5], t2[6], t2[7], t2[8]);
                s1 += String.format(sFormat, i, st1, history.get(i).lastAction, st2);
            }
            s1 += "\n}";
            try {
                FileWriter file = new FileWriter(filename + ".json");
                file.write(s1);
                file.close();
                System.out.println("Saved Successfully!!");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (false) {
            String s = makeStringJson(history.get(0));
            try {
                FileWriter file = new FileWriter(filename + "1.json");
                file.write(s);
                file.close();
                System.out.println("Saved Successfully!!");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (false) {
            String s = makeStringTxt(history.get(0));
            try {
                FileWriter file = new FileWriter(filename + ".txt");
                file.write(s);
                file.close();
                System.out.println("Saved Successfully!!");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        System.out.println("Finish");
    }

    public static String makeStringJson(Node n){

        String st = String.format("{\"state\": \"%d %d %d %d %d %d %d %d %d\", \"lastAction\" : \"%s\", \"costs\": %d, \"children\": [",
                n.puzzle.state[0], n.puzzle.state[1], n.puzzle.state[2], n.puzzle.state[3], n.puzzle.state[4],
                n.puzzle.state[5], n.puzzle.state[6], n.puzzle.state[7], n.puzzle.state[8],n.lastAction, n.puzzle.cost);
        ;
        int i = 1;
        for (Node node : n.childNodes.values()){
            st += makeStringJson(node);
            if (i < n.childNodes.size()) st+= ",";
            i++;
        }
        st += "]}\n";
        return st;
    }

    public static String makeStringTxt(Node n, int ...t){
        int tab = 0;
        if (t.length > 0) tab = t[0];
        String tt = "";
        for (int i = 0; i < tab; i++) tt += "\t";

        String st = String.format("%s%d %d %d %d %d %d %d %d %d Costs: %d\n", tt,
                n.puzzle.state[0], n.puzzle.state[1], n.puzzle.state[2], n.puzzle.state[3], n.puzzle.state[4],
                n.puzzle.state[5], n.puzzle.state[6], n.puzzle.state[7], n.puzzle.state[8], n.puzzle.cost);
        ;
        for (Node node : n.childNodes.values()){
            st += makeStringTxt(node, tab+1);
        }
        return st;
    }
}

