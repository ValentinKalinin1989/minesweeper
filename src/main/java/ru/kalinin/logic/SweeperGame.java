package ru.kalinin.logic;

import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;
import ru.kalinin.field.Field;
import ru.kalinin.field.RectangleField;
import ru.kalinin.rules.RulesOfGame;

import java.util.List;

public class SweeperGame {
    /**
     * игровое поле
     */
    private Field field;
    /**
     * список правил победы
     */
    private List<RulesOfGame> rulesToWin;
    /**
     * список правил проигрыша
     */
    private List<RulesOfGame> rulesToLose;
    /**
     * статус состояния игры
     */
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
    public GameStatus openCell(DecCoord coordinate) {
        if (gameStatus == GameStatus.PLAYING) {
            field.openCell(coordinate);
        }
        checkRules();
        return gameStatus;
    }

    /**
     * маркировка ячейки
     *
     * @param coordinate координаты ячейки
     */
    public void markedCell(DecCoord coordinate) {
        checkRules();
        if (gameStatus == GameStatus.PLAYING) {
            field.markedCell(coordinate);
        }
    }

    /**
     * проверка условий конца игры (победы или поражения)
     */
    private void checkRules() {
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
    }

    /**
     * получить ячейки игрового поля для отрисовки
     *
     * @return ячейки игрового поля
     */
    public List<RectangleCell> getField() {
        return field.getField();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
