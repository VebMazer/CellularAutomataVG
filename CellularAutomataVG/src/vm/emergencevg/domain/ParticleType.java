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
    public ArrayList<Integer> displayAttributes;

    /**
     * Luokan konstruktori.
     * @param name Partikkelityypin nimi.
     * @param key Partikkelityyppiä vastaava luku.
     * @param amountsForNew Lista sopivista ympäröivien partikkelien määristä,
     * uuden tämän tyypin partikkelin syntymiselle.
     * @param amountsToLive Lista sopivista ympäröivien partikkelien määristä
     * tämän tyypin partikkelin elossa pysymiseksi.
     * @param displayAttributes Lista Integer muuttujia, joilla voi määrittää
     * partikkelin piirtotyyliä käyttöliittymässä. Ensimmäinen muuttuja kuvaa
     * väriä ja toinen muotoa.
     */
    public ParticleType(String name, int key, ArrayList<Integer> amountsForNew, ArrayList<Integer> amountsToLive, ArrayList<Integer> displayAttributes) {
        this.name = name;
        this.key = key;
        this.amountsForNew = amountsForNew;
        this.amountsToLive = amountsToLive;
        this.displayAttributes = displayAttributes;
    }

    /**
     * Predikaatti palauttaa true, jos ympäröivien partikkelien määrä on sopiva
     * uudelle tämän tyypin partikkelin synnylle.
     * @param neighbors Ympäröivien partikkelien määrä.
     * @return Totuusarvo joka vastaa yllämainittuun kysymykseen.
     */
    public boolean generate(int neighbors) {
        for (Integer i : amountsForNew) {
            if (i == neighbors) return true;
        }
        return false;
    }

    /**
     * predikaatti palauttaa true, jos ympäröivien partikkelien määrä on sopiva
     * tämän tyypin partikkelin elossa pysymiseksi.
     *
     * @param neighbors Ympäröivien partikkelien määrä.
     * @return Totuusarvo joka vastaa yllämainittuun kysymykseen.
     */
    public boolean live(int neighbors) {
        for (Integer i : amountsToLive) {
            if (i == neighbors) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
