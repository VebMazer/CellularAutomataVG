package vm.emergencevg.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Vastuussa käyttäjän luomien esitysten tallentamisesta ja lataamisesta.
 */
public class FileIO {

    ArrayList<String> presets;
    HashMap<Integer, ArrayList<String>> commands;
    Environment environment;
    public CommandRecordRunner coReRunner;

    /**
     * Konstruktoi.
     *
     * @param environment logiikka-avaruus ja taustalooppi
     */
    public FileIO(Environment environment) {
        this.environment = environment;
        this.coReRunner = environment.coReRunner;
        presets = new ArrayList<String>();
        commands = new HashMap<Integer, ArrayList<String>>();
    }

    /**
     * Tallentaa ohjelmassa luodut partikkelityypit ja iteraatio aikajanalla
     * olevat komennot tiedostoon(presets komento lista ja commands komento
     * mappi).
     *
     * @param filename Tallennettavan tiedoston nimi.
     */
    public void save(String filename) {

        this.commands = coReRunner.commands;
        this.presets = coReRunner.presets;
        try {
            writeFile(filename);
        } catch (IOException e) {
            System.out.println("a problem occurred while saving file...");
        }
    }

    /**
     * Kirjoittaa komennot tiedostoon. Ensimmäiselle riville kirjoitetaan preset
     * komennot ja sen jälkeen kirjoitetaan jokaiselle komentoja sisältävälle
     * iteraatiolle oma rivi, joka sisältää sen komennot.
     *
     * @param filename Kirjoitettavan tiedoston nimi.
     * @throws IOException Virhe jos kirjoittaminen epäonnistuu.
     */
    public void writeFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(buildLine("presets", presets));
        for (int key : commands.keySet()) {
            fw.write(buildLine("\nit" + key, commands.get(key)));
        }
        fw.close();
    }

    /**
     * Rakentaa tiedostoon kirjoitettavan rivin listasta komentoja asettamalla
     * ';' merkin jokaisen komennon väliin.
     *
     * @param line Rivin alku(presets, tai it"key").
     * @param commandList Komento lista.
     * @return Kirjoitettava rivi String muodossa.
     */
    public String buildLine(String line, ArrayList<String> commandList) {
        for (String command : commandList) {
            line = line.concat(";" + command);
        }
        return line;
    }

    /**
     * Lisää partikkelityyppejä annettusta tiedostosta jo luotujen
     * partikkelityyppien lisäksi.
     *
     * @param filename Tiedoston nimi.
     */
    public void addPresets(String filename) {
        environment.functions.stop();
        try {
            readFile(filename);
            updatePresets();
            reInitializeCommandsAndPresets();
        } catch (IOException e) {
            System.out.println("a problem occurred while loading file...");
        }
    }

    /**
     * Lisää tiedostosta luettuja preset komentoja ohjelmaan.
     */
    public void updatePresets() {
        for (String preset : presets) {
            coReRunner.presets.add(preset);
        }
    }

    /**
     * Lataa kokonaisen esityksen tiedostosta ohjelman suoritettavaksi ja korvaa
     * sillä sen hetkisen iteraatiojanan ja partikkelityypit.
     *
     * @param filename Tiedoston nimi.
     */
    public void load(String filename) {
        environment.functions.stop();
        try {
            readFile(filename);
            replacePresets();
            replaceTimeline();
            reInitializeCommandsAndPresets();
        } catch (IOException e) {
            System.out.println("a problem occurred while loading file...");
        }
    }

    /**
     * Uuudelleen alustaa olion komento joukot.
     */
    public void reInitializeCommandsAndPresets() {
        presets = new ArrayList<String>();
        commands = new HashMap<Integer, ArrayList<String>>();
    }

    /**
     * Korvaa CommandRecordRunner luokan commands mapin komennot tiedostosta
     * ladatuilla komennoilla.
     */
    public void replaceTimeline() {
        coReRunner.commandsToBeAdded = new HashMap<Integer, ArrayList<String>>();
        coReRunner.commands = this.commands;
    }

    /**
     * Korvaa CommandRecordRunner luokan preset komennot tiedostosta ladatuilla
     * preset komennoilla.
     */
    public void replacePresets() {
        coReRunner.presetsToBeAdded = new ArrayList<String>();
        coReRunner.presets = presets;
    }

    /**
     * Lukee tiedoston komennot.
     *
     * @param filename tiedoston nimi
     * @throws IOException Virhe jos lukeminen epäonnistuu, tai tiedoston data on
     * korruptoitunut.
     */
    public void readFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        line = br.readLine();
        if (line != null) {
            presets = readLineCommands(line);
        }
        while ((line = br.readLine()) != null) {
            commands.put(Integer.parseInt(line.substring(2, findComma(3, line))), readLineCommands(line));
        }
        br.close();
    }

    /**
     * Lukee komennot annetulta tiedoston riviltä.
     *
     * @param line merkkijono, joka sisältää komentoja.
     * @return lista komentoja
     */
    public ArrayList<String> readLineCommands(String line) {
        ArrayList<String> commands = new ArrayList<String>();
        int index = 3;
        index = findComma(index, line);
        index++;
        int startIndex = index;
        while (index < line.length()) {
            index = findComma(index, line);
            commands.add(line.substring(startIndex, index));
            index++;
            startIndex = index;
        }
        return commands;
    }

    /**
     * Palauttaa seuraavan ; merkin sijainnin rivillä.
     *
     * @param index Indeksi josta merkkijonon läpikäynti alkaa.
     * @param line Rivi komentoja merkkijono muodossa.
     * @return Indeksi, josta löytyi merkki ';'.
     */
    public int findComma(int index, String line) {
        while (index < line.length() && line.charAt(index) != ';') {
            index++;
        }
        return index;
    }
}
