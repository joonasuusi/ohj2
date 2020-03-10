package ht.wt;

import java.io.PrintStream;

import static kanta.PaivaLuonti.*;

/**
 * TODO: Paivan CRC-kortti tähän!!!!!
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 27.2.2020
 *
 */
public class Paiva {
    
    private String pvm = "";
    private String paikka = "";
    private String kello = "";
    private double alinLampo = 0.0;
    private double ylinLampo = 0.0;
    private int saatila;
    private double sademaara = 0.0; 
    private String huomiot = "";
    

    /**
     * Hakee päivämäärän
     * @return palauttaa päivämäärän
     */
    public String getPvm() {
        return pvm;
    }
    
    /**
     * @return Säätilan tunnusnumero
     */
    public int getSaa() {
        return saatila;
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Paiva pvm = new Paiva();
        Paiva pvm1 = new Paiva();
        Paiva pvm2 = new Paiva();
        
        pvm.taytaPvmTiedoilla();
        pvm.tulosta(System.out);
        
        pvm1.taytaPvmTiedoilla();
        pvm1.tulosta(System.out);
        
        pvm2.taytaPvmTiedoilla();
        pvm2.tulosta(System.out);
        
    }
    
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
     * Apumetodi, jolla saadaan täytettyä testiarvot päivämäärälle
     * TODO: poista kun kun kaikki toimii
     */
    public void taytaPvmTiedoilla() {
        pvm = arvoPaiva();
        paikka = "Jyväskylä " +rand(1, 310);
        kello = "10:29";
        alinLampo = -2.2;
        ylinLampo = 3.3;
        sademaara = 0.2;
        huomiot = "";
    }

    /**
     * Tulostetaan päivän tiedot
     * @param out mihin virtaa tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(pvm +  ", " + paikka + ", " + kello);
        out.println("Päivän alin lämpötila " + String.format("%2.1f", alinLampo) + 
                "°C" + ", ylin lämpötila " + String.format("%2.1f", ylinLampo) + 
                "°C" + " ja sademäärä " + String.format("%2.1f", sademaara) + "mm");
        out.println("Huomiot: " + huomiot);
    } 
    
}
