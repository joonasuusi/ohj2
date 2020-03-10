/**
 * 
 */
package ht.wt;

import java.util.List;

/**
 * TODO: Lisää CRC-kortit
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
        
        try {
        Paiva pvm = new Paiva(), pvm1 = new Paiva();
        pvm.taytaPvmTiedoilla();
        pvm1.taytaPvmTiedoilla();
    
        
            weathertracker.lisaa(pvm);
            weathertracker.lisaa(pvm1);
            Saatila saa1 = new Saatila();
            saa1.paivanSaa();
            weathertracker.lisaa(saa1);
            Saatila saa2 = new Saatila();
            saa2.paivanSaa();
            weathertracker.lisaa(saa2);
            Saatila saa3 = new Saatila();
            saa3.paivanSaa();
            weathertracker.lisaa(saa3);
            Saatila saa4 = new Saatila();
            saa4.paivanSaa();
            weathertracker.lisaa(saa4);
        
        

        System.out.println("=============== Päivän sää ===============");
        for (int i = 0; i < weathertracker.getPaivat(); i++) {
            Paiva paiva = weathertracker.annaPaiva(i);
            System.out.println("Päivämäärä: " + i);
            paiva.tulosta(System.out);
            List<Saatila> loytyneet = weathertracker.annaSaatilat(paiva);
        }
        
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    
    /**
     * Lisätään uusi päivä
     * @param paiva lisättävä
     * @throws SailoException jos ei mahu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
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
     *  weathertracker.lisaa(pvm1); #THROWS SailoException
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
     * 
     * @param paiva päivä jonka säätila halutaan
     * @return lista säätiloista
     */
    public List<Saatila> annaSaatila(Paiva paiva) {
        saatilat.annaSaatila(paiva.getSaa()); //pitäsköhän olla joku tunnusnro...
    }
    
    /**
     * Saadaan päivien lukumäärä
     * @return päivien lukumäärä
     */
    public int getPaivat() {
        return paivat.getLkm();
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
}