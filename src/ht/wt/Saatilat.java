/**
 * 
 */
package ht.wt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

 /**
 * Pitää yllä varsinaista saatilarekisteria eli osaa lisätä ja poistaa säätilan               
 * Lukee ja kirjoittaa saatila tiedostoon        
 * Osaa etsiä ja lajitella
 * Avustaja: Saatila
 * @author Joonas Uusi-Autti ja Sini Lällä
 * @version 2.3.2020
 *
 */
public class Saatilat implements Iterable<Saatila> {
    private int lkm = 0;
    
    /**
     * Taulukko säätiloista
     */
    private final ArrayList<Saatila> alkiot = new ArrayList<Saatila>();
    
    
    /**
     * Säätilojen alustaminen
     */
    public Saatilat() {
        // toistaiseksi ei tartte tehä mitää
    }
    
   
    /**
     * Lisää uuden säätilan tietorakenteeseen
     * @param saa lisättävä säätila
     */
    public void lisaa(Saatila saa) {
        alkiot.add(saa);
        lkm++;
    }
    
    
    /**
     * Palauttaa säätilojen lukumäärän
     * @return säätilojen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    
    @Override
    public Iterator<Saatila> iterator() {
        return alkiot.iterator();
    }
    

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {;
        /*
        Saatila saa = new Saatila();
        Saatila saa1 = new Saatila();
        Saatila saa2 = new Saatila();
        Saatilat aur = new Saatilat();
        
        saa.rekisteroi();
        saa1.rekisteroi();
        saa2.rekisteroi();
        saa.taytaTiedoilla();
        saa1.taytaTiedoilla();
        saa2.taytaTiedoilla();
        
        aur.lisaa(saa);
        aur.lisaa(saa1);
        aur.lisaa(saa2);
        saa.tulosta(System.out);
        saa1.tulosta(System.out);
        saa2.tulosta(System.out);
        */
    }

    
    /**
     * Tallennetaan säätilat tiedostoon
     * @throws SailoException jos tiedosto ei aukea
     */
    public void tallenna() throws SailoException {
        try (PrintStream fo = new PrintStream(new FileOutputStream("saatilat.dat", false))) {
            //fo.println("WeatherTracker"); ei välttämättä tarvitse tai tulostuu joka kerta kun tallennetaan
            for (int i = 0; i < alkiot.size(); i++) {
                Saatila saa = anna(i);
                fo.println(saa.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto ei aukea " + e.getMessage());
        } 
    }
    
    
    /**
     * Luetaan saatilatiedosto
     * @throws SailoException jos tiedosto ei auke
     */
    public void lueTiedostosta() throws SailoException {
        try (Scanner fi = new Scanner(new FileInputStream(new File("saatilat.dat")))) {
          while ( fi.hasNext()) {
              String rivi = fi.nextLine();
              rivi = rivi.trim();
              if ("".equals(rivi) || rivi.charAt(0) == ';') continue; //tarvitaanko?
              Saatila saa = new Saatila("");
              System.out.println(rivi);
              saa.parse(rivi);
              lisaa(saa);
          }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto ei aukea " + e.getMessage());
        } 
    }


    /**
     * Palauttaa viitteen i:teen säätilaan
     * @param i monennenko säätilan viite halutaan
     * @return viite säätilaan jonka indeksi on i
     */
    public Saatila anna(int i) {
        return alkiot.get(i);
    }
}