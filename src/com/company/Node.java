package com.company;

import java.util.*;

public class Node {
    public Node parentNode;
    public ActionSource lastAction;
    public Map<ActionSource, Node> childNodes = new HashMap<>();
    public Puzzle puzzle;
    public int depth;

    public Node(Puzzle p, Node pn, ActionSource la) {
        this.lastAction = la;
        this.parentNode = pn;
        this.puzzle = new Puzzle(p);
        this.depth = (pn != null) ? pn.depth + 1 : 0;
    }

    public Node(Puzzle p) {
        this(p, null, null);
    }

    public List<Node> explore() {
        int zeroPlace = this.puzzle.findZero();
        List<Node> temp = new ArrayList<>();
        Node n;
        List<ActionSource> actions = this.puzzle.checkMovementAbility(zeroPlace);
        if (lastAction != null) actions.remove(ActionSource.getOpposite(lastAction));
        for (ActionSource act : actions) {
            Puzzle p = new Puzzle(this.puzzle);
            p.Move(act, zeroPlace);
            n = new Node(p, this, act);
            childNodes.put(act, n);
            temp.add(n);
        }
        return temp;
    }
}