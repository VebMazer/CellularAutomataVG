package vm.emergencevg.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Luokka tarjoaa apumetodeita esimerkiksi ohjelman taustaloopin arvojen
 * selvittämiseen.
 */
public class UtilityFunctions {

    GenerativeSpace space;
    public CommandRecordRunner coReRunner;

    public UtilityFunctions(GenerativeSpace space) {
        this.space = space;
        this.coReRunner = space.coReRunner;
    }

    /**
     * Etsii viimeisenä particleTypes listaan laitetun olion avaimen, joka on
     * siis aina suurin avainten joukossa oleva luku.
     */
    public int findLatestKey() {
        int lastKey = 1;
        for (int key : space.particleTypes.keySet()) {
            if (key > lastKey) {
                lastKey = key;
            }
        }
        return lastKey;
    }

    public boolean checkIfNumber(char c) {
        return (c >= '0' && c <= '9');
    }

    public int parseNumber(String str, int index) {
        int startIndex = index;
        while (index < str.length() && str.charAt(index) != ',' && str.charAt(index) != ')' && str.charAt(index) != ' ') {
            index++;
        }
        return Integer.parseInt(str.substring(startIndex, index));
    }

    public int initializeCommandArrayList() {
        int iteration = coReRunner.iterations;
        if (!coReRunner.commandsToBeAdded.containsKey(iteration)) {
            coReRunner.commandsToBeAdded.put(iteration, new ArrayList<String>());
        }
        return iteration;
    }

    public void addCommand(String command) {
        coReRunner.commandsToBeAdded.get(initializeCommandArrayList()).add(command);
    }

    /**
     * Lisää lisätyt komennot komento listaan.
     */
    public void uniteCommandMaps() {
        for (int key : coReRunner.commandsToBeAdded.keySet()) {
            if (coReRunner.commands.containsKey(key)) {
                ArrayList<String> commandList = coReRunner.commands.get(key);
                for (String command : coReRunner.commandsToBeAdded.get(key)) {
                    commandList.add(command);
                }
            } else {
                coReRunner.commands.put(key, coReRunner.commandsToBeAdded.get(key));
            }
        }
        coReRunner.commandsToBeAdded = new HashMap<Integer, ArrayList<String>>();
    }

    /**
     * Yhdistää lisätyt preset komennot preset komentojen listaan.
     */
    public void unitePresetLists() {
        for (String preset : coReRunner.presetsToBeAdded) {
            coReRunner.presets.add(preset);
        }
        coReRunner.presetsToBeAdded = new ArrayList<String>();
    }

    /**
     * Laskee ympäröivistä partikkeleistä yleisimmän ja palauttaa sen avaimen.
     * Palauttaa 0 jos yhtäkään partikkelia ei löydy.
     */
    public int mostCommonKey(int[] keys) {
        Arrays.sort(keys);
        int mostRep = 0;
        int reps = 0;
        int index = 0;
        boolean grew = false;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != 0) {
                if (reps < 1) {
                    reps = 1;
                }
                if (i + 1 < keys.length && keys[i] == keys[i + 1]) {
                    reps++;
                    grew = true;
                } else {
                    grew = false;
                }
                if (reps > mostRep) {
                    mostRep = reps;
                    index = i;
                }
                if (!grew) {
                    reps = 0;
                }
            }
        }
        return keys[index];
    }
}
