package vm.emergencevg.logic;

import java.util.ArrayList;
import vm.emergencevg.domain.*;
import java.util.HashMap;
import java.util.Arrays;
import vm.emergencevg.ui.Updatable;

/**
 * Ohjelman taustalooppi ja logiikka-avaruuden ylläpitäjä.
 */
public class GenerativeSpace implements Runnable {

    public int xlength;
    public int ylength;
    public int[][] field;
    public int[][] resultField;

    public HashMap<Integer, ParticleType> particleTypes;

    public ControlFunctions functions;
    public MouseController mController;
    public UtilityFunctions uFunctions;
    public CommandRecordRunner coReRunner;

    Updatable uiDrawBoard;
    public double speedModifier;
    boolean running;

    public GenerativeSpace(int x, int y) {
        xlength = x;
        ylength = y;
        field = new int[x][y];
        resultField = new int[x][y];
        this.particleTypes = new HashMap<Integer, ParticleType>();

        running = false;
        speedModifier = 0.20;

        coReRunner = new CommandRecordRunner(this);
        uFunctions = new UtilityFunctions(this);
        functions = new ControlFunctions(this);
        mController = new MouseController(this);
    }

    /**
     * Ohjelman taustalooppi, joka vastaa ohjelma ympäristön päivittämisestä
     * suhteessa aikaan.
     */
    public synchronized void run() {
        long lastTime = System.nanoTime();
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
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("Error in delaying the loop.");
                }
            }
        }
    }

    /**
     * Suorittaa logiikka-avaruuden seuraavan iteraation määrittämisen jos
     * simulaatio on päällä.
     */
    public void iterate() {
        if (running) {
            nextIteration();
            coReRunner.runCommands();
            coReRunner.iterations++;
            coReRunner.uiIterationDisplayer.update();
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
        for (int j = 0; j < ylength; j++) {
            for (int i = 0; i < xlength; i++) {
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
                if (j - 1 >= 0 && i + 1 < xlength && field[i + 1][j - 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i + 1][j - 1];
                    index++;
                }
                if (i - 1 >= 0 && field[i - 1][j] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i - 1][j];
                    index++;
                }
                if (i + 1 < xlength && field[i + 1][j] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i + 1][j];
                    index++;
                }
                if (i - 1 >= 0 && j + 1 < ylength && field[i - 1][j + 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i - 1][j + 1];
                    index++;
                }
                if (j + 1 < ylength && field[i][j + 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i][j + 1];
                    index++;
                }
                if (i + 1 < xlength && j + 1 < ylength && field[i + 1][j + 1] != 0) {
                    neighbors++;
                    neighborTypes[index] = field[i + 1][j + 1];
                }
                particleProcess(i, j, field[i][j], neighbors, neighborTypes);
            }
        }
    }

    /**
     * Asettaa uuden partikkelin jos data täyttää halutut ehdot.
     */
    public void particleProcess(int x, int y, int spotKey, int neighbors, int[] neighborTypes) {
        if (spotKey == 0) {
            int mostCommonKey = uFunctions.mostCommonKey(neighborTypes);
            if (mostCommonKey != 0) {
                if (particleTypes.get(mostCommonKey).generate(neighbors)) {
                    resultField[x][y] = mostCommonKey;
                } else {
                    resultField[x][y] = 0;
                }
            } else {
                resultField[x][y] = 0;
            }
        } else if (particleTypes.get(spotKey).live(neighbors)) {
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
        resultField = new int[xlength][ylength];
    }

    public void setUiDrawBoard(Updatable uiDrawBoard) {
        this.uiDrawBoard = uiDrawBoard;
    }
}
