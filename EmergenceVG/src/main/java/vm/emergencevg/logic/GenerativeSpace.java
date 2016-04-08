package vm.emergencevg.logic;

import java.util.ArrayList;
import vm.emergencevg.domain.*;

import java.util.HashMap;
import java.util.Arrays;
import vm.emergencevg.ui.Updatable;

public class GenerativeSpace implements Runnable {

    public int xlength;
    public int ylength;
    public int[][] field;
    public int[][] resultField;

    public HashMap<Integer, ParticleType> particleTypes;

    public ControlFunctions functions;
    public MouseController mController;
    Updatable uiDrawBoard;

    private double speedDivider;
    boolean running;

    public GenerativeSpace(int x, int y) {
        xlength = x;
        ylength = y;
        field = new int[x][y];
        resultField = new int[x][y];

        this.particleTypes = new HashMap<Integer, ParticleType>();
        running = false;
        speedDivider = 5.0;

        functions = new ControlFunctions(this);
        mController = new MouseController(this);
    }

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
            delta += (now - lastTime) / (ns * speedDivider);
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
        }
    }

    public void iterate() {
        if (running) {
            nextIteration();
        }
    }

    public synchronized void nextIteration() {
        computeNextIteration();
        updateField();
    }

    //Laskee loopin seuraavan iteraation valmiiksi resultField muuttujaan.
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

    //Asettaa uuden partikkelin jos data täyttää halutut ehdot.
    public void particleProcess(int x, int y, int spotKey, int neighbors, int[] neighborTypes) {
        if (spotKey == 0) {
            int mostCommonKey = mostCommonKey(neighborTypes);
            if (mostCommonKey != 0) {
                ParticleType type = particleTypes.get(mostCommonKey);
                if (type.generate(neighbors)) {
                    resultField[x][y] = type.key;
                }
            }
        } else if (!particleTypes.get(spotKey).live(neighbors)) {
            resultField[x][y] = 0;
        }
    }

    //laskee ympäröivistä partikkeleistä yleisimmän ja palauttaa sen avaimen
    public int mostCommonKey(int[] keys) {
        Arrays.sort(keys);
        int mostRep = 0;
        int reps = 0;
        int index = 0;
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != 0 && i + 1 < keys.length && keys[i] == keys[i + 1]) {
                reps++;
                if (reps > mostRep) {
                    mostRep = reps;
                    index = i;
                }
            }
        }
        return keys[index];
    }

    //Korvaa kentän arvot seuraavan iteraation arvoilla.
    public void updateField() {
        field = resultField;
    }

    public void placeParticle(int pKey, int i, int j) {
        resultField[i][j] = pKey;
    }

    public void setUiDrawBoard(Updatable uiDrawBoard) {
        this.uiDrawBoard = uiDrawBoard;
    }

    public void initialTestSetup() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(3);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        toLive.add(2);
        toLive.add(3);
        functions.addParticleType("life", forNew, toLive);

        forNew = new ArrayList<Integer>();
        forNew.add(3);
        forNew.add(4);
        forNew.add(5);
        toLive = new ArrayList<Integer>();
        toLive.add(3);
        functions.addParticleType("remenant", forNew, toLive);

    }

}
