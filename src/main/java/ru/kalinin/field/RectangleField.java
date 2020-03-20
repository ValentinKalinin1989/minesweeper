package ru.kalinin.field;

import ru.kalinin.cell.Cell;
import ru.kalinin.cell.MarkStatus;
import ru.kalinin.cell.MineStatus;
import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.Coordinate;
import ru.kalinin.coordinate.DecCoord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RectangleField implements Field {

    private List<Cell> field;
    private int openedMines;
    private int allMines;
    private int openedCells;
    private int sizeX;
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
        field = new ArrayList<>(sizeX*sizeY);
        for (int y = 0; y < theSizeX; y++) {
            for (int x = 0; x < theSizeY; x++) {
                field.add(new RectangleCell(new DecCoord(x, y)));
            }
        }
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
            RectangleCell cellForBomb = (RectangleCell) field.get(index);
            if (cellForBomb.getMineStatus().equals(MineStatus.BOMB)) {
                continue;
            }
            cellForBomb.setMineStatus(MineStatus.BOMB);
            int x = index % sizeY;
            int y = index / sizeY;
            List<Cell> aroundCell = getCellAround(new DecCoord(x, y));
            for (Cell cell : aroundCell) {
                MineStatus mineStatus = ((RectangleCell) cell).getMineStatus();
                if (!mineStatus.equals(MineStatus.BOMB)) {
                    ((RectangleCell) cell).setMineStatus(mineStatus.nextCountBomb());
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
    public List<Cell> getCellAround(Coordinate coordinate) {
        List<Cell> aroundCells = new ArrayList<>();
        int x = ((DecCoord) coordinate).getX();
        int y = ((DecCoord) coordinate).getY();
        if (coordInField(x + 1, y)) {
            aroundCells.add(field.get(y * 10 + x + 1));
        }
        if (coordInField(x - 1, y)) {
            aroundCells.add(field.get(y * 10 + x - 1));
        }
        if (coordInField(x, y + 1)) {
            aroundCells.add(field.get((y + 1) * 10 + x));
        }
        if (coordInField(x, y - 1)) {
            aroundCells.add(field.get((y - 1) * 10 + x));
        }
        if (coordInField(x + 1, y + 1)) {
            aroundCells.add(field.get((y + 1) * 10 + x + 1));
        }
        if (coordInField(x + 1, y - 1)) {
            aroundCells.add(field.get((y - 1) * 10 + x + 1));
        }
        if (coordInField(x - 1, y + 1)) {
            aroundCells.add(field.get((y + 1) * 10 + x - 1));
        }
        if (coordInField(x - 1, y - 1)) {
            aroundCells.add(field.get((y - 1) * 10 + x - 1));
        }
        return aroundCells;
    }

    /**
     * @param x координата  Х
     * @param y Координата У
     * @return true если есть ячейка с данными координатами
     */
    public boolean coordInField(int x, int y) {
        boolean result = false;
        if (x < sizeX && x >= 0 && y < sizeY && y >= 0) {
            result = true;
        }
        return result;
    }

    /**
     * открывает ячейку, если она не помечена флагом
     *
     * @param coordinate - координаты ячейки
     * @return - false - если открыта бомба
     */
    @Override
    public boolean openCell(Coordinate coordinate) {
        boolean isOpenFreeCell = true;
        DecCoord decCoord = (DecCoord) coordinate;
        RectangleCell rectangleCell = (RectangleCell) field.get(decCoord.getY() * sizeX + decCoord.getX());
        MarkStatus markStatus = rectangleCell.getMarkStatus();
        if (markStatus.equals(MarkStatus.MARKED) || markStatus.equals(MarkStatus.OPENED)) {
            return isOpenFreeCell;
        }
        rectangleCell.setMarkStatus(MarkStatus.OPENED);
        MineStatus mineStatus = rectangleCell.getMineStatus();
        if (mineStatus.equals(MineStatus.BOMB)) {
            openedMines++;
            rectangleCell.setMineStatus(MineStatus.OPENED_BOMB);
            isOpenFreeCell = false;
        } else if (mineStatus.equals(MineStatus.ZERO)) {
            openCellAroundIfNumbMinZero(coordinate);
        } else {
            openedCells++;
        }
        System.out.println(openedCells);
        return isOpenFreeCell;
    }

    /**
     * отрытие смежных ячеек, если в них нет мин
     *
     * @param coordinate - координаты ячейки
     */
    public void openCellAroundIfNumbMinZero(Coordinate coordinate) {
        System.out.println(openedCells);
        DecCoord decCoord = (DecCoord) coordinate;
        RectangleCell rectangleCell = (RectangleCell) field.get(decCoord.getY() * sizeX + decCoord.getX());
        openedCells++;
        rectangleCell.setMarkStatus(MarkStatus.OPENED);
        if (rectangleCell.getMineStatus().equals(MineStatus.ZERO)) {
            List<Cell> cellList = getCellAround(coordinate);
            for (Cell cell : cellList) {
                RectangleCell rectCell = (RectangleCell) cell;
                if (!rectCell.getMarkStatus().equals(MarkStatus.OPENED) && rectCell.getMineStatus().equals(MineStatus.ZERO)) {
                    openCellAroundIfNumbMinZero(rectCell.getDecCoord());
                } else if (!rectCell.getMarkStatus().equals(MarkStatus.OPENED)){
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
     * @param coordinate - координаты ячйки
     */
    @Override
    public void markedCell(Coordinate coordinate) {
        DecCoord decCoord = (DecCoord) coordinate;
        RectangleCell rectangleCell = (RectangleCell) field.get(decCoord.getY() * sizeX + decCoord.getX());
        if (rectangleCell.getMarkStatus().equals(MarkStatus.CLOSED)) {
            rectangleCell.setMarkStatus(MarkStatus.MARKED);
        } else if (rectangleCell.getMarkStatus().equals(MarkStatus.MARKED)) {
            rectangleCell.setMarkStatus(MarkStatus.CLOSED);
        }
    }

    @Override
    public List<Cell> getField() {
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
