package com.company;

import java.util.*;

public class Node {
    // گره والد
    public Node parentNode;
    // تغییر حالت اعمال شده بر روی گره والد برای تبدیل به این گره
    public ActionSource lastAction;
    // تغییر حالت هایی که قابل انجام هستند به همراه گره های ایجاد شده توسط اون تغییر حالات
    public Map<ActionSource, Node> childNodes = new HashMap<>();
    // پازل موجود در این گره
    public Puzzle puzzle;
    // عمق گره
    public int depth;
    // هزینه با تابع h2
    public int cost;

    // متد سازنده 1
    public Node(Puzzle p, Node pn, ActionSource la) {
        this.lastAction = la;
        this.parentNode = pn;
        this.puzzle = new Puzzle(p);
        this.depth = (pn != null) ? pn.depth + 1 : 0;
        this.cost = this.puzzle.calculateCost();
    }

    // متد سازنده 2
    public Node(Puzzle p) {
        this(p, null, null);
    }

    // متد تولید کننده فرزندان یک گره
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