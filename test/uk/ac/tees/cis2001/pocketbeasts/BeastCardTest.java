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
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.AttackBehaviour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.DoubleAttack;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NoHeal;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NormalAttack;

/**
 *
 * @author JATIN ANEJA
 */
public class BeastCardTest {
    
    public BeastCardTest() {
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
     * Test of setAttackBehaviour method, of class BeastCard.
     */
    //@Ignore
    @Test
    public void testSetAttackBehaviour() {
        System.out.println("setAttackBehaviour");
        AttackBehaviour a = new DoubleAttack();
        BeastCard instance = new BeastCard("BR", "Barn Rat", 1, 1, 1, new NormalAttack(), new NoHeal());
        instance.setAttackBehaviour(a);
        AttackBehaviour expResult = a;
        AttackBehaviour result = instance.getAttackBehaviour();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    // @Ignore
    /**
     * Test of getAttack method, of class BeastCard.
     */
    @Test
    public void testGetAttack() {
        System.out.println("getAttack");
        BeastCard instance = new BeastCard("BR", "Barn Rat", 1, 1, 1, new NormalAttack(), new NoHeal());
        int expResult = 1;
        int result = instance.getAttack();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
    /**
     * Test of getHealth method, of class BeastCard.
     */
    // @Ignore
    @Test
    public void testGetHealth() {
        System.out.println("getHealth");
       BeastCard instance = new BeastCard("BR", "Barn Rat", 1, 1, 1, new NormalAttack(), new NoHeal());
        int expResult = 1;
        int result = instance.getHealth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

     
    /**
     * Test of damage method, of class BeastCard.
     */
    //@Ignore
    @Test
    public void testDamage() {
        System.out.println("damage");
        int amount = 2;
        BeastCard instance = new BeastCard("BR", "Barn Rat", 1, 1, 1, new NormalAttack(), new NoHeal());
        instance.damage(amount);
        int expResult = 1;
        int result = instance.getHealth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
    /**
     * Test of toString method, of class BeastCard.
     */
    //@Ignore
    @Test
    public void testToString() {
        System.out.println("toString");
        BeastCard instance = new BeastCard ("ARH", "All Round Hound", 3, 3, 3,new NormalAttack(), new NoHeal());
        String expResult = "All Round Hound" + " (" +"ARH"+ ") Mana Cost/" + 3 + 
                " Attack/" + 3 + " Health/" + 3;
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

  
    // @Ignore
    /**
     * Test of setAttack method, of class BeastCard.
     */
    @Test
    public void testSetAttack() {
        System.out.println("setAttack");
        int amount = 2;
        BeastCard instance = new BeastCard ("ARH", "All Round Hound", 3, 3, 3,new NormalAttack(), new NoHeal());
        instance.setAttack(amount);
        int expResult = 2;
        int result = instance.getAttack();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
    
}
