package vm.emergencevg.logic;

import java.util.ArrayList;
import java.util.HashMap;
import vm.emergencevg.ui.Updatable;

/**
 * Includes the iteration count and saves user interactions that happen on
 * each iteration.
 */
public class CommandRecordRunner {

    Environment environment;
    public int iterations;
    
    public HashMap<Integer, ArrayList<String>> commands;
    public HashMap<Integer, ArrayList<String>> commandsToBeAdded;
    
    public ArrayList<String> presets;
    public ArrayList<String> presetsToBeAdded;
    
    Updatable uiIterationDisplayer;

    /**
     * Constructor receives the environment object which includes the
     * necessary values for this class. The command containers (commands and presets)
     * are initialized. The -ToBeAdded containers are used when commands or presets
     * are added so that the commands dont get issued twice. (once by the user and 
     * once by the CommandRecordRunner.)
     *
     * @param environment Object that includes the main loop controlling the program,
     * the particle board, and its properties.
     */
    public CommandRecordRunner(Environment environment) {
        this.environment = environment;
        iterations = 0;
        
        commands = new HashMap<Integer, ArrayList<String>>();
        commandsToBeAdded = new HashMap<Integer, ArrayList<String>>();
        
        presets = new ArrayList<String>();
        presetsToBeAdded = new ArrayList<String>();
    }

    /**
     * Reinitialize commands.
     */
    public void reInitializeCommands() {
        commandsToBeAdded = new HashMap<Integer, ArrayList<String>>();
        commands = new HashMap<Integer, ArrayList<String>>();
    }

    /**
     * Reinitialize preset lists.
     */
    public void reInitializePresets() {
        presetsToBeAdded = new ArrayList<String>();
        presets = new ArrayList<String>();
    }

    /**
     * Applies the preset commands in the preset variable.
     */
    public void runPresets() {
        ArrayList<String> temp = presets;
        presets = new ArrayList<String>();
        
        for (String command : temp) {
            runPresetCommand(command);
        }

    }

    /**
     * Runs the preset command.
     *
     * @param command String containing the command.
     */
    public void runPresetCommand(String command) {
        if (command.charAt(0) == 'l') {
            environment.functions.processVariablesToParticle(
                command.substring(2, findDoubleComma(2, command)),
                parseDisplayAttributes(command)
            );
        } else if (command.charAt(0) == 'f') {
            parseAndSetFieldSize(command);
        }
    }

    /**
     * Parse and run fieldSize command.
     *
     * @param command String containing the command.
     */
    public void parseAndSetFieldSize(String command) {
        ArrayList<Integer> coordinates = new ArrayList<Integer>();
        
        int index = environment.uFunctions.parseNextNumber(10, command, coordinates);
        environment.uFunctions.parseNextNumber(index + 1, command, coordinates);
        
        environment.functions.resizeBoard(coordinates.get(0), coordinates.get(1));
    }

    /**
     * Parses the display attributes that define how a particle is drawn from
     * a preset command that defines a particle.
     *
     * @param command Includes the preset command in a string.
     * @return A list of the display attributes which are represented by integers.
     */
    public ArrayList<Integer> parseDisplayAttributes(String command) {
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        
        int index = findDoubleComma(0, command);
        index += 2;
        index = environment.uFunctions.parseNextNumber(index, command, displayAttributes);
        
        environment.uFunctions.parseNextNumber(index, command, displayAttributes);
        
        return displayAttributes;
    }

    /**
     * Finds the string: ",," from a command and returns its starting index.
     *
     * @param index Indeksi, josta etsityn merkkijonon etsintä aloitetaan. Index from which the search begins.
     * @param command Command string from which the ",," separator sign is searched from.
     * @return Index from which ",," started from or the last index of the command.
     */
    public int findDoubleComma(int index, String command) {
        while (index < command.length() - 1) {
            
            if (command.substring(index, index + 2).equals(",,")) break;
            index++;
        }
        return index;
    }

    /**
     * Runs the commands that match the the iteration in iterations.
     */
    public void runCommands() {
        ArrayList<String> commandList = commands.get(iterations);
        
        if (commandList != null) {
            
            for (String command : commandList) runCommand(command);
        }
    }

    /**
     * Runs a command.
     *
     * @param command Command in String form.
     */
    public void runCommand(String command) {
        if (environment.uFunctions.checkIfNumber(command.charAt(1))) {
            parsePlacementStringToPlaceIt(command);
        
        } else if (command.equals("clear")) {
            environment.functions.clear();
        
        } else if (command.substring(0, 5).equals("speed")) {
            environment.functions.setSpeed(command.substring(6, command.length() - 1));
        
        } else if (command.substring(0, 2).equals("sc")) {
            environment.functions.setScale(Integer.parseInt(command.substring(6, command.length() - 1)));
        
        } else if (command.charAt(0) == 'f') {
            parseAndSetFieldSize(command);
        }
    }
    
    /**
     * Parses the integer values needed for the placement of a particle. Which include:
     * the particle key and the x and y coordinates.
     *
     * @param command The command to be processed in string form.
     */
    public void parsePlacementStringToPlaceIt(String command) {
        int index = 1;
        
        Integer particleKey = environment.uFunctions.parseNumber(command, index);
        
        if (environment.particles.containsKey(particleKey)) {
            index += particleKey.toString().length() + 1;
            
            Integer x = environment.uFunctions.parseNumber(command, index);
            
            index += x.toString().length() + 1;
            
            int y = environment.uFunctions.parseNumber(command, index);
            
            if (x < environment.width && y < environment.height) {
                environment.functions.placeParticle(particleKey, x, y);
            }
        }
    }

    public void setIterationDisplayer(Updatable uiIterationDisplayer) {
        this.uiIterationDisplayer = uiIterationDisplayer;
    }
}
