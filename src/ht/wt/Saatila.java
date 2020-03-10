/**
 * 
 */
package ht.wt;

import java.io.PrintStream;

/**
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 2.3.2020
 *
 */
public class Saatila {
    
    private String saatila = "";
    
    /**
     * Arvotaan satunnainen kokonaisluku välille [ala, ylä]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala, ylä]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot säätilalle
     * TODO: poista kun kun kaikki toimii
     */
    public void paivanSaa() {
        if (rand(0, 600) > 500) saatila = "aurinkoinen";
        else if (rand(0, 600) > 400) saatila = "pilvinen";
        else if (rand(0, 600) > 300) saatila = "puolipilvinen";
        else if (rand(0, 600) > 200) saatila = "vesisade";
        else if (rand(0, 600) > 100) saatila = "räntäsade";
        else if (rand(0, 600) > 0) saatila = "lumisade";
    }
    
    /**
     * Tulostetaan päivän tiedot
     * @param out mihin virtaa tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Säätila: " + saatila);
        
    } 

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Saatila aur = new Saatila();
        Saatila pilvi = new Saatila();
        Saatila sade = new Saatila();
        Saatila lumi = new Saatila();
        aur.paivanSaa();
        pilvi.paivanSaa();
        sade.paivanSaa();
        lumi.paivanSaa();
        
        aur.tulosta(System.out);
        pilvi.tulosta(System.out);
        sade.tulosta(System.out);
        lumi.tulosta(System.out);
    }

}
