package coordinate;

import org.junit.Test;
import ru.kalinin.coordinate.DecCoord;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestDecCoord {
    @Test
    public void whenTestSetterGetterEqaulsMethDecCoord() {
        DecCoord firstCoord = new DecCoord(1, 2);
        DecCoord secondCoord = new DecCoord(1, 7);
        boolean eqaulsIsFalse = firstCoord.equals(secondCoord);
        firstCoord.setX(5);
        secondCoord.setX(5);
        secondCoord.setY(2);
        secondCoord.setY(2);
        boolean eqaulsIsTrue = firstCoord.equals(secondCoord);
        assertThat(secondCoord.getX(), is(5));
        assertThat(secondCoord.getY(), is(2));
        assertThat(eqaulsIsFalse, is(false));
        assertThat(eqaulsIsTrue, is(true));
    }
}
