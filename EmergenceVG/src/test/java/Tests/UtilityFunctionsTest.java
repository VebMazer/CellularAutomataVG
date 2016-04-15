
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
import vm.emergencevg.logic.UtilityFunctions;

public class UtilityFunctionsTest {
    GenerativeSpace space;
    ControlFunctions cFunctions; 
    UtilityFunctions uFunctions;
    
    public UtilityFunctionsTest() {
        space = new GenerativeSpace(100, 100);
        cFunctions = space.functions;
        uFunctions = space.uFunctions;
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
    public void testFindLatestKey() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(2);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        cFunctions.addParticleType("ss", forNew, toLive);
        boolean firstFound = false;
        if(uFunctions.findLatestKey() == 1) firstFound = true;
        forNew = new ArrayList<Integer>();
        forNew.add(1);
        toLive = new ArrayList<Integer>();
        cFunctions.addParticleType("sss", forNew, toLive);
        boolean secondFound = false;
        if(uFunctions.findLatestKey() == 2) secondFound = true;
        
        assertEquals(true, (firstFound && secondFound));
    }
}
