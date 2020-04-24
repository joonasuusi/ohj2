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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

 /**
 * Pitää yllä varsinaista paivarekisteriä eli osaa lisätä ja poistaa päivän                         
 * Lukee ja kirjoittaa paivan tiedostoon            
 * Osaa etsiä ja lajitella
 * Avustaja: Paiva     
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 27.2.2020
 *
 */
public class Paivat implements Iterable<Paiva> {
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
     * Korvaa päivän tietorakenteessa. Ottaa päivän omistukseensa. 
     * Etsitään samalla tunnusnumerola oleva päivä jos ei löyvy nii lisätään
     * uutena päivänä.
     * @param paiva korvattava tai lisättävän päivä viite
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     *  #THROWS SailoException, CloneNotSupportedException
     *  #PACKAGEIMPORT
     *  #import java.util.*;
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

    /**
     * palauttaa "taulukossa" hakuehtoa vastaavien päivien viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä päivistä
     * @example
     * <pre name="test">
     *  Paivat paivat = new Paivat();
     *  Paiva paiva1 = new Paiva(); paiva1.parse("1|13.09.2002|Rovaniemi|13.05|");
     *  Paiva paiva2 = new Paiva(); paiva2.parse("2|03.10.2020|Orivesi|13.45|");
     *  Paiva paiva3 = new Paiva(); paiva3.parse("3|20.01.2019|Oulu|20.45|");
     *  Paiva paiva4 = new Paiva(); paiva4.parse("4|24.12.2020|Helsinki|07.45|");
     *  paivat.lisaa(paiva1); paivat.lisaa(paiva2); paivat.lisaa(paiva3); paivat.lisaa(paiva4);
     *  List<Paiva> loytyneet;
     *  loytyneet = (List<Paiva>)paivat.etsi("*3*", 1);
     *  loytyneet.size() === 2;
     *  loytyneet.get(0) == paiva2 === true;
     *  loytyneet.get(1) == paiva1 === true;
     *  
     *  loytyneet = (List<Paiva>)paivat.etsi("*l*", 2);
     *  loytyneet.size() === 2;
     *  loytyneet.get(0) == paiva4 === true;
     *  loytyneet.get(1) == paiva3 === true;
     *  
     *  loytyneet = (List<Paiva>)paivat.etsi(null, -1);
     *  loytyneet.size() === 4;
     * </pre>
     */
    public Collection<Paiva> etsi(String hakuehto, int k) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto;
        int hk = k;
        if (hk < 0) hk = 1;
        List<Paiva> loytyneet = new ArrayList<Paiva>();
        for (Paiva paiva : this) {
            if (WildChars.onkoSamat(paiva.anna(hk), ehto))
                loytyneet.add(paiva);
        }
        Collections.sort(loytyneet, new Paiva.Vertailija(hk));
        Collections.reverse(loytyneet);
        return loytyneet;
    }
    
    /**
     * @author Joonas Uusi-Autti ja Sini Lällä
     * @version 8.4.2020
     *
     */
    public class PaivatIterator implements Iterator<Paiva> {
        private int kohdalla = 0;

        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }

        @Override
        public Paiva next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException("Ei oo!");
            return anna(kohdalla++);
        }
           
    }


    @Override
    public Iterator<Paiva> iterator() {
        return new PaivatIterator();
    }


    /**
     * Poistetaan valittua tunnusnumeroa vastaava päivämäärä
     * @param tunnusNro poistettavan päivän tunnusnumero
     * @return 1, jos poistettiin, 0 jos ei löydy
     * @example
     * <pre name="test">
     * Paivat paivat = new Paivat();
     * Paiva pv1 = new Paiva(); Paiva pv2 = new Paiva(); Paiva pv3 = new Paiva();
     * pv1.rekisteroi(); pv2.rekisteroi(); pv3.rekisteroi();
     * int id1 = pv1.getTunnusNro();
     * paivat.lisaa(pv1); paivat.lisaa(pv2); paivat.lisaa(pv3);
     * paivat.poista(id1+1) === 1;
     * paivat.annaId(id1+1) === null; paivat.getLkm() === 2;
     * paivat.poista(id1) === 1; paivat.getLkm() === 1;
     * paivat.poista(id1+3) === 0; paivat.getLkm() === 1;
     * </pre>
     */
    public int poista(int tunnusNro) {
        int ind = etsiId(tunnusNro); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        //muutettu = true; 
        return 1;
    }
    
    /**
     * Etsii päivän id:n perusteella
     * @param id tunnusnumero, jonka mukaan etsitään
     * @return päivä, jolla etsittävä id tai null
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Paivat paivat = new Paivat();
     * Paiva pv1 = new Paiva(); Paiva pv2 = new Paiva(); Paiva pv3 = new Paiva();
     * pv1.rekisteroi(); pv2.rekisteroi(); pv3.rekisteroi();
     * int id1 = pv1.getTunnusNro();
     * paivat.lisaa(pv1); paivat.lisaa(pv2); paivat.lisaa(pv3);
     * paivat.annaId(id1) == pv1 === true;
     * paivat.annaId(id1 +1 ) == pv2 === true;
     * paivat.annaId(id1+2) == pv3 === true;
     * </pre>
     */
    public Paiva annaId(int id) {
        for(Paiva paiva : this) {
            if ( id == paiva.getTunnusNro()) return paiva;
        }
        return null;
    }
    
    /**
     * 
     * @param id päivän id mitä etsitään
     * @return palauttaa i:n jos löytyy, muuten -1
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    } 

}