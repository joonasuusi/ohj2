/**
 * 
 */
package ht.wt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Joonas Uusi-Autti ja Sini Lällä
 * @version 2.3.2020
 *
 */
public class Saatilat implements Iterable<Saatila> {
    private int lkm = 0;
    private String tiedostonNimi = "";
    
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
        return alkiot.size();
    }
    
    @Override
    public Iterator<Saatila> iterator() {
        return alkiot.iterator();
    }
    
    public List<Saatila> annaSaatilat(int i) {
        List<Saatila> loydetyt = new ArrayList<Saatila>();
        for (Saatila saa : alkiot)
            if (saa.rand(0, 6) == lkm) loydetyt.add(saa);
        
        return loydetyt;
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Saatilat saatilat = new Saatilat();
        Saatila aur = new Saatila();
        Saatila pilvi = new Saatila();

        Saatila puolipilvi = new Saatila();
        Saatila sade = new Saatila();
        aur.paivanSaa();
        pilvi.paivanSaa();
        puolipilvi.paivanSaa();
        sade.paivanSaa();
        
        saatilat.lisaa(aur);
        saatilat.lisaa(pilvi);
        saatilat.lisaa(puolipilvi);
        saatilat.lisaa(sade);
    
    }

}
