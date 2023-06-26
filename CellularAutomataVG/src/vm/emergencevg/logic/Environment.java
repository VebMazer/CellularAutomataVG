package vm.emergencevg.logic;

import java.util.HashMap;
import vm.emergencevg.control.*;
import vm.emergencevg.ui.Updatable;

/**
 * Ohjelman taustalooppi ja logiikka-avaruuden ylläpitäjä.
 */
public class Environment implements Runnable {

    public int     width;
    public int     height;
    public int[][] field;
    public int[][] resultField;

    public HashMap<Integer, Particle> particles;

    public ControlFunctions functions;
    public MouseController mController;
    public UtilityFunctions uFunctions;
    public CommandRecordRunner coReRunner;
    public Bot bot;
    public Communicator communicator;

    Updatable uiDrawBoard;
    public double speedModifier;
    public boolean running;
    boolean trigger;
    boolean triggering;

    /**
     * Konstruktori, joka alustaa oliomuuttujat.
     * @param x Logiikka-avaruuden x akselin koko.
     * @param y Logiikka-avaruuden x akselin koko.
     */
    public Environment(int width, int height) {
        this.width    = width;
        this.height   = height;
        field         = new int[width][height];
        resultField   = new int[width][height];
        particles     = new HashMap<Integer, Particle>();

        running = false;
        trigger = true;
        triggering = false;
        speedModifier = 0.20;

        coReRunner   = new CommandRecordRunner(this);
        bot          = new Bot(this);
        communicator = new Communicator(this);
        communicator.connect("localhost", 7799);

        uFunctions = new UtilityFunctions(this);
        functions = new ControlFunctions(this);
        mController = new MouseController(this);
    }

    /**
     * Ohjelman taustalooppi, joka vastaa ohjelma ympäristön päivittämisestä
     * suhteessa aikaan.
     */
    public synchronized void run() {
        long   lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        
        int updates = 0;
        int cycles = 0;
        
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) * speedModifier / ns;
            lastTime = now;
            
            while (delta >= 1) {
                iterate();
                uiDrawBoard.update();
                
                updates++;
                delta--;
            }
            
            cycles++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                
                System.out.println("CyclesPS: " + cycles + " TICKS: " + updates);
                
                cycles = 0;
                updates = 0;
            }
            if (speedModifier < 1000) {
                try {
                    Thread.sleep(5);
                } catch (Exception e) {
                    System.out.println("Error in delaying the loop.");
                }
            }
        }
    }

    /**
     * Suorittaa logiikka-avaruuden seuraavan iteraation määrittämisen jos
     * simulaatio on käynnissä.
     */
    public void iterate() {
        if (running && trigger) {
            
            // send or read commands if the user is in "network sender mode"
            // or "network receiver mode".
            if      (bot.mode == -1) communicator.sendCommands();
            else if (bot.mode == -2) communicator.readCommands();
            
            // Calculate next iteration.
            nextIteration();
            
            // Run commands marked for the current iteration.
            coReRunner.runCommands();
            
            // Update the mode bot.
            bot.update();
            
            // Update iteration counter and the component displaying it.
            coReRunner.iterations++;
            coReRunner.uiIterationDisplayer.update();
            
            if(triggering) trigger = false;
        }
    }

    /**
     * Kutsuu logiikka-avaruuden suraavan iteraation laskevaa metodia ja sen
     * jälkeen metodia joka päivittää logiikka-avaruuden.
     */
    public synchronized void nextIteration() {
        computeNextIteration();
        updateField();
    }

    /**
     * Laskee logiikka-avaruuden seuraavan iteraation valmiiksi resultField
     * muuttujaan.
     */
    public void computeNextIteration() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int neighbors = 0;
                int[] neighborTypes = new int[8];
                int index = 0;
                if (j - 1 >= 0 && i - 1 >= 0 && field[i - 1][j - 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i - 1][j - 1];
                    index++;
                }
                if (j - 1 >= 0 && field[i][j - 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i][j - 1];
                    index++;
                }
                if (j - 1 >= 0 && i + 1 < width && field[i + 1][j - 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i + 1][j - 1];
                    index++;
                }
                if (i - 1 >= 0 && field[i - 1][j] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i - 1][j];
                    index++;
                }
                if (i + 1 < width && field[i + 1][j] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i + 1][j];
                    index++;
                }
                if (i - 1 >= 0 && j + 1 < height && field[i - 1][j + 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i - 1][j + 1];
                    index++;
                }
                if (j + 1 < height && field[i][j + 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i][j + 1];
                    index++;
                }
                if (i + 1 < width && j + 1 < height && field[i + 1][j + 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i + 1][j + 1];
                }
                particleProcess(i, j, field[i][j], neighbors, neighborTypes);
            }
        }
    }

    /**
     * Asettaa uuden partikkelin logiikka-avaruuteen jos data täyttää halutut ehdot.
     * @param x x akselin arvo koordinaatissa.
     * @param y x akselin arvo koordinaatissa.
     * @param spotKey logiikka-avaruuden sijainnin avain
     * arvo(kuvaa partikkelityyppiä tai tyhjää jos 0).
     * @param neighbors Sijaintia ympäröivien partikkelien määrä(0-8).
     * [0, 4, 0]
     * [1, spotKey, 0]
     * [0, 0, 1] <==> neighbors == 3
     * @param neighborTypes Taulukko, joka sisältää ympäröivien partikkelien tyyppi
     * avaimet(Ylemmässä esimerkissä sisältäisi arvot(0, 4, 0, 1, 0, 0, 0, 1)).
     */
    public void particleProcess(int x, int y, int spotKey, int neighbors, int[] neighborTypes) {
        if (spotKey == 0) {
            int mostCommonKey = uFunctions.mostCommonKey(neighborTypes);
            if (mostCommonKey != 0) {
                if (particles.get(mostCommonKey).generate(neighbors)) {
                    resultField[x][y] = mostCommonKey;
                } else {
                    resultField[x][y] = 0;
                }
            } else {
                resultField[x][y] = 0;
            }
        } else if (particles.get(spotKey).live(neighbors)) {
            resultField[x][y] = spotKey;
        } else {
            resultField[x][y] = 0;
        }
    }

    /**
     * Korvaa logiikka-avaruuden(field muuttuja) sen seuraavalla iteraatiolla
     * (resultField) ja alustaa resultField muuttujan uusiksi.
     */
    public void updateField() {
        field = resultField;
        resultField = new int[width][height];
    }

    public void setUiDrawBoard(Updatable uiDrawBoard) {
        this.uiDrawBoard = uiDrawBoard;
    }
}
