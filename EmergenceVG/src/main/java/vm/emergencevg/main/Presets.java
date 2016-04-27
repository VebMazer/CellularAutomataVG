package vm.emergencevg.main;

import java.util.ArrayList;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * Alkuasetuksia sisältävä luokka.
 */
public class Presets {

    GenerativeSpace space;

    public Presets(GenerativeSpace space) {
        this.space = space;
    }

    /**
     * Valmiita valinnaisia alkuasetuksia tuova metodi.
     */
    public void initialTestSetup() {
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(3);
        displayAttributes.add(1);
        space.functions.processVariablesToParticleType("life, 3, 2 3", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(2);
        displayAttributes.add(1);
        space.functions.processVariablesToParticleType("seed, 2", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(4);
        displayAttributes.add(1);
        space.functions.processVariablesToParticleType("remenant, 3 4 5, 3", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(5);
        displayAttributes.add(1);
        space.functions.processVariablesToParticleType("mobile, 1 3, 2 4", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(6);
        displayAttributes.add(1);
        space.functions.processVariablesToParticleType("forming, 3, 1 2 4", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(1);
        displayAttributes.add(1);
        space.functions.processVariablesToParticleType("filler, 1 2 3 4 5 6", displayAttributes);

        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(2);
        displayAttributes.add(1);
        space.functions.processVariablesToParticleType("replicating, 1 2, 1 2 3 4 5", displayAttributes);
        
        displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(10);
        displayAttributes.add(1);
        space.functions.processVariablesToParticleType("wall,  , 1 2 3 4 5 6 7 8", displayAttributes);
    }
}
