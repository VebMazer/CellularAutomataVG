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

    /**
     * Konstruktori.
     *
     * @param space logiikka-avaruus ja taustalooppi.
     */
    public UtilityFunctions(GenerativeSpace space) {
        this.space = space;
        this.coReRunner = space.coReRunner;
    }

    /**
     * Etsii viimeisenä particleTypes listaan laitetun olion avaimen, joka on
     * siis aina suurin avainten joukossa oleva luku.
     *
     * @return Palauttaa suurimman partikkelityyppimapin avaimen.
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

    /**
     * Tarkistaa onko merkki numero.
     *
     * @param c merkki
     * @return totuusarvo
     */
    public boolean checkIfNumber(char c) {
        return (c >= '0' && c <= '9');
    }

    /**
     * Parseroi merkkijonosta indeksin jälkeen seuraavan numeron ja liittää sen
     * listaan ja palauttaa numeron jälkeen seuraavan indeksin.
     *
     * @param index Indeksi josta merkkijonon läpikäynti alkaa.
     * @param str Merkkijono jota käydään läpi-
     * @param list Lista johon löydetty numero listään.
     * @return Numeroa seuraavan indeksin, tai merkkijonon pituuden.
     */
    public int parseNextNumber(int index, String str, ArrayList<Integer> list) {
        while (index < str.length()) {
            if (space.uFunctions.checkIfNumber(str.charAt(index))) {
                break;
            }
            index++;
        }
        int beginIndex = index;
        while (index < str.length()) {
            index++;
            if (!space.uFunctions.checkIfNumber(str.charAt(index))) {
                break;
            }
        }
        list.add(Integer.parseInt(str.substring(beginIndex, index)));
        return index;
    }

    /**
     * Parseroi merkkijonosta numeron.
     *
     * @param str merkkijono
     * @param index numeron alkuun viittaava indeksi.
     * @return int arvo
     */
    public int parseNumber(String str, int index) {
        int startIndex = index;
        while (index < str.length() && str.charAt(index) != ',' && str.charAt(index) != ')' && str.charAt(index) != ' ') {
            index++;
        }
        return Integer.parseInt(str.substring(startIndex, index));
    }

    /**
     * Luo komentojen lisäys mappiin tämän hetkisen iteraation paikalle uuden
     * ArrayListin komentoja varten ja palauttaa kyseisen iteraation arvon.
     *
     * @return Kyseiseen listaan viittaava avain.
     */
    public int initializeCommandArrayList() {
        int iteration = coReRunner.iterations;
        if (!coReRunner.commandsToBeAdded.containsKey(iteration)) {
            coReRunner.commandsToBeAdded.put(iteration, new ArrayList<String>());
        }
        return iteration;
    }

    /**
     * Lisää komennon komento mappiin.
     *
     * @param command komento tekstimuodossa
     */
    public void addCommand(String command) {
        space.communicator.addOutputCommand(command); //For communicator class..
        coReRunner.commandsToBeAdded.get(initializeCommandArrayList()).add(command);
    }

    /**
     * Lisää preset komennon listaan.
     *
     * @param command preset komento tekstimuodossa
     */
    public void addPreset(String command) {
        space.communicator.addOutputCommand(command);
        coReRunner.presetsToBeAdded.add(command);
    }

    /**
     * Lisää lisätyt komennot komento mappiin.
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
     * Laskee yleisimmän int arvon, joka ei ole nolla ja palauttaa sen avaimen.
     * Palauttaa 0 jos yhtäkään partikkelia ei löydy. Metodi suosii yhtä monen
     * arvon tilanteissa pienintä arvoa(Listan ensimmäinen partikkeli.)
     *
     * @param keys Taulukko, joka sisältää int muuttujia.
     * @return Taulukon yleisin int arvo, joka ei ole nolla, tai nolla jos muita
     * arvoja ei ole.
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
