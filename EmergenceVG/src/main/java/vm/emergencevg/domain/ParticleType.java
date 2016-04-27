package vm.emergencevg.domain;

import java.util.ArrayList;

/**
 * Luokan ilmentymät sisältävät partikkelien käsittelyyn tarvittavan logiikan
 * Itse partikkeleita käsitellään ohjelman logiikka-avaruudessa int tyyppisinä
 * numeroina, mutta nämä numerot viittaavat samalla partikkelin tyyppiin, jonka
 * arvot määrittävät miten eteneminen seuraavaan iteraatioon tapahtuu.
 */
public class ParticleType {

    public String name;
    public int key;
    public ArrayList<Integer> amountsForNew;
    public ArrayList<Integer> amountsToLive;
    public ArrayList<Integer> displayAttributes;    //i0:Color i1:Form

    /**
     *
     * @param name Partikkelityypin nimi.
     * @param key Partikkelityyppiä vastaava luku.
     * @param amountsForNew Lista sopivista ympäröivien partikkelien määristä,
     * uuden tämän tyypin partikkelin syntymiselle.
     * @param amountsToLive Lista sopivista ympäröivien partikkelien määristä
     * tämän tyypin partikkelin elossa pysymiseksi.
     */
    public ParticleType(String name, int key, ArrayList<Integer> amountsForNew, ArrayList<Integer> amountsToLive, ArrayList<Integer> displayAttributes) {
        this.name = name;
        this.key = key;
        this.amountsForNew = amountsForNew;
        this.amountsToLive = amountsToLive;
        this.displayAttributes = displayAttributes;
    }

    /**
     * predikaatti palauttaa true, jos ympäröivien partikkelien määrä on sopiva
     * uudelle tämän tyypin partikkelin synnylle.
     */
    public boolean generate(int neighbors) {
        for (Integer i : amountsForNew) {
            if (i == neighbors) {
                return true;
            }
        }
        return false;
    }

    /**
     * predikaatti palauttaa true, jos ympäröivien partikkelien määrä on sopiva
     * tämän tyypin partikkelin elossa pysymiseksi.
     */
    public boolean live(int neighbors) {
        for (Integer i : amountsToLive) {
            if (i == neighbors) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
