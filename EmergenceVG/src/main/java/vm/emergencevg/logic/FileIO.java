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
    GenerativeSpace space;
    public CommandRecordRunner coReRunner;

    public FileIO(GenerativeSpace space) {
        this.space = space;
        this.coReRunner = space.coReRunner;
        presets = new ArrayList<String>();
        commands = new HashMap<Integer, ArrayList<String>>();
    }

    /**
     * Tallentaa ohjelmassa luodut partikkelityypi ja iteraatio aikajanalla
     * olevat komennot tiedostoon.
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
     * Kirjoittaa tiedostoon.
     */
    public void writeFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(buildLine("presets", presets));
        for (int key : commands.keySet()) {
            fw.write(buildLine("\nit" + key, commands.get(key)));
        }
        fw.close();
    }

    public String buildLine(String line, ArrayList<String> commandList) {
        for (String command : commandList) {
            line = line.concat(";" + command);
        }
        return line;
    }

    /**
     * Lisää partikkelityyppejä annettusta tiedostosta jo luotujen
     * partikkelityyppien lisäksi.
     */
    public void addPresets(String filename) {
        space.functions.stop();
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
     */
    public void load(String filename) {
        space.functions.stop();
        try {
            readFile(filename);
            replacePresets();
            replaceTimeline();
            reInitializeCommandsAndPresets();
        } catch (IOException e) {
            System.out.println("a problem occurred while loading file...");
        }
    }

    public void reInitializeCommandsAndPresets() {
        presets = new ArrayList<String>();
        commands = new HashMap<Integer, ArrayList<String>>();
    }

    public void replaceTimeline() {
        coReRunner.commandsToBeAdded = new HashMap<Integer, ArrayList<String>>();
        coReRunner.commands = this.commands;
    }

    public void replacePresets() {
        coReRunner.presetsToBeAdded = new ArrayList<String>();
        coReRunner.presets = presets;
    }

    /**
     * Lukee tiedoston.
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
    
    public ArrayList<String> readLineCommandsOrig(String line) {
        ArrayList<String> commands = new ArrayList<String>();
        int index = 3;
        index = findComma(index, line);
        index++;
        int startIndex = index;
        while (index < line.length()) {
            index = findComma(index, line);
            commands.add(line.substring(startIndex, index));
            index++;
        }
        return commands;
    }
    /**
     * Palauttaa seuraavan ; merkin sijainnin rivillä.
     */
    public int findComma(int index, String line) {
        while (index < line.length() && line.charAt(index) != ';') {
            index++;
        }
        return index;
    }
}
