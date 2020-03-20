package ru.kalinin.cell;

public enum MineStatus {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    BOMB,
    OPENED_BOMB;
    public MineStatus nextCountBomb() {
        return MineStatus.values()[this.ordinal() + 1];
    }
}
