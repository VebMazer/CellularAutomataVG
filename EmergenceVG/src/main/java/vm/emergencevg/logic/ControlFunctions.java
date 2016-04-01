package vm.emergencevg.logic;

import java.util.ArrayList;
import vm.emergencevg.domain.ParticleType;

public class ControlFunctions {

    public GenerativeSpace space;

    public ControlFunctions(GenerativeSpace space) {
        this.space = space;
    }

    public void addParticleType(String name, ArrayList<Integer> amountsForNew, ArrayList<Integer> amountsToLive) {
        int key = 0;
        for (int i = 1; i < 100; i++) {
            if (space.particleTypes.get(i) == null) {
                key = i;
                break;
            }
        }
        if (key != 0) {
            space.particleTypes.put(key, new ParticleType(name, key, amountsForNew, amountsToLive));
        }

    }

    public void readParticleTypesFromMemory() {

    }

    public void pause() {
        if (space.running = false) {
            space.running = true;
        } else {
            space.running = false;
        }
    }
}
