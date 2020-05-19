/**
 * 
 */
package ht.wt;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**                                                
 * Huolehtii Paivat ja Saatilat luokkien välisestä  
 * yhteistyöstä ja välittää näitä tietoja pyydettäessä                                     
 * Lukee ja kirjoittaa WeatherTracker tiedostoon
 * Avustajat: Paivat, Saatilat, Paiva, Saatila      
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 1.3.2020
 *
 */
public class WeatherTracker {
    private Paivat paivat;
    private Saatilat saatilat;
 

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
    
        try {
            new File("kokeilu.db").delete();
            WeatherTracker weathertracker = new WeatherTracker();
            weathertracker.lueTiedostosta("kokeilu");

            Paiva pvm = new Paiva(), pvm1 = new Paiva();
            pvm.taytaPvmTiedoilla();
            pvm1.taytaPvmTiedoilla();
            
            weathertracker.lisaa(pvm);
            weathertracker.lisaa(pvm1);
            //int id1 = pvm.getSaatila();
            //int id2 = pvm1.getSaatila();
            
            System.out.println("=============== Päivän sää ===============");
            Collection<Paiva> paivat = weathertracker.etsi("", -1);
            int i = 0;
            for (Paiva paiva : paivat) {
                System.out.println("Päivän id: " + i);
                paiva.tulosta(System.out);
                i++;
            }
        } catch (SailoException e) {
            e.printStackTrace();
        }

        new File("kokeilu.db").delete();
    }
    
    
    /**
     * Lisätään uusi päivä
     * @param paiva lisättävä
     * @throws SailoException jos ei mahu
     * @example
     * <pre name="test">
     *  WeatherTracker weathertracker = new WeatherTracker();
     *  Paiva pvm = new Paiva(), pvm1 = new Paiva();
     *  weathertracker.getPaivat() === 0;
     *  weathertracker.lisaa(pvm1); weathertracker.getPaivat() === 1;
     *  weathertracker.lisaa(pvm); weathertracker.getPaivat() === 2;
     *  weathertracker.lisaa(pvm1); weathertracker.getPaivat() === 3;
     *  weathertracker.annaPaiva(0) === pvm1;
     *  weathertracker.annaPaiva(1) === pvm;
     *  weathertracker.annaPaiva(2) === pvm1;
     *  weathertracker.annaPaiva(3) === pvm; #THROWS IndexOutOfBoundsException
     *  weathertracker.lisaa(pvm1); weathertracker.getPaivat() === 4;
     *  weathertracker.lisaa(pvm1); weathertracker.getPaivat() === 5;
     *  weathertracker.lisaa(pvm1); weathertracker.getPaivat() === 6;
     * </pre>
     */
    public void lisaa(Paiva paiva) throws SailoException {
       paivat.lisaa(paiva); 
    }
    
    
    /**
     * Lisätään säätila
     * @param saatila lisätään
     * @throws SailoException jos ei mahu
     */
    public void lisaa(Saatila saatila) throws SailoException {
        saatilat.lisaa(saatila); 
     }
    
    
    /**
     * Tallentaa molemmat tiedostot
     * @throws SailoException jos joku menee pieleen
     */
    public void tallenna() throws SailoException {
        return;
    }

    /**
     * Luetaan tiedosto
     * @param nimi tiedoston nimi
     * @throws SailoException jos joku menee pieleen
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        paivat = new Paivat(nimi);
        saatilat = new Saatilat(nimi);   
    }

    
    /**
     * Palauttaa "taulukossa" vastaavien päivien viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenteen löytyvistä päivistä
     * @throws SailoException jos ei mahu
     */
    public Collection<Paiva> etsi(String hakuehto, int k) throws SailoException {
        return paivat.etsi(hakuehto, k);
    }


    /**
     * Poistetaan halutun päivän tiedot
     * @param paiva joka halutaan poistaa
     * @return montako päivää poistettiin
     */
    public int poista(Paiva paiva) {
        return 0;

    }


    public List<Saatila> annaSaatilat(Paiva paiva) throws SailoException {
        return saatilat.annaSaatilat(paiva.getTunnusNro());
    }

}