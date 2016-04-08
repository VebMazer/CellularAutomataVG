package Tests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vm.emergencevg.domain.ParticleType;

import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.logic.GenerativeSpace;

public class ControlFunctionsTest {

    GenerativeSpace space;
    ControlFunctions functions;

    public ControlFunctionsTest() {
        space = new GenerativeSpace(100, 100);
        this.functions = space.functions;
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

    @Test
    public void testParsingIntegersToList() {
        ArrayList<Integer> integers = new ArrayList<Integer>();
        functions.addIntegersToList(0, "5 6, 7,", integers);
        assertEquals(true, (integers.get(0) == 5 && integers.get(1) == 6 && integers.size() == 2));
    }

    @Test
    public void testStringProcessing() {
        functions.processStringToParticleType("name, 3, 2 1");
        ParticleType pType = space.particleTypes.get(1);

        assertEquals(true, (pType.name.equals("name") && pType.amountsForNew.get(0) == 3 && pType.amountsToLive.get(0) == 2 && pType.amountsToLive.get(1) == 1));
    }
}
