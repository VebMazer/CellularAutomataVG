package vm.emergencevg.logic;

import java.util.ArrayList;
import java.util.HashMap;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.ui.Updatable;

/**
 * Ylläpitää aikaa iteraatioiden suoritusmäärän muodossa ja tallentaa käyttäjän
 * interaktioita logiikka-avaruuden kanssa suhteessa siihen.
 */
public class CommandRecordRunner {

    GenerativeSpace space;
    public int iterations;
    public HashMap<Integer, ArrayList<String>> commands;
    public HashMap<Integer, ArrayList<String>> commandsToBeAdded;
    public ArrayList<String> presets;
    public ArrayList<String> presetsToBeAdded;
    Updatable uiIterationDisplayer;

    /**
     * Konstruktori joka vastaanottaa GenerativeSpace tyypin olion, jonka kautta
     * luokka saa muut arvonsa. Komentojoukot(commands, presets) vaativat
     * apukomento joukon(commandsToBeAdded, presetsToBeAdded) ylläptioon siksi
     * ettei komentojen lisääminen suorituksen aikana, häiritse ohjelman
     * suoritusta.
     *
     * @param space Ohjelman logiikka-avaruutta ja taustalooppia ylläpitävä
     * muuttuja.
     */
    public CommandRecordRunner(GenerativeSpace space) {
        this.space = space;
        iterations = 0;
        commands = new HashMap<Integer, ArrayList<String>>();
        commandsToBeAdded = new HashMap<Integer, ArrayList<String>>();
        presets = new ArrayList<String>();
        presetsToBeAdded = new ArrayList<String>();
    }

    /**
     * Uudellen alustetaan komennot.
     */
    public void reInitializeCommands() {
        commandsToBeAdded = new HashMap<Integer, ArrayList<String>>();
        commands = new HashMap<Integer, ArrayList<String>>();
    }

    /**
     * Uudellen alustetaan preset komennot.
     */
    public void reInitializePresets() {
        presetsToBeAdded = new ArrayList<String>();
        presets = new ArrayList<String>();
    }

    /**
     * Suorittaa preset komennot.
     */
    public void runPresets() {
        ArrayList<String> temp = presets;
        presets = new ArrayList<String>();
        for (String command : temp) {
            runPresetCommand(command);
        }

    }

    /**
     * Suorittaa preset komennon.
     *
     * @param command Komento tekstimuodossa.
     */
    public void runPresetCommand(String command) {
        if (command.charAt(0) == 'l') {
            space.functions.processVariablesToParticleType(command.substring(2, findDoubleComma(2, command)), parseDisplayAttributes(command));
        } else if (command.charAt(0) == 'f') {
            parseAndSetFieldSize(command);
        }
    }

    /**
     * Parseroi ja suorittaa fieldSize komennon.
     *
     * @param command Komento tekstimuodossa.
     */
    public void parseAndSetFieldSize(String command) {
        ArrayList<Integer> coordinates = new ArrayList<Integer>();
        int index = space.uFunctions.parseNextNumber(10, command, coordinates);
        space.uFunctions.parseNextNumber(index + 1, command, coordinates);
        space.functions.resetField(coordinates.get(0), coordinates.get(1));
    }

    /**
     * Parseroi partikkelin määrittelykomennosta sen piirtoon liittyvät
     * muuttujat.
     *
     * @param command Komento tekstimuodossa.
     * @return listan partikkelin piirtoon liittyvistä Integer muuttujista.
     */
    public ArrayList<Integer> parseDisplayAttributes(String command) {
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        int index = findDoubleComma(0, command);
        index += 2;
        index = space.uFunctions.parseNextNumber(index, command, displayAttributes);
        space.uFunctions.parseNextNumber(index, command, displayAttributes);
        return displayAttributes;
    }

    /**
     * Etsii ",," merkkijonon komennosta ja palauttaa sen alkamis indeksin.
     *
     * @param index Indeksi, josta etsityn merkkijonon etsintä aloitetaan.
     * @param command Komennon merkkijono, josta merkkijonoa ",," etsitään.
     * @return Indeksi, josta etsitty merkkijono alkoi, tai komennon viimeinen
     * indeksi
     */
    public int findDoubleComma(int index, String command) {
        while (index < command.length() - 1) {
            if (command.substring(index, index + 2).equals(",,")) {
                break;
            }
            index++;
        }
        return index;
    }

    /**
     * Suorittaa komennot, jotka on määrätty suoritettavalle iteraatiolle.
     */
    public void runCommands() {
        ArrayList<String> commandList = commands.get(iterations);
        if (commandList != null) {
            for (String command : commandList) {
                runCommand(command);
            }
        }
    }

    /**
     * Suorittaa komennon.
     *
     * @param command Suoritettava komento String muodossa.
     */
    public void runCommand(String command) {
        if (space.uFunctions.checkIfNumber(command.charAt(1))) {
            parsePlacementStringToPlaceIt(command);
        } else if (command.equals("clear")) {
            space.functions.clear();
        } else if (command.substring(0, 5).equals("speed")) {
            space.functions.setSpeed(command.substring(6, command.length() - 1));
        } else if (command.substring(0, 2).equals("sc")) {
            space.functions.setScale(Integer.parseInt(command.substring(6, command.length() - 1)));
        } else if (command.charAt(0) == 'f') {
            parseAndSetFieldSize(command);
        }
    }

    /**
     * Parseroidaan partikkelin sijoituskomennosta tarvittavat int arvot ja
     * sijoitetaan partikkeli.
     *
     * @param command Suoritettava komento String muodossa.
     */
    public void parsePlacementStringToPlaceIt(String command) {
        int index = 1;
        Integer particleKey = space.uFunctions.parseNumber(command, index);
        if (space.particleTypes.containsKey(particleKey)) {
            index += particleKey.toString().length() + 1;
            Integer x = space.uFunctions.parseNumber(command, index);
            index += x.toString().length() + 1;
            int y = space.uFunctions.parseNumber(command, index);
            if (x < space.xlength && y < space.ylength) {
                space.functions.placeParticle(particleKey, x, y);
            }
        }
    }

    public void setIterationDisplayer(Updatable uiIterationDisplayer) {
        this.uiIterationDisplayer = uiIterationDisplayer;
    }
}
