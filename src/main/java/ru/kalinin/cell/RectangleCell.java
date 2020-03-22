package ru.kalinin.cell;

import ru.kalinin.coordinate.DecCoord;

/**
 * используется для хранения ячейки игрового поля
 */
public class RectangleCell {
    private MarkStatus markStatus;
    private MineStatus mineStatus;
    private DecCoord decCoord;

    public RectangleCell(DecCoord theDecCoord) {
        markStatus = MarkStatus.CLOSED;
        mineStatus = MineStatus.ZERO;
        decCoord = theDecCoord;
    }

    public MarkStatus getMarkStatus() {
        return markStatus;
    }

    public void setMarkStatus(MarkStatus markStatus) {
        this.markStatus = markStatus;
    }

    public MineStatus getMineStatus() {
        return mineStatus;
    }

    public void setMineStatus(MineStatus mineStatus) {
        this.mineStatus = mineStatus;
    }

    public DecCoord getDecCoord() {
        return decCoord;
    }

}
