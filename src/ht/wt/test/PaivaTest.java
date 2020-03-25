package ht.wt.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import ht.wt.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.03.20 09:41:24 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class PaivaTest {



  // Generated by ComTest BEGIN
  /** testToString45 */
  @Test
  public void testToString45() {    // Paiva: 45
    Paiva paiva = new Paiva(); 
    paiva.parse("3  |    12.3.2020   |   Orivesi       |  07:18"); 
    assertEquals("From: Paiva line: 48", true, paiva.toString().startsWith("3|12.3.2020|Orivesi|07:18|")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi136 */
  @Test
  public void testRekisteroi136() {    // Paiva: 136
    Paiva paiva1 = new Paiva(); 
    assertEquals("From: Paiva line: 138", 0, paiva1.getTunnusNro()); 
    paiva1.rekisteroi(); 
    Paiva paiva2 = new Paiva(); 
    paiva2.rekisteroi(); 
    int n1 = paiva1.getTunnusNro(); 
    int n2 = paiva2.getTunnusNro(); 
    assertEquals("From: Paiva line: 144", n2-1, n1); 
  } // Generated by ComTest END
}