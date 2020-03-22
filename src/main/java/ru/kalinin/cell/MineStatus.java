package ru.kalinin.cell;

/**
 * значения наличия мины в ячейки
 * и числа мин в смежных ячейках
 */
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
