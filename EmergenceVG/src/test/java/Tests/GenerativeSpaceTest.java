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

public class GenerativeSpaceTest {

    GenerativeSpace space;
    ControlFunctions functions;

    public GenerativeSpaceTest() {
        space = new GenerativeSpace(100, 100);
        functions = space.functions;
        particleTestInitialization();
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

    //Testataan taulukoiden x akselien kokojen samuus.
    @Test
    public void testFields1() {

        assertEquals(space.field.length, space.resultField.length);
    }

    //Testataan taulukoiden y akselien kokojen samuus.
    @Test
    public void testFields2() {

        assertEquals(space.field[0].length, space.resultField[0].length);
    }
    
    public void particleTestInitialization() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(3);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        toLive.add(2);
        toLive.add(3);
        functions.addParticleType("life", forNew, toLive);

        forNew = new ArrayList<Integer>();
        forNew.add(3);
        forNew.add(4);
        forNew.add(5);
        toLive = new ArrayList<Integer>();
        toLive.add(3);
        functions.addParticleType("remenant", forNew, toLive);
    }

    //Testataan syntyykö uusi partikkeli, kun määritellyt ehdot on täytetty.
    @Test
    public void testParticleProcessing1() {
        int[] neighborTypes = new int[8];
        neighborTypes[1] = 1;
        neighborTypes[2] = 1;
        neighborTypes[5] = 1;

        space.particleProcess(5, 5, 0, 3, neighborTypes);
        space.updateField();
        assertEquals(1, space.field[5][5]);
    }

    //Testataan selviääkö partikkeli seuraavaan iteraatioon, kun määritellyt ehdot on täytetty.
    @Test
    public void testParticleProcessing2() {
        space.resultField[5][5] = 2;
        int[] neighborTypes = new int[8];
        neighborTypes[0] = 2;
        neighborTypes[2] = 2;
        neighborTypes[5] = 2;

        space.particleProcess(5, 5, 2, 3, neighborTypes);
        space.updateField();
        assertEquals(2, space.field[5][5]);
    }

    //Testataan Jääkö partikkeli syntymättä, kun määritellyt ehdot on täytetty.
    @Test
    public void testParticleProcessing3() {
        int[] neighborTypes = new int[8];
        neighborTypes[0] = 2;
        neighborTypes[2] = 2;

        space.particleProcess(5, 5, 0, 2, neighborTypes);
        space.updateField();
        assertEquals(0, space.field[5][5]);
    }

    //Testataan katoaako partikkeli, kun määritellyt ehdot on täytetty.
    @Test
    public void testParticleProcessing4() {
        int[] neighborTypes = new int[8];
        neighborTypes[0] = 1;

        space.particleProcess(5, 5, 1, 1, neighborTypes);
        space.updateField();
        assertEquals(0, space.field[5][5]);
    }

}
