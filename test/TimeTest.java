import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Αυτή η κλάση ελέγχει την συνάρτηση getSecLeft1 (όμοια με την getSecLeft2).
 */
class TimeTest   {

    @Test
    void TimerTest(){
        GUI gui = new GUI();
        long start = System.currentTimeMillis();
        long temp = start+1000;
        int sec = 4;
        while (System.currentTimeMillis() < start + 5*1000) {
            if (System.currentTimeMillis() > temp) {
                gui.setSecLeft1(sec--);
                temp += 1000;
            }
        }
        assertEquals(1,gui.getSecLeft1());
    }

}
