import junit.framework.TestCase;

import org.junit.jupiter.api.Test;

/**
 * Αυτή η κλάση ελέγχει την συνάρτηση addGamePoints.
 */
class PlayerTest extends TestCase {


    @Test
    void  TestPlayerPoints() {
        Player player = new Player();
        player.addGamePoints(1000);
        assertEquals(1000.0,player.getGamePoints());
        player.addGamePoints(132.65);
        assertEquals(1132.65,player.getGamePoints());
    }

}