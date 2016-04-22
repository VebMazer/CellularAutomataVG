package vm.emergencevg.logic;

import java.util.ArrayList;
import java.util.HashMap;
import vm.emergencevg.domain.ParticleType;

/**
 * Luokka tarjoaa metodeita joita käytetään, kun halutaan määrittää ohjelman
 * taustaloopin toimintaa, tai syöttää sille dataa.
 */
public class ControlFunctions {

    public GenerativeSpace space;
    public CommandRecordRunner coReRunner;
    public UtilityFunctions uFunctions;
    public FileIO fileIo;

    public ControlFunctions(GenerativeSpace space) {
        this.space = space;
        this.coReRunner = space.coReRunner;
        this.uFunctions = space.uFunctions;
        this.fileIo = new FileIO(space);
    }

    /**
     * Metodi muuntaa käyttöliittymältä saatuja String olioita. ParticleType
     * olion haluamaan malliin.
     */
    public void processStringToParticleType(String input) {
        coReRunner.presetsToBeAdded.add("l(" + input + ")");
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
            if (uFunctions.checkIfNumber(input.charAt(index))) {
                list.add(Integer.parseInt(input.substring(index, index + 1)));
            }
            index++;
        }
        return index;
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

    public void setIterations(String iterations) {
        try {
            coReRunner.iterations = Integer.parseInt(iterations);
        } catch (Exception e) {
            System.out.println("Bad input to iterations field!");
        }
    }

    public void clearParticleTypes() {
        stop();
        space.particleTypes = new HashMap<Integer, ParticleType>();
        coReRunner.presets = new ArrayList<String>();
    }

    public void clearRecord() {
        stop();
        coReRunner.commands = new HashMap<Integer, ArrayList<String>>();
        coReRunner.iterations = 0;
        coReRunner.uiIterationDisplayer.update();
    }

    public void save(String filename) {
        fileIo.save(filename);
    }

    public void loadPresentation(String filename) {
        clearRecord();
        fileIo.load(filename);
        coReRunner.runPresets();
    }

    public void addPresets(String filename) {
        fileIo.addPresets(filename);
        coReRunner.runPresets();
    }

    public void start() {
        space.running = true;
    }

    public void stop() {
        space.running = false;
        uFunctions.uniteCommandMaps();
        uFunctions.unitePresetLists();
    }

    public void pause() {
        if (space.running = false) {
            start();
        } else {
            stop();
        }
    }

    public void setSpeed(String speed) {
        try {
            space.speedModifier = Double.parseDouble(speed);
            uFunctions.addCommand("speed(" + speed + ")");
        } catch (Exception e) {
            System.out.println("Bad input!!");
        }
    }

    /**
     * Tyhjentää logiikka-avaruuden partikkeleista.
     */
    public void clear() {
        space.field = new int[space.xlength][space.ylength];
        space.resultField = new int[space.xlength][space.ylength];
        uFunctions.addCommand("clear");
    }

    /**
     * Asettaa uuden partikkelin.
     */
    public void placeParticle(int pKey, int i, int j) {
        if (i >= 0 && j >= 0 && i < space.xlength && j < space.ylength) {
            if (space.field[i][j] != 0) {
                space.field[i][j] = 0;
                space.resultField[i][j] = 0;
            } else {
                space.field[i][j] = pKey;
                space.resultField[i][j] = pKey;
            }
        }
    }
}
