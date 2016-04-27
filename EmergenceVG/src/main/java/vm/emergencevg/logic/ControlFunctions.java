package vm.emergencevg.logic;

import java.util.ArrayList;
import java.util.HashMap;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.ui.Updatable;

/**
 * Luokka tarjoaa metodeita joita käytetään, kun halutaan määrittää ohjelman
 * taustaloopin toimintaa, tai syöttää sille dataa.
 */
public class ControlFunctions {

    public GenerativeSpace space;
    public CommandRecordRunner coReRunner;
    public UtilityFunctions uFunctions;
    public FileIO fileIo;
    public Updatable scaleUpdater;
    public int scale;

    public ControlFunctions(GenerativeSpace space) {
        this.space = space;
        this.coReRunner = space.coReRunner;
        this.uFunctions = space.uFunctions;
        this.fileIo = new FileIO(space);
        scale = 5;
    }

    /**
     * Metodi muuntaa käyttöliittymältä saatuja String olioita. ParticleType
     * olion haluamaan malliin.
     */
    public void processVariablesToParticleType(String input, ArrayList<Integer> displayAttributes) {
        uFunctions.addPreset("l(" + input + ",," + displayAttributes.get(0) + " " + displayAttributes.get(1) + ")");
        String name = "";
        ArrayList<Integer> amountsForNew = new ArrayList<Integer>();
        ArrayList<Integer> amountsToLive = new ArrayList<Integer>();
        int index = 0;
        while (index < input.length() && input.charAt(index) != ',') {
            index++;
        }
        name = input.substring(0, index);
        index++;
        index = addIntegersToList(index, input, amountsForNew);
        index++;
        addIntegersToList(index, input, amountsToLive);
        addParticleType(name, amountsForNew, amountsToLive, displayAttributes);
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
    public void addParticleType(String name, ArrayList<Integer> amountsForNew, ArrayList<Integer> amountsToLive, ArrayList<Integer> displayAttributes) {
        int key = 0;
        for (int i = 1; i < 100; i++) {
            if (space.particleTypes.get(i) == null) {
                key = i;
                break;
            }
        }
        if (key != 0) {
            space.particleTypes.put(key, new ParticleType(name, key, amountsForNew, amountsToLive, displayAttributes));
        }
    }

    public void resetFieldCommand(int x, int y) {
        resetField(x, y);
        uFunctions.addCommand("fieldSize(" + space.xlength + "," + space.ylength + ")");
    }
    
    public void resetField(int x, int y) {
        boolean wasRunning = space.running;
        if (wasRunning) {
            stop();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println("Slowing thread did not succeed...");
            }
        }
        space.xlength = x;
        space.ylength = y;
        space.field = new int[x][y];
        space.resultField = new int[x][y];
        if (wasRunning) {
            start();
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
        coReRunner.reInitializePresets();
    }

    public void clearRecord() {
        stop();
        coReRunner.reInitializeCommands();
        coReRunner.iterations = 0;
        coReRunner.uiIterationDisplayer.update();
    }
    
    public void setScaleCommand(int scale) {
        setScale(scale);
        uFunctions.addCommand("scale("+ scale +")");
    }
    
    public void setScale(int scale) {
        this.scale = scale;
        scaleUpdater.update();
    }

    public void save(String filename) {
        stop();
//        boolean found = false;
//        for (String preset : coReRunner.presets) {
//            if(preset.length() > 9 && preset.substring(0, 10).equals("fieldSize(")) {
//                found = true;
//                break;
//            }
//        }
//        if (!found) {
//            coReRunner.presets.add("fieldSize(" + space.xlength + "," + space.ylength + ")");
//        }
        fileIo.save(filename);
    }

    public void loadPresentation(String filename) {
        clearCommand();
        clearRecord();
        clearParticleTypes();
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

    public void setSpeedCommand(String speed) {
        try {
            setSpeed(speed);
            uFunctions.addCommand("speed(" + speed + ")");
        } catch (Exception e) {
            System.out.println("Bad input!!");
        }
    }
    
    public void setSpeed(String speed) {
        space.speedModifier = Double.parseDouble(speed);
    }

    /**
     * Tyhjentää logiikka-avaruuden partikkeleista.
     */
    public void clearCommand() {
        clear();
        uFunctions.addCommand("clear");
    }
    
    public void clear() {
        space.field = new int[space.xlength][space.ylength];
        space.resultField = new int[space.xlength][space.ylength];
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
    
    public void setScaleUpdater(Updatable updatable) {
        scaleUpdater = updatable;
    }
}
