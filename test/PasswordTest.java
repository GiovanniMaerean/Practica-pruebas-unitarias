
import data.Password;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PasswordTest {

    @Test
    public void nullPasswordTest(){
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Password(null);
        });

        assertEquals("Password cannot be null", exception.getMessage());
    }
}
