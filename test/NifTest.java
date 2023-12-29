import data.Nif;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class NifTest {

    @Test
    public void nullNifTest() {

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Nif(null);
        });

        assertEquals("NIF cannot be null", exception.getMessage());
    }
}
