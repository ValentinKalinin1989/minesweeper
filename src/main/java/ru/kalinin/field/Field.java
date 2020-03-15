package ru.kalinin.field;

import ru.kalinin.cell.Cell;
import ru.kalinin.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;

public interface Field {
    List<Cell> field = new ArrayList<Cell>();
    int openedMines = 0;
    int allMines = 0;
    int openedCells = 0;
    void plantedMines(int numberOfMines);
    boolean openCell(Coordinate coordinate);
    void markedCell(Coordinate coordinate);
    List<Cell> getField();
    int getOpenedMines();
    int getAllMines();
    int getOpenedCells();
}
