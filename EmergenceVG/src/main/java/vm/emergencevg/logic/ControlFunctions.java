package vm.emergencevg.logic;

import java.util.ArrayList;
import java.util.HashMap;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.ui.Updatable;

/**
 * Luokka tarjoaa metodeita joita käytetään, kun halutaan määrittää ohjelman
 * taustaloopin toimintaa, tai syöttää sille dataa.
 */
public class ControlFunctions {

    public GenerativeSpace space;
    public CommandRecordRunner coReRunner;
    public UtilityFunctions uFunctions;
    public FileIO fileIo;
    public Updatable scaleUpdater;
    public int scale;

    /**
     * Konstruktori saa GenerativeSpace olion, jonka kautta se saa, muut
     * arvonsa.
     *
     * @param space Ohjelman logiikka-avaruutta ja taustalooppia ylläpitävä
     * muuttuja.
     */
    public ControlFunctions(GenerativeSpace space) {
        this.space = space;
        this.coReRunner = space.coReRunner;
        this.uFunctions = space.uFunctions;
        this.fileIo = new FileIO(space);
        scale = 5;
    }

    /**
     * Metodi muuntaa käyttöliittymältä saatuja String olioita. ParticleType
     * olion haluamaan malliin.
     *
     * @param input Käyttöliittymältä saatu käyttäjän syöttämä merkkijono.
     * @param displayAttributes Lista Integer muuttujia, joilla määritetään
     * partikkelin piirtotyyliä.
     */
    public void processVariablesToParticleType(String input, ArrayList<Integer> displayAttributes) {
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
        addParticleType(name, amountsForNew, amountsToLive, displayAttributes);
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
     * Etsii ensimmäisen käyttämättömän avaimen logiikka-avaruuden particleTypes
     * HashMapistä ja lisää uuden particleTypes olion samaan mappiin
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
    public void addParticleType(String name, ArrayList<Integer> amountsForNew, ArrayList<Integer> amountsToLive, ArrayList<Integer> displayAttributes) {
        int key = 0;
        for (int i = 1; i < 100; i++) {
            if (space.particleTypes.get(i) == null) {
                key = i;
                break;
            }
        }
        if (key != 0) {
            space.particleTypes.put(key, new ParticleType(name, key, amountsForNew, amountsToLive, displayAttributes));
        }
    }

    /**
     * Määrittää uuden, logiikka-avaruuden ja lisää komennon sen luomiseksi.
     *
     * @param xlength Määrittää logiikka-avaruuden x koordinaatin pituuden.
     * @param ylength Määrittää logiikka-avaruuden y koordinaatin pituuden.
     */
    public void resetFieldCommand(int xlength, int ylength) {
        resetField(xlength, ylength);
        uFunctions.addCommand("fieldSize(" + space.xlength + "," + space.ylength + ")");
    }

    /**
     * Määrittää uuden, logiikka-avaruuden.
     *
     * @param xlength Määrittää logiikka-avaruuden x koordinaatin pituuden.
     * @param ylength Määrittää logiikka-avaruuden y koordinaatin pituuden.
     */
    public void resetField(int xlength, int ylength) {
        boolean wasRunning = space.running;
        if (wasRunning) {
            stop();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println("Slowing thread did not succeed...");
            }
        }
        space.xlength = xlength;
        space.ylength = ylength;
        space.field = new int[xlength][ylength];
        space.resultField = new int[xlength][ylength];
        if (wasRunning) {
            start();
        }
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
    public void clearParticleTypes() {
        stop();
        space.particleTypes = new HashMap<Integer, ParticleType>();
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
        clearParticleTypes();
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
        fileIo.addPresets(filename);
        coReRunner.runPresets();
    }

    /**
     * Käynnistää logiikka-avaruuden iteraatioiden suorituksen.
     */
    public void start() {
        space.running = true;
    }

    /**
     * Pysäyttää logiikka-avaruuden iteraatioiden suorituksen ja lisää
     * määritellyt komennot aikajanaan.
     */
    public void stop() {
        space.running = false;
        uFunctions.uniteCommandMaps();
        uFunctions.unitePresetLists();
    }

    /**
     * Käynnistää, tai pysäyttää logiikka-avaruuden iteraatioiden suorituksen
     * riippuen siitä onko se jo käynnissä, vai ei(Metodi ei ole käytössä tällä
     * hetkellä).
     */
    public void pause() {
        if (space.running = false) {
            start();
        } else {
            stop();
        }
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
        space.speedModifier = Double.parseDouble(speed);
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
        space.field = new int[space.xlength][space.ylength];
        space.resultField = new int[space.xlength][space.ylength];
    }

    /**
     * Asettaa uuden partikkelin logiikka-avaruuteen.
     *
     * @param pKey Partikkelityyppiä kuvaava avain.
     * @param x x akselin koordinaatti johon sijoitus tehdään.
     * @param y y akselin koordinaatti johon sijoitus tehdään.
     */
    public void placeParticle(int pKey, int x, int y) {
        if (x >= 0 && y >= 0 && x < space.xlength && y < space.ylength) {
            if (space.field[x][y] != 0) {
                space.field[x][y] = 0;
                space.resultField[x][y] = 0;
            } else {
                space.field[x][y] = pKey;
                space.resultField[x][y] = pKey;
            }
        }
    }

    public void setScaleUpdater(Updatable updatable) {
        scaleUpdater = updatable;
    }
}
