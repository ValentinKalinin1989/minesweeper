package ru.kalinin.field;

import ru.kalinin.cell.Cell;
import ru.kalinin.coordinate.Coordinate;

import java.util.List;

public interface Field {
    boolean openCell(Coordinate coordinate);
    void markedCell(Coordinate coordinate);
    List<Cell> getField();
    void initField(int theSizeX, int theSizeY, int theAllMines);
    int getOpenedMines();
    int getAllMines();
    int getOpenedCells();
    int getNumberAllCells();
}
