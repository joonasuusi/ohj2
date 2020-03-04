package ht.wt.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import ht.wt.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.03.01 16:55:46 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class WeatherTrackerTest {


  // Generated by ComTest BEGIN
  /** 
   * testLisaa50 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa50() throws SailoException {    // WeatherTracker: 50
    WeatherTracker weathertracker = new WeatherTracker(); 
    Paiva pvm = new Paiva(), pvm1 = new Paiva(); 
    assertEquals("From: WeatherTracker line: 54", 0, weathertracker.getPaivat()); 
    weathertracker.lisaa(pvm1); assertEquals("From: WeatherTracker line: 55", 1, weathertracker.getPaivat()); 
    weathertracker.lisaa(pvm); assertEquals("From: WeatherTracker line: 56", 2, weathertracker.getPaivat()); 
    weathertracker.lisaa(pvm1); assertEquals("From: WeatherTracker line: 57", 3, weathertracker.getPaivat()); 
    assertEquals("From: WeatherTracker line: 58", pvm1, weathertracker.annaPaiva(0)); 
    assertEquals("From: WeatherTracker line: 59", pvm, weathertracker.annaPaiva(1)); 
    assertEquals("From: WeatherTracker line: 60", pvm1, weathertracker.annaPaiva(2)); 
    try {
    assertEquals("From: WeatherTracker line: 61", pvm, weathertracker.annaPaiva(3)); 
    fail("WeatherTracker: 61 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    weathertracker.lisaa(pvm1); assertEquals("From: WeatherTracker line: 62", 4, weathertracker.getPaivat()); 
    weathertracker.lisaa(pvm1); assertEquals("From: WeatherTracker line: 63", 5, weathertracker.getPaivat()); 
    try {
    weathertracker.lisaa(pvm1); 
    fail("WeatherTracker: 64 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END
}