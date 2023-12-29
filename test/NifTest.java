import data.Nif;
import exception.NifFormatException;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class NifTest {

    private final ArrayList<String> wrongNifs = new ArrayList<>() {
        {
            add("A687698");
            add("93B");
            add("67321C");
            add("EUASQ73");
            add("EC787AD");

        }
    };

    @Test
    public void nullNifTest() {

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Nif(null);
        });

        assertEquals("NIF cannot be null", exception.getMessage());
    }

    @Test
    public void wrongNifFormat() {
        for (int i = 0; i < wrongNifs.size(); i++) {
            final int index = i;
            NifFormatException exception = assertThrows(NifFormatException.class, () -> {
                new Nif(wrongNifs.get(index));
            });

            assertEquals("The NIF format it's not correct", exception.getMessage());
        }
    }
}
