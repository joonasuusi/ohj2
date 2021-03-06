package ht.wt.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import ht.wt.*;
import java.util.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.23 21:13:00 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class PaivatTest {



  // Generated by ComTest BEGIN
  /** testLisaa81 */
  @Test
  public void testLisaa81() {    // Paivat: 81
    Paivat paivat = new Paivat(); 
    Paiva pvm = new Paiva(), pvm1 = new Paiva(); 
    assertEquals("From: Paivat line: 84", 0, paivat.getLkm()); 
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 85", 1, paivat.getLkm()); 
    paivat.lisaa(pvm); assertEquals("From: Paivat line: 86", 2, paivat.getLkm()); 
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 87", 3, paivat.getLkm()); 
    assertEquals("From: Paivat line: 88", pvm1, paivat.anna(0)); 
    assertEquals("From: Paivat line: 89", pvm, paivat.anna(1)); 
    assertEquals("From: Paivat line: 90", pvm1, paivat.anna(2)); 
    assertEquals("From: Paivat line: 91", false, paivat.anna(1) == pvm1); 
    assertEquals("From: Paivat line: 92", true, paivat.anna(1) == pvm); 
    try {
    assertEquals("From: Paivat line: 93", pvm, paivat.anna(3)); 
    fail("Paivat: 93 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 94", 4, paivat.getLkm()); 
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 95", 5, paivat.getLkm()); 
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 96", 6, paivat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa168 
   * @throws SailoException when error
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testKorvaaTaiLisaa168() throws SailoException, CloneNotSupportedException {    // Paivat: 168
    Paivat paivat = new Paivat(); 
    Paiva pvm1 = new Paiva(); 
    Paiva pvm2 = new Paiva(); 
    pvm1.rekisteroi(); 
    pvm2.rekisteroi(); 
    assertEquals("From: Paivat line: 177", 0, paivat.getLkm()); 
    paivat.korvaaTaiLisaa(pvm1); 
    assertEquals("From: Paivat line: 179", 1, paivat.getLkm()); 
    paivat.korvaaTaiLisaa(pvm2); 
    assertEquals("From: Paivat line: 181", 2, paivat.getLkm()); 
    Paiva pvm3 = pvm1.clone(); 
    pvm3.setPaikka("Rovaniemi"); 
    Iterator<Paiva> it = paivat.iterator(); 
    assertEquals("From: Paivat line: 185", true, it.next() == pvm1); 
    paivat.korvaaTaiLisaa(pvm3); 
    assertEquals("From: Paivat line: 187", 2, paivat.getLkm()); 
    it = paivat.iterator(); 
    Paiva p0 = it.next(); 
    assertEquals("From: Paivat line: 190", pvm3, p0); 
    assertEquals("From: Paivat line: 191", true, p0 == pvm3); 
    assertEquals("From: Paivat line: 192", false, p0 == pvm1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsi213 */
  @Test
  public void testEtsi213() {    // Paivat: 213
    Paivat paivat = new Paivat(); 
    Paiva paiva1 = new Paiva(); paiva1.parse("1|13.09.2002|Rovaniemi|13.05|"); 
    Paiva paiva2 = new Paiva(); paiva2.parse("2|03.10.2020|Orivesi|13.45|"); 
    Paiva paiva3 = new Paiva(); paiva3.parse("3|20.01.2019|Oulu|20.45|"); 
    Paiva paiva4 = new Paiva(); paiva4.parse("4|24.12.2020|Helsinki|07.45|"); 
    paivat.lisaa(paiva1); paivat.lisaa(paiva2); paivat.lisaa(paiva3); paivat.lisaa(paiva4); 
    List<Paiva> loytyneet; 
    loytyneet = (List<Paiva>)paivat.etsi("*3*", 1); 
    assertEquals("From: Paivat line: 222", 2, loytyneet.size()); 
    assertEquals("From: Paivat line: 223", true, loytyneet.get(0) == paiva2); 
    assertEquals("From: Paivat line: 224", true, loytyneet.get(1) == paiva1); 
    loytyneet = (List<Paiva>)paivat.etsi("*l*", 2); 
    assertEquals("From: Paivat line: 227", 2, loytyneet.size()); 
    assertEquals("From: Paivat line: 228", true, loytyneet.get(0) == paiva4); 
    assertEquals("From: Paivat line: 229", true, loytyneet.get(1) == paiva3); 
    loytyneet = (List<Paiva>)paivat.etsi(null, -1); 
    assertEquals("From: Paivat line: 232", 4, loytyneet.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoista283 */
  @Test
  public void testPoista283() {    // Paivat: 283
    Paivat paivat = new Paivat(); 
    Paiva pv1 = new Paiva(); Paiva pv2 = new Paiva(); Paiva pv3 = new Paiva(); 
    pv1.rekisteroi(); pv2.rekisteroi(); pv3.rekisteroi(); 
    int id1 = pv1.getTunnusNro(); 
    paivat.lisaa(pv1); paivat.lisaa(pv2); paivat.lisaa(pv3); 
    assertEquals("From: Paivat line: 289", 1, paivat.poista(id1+1)); 
    assertEquals("From: Paivat line: 290", null, paivat.annaId(id1+1)); assertEquals("From: Paivat line: 290", 2, paivat.getLkm()); 
    assertEquals("From: Paivat line: 291", 1, paivat.poista(id1)); assertEquals("From: Paivat line: 291", 1, paivat.getLkm()); 
    assertEquals("From: Paivat line: 292", 0, paivat.poista(id1+3)); assertEquals("From: Paivat line: 292", 1, paivat.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaId311 
   * @throws SailoException when error
   */
  @Test
  public void testAnnaId311() throws SailoException {    // Paivat: 311
    Paivat paivat = new Paivat(); 
    Paiva pv1 = new Paiva(); Paiva pv2 = new Paiva(); Paiva pv3 = new Paiva(); 
    pv1.rekisteroi(); pv2.rekisteroi(); pv3.rekisteroi(); 
    int id1 = pv1.getTunnusNro(); 
    paivat.lisaa(pv1); paivat.lisaa(pv2); paivat.lisaa(pv3); 
    assertEquals("From: Paivat line: 318", true, paivat.annaId(id1) == pv1); 
    assertEquals("From: Paivat line: 319", true, paivat.annaId(id1 +1 ) == pv2); 
    assertEquals("From: Paivat line: 320", true, paivat.annaId(id1+2) == pv3); 
  } // Generated by ComTest END
}