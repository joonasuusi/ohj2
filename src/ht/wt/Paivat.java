/**
 * 
 */
package ht.wt;

/**
 * TODO: crc-kortin tiedot Paivat
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 27.2.2020
 *
 */
public class Paivat {
    
    private static final int MAX_PAIVIA = 5;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Paiva alkiot[] = new Paiva[MAX_PAIVIA];

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Paivat paivat = new Paivat();
        Paiva pvm = new Paiva(), pvm1 = new Paiva();
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
     *  #THROWS SailoException 
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
     *  paivat.lisaa(pvm1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Paiva pvm) {
        if (lkm >= alkiot.length) {
            Paiva uusi[] = new Paiva[alkiot.length*2];
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

}
