package Tests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vm.emergencevg.logic.Environment;
import vm.emergencevg.logic.MouseController;

public class MouseControllerTest {

    Environment environment;
    MouseController mController;

    public MouseControllerTest() {
        environment = new Environment(100, 100);
        this.mController = environment.mController;
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(1);
        environment.functions.addParticleType("testType", list, list, list);
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

    //Testing that no duplicate spots are added.
    @Test
    public void testSpotAdding() {
        mController.updateSpot(5, 5);
        mController.updateSpot(5, 5);
        assertEquals(true, (mController.spotsPressed.size() == 1));
    }

    @Test
    public void testCasting() {
        mController.updateSpot(5, 5);
        mController.updateSpot(7, 5);
        mController.updateSpot(5, 2);
        mController.updateSpot(1, 5);
        mController.cast();
        assertEquals(true, (environment.resultField[5][5] == 1 && environment.resultField[7][5] == 1 && environment.resultField[5][2] == 1 && environment.resultField[1][5] == 1));
    }
}
