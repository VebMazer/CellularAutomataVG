Ohjelman rakennekuvaus:

Ohjelman toiminnan keskeisin komponentti on GenerativeSpace luokka, joka ylläpitää
ohjelman taustalooppia , metodissaan run(). Tämä luokka myös ylläpitää ohjelman taustalla
olevaa logiikka-avaruutta, jossa ohjelman tuottamat partikkeliefektit tapahtuvat.

Partikkelit, joiden käsittelyyn ohjelma perustuu ovat GenerativeSpace luokan taulukossa field
niitten tyyppiin viittaavan int tyyppisen avaimen muodossa. Avaimet viittaavat HashMapin
particleTypes sisältämiin ParticleType olioihin, jotka sisältävät informaation siitä miten metodin 
particleProcess() tulisi menetellä, kyseisten avainten(partikkeleiden) kanssa. logiikka-avaruus 
elää elämäänsä niin, että computeNextIteration() metodi laskee field taulukon seuraavan 
iteraation taulukkoon resultField ja kun tämä on tehty metodi updateField() päivittää nämä 
arvot field taulukkoon ja alustaa taulukon resultField uusiksi.

ParticleType oliot vastaavat kahdentyyppisiin kysymyksiin, joita kuvaavat metodit 
generate(int neighbors) ja live(int neighbors), jotka palauttavat totuusarvon, joka
riippuu siitä kuuluuko niille annettu arvo partikkelityypille määriteltyyn joukkoon.
generate metodilta kysytään luodaanko positioon johon harkitaan uuden tämän tyypin partikkelin 
luomista uusi partikkeli vai ei. live metodilta taas kysytään pysyykö kyseinen partikkeli elossa vai ei.
Molempien tulos riippuu siitä, kuinka monta naapuria(neighbors) ympäröi kyseistä positiota.

ControlFunctions luokka toimii ohjelman ensisijaisena hallintatyökaluna. Sen, kautta käsitellään
käyttöliittymässä vastaanotetut käskyt, ja muokkataan ohjelman sisäistä tilaa. Myös tallennetut
esitykset toimivat tämän luokan tuottamien komentojen ja metodien kautta.

MouseController vastaanottaa dataa hiiren kuuntelijalta ja suorittaa sen käskyjen mukaan toimintoja.
luokan pääosallinen tehtävä on selvittää minkä tyypin partikkeleita sijoitetaan ja minne, kunnes ne 
sijoitetaan alustalle ControlFunctions luokan metodin kautta.

CommandRecordRunner ylläpitää informaatiota käyttäjän interaktioista ja tallentaa niitä mappiin
käyttäen iterations muuttujaa avaimena.(Näin ne osataan uudelleen suorittaa oikeaan aikaan.)
luokan ilmentymä siis myös suorittaa komentoja, jos suoritettavalle iteraatiolle on tallennettu niitä.
Luokan toiminta ilmenee siis, kun siihen tallennetaan uusia komentoja ControlFunctions luokan 
metodien kautta. Se myös suorittaa tallennettuja komentoja kutsumalla ControlFunctions 
luokan metodeita. 

CommandRecordRunner toimii yhdessä myös luokan fileIO kanssa niin, että sen
avulla muistetut komennot voidaan tallentaa tiedostoon. Vastaavasti komennot
voidaan myös ladata tiedostosta. Näin voidaan siis tallentaa ja ladata käyttäjän luomia esityksiä
toistamalla käyttäjän suorittamia komentoja, jotka muokkaavat alustaa.

UtilityFunctions luokka sisältää metodeja merkkijonojen parserointiin, muuttujien käsittelyyn, ohjelman tilan
tarkasteluun ja ylläpitoon. Sen ilmentymää hyödynnetään, monessa osassa ohjelmaa.

Updatable rajapintaa hyödynnetään, kun käyttöliittymä olioiden päivittämiseen logiikka olioiden kautta.
GenerativeSpace päivittää piirtoalustaa, ControlFucntions piirto skaala muuttujaa käyttöliittymälle kuuntelijan 
kautta ja CommandRecordRunner iterations muuttujaa käyttöliittymän näytettäväksi kuuntelijan kautta.
