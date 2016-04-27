
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

    //Testataan mostCommonKey metodin toimivuutta.
    //Metodin kuulu palauttaa yleisin kokonaisluku, joka ei ole nolla.
    @Test
    public void testMostCommonKey1() {
        int[] array = {5, 2, 0, 1, 8, 2, 0, 0};
        assertEquals(uFunctions.mostCommonKey(array), 2);
    }

    //Testataan mostCommonKey metodin toimivuutta.
    @Test
    public void testMostCommonKey2() {
        int[] array = {0, 0, 2, 5, 0, 5, 5, 0};
        assertEquals(uFunctions.mostCommonKey(array), 5);
    }
    
    @Test
    public void testMostCommonKey3() {
        int[] array = {0, 0, 0, 0, 1, 0, 0, 0};
        assertEquals(uFunctions.mostCommonKey(array), 1);
    }
    
    @Test
    public void testMostCommonKey4() {
        int[] array = {0, 0, 0, 0, 0, 0, 0, 0};
        assertEquals(uFunctions.mostCommonKey(array), 0);
    }
    
    @Test
    public void testFindLatestKey() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(2);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        cFunctions.addParticleType("ss", forNew, toLive, displayAttributes);
        boolean firstFound = false;
        if(uFunctions.findLatestKey() == 1) firstFound = true;
        forNew = new ArrayList<Integer>();
        forNew.add(1);
        toLive = new ArrayList<Integer>();
        cFunctions.addParticleType("sss", forNew, toLive, displayAttributes);
        boolean secondFound = false;
        if(uFunctions.findLatestKey() == 2) secondFound = true;
        
        assertEquals(true, (firstFound && secondFound));
    }
}
