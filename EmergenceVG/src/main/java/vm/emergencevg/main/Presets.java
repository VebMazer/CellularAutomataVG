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
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(3);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        toLive.add(2);
        toLive.add(3);
        space.functions.addParticleType("life", forNew, toLive);
        ParticleType p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 3);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(2);
        toLive = new ArrayList<Integer>();
        space.functions.addParticleType("seed", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 2);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(3);
        forNew.add(4);
        forNew.add(5);
        toLive = new ArrayList<Integer>();
        toLive.add(3);
        space.functions.addParticleType("remenant", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 4);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(1);
        forNew.add(3);
        toLive = new ArrayList<Integer>();
        toLive.add(2);
        toLive.add(4);
        space.functions.addParticleType("mobile", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 5);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(3);
        toLive = new ArrayList<Integer>();
        toLive.add(1);
        toLive.add(2);
        toLive.add(4);
        space.functions.addParticleType("forming", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 6);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(1);
        forNew.add(2);
        forNew.add(3);
        forNew.add(4);
        forNew.add(5);
        forNew.add(6);
        toLive = new ArrayList<Integer>();
        space.functions.addParticleType("filler", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 1);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(1);
        forNew.add(2);
        toLive = new ArrayList<Integer>();
        toLive.add(1);
        toLive.add(2);
        toLive.add(3);
        toLive.add(4);
        toLive.add(5);
        space.functions.addParticleType("replicating", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 2);
        p.displayAttributes.set(1, 1);
    }
}
