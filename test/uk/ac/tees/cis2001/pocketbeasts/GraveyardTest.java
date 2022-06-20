/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.cis2001.pocketbeasts;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NoHeal;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NormalAttack;

/**
 *
 * @author JATIN ANEJA
 */
public class GraveyardTest {
    
    public GraveyardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class Graveyard.
     */
    //@Ignore
    @Test
    public void testAdd() {
        System.out.println("add");
        Card card = new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal());
        Graveyard instance = new Graveyard();
        instance.add(card);
        int expResult = 1;
        int result = instance.count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class Graveyard.
     */
    //@Ignore
    @Test
    public void testCount() {
        System.out.println("count");
        Graveyard instance = new Graveyard();
        Card card = new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal());
        Card card2 = new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal());
        Card card3 = new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal());
        instance.add(card);
        instance.add(card2);
        instance.add(card3);
        int expResult = 3;
        int result = instance.count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
