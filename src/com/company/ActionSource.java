package com.company;

public enum ActionSource {
    Up,
    Down,
    Right,
    Left;

    public static ActionSource getOpposite(ActionSource action){
        ActionSource ac = ActionSource.Up;
        switch (action){
            case Up -> ac = ActionSource.Down;
            case Down -> ac = ActionSource.Up;
            case Right -> ac = ActionSource.Left;
            case Left -> ac = ActionSource.Right;
        }
        return ac;
    }
}
