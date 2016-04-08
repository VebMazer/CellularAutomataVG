package vm.emergencevg.logic;

import java.util.ArrayList;
import vm.emergencevg.domain.ParticleType;

public class ControlFunctions {

    public GenerativeSpace space;

    public ControlFunctions(GenerativeSpace space) {
        this.space = space;
    }

    public void processStringToParticleType(String input) {
        String name = "";
        ArrayList<Integer> amountsForNew = new ArrayList<Integer>();
        ArrayList<Integer> amountsToLive = new ArrayList<Integer>();
        int index = 0;
        while (index < input.length() && input.charAt(index) != ',') {
            index++;
        }
        name = input.substring(0, index);
        index += 2;
        index = addIntegersToList(index, input, amountsForNew);
        index += 2;
        addIntegersToList(index, input, amountsToLive);
        addParticleType(name, amountsForNew, amountsToLive);
    }

    public int addIntegersToList(int index, String input, ArrayList<Integer> list) {
        while (index < input.length() && input.charAt(index) != ',') {
            if (checkIfNumber(input.charAt(index))) {
                list.add(Integer.parseInt(input.substring(index, index + 1)));
            }
            index++;
        }
        return index;
    }

    public boolean checkIfNumber(char c) {
        return (c >= '0' && c <= '9');
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

    public void start() {
        space.running = true;
    }

    public void stop() {
        space.running = false;
    }

    public void pause() {
        if (space.running = false) {
            space.running = true;
        } else {
            space.running = false;
        }
    }

    public void clear() {
        space.field = new int[space.xlength][space.ylength];
        space.resultField = new int[space.xlength][space.ylength];
    }
}
