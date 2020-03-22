package ru.kalinin.field;

import ru.kalinin.cell.MarkStatus;
import ru.kalinin.cell.MineStatus;
import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * класс реализующий прямоугольное игровое поле
 */
public class RectangleField implements Field {

    /**
     * список ячеек игрового поля
     */
    private List<RectangleCell> field;
    /**
     * число открытых мин
     */
    private int openedMines;
    /**
     * число всех мин
     */
    private int allMines;
    /**
     * число открытых ячеек
     */
    private int openedCells;
    /**
     * длина поля (размер поля по оси Х)
     */
    private int sizeX;
    /**
     * высота игрового поля (размер поля по оси У)
     */
    private int sizeY;

    /**
     * создание игрового поля
     *
     * @param theSizeX    - длина по оси Х
     * @param theSizeY    - длина по оси У
     * @param theAllMines - число мин
     */
    @Override
    public void initField(int theSizeX, int theSizeY, int theAllMines) {
        openedMines = 0;
        allMines = theAllMines;
        openedCells = 0;
        sizeX = theSizeX;
        sizeY = theSizeY;
        List<RectangleCell> newfield = new ArrayList<>(sizeX * sizeY);
        for (int y = 0; y < theSizeY; y++) {
            for (int x = 0; x < theSizeX; x++) {
                newfield.add(new RectangleCell(new DecCoord(x, y)));
            }
        }
        field = newfield;
        plantedMines(allMines);
    }

    /**
     * расстановка мин
     *
     * @param numberOfMines - число мин
     */
    private void plantedMines(int numberOfMines) {
        Random random = new Random();
        int numbersOfCell = sizeX * sizeY - 1;
        while (numberOfMines > 0) {
            int index = random.nextInt(numbersOfCell);
            RectangleCell cellForBomb = field.get(index);
            if (cellForBomb.getMineStatus().equals(MineStatus.BOMB)) {
                continue;
            }
            cellForBomb.setMineStatus(MineStatus.BOMB);
            int x = index % sizeX;
            int y = index / sizeX;
            List<RectangleCell> aroundCell = getCellAround(new DecCoord(x, y));
            for (RectangleCell cell : aroundCell) {
                MineStatus mineStatus = cell.getMineStatus();
                if (!mineStatus.equals(MineStatus.BOMB)) {
                    cell.setMineStatus(mineStatus.nextCountBomb());
                }
            }
            numberOfMines--;
        }
    }

    /**
     * получение списка смежных ячеек
     *
     * @param coordinate координаты ячейки для которой необходимо провести поиск
     * @return список ячеек
     */
    public List<RectangleCell> getCellAround(DecCoord coordinate) {
        List<RectangleCell> aroundCells = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (coordInField(x + 1, y)) {
            aroundCells.add(field.get(y * sizeX + x + 1));
        }
        if (coordInField(x - 1, y)) {
            aroundCells.add(field.get(y * sizeX + x - 1));
        }
        if (coordInField(x, y + 1)) {
            aroundCells.add(field.get((y + 1) * sizeX + x));
        }
        if (coordInField(x, y - 1)) {
            aroundCells.add(field.get((y - 1) * sizeX + x));
        }
        if (coordInField(x + 1, y + 1)) {
            aroundCells.add(field.get((y + 1) * sizeX + x + 1));
        }
        if (coordInField(x + 1, y - 1)) {
            aroundCells.add(field.get((y - 1) * sizeX + x + 1));
        }
        if (coordInField(x - 1, y + 1)) {
            aroundCells.add(field.get((y + 1) * sizeX + x - 1));
        }
        if (coordInField(x - 1, y - 1)) {
            aroundCells.add(field.get((y - 1) * sizeX + x - 1));
        }
        return aroundCells;
    }

    /**
     * проверяет принадлежат ли данные координаты,
     * созданному игровому полю
     *
     * @param x координата  Х
     * @param y Координата У
     * @return true если есть ячейка с данными координатами
     */
    private boolean coordInField(int x, int y) {
        boolean result = false;
        if (x < sizeX && x >= 0 && y < sizeY && y >= 0) {
            result = true;
        }
        return result;
    }

    /**
     * открывает ячейку, если она не помечена флагом
     *
     * @param decCoord - координаты ячейки
     */
    @Override
    public void openCell(DecCoord decCoord) {
        RectangleCell rectangleCell = field.get(decCoord.getY() * sizeX + decCoord.getX());
        MarkStatus markStatus = rectangleCell.getMarkStatus();
        if (markStatus.equals(MarkStatus.MARKED) || markStatus.equals(MarkStatus.OPENED)) {
            return;
        }
        rectangleCell.setMarkStatus(MarkStatus.OPENED);
        MineStatus mineStatus = rectangleCell.getMineStatus();
        if (mineStatus.equals(MineStatus.BOMB)) {
            openedMines++;
            rectangleCell.setMineStatus(MineStatus.OPENED_BOMB);
        } else if (mineStatus.equals(MineStatus.ZERO)) {
            openCellAroundIfNumbMinZero(decCoord);
        } else {
            openedCells++;
        }
        System.out.println(openedCells);
    }

    /**
     * отрытие смежных ячеек, если в них нет мин
     *
     * @param decCoord - координаты ячейки
     */
    private void openCellAroundIfNumbMinZero(DecCoord decCoord) {
        RectangleCell rectangleCell = field.get(decCoord.getY() * sizeX + decCoord.getX());
        openedCells++;
        rectangleCell.setMarkStatus(MarkStatus.OPENED);
        if (rectangleCell.getMineStatus().equals(MineStatus.ZERO)) {
            List<RectangleCell> cellList = getCellAround(decCoord);
            for (RectangleCell rectCell : cellList) {
                if (!rectCell.getMarkStatus().equals(MarkStatus.OPENED) && rectCell.getMineStatus().equals(MineStatus.ZERO)) {
                    openCellAroundIfNumbMinZero(rectCell.getDecCoord());
                } else if (!rectCell.getMarkStatus().equals(MarkStatus.OPENED)) {
                    openedCells++;
                    System.out.println(openedCells);
                }
                rectCell.setMarkStatus(MarkStatus.OPENED);
            }
            cellList.clear();
        }
        System.out.println(openedCells);
    }

    /**
     * маркировка ячейки,
     * или снятие маркировки, если ячейка помечена
     *
     * @param decCoord - координаты ячйки
     */
    @Override
    public void markedCell(DecCoord decCoord) {
        RectangleCell rectangleCell = field.get(decCoord.getY() * sizeX + decCoord.getX());
        if (rectangleCell.getMarkStatus().equals(MarkStatus.CLOSED)) {
            rectangleCell.setMarkStatus(MarkStatus.MARKED);
        } else if (rectangleCell.getMarkStatus().equals(MarkStatus.MARKED)) {
            rectangleCell.setMarkStatus(MarkStatus.CLOSED);
        }
    }

    @Override
    public List<RectangleCell> getField() {
        return field;
    }

    @Override
    public int getOpenedMines() {
        return openedMines;
    }

    @Override
    public int getAllMines() {
        return allMines;
    }

    @Override
    public int getOpenedCells() {
        return openedCells;
    }

    @Override
    public int getNumberAllCells() {
        return sizeX * sizeY;
    }
}
