Käyttöohjeet

Kaiken kattava esityksen luonti opastus:

1.Määrittele partikkelit joita aiot käyttää(vapaaehtoista): 
- Tämä tapahtuu tekstinsyöttökentässä, joka sijaitsee Tagin "Describe particleType:" ja 
sitä seuraavien esimerkki tagien alla. Partikkelin määrittelyn tulisi olla formaattia: 
(nimi, 2 3, 3 4)  (Sulkeet eivät kuulu määrittelyyn.) Määrittelyyn kuuluu siis nimi ja 
kaksi lukujoukkoa joiden pituus voi olla, mikä tahansa, tosin vain luvuilla (1-8) on 
merkitystä ja duplikaatit ovat merkityksettömiä jonossa. Jos ensimmäisen joukon 
lukuja haluaa jättää pois voi käyttää ",," merkintää ja kirjoittaa esim. (nimi,, 3 4) ja 
toisen joukon voi halutessaan yksinkertaisesti jättää kirjoittamatta esim. (nimi, 2 3).
- Ensimmäisen joukon luvut kuvaavat niitä määriä ympäröiviä partikkeleita, joilla uusi
partikkeli luodaan tyhjään kohtaan jo olemassa olevien partikkelien vieressä. Toisin 
sanoen missä tilanteissa partikkelit leviävät.
- Toisen joukon luvut puolestaan kuvaavat niitä määriä ympäröiviä partikkeleita, joilla
jo olemassa oleva partikkeli pysyy "elossa" paikallaan. Jos määrä on siis, jokin muu 
kuin jokin määrittelyissä mainituista, niin partikkeli katoaa.
- Voita valita tekstikentän alapuolella olevista listoista partikkelillesi värin ja muodon
- Kun olet kirjoittanut partikkelisi määrittelyn tekstikenttään ja kursorisi on vielä
tekstikentässä paina <enter> painiketta näppäimistössäsi. Partikkelisi löytyy tämän 
jälkeen listasta.

2.Simulaatioalustan koon ja piirtoskaalan määrittely:
- Oletus arvot ovat (x = 180) ja (y = 130). Piirto skaala on oletuksena 5.Nämä
arvot on konfiguroitu valmista ikkunaa varten sopiviksi. x akseli etenee 
oikealle ja y akseli alaspäin. Piirto skaala määrittelee, kuinka monen pikselin mittaisia 
piirrettävät partikkelit ovat. Ohjelma siis piirtää mustalle alustalle näkyviin kaiken 
mikä siihen mahtuu ja jos piirtoskaala on niin pieni, ettei koko alusta täyty
niin piirtoalue löytyy vasemmasta yläkulmasta mustaa alustaa.
- Voit halutessasi muuttaa simulaatio alustan kokoa määrittelemmä sen akselien
x ja y mitat kirjoittamalla haluamasi kokonaisluku arvot tagien "fieldX" ja "fieldY" 
alta löytyviin kenttiin ja tämän jälkeen painamalla nappia setFieldSize.
- Tämän jälkeen kannattaa yleensä säätää alustan piirtoskaala sopivaksi. Tämä 
tapahtuu määrittelemmä sen kokonaisluku arvo tekstikenttään tagin 
"particleScale(5):" alapuolella ja painamalla kursorin vielä ollessa tekstikentässä 
<enter> painiketta näppäimistössäsi.
- Simulaatioalustan koon muokkaaminen pysäyttää alustan ja tyhjentää sen
partikkeleista. Piirtoskaalan muokkaaminen ei vaikuta alustan toimintaan
ja sitä voikin vapaasti säätää samalla kun seuraa alustan iteraatioiden etenemistä.

3.Partikkelien sijoittaminen alustalle.
- Valitse mitä partikkeli tyyppiä haluat piirtää listasta tagin "Choose particle:"
alapuolella.
- Voit nyt sijoittaa valitsemasi partikkelityypin partikkeleita mustalle alustalle
hiiren painikkeilla. Pidä painiketta pohjassa jos halua piirtää useamman partikkelin
kerralla.
- Voit sijoittaa useamman tyypin partikkeleita alustalle vapaasti.
- Koko alusta toimii jos simulaatioalustan koko ja piirtoskaala on määritelty oikein, 
tai et muokannut arvoja, tai ikkunan kokoa ohjelman käynnistettyäsi.
- clearParticleTypes tyhjentää partikkelityyppi listan ja samalla

4.Simulaatio alustan suoritus.
- Simulaatio alustan suorituksen voi käynnistää painamalla Start nappulaa.
- Suorituksen nopeutta voi säättää kirjoittamalla halutun nopeuden kerroin
arvon tagin "Speed(0.2)" alapuolella olevaan tekstikenttään ja painamalla kursorin 
vielä ollessa tekstikentässä <enter> painiketta näppäimistössäsi. Annetun arvon
tulee olla kokonais-, tai desimaaliluku. Oletus arvo on 0.2
- Suorituksen voi pysäyttää painamalla Stop nappulaa.

5.Esityksen luominen
- Ohjelma ylläpitää aikamuuttujaa iterations. Sen arvo kasvaa simulaatio alustan
ollessa käynnissä. 
- Ohjelma tallentaa partikkelien piirrot, Clear nappulan käytön, simulaation 
suoritus nopeuden muokkaamisen, simulaatioalustan koon muokkaamisen ja
piirtoskaalan muokkaamisen komentoina muistiin niin, että ne sidotaan
iterations muuttujan arvoon komennon suoritus hetkellä.
-  Iterations muuttujan arvo näkyy tekstikentässä "iteration:" tagin alapuolella.
sitä voi muokata silloin kun simulaatio on pysäytetty määrittelemällä halutun 
kokonaisluku arvon tähän tekstikenttään. Näin siis siirrytän ajassa tähän 
iteraatioon ja simulaatio käynnistettäessä alkaa ohjelma suorittaa vastaan
tuleviin iterations muuttujan arvoihin sidottuja komentoja. Esim. jos teit
jotain alustan ollessa käynnissä ja nollaat tämän muuttujan ja aloitat 
suorituksen uusiksi suorittaa ohjelma uudelleen jo suorittamasi komennot
ajan edetessä.
- Kaikki iterations muuttujan arvoihin sidotut komennot voi poistaa painamalla
nappia clearRecord.
-Älä tyhjennä partikkeli tyyppi listaa komentojesi määrittelyn jälkeen  jos haluat 
piirtely komentojesi toimivan esityksessä.

6.Tiedostojen tallentaminen ja lataaminen.
- Tallennettavan tai ladattavan tiedoston nimi kiroitetaan tagin "Filename:" 
alapuolella olevaan tekstilaatikkoon.
- Save nappula tallentaa esityksen ja partikkelityypit nimettyyn tiedostoon.
- LoadPresentation nappula Tyhjentää alustan partikkeleista, komennoista
ja partikkelityypeistä ja korvaa partikkelityypit ja komennot tiedostosta
ladatuilla arvoilla.
- LoadParticleTypes nappula lisää tiedostoon tallennetut partikkelityypit
partikkelityyppi listaan.
