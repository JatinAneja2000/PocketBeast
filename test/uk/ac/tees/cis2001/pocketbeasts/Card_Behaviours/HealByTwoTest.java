/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author JATIN ANEJA
 */
public class HealByTwoTest {
    
    public HealByTwoTest() {
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
     * Test of setHealth method, of class HealByTwo.
     */
    @Test
    //@Ignore
    public void testSetHealth() {
        System.out.println("setHealth");
        int health = 2;
        HealByTwo instance = new HealByTwo();
        instance.setHealth(health);
        int expresult = instance.getHealth();
        int result  = 4;
        assertEquals(expresult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
    
}
