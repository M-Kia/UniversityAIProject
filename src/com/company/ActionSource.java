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
            case Right -> ac = ActionSource.Left;
            case Left -> ac = ActionSource.Right;
//            چون مقدار پیشفرض همین مقدار است نیازی به نوشتن نداریم. در حقیقت مقدار اولیه تغییر نمیکند.
//            case Down -> ac = ActionSource.Up;
        }
        return ac;
    }
}
