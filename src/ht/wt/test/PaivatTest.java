package ht.wt.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import ht.wt.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.02.27 18:30:23 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class PaivatTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa62 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa62() throws SailoException {    // Paivat: 62
    Paivat paivat = new Paivat(); 
    Paiva pvm = new Paiva(), pvm1 = new Paiva(); 
    assertEquals("From: Paivat line: 66", 0, paivat.getLkm()); 
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 67", 1, paivat.getLkm()); 
    paivat.lisaa(pvm); assertEquals("From: Paivat line: 68", 2, paivat.getLkm()); 
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 69", 3, paivat.getLkm()); 
    assertEquals("From: Paivat line: 70", pvm1, paivat.anna(0)); 
    assertEquals("From: Paivat line: 71", pvm, paivat.anna(1)); 
    assertEquals("From: Paivat line: 72", pvm1, paivat.anna(2)); 
    assertEquals("From: Paivat line: 73", false, paivat.anna(1) == pvm1); 
    assertEquals("From: Paivat line: 74", true, paivat.anna(1) == pvm); 
    try {
    assertEquals("From: Paivat line: 75", pvm, paivat.anna(3)); 
    fail("Paivat: 75 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 76", 4, paivat.getLkm()); 
    paivat.lisaa(pvm1); assertEquals("From: Paivat line: 77", 5, paivat.getLkm()); 
    try {
    paivat.lisaa(pvm1); 
    fail("Paivat: 78 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END
}