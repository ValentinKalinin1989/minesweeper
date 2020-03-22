package field;

import org.junit.Test;
import ru.kalinin.cell.MarkStatus;
import ru.kalinin.cell.RectangleCell;
import ru.kalinin.coordinate.DecCoord;
import ru.kalinin.field.RectangleField;

import java.util.List;

public class TestField {

    @Test
    public void whenTestPopulateField() {
        RectangleField rectangleField = new RectangleField();
        rectangleField.initField(10, 10, 1);
        List<RectangleCell> cellList = rectangleField.getField();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(cellList.get(x + 10 * y).getMineStatus());
                System.out.print(" ");
            }
            System.out.println();
        }

        rectangleField.openCell(new DecCoord(8, 7));

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(cellList.get(x + 10 * y).getMarkStatus());
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println();

        cellList.get(99).setMarkStatus(MarkStatus.OPENED);
        cellList.get(99).getMineStatus().nextCountBomb();
        List<RectangleCell> arroundList = rectangleField.getCellAround(new DecCoord(9, 0));
        for (RectangleCell cell : arroundList) {
            cell.setMarkStatus(MarkStatus.MARKED);
        }
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(cellList.get(x + 10 * y).getMarkStatus());
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(cellList.get(x + 10 * y).getMineStatus());
                System.out.print(" ");
            }
            System.out.println();
        }

        for (RectangleCell cell : cellList) {
            System.out.print(cell.getDecCoord().getX() + " ");
            System.out.print(cell.getDecCoord().getY() + " ");
            System.out.print(cell.getMarkStatus() + " ");
            System.out.println(cell.getMineStatus());
        }

        System.out.println();

        List<RectangleCell> cellAround = rectangleField.getCellAround(new DecCoord(9, 9));
        for (RectangleCell cell : cellAround) {
            System.out.print(cell.getDecCoord().getX() + " ");
            System.out.print(cell.getDecCoord().getY() + " ");
            System.out.print(cell.getMarkStatus() + " ");
            System.out.println(cell.getMineStatus());
        }
    }

}
