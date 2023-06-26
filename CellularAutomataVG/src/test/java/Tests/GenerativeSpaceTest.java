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

public class EnvironmentTest {

    Environment environment;
    ControlFunctions functions;

    public EnvironmentTest() {
        environment = new Environment(100, 100);
        functions = environment.functions;
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

        assertEquals(environment.field.length, environment.resultField.length);
    }

    //Testataan taulukoiden y akselien kokojen samuus.
    @Test
    public void testFields2() {

        assertEquals(environment.field[0].length, environment.resultField[0].length);
    }
    
    public void particleTestInitialization() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(3);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        toLive.add(2);
        toLive.add(3);
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(1);
        displayAttributes.add(1);
        functions.addParticle("life", forNew, toLive, displayAttributes);

        forNew = new ArrayList<Integer>();
        forNew.add(3);
        forNew.add(4);
        forNew.add(5);
        toLive = new ArrayList<Integer>();
        toLive.add(3);
        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(1);
        displayAttributes.add(1);
        functions.addParticle("remenant", forNew, toLive, displayAttributes);
    }

    //Testataan syntyykö uusi partikkeli, kun määritellyt ehdot on täytetty.
    @Test
    public void testParticleProcessing1() {
        int[] neighborTypes = new int[8];
        neighborTypes[1] = 1;
        neighborTypes[2] = 1;
        neighborTypes[5] = 1;

        environment.particleProcess(5, 5, 0, 3, neighborTypes);
        environment.updateField();
        assertEquals(1, environment.field[5][5]);
    }

    //Testataan selviääkö partikkeli seuraavaan iteraatioon, kun määritellyt ehdot on täytetty.
    @Test
    public void testParticleProcessing2() {
        environment.resultField[5][5] = 2;
        int[] neighborTypes = new int[8];
        neighborTypes[0] = 2;
        neighborTypes[2] = 2;
        neighborTypes[5] = 2;

        environment.particleProcess(5, 5, 2, 3, neighborTypes);
        environment.updateField();
        assertEquals(2, environment.field[5][5]);
    }

    //Testataan Jääkö partikkeli syntymättä, kun määritellyt ehdot on täytetty.
    @Test
    public void testParticleProcessing3() {
        int[] neighborTypes = new int[8];
        neighborTypes[0] = 2;
        neighborTypes[2] = 2;

        environment.particleProcess(5, 5, 0, 2, neighborTypes);
        environment.updateField();
        assertEquals(0, environment.field[5][5]);
    }

    //Testataan katoaako partikkeli, kun määritellyt ehdot on täytetty.
    @Test
    public void testParticleProcessing4() {
        int[] neighborTypes = new int[8];
        neighborTypes[0] = 1;

        environment.particleProcess(5, 5, 1, 1, neighborTypes);
        environment.updateField();
        assertEquals(0, environment.field[5][5]);
    }

}
