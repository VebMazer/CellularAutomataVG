package Tests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.logic.Environment;
import vm.emergencevg.logic.Particle;

public class ControlFunctionsTest {

    Environment environment;
    ControlFunctions functions;

    public ControlFunctionsTest() {
        environment = new Environment(100, 100);
        this.functions = environment.functions;
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
    public void testParticleAdding() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(3);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        toLive.add(2);
        toLive.add(3);
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(1);
        displayAttributes.add(1);
        functions.addParticle("life", forNew, toLive, displayAttributes);

        int testInt = environment.particles.get(1).amountsToLive.get(1);
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
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(1);
        displayAttributes.add(1);
        functions.processVariablesToParticle("name, 3, 2 1,,", displayAttributes);
        Particle pType = environment.particles.get(1);

        assertEquals(true, (pType.name.equals("name") && pType.amountsForNew.get(0) == 3 && pType.amountsToLive.get(0) == 2 && pType.amountsToLive.get(1) == 1));
    }
    
    @Test
    public void testParticlePlacement() {
        functions.placeParticle(3, 6, 7);
        assertEquals(environment.field[6][7], environment.resultField[6][7]);
    }
}
