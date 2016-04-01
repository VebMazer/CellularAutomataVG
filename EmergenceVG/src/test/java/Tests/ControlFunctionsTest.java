package Tests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.logic.GenerativeSpace;

public class ControlFunctionsTest {

    GenerativeSpace space;
    ControlFunctions functions;

    public ControlFunctionsTest() {
        space = new GenerativeSpace(100, 100);
        functions = space.functions;
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
    public void testParticleTypeAdding() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(3);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        toLive.add(2);
        toLive.add(3);
        functions.addParticleType("life", forNew, toLive);

        int testInt = space.particleTypes.get(1).amountsToLive.get(1);
        assertEquals(testInt, 3);
    }

}
