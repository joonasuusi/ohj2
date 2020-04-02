/**
 * 
 */
package ht.wt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

 /**
 * Pitää yllä varsinaista paivarekisteriä eli osaa lisätä ja poistaa päivän                         
 * Lukee ja kirjoittaa paivan tiedostoon            
 * Osaa etsiä ja lajitella
 * Avustaja: Paiva     
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 27.2.2020
 *
 */
public class Paivat {
    private static final int MAX_PAIVIA = 5;
    private int lkm = 0;
    private Paiva alkiot[] = new Paiva[MAX_PAIVIA];

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Paivat paivat = new Paivat();
        Paiva pvm = new Paiva(), pvm1 = new Paiva();
        
        pvm.rekisteroi();
        pvm1.rekisteroi();
        pvm.taytaPvmTiedoilla();
        pvm1.taytaPvmTiedoilla();
        
        paivat.lisaa(pvm);
        paivat.lisaa(pvm1);
        paivat.lisaa(pvm1);
        paivat.lisaa(pvm1);
        paivat.lisaa(pvm1);
        paivat.lisaa(pvm1);

        
            System.out.println("=============== Päivän sää ===============");
            for (int i = 0; i < paivat.getLkm(); i++) {
                Paiva paiva = paivat.anna(i);
                System.out.println("Päivämäärä: " + i);
                paiva.tulosta(System.out);
            }
        }

    
    /**
     * Palauttaa päivien lukumäärän
     * @return päivien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }

    
    /**
     * 
     * Lisää uuden päivämäärän tietorakenteeeseen.
     * Ottaa päivämäärän omistukseensa.
     * @param pvm Lisättävän päivämäärän viite. Huom. tietorakenne muuttuu omistajaksi.
     * @example
     * <pre name="test">
     *  Paivat paivat = new Paivat();
     *  Paiva pvm = new Paiva(), pvm1 = new Paiva();
     *  paivat.getLkm() === 0;
     *  paivat.lisaa(pvm1); paivat.getLkm() === 1;
     *  paivat.lisaa(pvm); paivat.getLkm() === 2;
     *  paivat.lisaa(pvm1); paivat.getLkm() === 3;
     *  paivat.anna(0) === pvm1;
     *  paivat.anna(1) === pvm;
     *  paivat.anna(2) === pvm1;
     *  paivat.anna(1) == pvm1 === false;
     *  paivat.anna(1) == pvm === true;
     *  paivat.anna(3) === pvm; #THROWS IndexOutOfBoundsException
     *  paivat.lisaa(pvm1); paivat.getLkm() === 4;
     *  paivat.lisaa(pvm1); paivat.getLkm() === 5;
     *  paivat.lisaa(pvm1); paivat.getLkm() === 6;
     * </pre>
     */
    public void lisaa(Paiva pvm) {
        if (lkm >= alkiot.length) {
            Paiva uusi[] = new Paiva[alkiot.length+10];
            for (int i = 0; i < alkiot.length; i++) {
                uusi[i] = alkiot[i];
            }
            alkiot = uusi;
        }
        alkiot[lkm] = pvm;
        lkm++;
    }

    
    /**
     * Palauttaa viitteen i:teen päivämäärään
     * @param i monennenko päivämäärän viite halutaan
     * @return viite päivämäärään jonka indeksi on i
     * @throws IndexOutOfBoundsException Jos i ei ole sallitulla alueella
     */
    public Paiva anna(int i) throws IndexOutOfBoundsException {  
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    
    /**
     * Tallennetaan paivatiedosto
     * @throws SailoException jos tiedosto ei aukea
     */
    public void tallenna() throws SailoException {
        try (PrintStream fo = new PrintStream(new FileOutputStream("paivat.dat", false))) {
            //fo.println("WeatherTracker"); ei välttämättä tarvitse tai tulostuu joka kerta kun tallennetaan
            for (int i = 0; i < getLkm(); i++) {
                Paiva paiva = anna(i);
                fo.println(paiva.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto ei aukea " + e.getMessage());
        }         
    }

    
    /**
     * Luetaan paivatiedosto
     * @throws SailoException jos tiedosto ei auke
     */
    public void lueTiedostosta() throws SailoException {
        try (Scanner fi = new Scanner(new FileInputStream(new File("paivat.dat")))) {
          while ( fi.hasNext()) {
              String rivi = fi.nextLine();
              rivi = rivi.trim();
              if ("".equals(rivi) || rivi.charAt(0) == ';') continue; //tarvitaanko?
              Paiva paiva = new Paiva();
              paiva.parse(rivi);
              lisaa(paiva);
          }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto ei aukea " + e.getMessage());
        } 
    }
    
    
    /**
     * TODO: korjaa testi
     * Korvaa päivän tietorakenteessa. Ottaa päivän omistukseensa. 
     * Etsitään samalla tunnusnumerola oleva päivä jos ei löyvy nii lisätään
     * uutena päivänä.
     * @param paiva korvattava tai lisättävän päivä viite
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     *  #THROWS SailoException, CloneNotSupportedException
     *  #PACKAGEIMPORT
     *  Paivat paivat = new Paivat();
     *  Paiva pvm1 = new Paiva();
     *  Paiva pvm2 = new Paiva();
     *  pvm1.rekisteroi();
     *  pvm2.rekisteroi();
     *  paivat.getLkm() === 0;
     *  paivat.korvaaTaiLisaa(pvm1);
     *  paivat.getLkm() === 1;
     *  paivat.korvaaTaiLisaa(pvm2);
     *  paivat.getLkm() === 2;
     *  Paiva pvm3 = pvm1.clone();
     *  pvm3.setPaikka("Rovaniemi");
     *  Iterator<Paiva> it = paivat.iterator();
     *  it.next() == pvm1 === true;
     *  paivat.korvaaTaiLisaa(pvm3);
     *  paivat.getLkm() === 2;
     *  it = paivat.iterator();
     *  Paiva p0 = it.next();
     *  p0 === pvm3;
     *  p0 == pvm3 === true;
     *  p0 == pvm1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Paiva paiva) throws SailoException {
        int id = paiva.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getTunnusNro() == id) {
                alkiot[i] = paiva;
                return;
            }
        }
        lisaa(paiva);
    }
}