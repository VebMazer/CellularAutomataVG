package Tests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vm.emergencevg.domain.Particle;

public class ParticleTest {

    Particle type;

    public ParticleTest() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
    
        forNew.add(2);
        forNew.add(5);
        forNew.add(6);
        toLive.add(3);
        displayAttributes.add(1);
        displayAttributes.add(1);
        
        type = new Particle("something", 2, forNew, toLive, displayAttributes);
        
        
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
    
    @Test
    public void testForFalsePositives() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        forNew.add(6);
        //toLive.add(3);
        Particle particle = new Particle("somethingElse", 3, forNew, toLive, displayAttributes);
        assertEquals(particle.live(3) || particle.generate(7), false);
    }
}
