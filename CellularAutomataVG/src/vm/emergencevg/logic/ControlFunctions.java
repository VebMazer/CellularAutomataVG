package vm.emergencevg.logic;

import java.util.ArrayList;
import java.util.HashMap;

import vm.emergencevg.ui.Updatable;

/**
 * Offers functions for controlling the state of the environment.
 */
public class ControlFunctions {

    public Environment environment;

    // Used in keeping record of the users commands, so that they can be repeated if
    // the board iterations are replayed and also saved should the user wish to do so.
    public CommandRecordRunner coReRunner;
    
    public UtilityFunctions uFunctions;
    
    public FileIO fileIo;
    
    public Updatable scaleUpdater;
    public int scale;

    /**
     * The required values all come from the environment object.
     *
     * @param environment Contains the environment of the program, including
     * the main loop, the particle board and other objects used in the program.
     */
    public ControlFunctions(Environment environment) {
        this.environment = environment;
        this.coReRunner  = environment.coReRunner;
        this.uFunctions  = environment.uFunctions;
        this.fileIo      = new FileIO(environment);
        scale = 5;
    }

    /**
     * Makes a Particle object out of an input string and a list of display attributes.
     *
     * @param input A string that contains the name, amountsForNew and amountsToLive values
     * required to define a particle.
     * @param displayAttributes List of integers that define the visual appearance of the particle.
     */
    public void processVariablesToParticle(String input, ArrayList<Integer> displayAttributes) {
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
        addParticle(name, amountsForNew, amountsToLive, displayAttributes);
    }

    /**
     * Processes values gained from the UI to a particle.
     * 
     * @param displayAttributes List of integers that define the visual appearance of the particle.
     */
    public void processVariablesToParticle(
        String name,
        ArrayList<Integer> amountsForNew,
        ArrayList<Integer> amountsToLive,
        ArrayList<Integer> displayAttributes
    ) {

        // Create a particle objet and add it to the list of particles.
        addParticle(name, amountsForNew, amountsToLive, displayAttributes);
        
        // Create a string representation and add it to the presets.
        String amountsForNewString = "";
        String amountsToLiveString = "";

        for (int i = 0; i < amountsForNew.size(); i++) {
            amountsForNewString += amountsForNew.get(i) + " ";
        }
        for (int i = 0; i < amountsToLive.size(); i++) {
            amountsToLiveString += amountsToLive.get(i) + " ";
        }

        amountsForNewString = amountsForNewString.substring(0, amountsForNewString.length() - 1);
        amountsToLiveString = amountsToLiveString.substring(0, amountsToLiveString.length() - 1);
        
        uFunctions.addPreset(
            "l(" + name + ", " + amountsForNewString + ", " + amountsToLiveString
            + ",," + displayAttributes.get(0) + " " + displayAttributes.get(1) + ")"
        );
    }

    /**
     * processStringToList metodin apumetodi, joka etsii halutusta String olion
     * indeksistä lähtien kaikki numerot ',' chariin, tai string olion
     * viimeiseen indeksiin asti.
     *
     * @param index Indeksi, josta merkkijonon läpikäynti aloitetaan.
     * @param input Syöte merkkijono.
     * @param list Lista johon löydetyt kokonaisluvut lisätään.
     * @return Indeksi, josta merkki löytyi, tai merkkijonon pituus.
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
     * Etsii ensimmäisen käyttämättömän avaimen logiikka-avaruuden particles
     * HashMapistä ja lisää uuden particles olion samaan mappiin
     * löydettäväksi tällä avaimella.
     *
     * @param name Partikkelityypin nimi.
     * @param amountsForNew Lista partikkelia ympäröivien partikkelien määristä,
     * joilla partikkeli säilyy logiikka-avaruudessa.
     * @param amountsToLive Lista sopivista ympäröivien partikkelien määristä,
     * uuden tämän tyypin partikkelin syntymiselle.
     * @param displayAttributes Lista numeroita, jotka määrittävät partikkelin
     * piirtotyyliä.
     */
    public void addParticle(String name, ArrayList<Integer> amountsForNew, ArrayList<Integer> amountsToLive, ArrayList<Integer> displayAttributes) {
        int key = 0;
        
        for (int i = 1; i < 100; i++) {
            
            if (environment.particles.get(i) == null) {
                key = i;
                break;
            }
        }
        if (key != 0) {
            
            environment.particles.put(
                key,
                new Particle(name, key, amountsForNew, amountsToLive, displayAttributes)
            );
        }
    }

    public void setBoardWidthCommand(int width) {
        resizeBoard(width, environment.height);
        
        uFunctions.addCommand("fieldSize(" + environment.width + "," + environment.height + ")");
    }

    public void setBoardHeightCommand(int height) {
        resizeBoard(environment.width, height);
        
        uFunctions.addCommand("fieldSize(" + environment.width + "," + environment.height + ")");
    }

    /*
     * Creates a new resized board and copies the content from the old
     * board to the new board.
     */
    public void resizeBoard(int width, int height) {
        
        // Halt iterations if they are running.
        boolean wasRunning = environment.running;
        if (wasRunning) {
            stop();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println("Slowing thread did not succeed...");
            }
        }
        
        // Create new boards.
        int[][] board       = new int[width][height];
        int[][] resultBoard = new int[width][height];
        
        // Copy the content from the old boards to the new boards.
        for (int y = 0; y < Math.min(height, environment.height); y++) {
            for (int x = 0; x < Math.min(width, environment.width); x++) {
                
                board[x][y]       = environment.field[x][y];
                resultBoard[x][y] = environment.resultField[x][y];
            }
        }

        // Replace the old boards with the new ones.
        environment.field       = board;
        environment.resultField = resultBoard;

        // Reset the board size variables.
        environment.width = width;
        environment.height = height;

        if (wasRunning) start();
    }

    /**
     * Asettaa iterations muuttujan uuden arvon, käyttäjän syötteen perusteella.
     *
     * @param iterations Syötetty arvo iterations muuttujalle string muodossa.
     */
    public void setIterations(String iterations) {
        try {
            coReRunner.iterations = Integer.parseInt(iterations);
        
        } catch (Exception e) {
            System.out.println("Bad input to iterations field!");
        }
    }

    /**
     * Tyhjentää partikkelityyppilistan ja uudelleen alustaa preset komennot.
     */
    public void clearParticles() {
        stop();
        
        environment.particles = new HashMap<Integer, Particle>();
        
        coReRunner.reInitializePresets();
    }

    /**
     * Tyhjentää esityksen suoritusaikajanan. Komento mappi siis tyhjennetään.
     * ja iterations muuttuja nollataan.
     */
    public void clearRecord() {
        stop();
        
        coReRunner.reInitializeCommands();
        coReRunner.iterations = 0;
        coReRunner.uiIterationDisplayer.update();
    }

    /**
     * Määrittää partikkelien piirtoskaalan(partikkelien koko piirtoalustalla)
     * ja asettaa tämän tekevän komennon komento mappiin commands.
     *
     * @param scale piirtoskaala(partikkelien koko piirtoalustalla).
     */
    public void setScaleCommand(int scale) {
        setScale(scale);
        
        uFunctions.addCommand("scale(" + scale + ")");
    }

    /**
     * Määrittää partikkelien piirtoskaalan(partikkelien koko piirtoalustalla).
     *
     * @param scale piirtoskaala(partikkelien koko piirtoalustalla).
     */
    public void setScale(int scale) {
        this.scale = scale;
        scaleUpdater.update();
    }

    /**
     * Tallentaa visuaaliesityksen.
     *
     * @param filename Tallennettavan tiedoston nimi. polun kansioon voi myös
     * ilmaista ennen nimeä tarvittaessa.
     */
    public void save(String filename) {
        stop();
        fileIo.save(filename);
    }

    /**
     * Tyhjentää Ohjelman asetuksita ja lataa visuaaliesityksen suoritettavaksi.
     *
     * @param filename Ladattavan tiedoston nimi. polun kansioon voi myös
     * ilmaista ennen nimeä tarvittaessa.
     */
    public void loadPresentation(String filename) {
        clearCommand();
        clearRecord();
        clearParticles();
        
        fileIo.load(filename);
        coReRunner.runPresets();
    }

    /**
     * Lataa partikkelityypit visuaaliesitys tiedostosta ja lisää ne olemassa
     * olevaan partikkelityyppi mappiin.
     *
     * @param filename Ladattavan tiedoston nimi. polun kansioon voi myös
     * ilmaista ennen nimeä tarvittaessa.
     */
    public void addPresets(String filename) {
        environment.particles = new HashMap<Integer, Particle>();
        
        fileIo.addPresets(filename);
        coReRunner.runPresets();
    }

    public void playSwitch() {
        if (environment.running) stop();
        
        else environment.running = true;
    }

    /**
     * Starts running the board iterations.
     */
    public void start() {
        environment.running = true;
    }

    /**
     * Pysäyttää logiikka-avaruuden iteraatioiden suorituksen ja lisää
     * määritellyt komennot aikajanaan.
     */
    public void stop() {
        environment.running = false;
        
        uFunctions.uniteCommandMaps();
        uFunctions.unitePresetLists();
    }

    /**
     * Asettaa suorituksen nopeuden käyttäjän syötteen perusteella ja lisää
     * saman tekevän komennon.
     *
     * @param speed Syöte merkkijono nopeuden määrittämiseen.
     */
    public void setSpeedCommand(String speed) {
        try {
            setSpeed(speed);
            uFunctions.addCommand("speed(" + speed + ")");
        
        } catch (Exception e) {
            System.out.println("Bad input!!");
        }
    }

    /**
     * Asettaa suorituksen nopeuden merkkijonon perusteella.
     *
     * @param speed Syöte merkkijono nopeuden määrittämiseen.
     */
    public void setSpeed(String speed) {
        environment.speedModifier = Double.parseDouble(speed);
    }

    /**
     * Tyhjentää logiikka-avaruuden partikkeleista ja lisää saman tekevän
     * komennon.
     */
    public void clearCommand() {
        clear();
        uFunctions.addCommand("clear");
    }

    /**
     * Tyhjentää logiikka-avaruuden partikkeleista.
     */
    public void clear() {
        
        environment.field       = new int[environment.width][environment.height];
        environment.resultField = new int[environment.width][environment.height];
    }

    /**
     * Asettaa uuden partikkelin logiikka-avaruuteen.
     *
     * @param pKey Partikkelityyppiä kuvaava avain.
     * @param x x akselin koordinaatti johon sijoitus tehdään.
     * @param y y akselin koordinaatti johon sijoitus tehdään.
     */
    public void placeParticle(int pKey, int x, int y) {
        
        if (x >= 0 && y >= 0 && x < environment.width && y < environment.height) {
            
            if (environment.field[x][y] != 0) {
                environment.field[x][y] = 0;
                environment.resultField[x][y] = 0;
            
            } else {
                environment.field[x][y] = pKey;
                environment.resultField[x][y] = pKey;
            }
        }
    }

    public void setScaleUpdater(Updatable updatable) {
        scaleUpdater = updatable;
    }
    
    public void execCommand(String command) {
        if(command == null || command.length() < 2) {
            return;
        
        } else if (environment.uFunctions.checkIfNumber(command.charAt(1))) {
            
            environment.coReRunner.parsePlacementStringToPlaceIt(command);
            environment.uFunctions.addCommand(command);
        
        } else if (command.equals("clear")) {
            environment.functions.clearCommand();
        
        } else if (command.substring(0, 5).equals("speed")) {
            environment.functions.setSpeed(command.substring(6, command.length() - 1));
            environment.uFunctions.addCommand(command);
        
        } else if (command.substring(0, 2).equals("sc")) {
            environment.functions.setScale(Integer.parseInt(command.substring(6, command.length() - 1)));
            environment.uFunctions.addCommand(command);
        
        } else if (command.charAt(0) == 'f') {
            environment.coReRunner.parseAndSetFieldSize(command);
            environment.uFunctions.addCommand(command);
        
        } else if (command.charAt(0) == 'l') {
            environment.coReRunner.runPresetCommand(command);
            
        }
    }
    
}
