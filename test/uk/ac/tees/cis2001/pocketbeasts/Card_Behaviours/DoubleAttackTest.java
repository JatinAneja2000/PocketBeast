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
public class DoubleAttackTest {
    
    public DoubleAttackTest() {
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
     * Test of setAttack method, of class DoubleAttack.
     */
    @Test
    //@Ignore
    public void testSetAttack() {
        System.out.println("setAttack");
        int amount = 2;
        DoubleAttack instance = new DoubleAttack();
        instance.setAttack(amount);
        int expresult = instance.getAttack();
        int result  = 4;
        assertEquals(expresult, result);
        // TODO review the generated test code and remove the default call to fail.
     //   fail("The test case is a prototype.");
    }
    
}
