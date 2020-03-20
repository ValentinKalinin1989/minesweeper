package ru.kalinin.logic;

import ru.kalinin.cell.Cell;
import ru.kalinin.coordinate.Coordinate;
import ru.kalinin.field.Field;
import ru.kalinin.field.RectangleField;
import ru.kalinin.rules.RulesOfGame;

import java.util.List;

public class SweeperGame {
    private Field field;
    private List<RulesOfGame> rulesToWin;
    private List<RulesOfGame> rulesToLose;
    private GameStatus gameStatus;


    public void startGame(int theSizeX, int theSizeY, int theAllMines, List<RulesOfGame> theRulesToWin, List<RulesOfGame> theRulesToLose) {
        field = new RectangleField();
        field.initField(theSizeX, theSizeY, theAllMines);
        rulesToWin = theRulesToWin;
        rulesToLose = theRulesToLose;
        gameStatus = GameStatus.PLAYING;
    }

    /**
     * проверка условий конца игры при открытии ячейки
     *
     * @param coordinate координаты ячейки
     * @return статус игры
     */
    public GameStatus openCell(Coordinate coordinate) {
        for (RulesOfGame ruleWin : rulesToWin) {
            if (ruleWin.checkCondition(field)) {
                gameStatus = GameStatus.WIN;
            }
        }
        for (RulesOfGame ruleLose : rulesToLose) {
            if (ruleLose.checkCondition(field)) {
                gameStatus = GameStatus.LOSE;
            }
        }
        if (gameStatus == GameStatus.PLAYING) {
            field.openCell(coordinate);
        }
        return gameStatus;
    }

    /**
     * маркировка ячейки
     *
     * @param coordinate координаты ячейки
     */
    public void markedCell(Coordinate coordinate) {
        if (gameStatus == GameStatus.PLAYING) {
            field.markedCell(coordinate);
        }
    }

    /**
     * получить ячейки игрового поля для отрисовки
     *
     * @return ячейки игрового поля
     */
    public List<Cell> getField() {
        return field.getField();
    }
}
