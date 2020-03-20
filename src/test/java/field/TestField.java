package field;

import org.junit.Test;
import ru.kalinin.cell.Cell;
import ru.kalinin.cell.MarkStatus;
import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;
import ru.kalinin.field.RectangleField;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestField {

    @Test
    public void whenTestPopulateField() {
        RectangleField rectangleField = new RectangleField();
        rectangleField.initField(10, 10, 1);
        List<Cell> cellList = rectangleField.getField();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(((RectangleCell)cellList.get(x + 10 * y)).getMineStatus());
                System.out.print(" ");
            }
            System.out.println();
        }

        rectangleField.openCell(new DecCoord(8, 7));

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(((RectangleCell)cellList.get(x + 10 * y)).getMarkStatus());
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println();

        ((RectangleCell) cellList.get(99)).setMarkStatus(MarkStatus.OPENED);
        ((RectangleCell) cellList.get(99)).getMineStatus().nextCountBomb();
        List<Cell> arroundList = rectangleField.getCellAround(new DecCoord(9, 0));
        for (Cell cell: arroundList) {
            ((RectangleCell) cell).setMarkStatus(MarkStatus.MARKED);
        }
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(((RectangleCell)cellList.get(x + 10 * y)).getMarkStatus());
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(((RectangleCell)cellList.get(x + 10 * y)).getMineStatus());
                System.out.print(" ");
            }
            System.out.println();
        }

    }

}
