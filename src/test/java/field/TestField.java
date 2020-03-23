package field;

import org.junit.Test;
import ru.kalinin.cell.MineStatus;
import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;
import ru.kalinin.field.RectangleField;
import ru.kalinin.rules.LoseIfOpenBomb;
import ru.kalinin.rules.WinIfOpenAllField;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestField {

    /**
     * прооверка открытия смежных ячеек при открытия ячейки,
     * у которой число мин смежных мин равно нулю
     * <p>
     * проверка условия победы при открытии всех ячеек,
     * не содержащих мин
     */
    @Test
    public void whenOpenCellWithZeroNubmMines_checkWinIfOpenAllCells() {

        List<RectangleCell> testField = new ArrayList<>(16);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                testField.add(new RectangleCell(new DecCoord(x, y)));
            }
        }
        testField.get(0).setMineStatus(MineStatus.BOMB);
        testField.get(1).setMineStatus(MineStatus.BOMB);
        testField.get(15).setMineStatus(MineStatus.BOMB);
        testField.get(2).setMineStatus(MineStatus.ONE);
        testField.get(4).setMineStatus(MineStatus.TWO);
        testField.get(5).setMineStatus(MineStatus.TWO);
        testField.get(6).setMineStatus(MineStatus.ONE);
        testField.get(10).setMineStatus(MineStatus.ONE);
        testField.get(11).setMineStatus(MineStatus.ONE);
        testField.get(14).setMineStatus(MineStatus.ONE);

        /*
             0  1  2  3
          0| x  x  1  0
          1| 2  2  1  0
          2| 0  0  1  1
          3| 0  0  1  х
         расположение мин на игроваом поле для тестирования
         х - мина,
         0..2 - число мин в смежных ячейках
         */

        RectangleField testRectField = new RectangleField();
        testRectField.initFieldForTest(testField, 4, 4, 3);
        testRectField.openCell(new DecCoord(0, 3));

        /*
        проверка смены статуса ячеек рядом на OPENED
         */
        assertThat(testField.get(8).getMarkStatus().toString(), is("OPENED"));
        assertThat(testField.get(9).getMarkStatus().toString(), is("OPENED"));
        assertThat(testField.get(12).getMarkStatus().toString(), is("OPENED"));
        assertThat(testField.get(13).getMarkStatus().toString(), is("OPENED"));
        /*
        проверка числа открытых ячеек
        */
        assertThat(testRectField.getOpenedCells(), is(9));
        /*
        открытие ячейки
        */
        testRectField.openCell(new DecCoord(3, 0));

        /*
        проверка числа открытых ячеек
        */
        assertThat(testRectField.getOpenedCells(), is(13));

        /*
        проверка условия победы после открытия всех ячеек
        */
        WinIfOpenAllField winIfOpenAllField = new WinIfOpenAllField();
        boolean isWin = winIfOpenAllField.checkCondition(testRectField);
        assertThat(isWin, is(true));
    }

    /**
     * тест условия проигрыша, если открыта бомба
     */
    @Test
    public void whenTestRuleToLose() {
        List<RectangleCell> testField = new ArrayList<>(4);
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                testField.add(new RectangleCell(new DecCoord(x, y)));
            }
        }
        testField.get(0).setMineStatus(MineStatus.BOMB);
        RectangleField testRectField = new RectangleField();
        testRectField.initFieldForTest(testField, 2, 2, 1);
        testRectField.openCell(new DecCoord(0, 0));

        LoseIfOpenBomb loseIfOpenBomb = new LoseIfOpenBomb();
        boolean isLose = loseIfOpenBomb.checkCondition(testRectField);

        assertThat(isLose, is(true));
    }

    /**
     * проверка установки и снятия маркировки с ячейки
     * проверка открытия маркированной ячейки
     */
    @Test
    public void whenTryToOpenMarkedCell() {
        List<RectangleCell> testField = new ArrayList<>(4);
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                RectangleCell rectangleCell = new RectangleCell(new DecCoord(x, y));
                rectangleCell.setMineStatus(MineStatus.BOMB);
                testField.add(rectangleCell);
            }
        }
        RectangleField testRectField = new RectangleField();
        testRectField.initFieldForTest(testField, 2, 2, 0);

        testRectField.markedCell(new DecCoord(0, 0));
        testRectField.markedCell(new DecCoord(1, 0));
        testRectField.markedCell(new DecCoord(1, 0));

        testRectField.openCell(new DecCoord(0, 0));
        testRectField.openCell(new DecCoord(1, 0));


        assertThat(testField.get(0).getMarkStatus().toString(), is("MARKED"));
        assertThat(testField.get(1).getMarkStatus().toString(), is("OPENED"));
    }
}
