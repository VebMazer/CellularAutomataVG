package vm.emergencevg.main;

import java.util.ArrayList;
import vm.emergencevg.logic.Environment;

/**
 * Alkuasetuksia sisältävä luokka.
 */
public class Presets {

    Environment environment;

    public Presets(Environment environment) {
        this.environment = environment;
    }

    /**
     * Valmiita valinnaisia alkuasetuksia tuova metodi.
     */
    public void initialTestSetup() {
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(3);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("life, 3, 2 3", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(2);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("seed, 2", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(4);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("remenant, 3 4 5, 3", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(5);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("mobile, 1 3, 2 4", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(6);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("forming, 3, 1 2 4", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(1);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("filler, 1 2 3 4 5 6", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(2);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("replicating, 1 2, 1 2 3 4 5", displayAttributes);
        
        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(10);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("wall,  , 1 2 3 4 5 6 7 8", displayAttributes);
    
        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(8);
        displayAttributes.add(1);
        environment.functions.processVariablesToParticle("gem, 1 2 3 4, 3 4 5", displayAttributes);
    }
}
