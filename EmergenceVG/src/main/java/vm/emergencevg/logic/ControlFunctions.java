package vm.emergencevg.logic;

import java.util.ArrayList;
import vm.emergencevg.domain.ParticleType;

/**
 * Luokka tarjoaa metodeita joita käytetään, kun halutaan määrittää ohjelman
 * taustaloopin toimintaa, tai syöttää sille dataa.
 */
public class ControlFunctions {

    public GenerativeSpace space;

    public ControlFunctions(GenerativeSpace space) {
        this.space = space;
    }

    /**
     * Metodi muuntaa käyttöliittymältä saatuja String olioita. ParticleType
     * olion haluamaan malliin.
     */
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

    /**
     * processStringToList metodin apumetodi, joka etsii halutusta String olion
     * indexistä lähtien kaikki numerot ',' chariin, tai string olion viimeiseen
     * indexiin asti.
     */
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

    /**
     * Etsii ensimmäisen käyttämättömän avaimen logiikka-avaruuden particleTypes
     * HashMapistä ja lisää uuden particleTypes olion samaan mappiin
     * löydettäväksi tällä avaimella.
     */
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

    /**
     * Tyhjentää logiikka-avaruuden partikkeleista.
     */
    public void clear() {
        space.field = new int[space.xlength][space.ylength];
        space.resultField = new int[space.xlength][space.ylength];
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
        addParticleType("life", forNew, toLive);
        ParticleType p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 3);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(2);
        toLive = new ArrayList<Integer>();
        addParticleType("seed", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 2);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(3);
        forNew.add(4);
        forNew.add(5);
        toLive = new ArrayList<Integer>();
        toLive.add(3);
        addParticleType("remenant", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 4);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(1);
        forNew.add(3);
        toLive = new ArrayList<Integer>();
        toLive.add(2);
        toLive.add(4);
        addParticleType("mobile", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 5);
        p.displayAttributes.set(1, 1);

        forNew = new ArrayList<Integer>();
        forNew.add(3);
        toLive = new ArrayList<Integer>();
        toLive.add(1);
        toLive.add(2);
        toLive.add(4);
        addParticleType("forming", forNew, toLive);
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
        addParticleType("filler", forNew, toLive);
        p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, 1);
        p.displayAttributes.set(1, 1);
    }
}
