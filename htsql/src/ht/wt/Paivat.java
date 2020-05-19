/**
 * 
 */
package ht.wt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

import static ht.wt.Kanta.alustaKanta;

 /**
 * Pitää yllä varsinaista paivarekisteriä eli osaa lisätä ja poistaa päivän                         
 * Lukee ja kirjoittaa paivan tiedostoon            
 * Osaa etsiä ja lajitella
 * Avustaja: Paiva     
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 27.2.2020
 *
 */
public class Paivat { //implements Iterable<Paiva> {
    private Kanta kanta;
    private static Paiva apuPaiva = new Paiva();


    /**
     * Tarkistetaan että kannassa päivien tarvitsema taulu
     * @param nimi tietokannan nimi
     * @throws SailoException jos jokin menee pieleen
     */
    public Paivat(String nimi) throws SailoException {
        kanta = alustaKanta(nimi);
        try ( Connection con = kanta.annaKantayhteys() ) {
            // Hankitaan tietokannan metadata ja tarkistetaan siitä onko
            // Jasenet nimistä taulua olemassa.
            // Jos ei ole, luodaan se. Ei puututa tässä siihen, onko
            // mahdollisesti olemassa olevalla taululla oikea rakenne,
            // käyttäjä saa kuulla siitä virheilmoituksen kautta
            DatabaseMetaData meta = con.getMetaData();
            
            try ( ResultSet taulu = meta.getTables(null, null, "Paivat", null) ) {
                if ( !taulu.next() ) {
                    // Luodaan Jasenet taulu
                    try ( PreparedStatement sql = con.prepareStatement(apuPaiva.annaLuontilauseke()) ) {
                        sql.execute();
                    }
                }
            }
            
        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        try {
            new File("kokeilu.db").delete();
            Paivat paivat = new Paivat("kokeilu");
            Paiva pvm = new Paiva(), pvm1 = new Paiva();

            pvm.taytaPvmTiedoilla();
            pvm1.taytaPvmTiedoilla();
            
            paivat.lisaa(pvm);
            paivat.lisaa(pvm1);
            pvm1.tulosta(System.out);
            
            System.out.println("=============== Päivän sää ===============");
            int i = 0;
            for (Paiva paiva : paivat.etsi("", -1)) {
                System.out.println("Päivämäärä: " + i++);
                paiva.tulosta(System.out);
            }
            new File("kokeilu.db").delete();
        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
           
    }

    
    /**
     * Lisää uuden päivämäärän tietorakenteeeseen.
     * Ottaa päivämäärän omistukseensa.
     * @param paiva Lisättävän päivämäärän viite. Huom. tietorakenne muuttuu omistajaksi.
     * @throws SailoException jos ei mahu
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
    public void lisaa(Paiva paiva) throws SailoException {
        try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = paiva.annaLisayslauseke(con) ) {
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
               paiva.tarkistaId(rs);
            }   
            
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }


    /**
     * palauttaa "taulukossa" hakuehtoa vastaavien päivien viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä päivistä
     * @throws SailoException jos ei mahu
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
    public Collection<Paiva> etsi(String hakuehto, int k) throws SailoException {
        String ehto = hakuehto;
        String kysymys = apuPaiva.getKysymys(k);
        if ( k < 0 ) { kysymys = apuPaiva.getKysymys(0); ehto = ""; }
        // Avataan yhteys tietokantaan try .. with lohkossa.
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Paivat WHERE " + kysymys + " LIKE ?") ) {
            ArrayList<Paiva> loytyneet = new ArrayList<Paiva>();
            
            sql.setString(1, "%" + ehto + "%");
            try ( ResultSet tulokset = sql.executeQuery() ) {
                while ( tulokset.next() ) {
                    Paiva p = new Paiva();
                    p.parse(tulokset);
                    loytyneet.add(p);
                }
            }
            return loytyneet;
        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }

}