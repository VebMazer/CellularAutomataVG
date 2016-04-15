package vm.emergencevg.logic;

/**
 * Luokka tarjoaa apumetodeita esimerkiksi ohjelman taustaloopin arvojen
 * selvittämiseen.
 */
public class UtilityFunctions {

    GenerativeSpace space;

    public UtilityFunctions(GenerativeSpace space) {
        this.space = space;
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
}
