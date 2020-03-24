package field;

import org.junit.Test;
import ru.kalinin.cell.MineStatus;
import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;
import ru.kalinin.logic.GameStatus;
import ru.kalinin.logic.SweeperGame;
import ru.kalinin.rules.LoseIfOpenBomb;
import ru.kalinin.rules.RulesOfGame;
import ru.kalinin.rules.WinIfOpenAllField;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestSweeperGame {

    @Test
    public void whenOpenAllCellsAndGetWin() {
        SweeperGame testGame = new SweeperGame();
        List<RulesOfGame> rulesToWin = new ArrayList<>();
        List<RulesOfGame> rulesToLose = new ArrayList<>();
        rulesToWin.add(new WinIfOpenAllField());
        rulesToLose.add(new LoseIfOpenBomb());
        testGame.startGame(4, 4, 0, rulesToWin, rulesToLose);
        int numberOfMine = 0;
        List<RectangleCell> listCells = testGame.getField();
        for (RectangleCell cell : listCells) {
            if (cell.getMineStatus().equals(MineStatus.BOMB)) {
                numberOfMine++;
            }
        }
        testGame.openCell(new DecCoord(2, 2));
        GameStatus resultGame = testGame.getGameStatus();

        assertThat(numberOfMine, is(0));
        assertThat(resultGame.toString(), is("WIN"));
    }

    @Test
    public void whenOpenMineAndGetLose() {
        SweeperGame testGame = new SweeperGame();
        List<RulesOfGame> rulesToWin = new ArrayList<>();
        List<RulesOfGame> rulesToLose = new ArrayList<>();
        rulesToWin.add(new WinIfOpenAllField());
        rulesToLose.add(new LoseIfOpenBomb());
        testGame.startGame(4, 4, 2, rulesToWin, rulesToLose);
        int x = 0;
        int y = 0;
        List<RectangleCell> listCells = testGame.getField();
        for (RectangleCell cell : listCells) {
            if (cell.getMineStatus().equals(MineStatus.BOMB)) {
                x = cell.getDecCoord().getX();
                y = cell.getDecCoord().getY();
                break;
            }
        }
        testGame.openCell(new DecCoord(x, y));
        GameStatus resultGame = testGame.getGameStatus();

        assertThat(resultGame.toString(), is("LOSE"));
    }
}
