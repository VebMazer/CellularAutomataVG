package vm.emergencevg.domain;

import java.util.ArrayList;

public class ParticleType {

    public String name;
    public int key;
    public ArrayList<Integer> amountsForNew;
    public ArrayList<Integer> amountsToLive;

    public ParticleType(String name, int key, ArrayList<Integer> amountsForNew, ArrayList<Integer> amountsToLive) {
        this.name = name;
        this.key = key;
        this.amountsForNew = amountsForNew;
        this.amountsToLive = amountsToLive;
    }

    public boolean generate(int amount) {
        for (Integer i : amountsForNew) {
            if (i == amount) {
                return true;
            }
        }
        return false;
    }

    public boolean live(int amount) {
        for (Integer i : amountsToLive) {
            if (i == amount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
