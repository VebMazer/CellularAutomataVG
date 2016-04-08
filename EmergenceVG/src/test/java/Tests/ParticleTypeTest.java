package Tests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vm.emergencevg.domain.ParticleType;

public class ParticleTypeTest {

    ParticleType type;

    public ParticleTypeTest() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        ArrayList<Integer> toLive = new ArrayList<Integer>();

        forNew.add(2);
        forNew.add(5);
        forNew.add(6);
        toLive.add(3);

        type = new ParticleType("life", 2, forNew, toLive);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void generateTest() {
        assertEquals(type.generate(6), true);
    }

    @Test
    public void liveTest() {
        assertEquals(type.live(3), true);
    }
}
