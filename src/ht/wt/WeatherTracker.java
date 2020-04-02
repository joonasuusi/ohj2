/**
 * 
 */
package ht.wt;

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
    Paivat paivat = new Paivat();
    Saatilat saatilat = new Saatilat();
 

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        WeatherTracker weathertracker = new WeatherTracker();
        
        Paiva pvm = new Paiva(), pvm1 = new Paiva();
        pvm.rekisteroi();
        pvm1.rekisteroi();
        pvm.taytaPvmTiedoilla();
        pvm1.taytaPvmTiedoilla();
    
        weathertracker.lisaa(pvm);
        weathertracker.lisaa(pvm1);
        
        System.out.println("=============== Päivän sää ===============");
        for (int i = 0; i < weathertracker.getPaivat(); i++) {
            Paiva paiva = weathertracker.annaPaiva(i);
            System.out.println("Päivämäärä: " + i);
            paiva.tulosta(System.out);
        }
    }
    
    
    /**
     * Lisätään uusi päivä
     * @param paiva lisättävä
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
    public void lisaa(Paiva paiva) {
       paivat.lisaa(paiva); 
    }
    
    
    /**
     * Lisätään säätila
     * @param saatila lisätään
     */
    public void lisaa(Saatila saatila) {
        saatilat.lisaa(saatila); 
     }
    
    
    /**
     * Saadaan päivien lukumäärä
     * @return päivien lukumäärä
     */
    public int getPaivat() {
        return paivat.getLkm();
    }
    
    /**
     * Saadaan säätilojen lukumäärä
     * @return säätilojen lukumäärä
     */
    public int getSaatilat() {
        return saatilat.getLkm();
    }
    
    
    /**
     * Palauttaa i:n päivän
     * @param i monesko päivä palautetaan
     * @return viite i:teen päivään
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Paiva annaPaiva(int i) throws IndexOutOfBoundsException {
        return paivat.anna(i);
    }
    

    /**
     * Palauttaa i:n säätilan
     * @param i monesko säätila palautetaan
     * @return viite i:teen söötilaan
     */
    public Saatila annaSaa(int i) {
        return saatilat.anna(i);
    }

    
    /**
     * Tallentaa molemmat tiedostot
     * @throws SailoException jos joku menee pieleen
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            paivat.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        try {
            saatilat.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }

        if (virhe.length() > 0 )
            throw new SailoException(virhe);
    }

    /**
     * Luetaan tiedosto
     * @throws SailoException jos joku menee pieleen
     */
    public void lueTiedostosta() throws SailoException {
        paivat.lueTiedostosta();
        saatilat.lueTiedostosta();    
    }
    
    
    /**
     * Korvaa päivän tietorakenteessa. Ottaa päivän omistukseensa. 
     * Etsitään samalla tunnusnumerola oleva päivä jos ei löyvy nii lisätään
     * uutena päivänä.
     * @param paiva korvattava tai lisättävän päivä viite
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    public void korvaaTaiLisaa(Paiva paiva) throws SailoException {
        paivat.korvaaTaiLisaa(paiva);
    }
}