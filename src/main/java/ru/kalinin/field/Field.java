package ru.kalinin.field;

import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;

import java.util.List;

/**
 * интерфейс определяющий основные методы игрового поля
 */
public interface Field {
    void openCell(DecCoord coordinate);

    void markedCell(DecCoord coordinate);

    List<RectangleCell> getField();

    void initField(int theSizeX, int theSizeY, int theAllMines);

    int getOpenedMines();

    int getAllMines();

    int getOpenedCells();

    int getNumberAllCells();
}
