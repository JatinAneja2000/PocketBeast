/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.cis2001.pocketbeasts.factory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.tees.cis2001.pocketbeasts.BeastCard;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.AttackBehaviour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.DoubleAttack;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealBehaviour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealByTwo;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NoHeal;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NormalAttack;

/**
 *
 * @author JATIN ANEJA
 */
public class StrategyFactoryTest {
    
    public StrategyFactoryTest() {
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
     * Test of getAttackStrategy method, of class StrategyFactory.
     */
    @Test
    public void testGetAttackStrategy() {
        System.out.println("getAttackStrategy");
        BeastCard card =  new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal());
        AttackBehaviour expResult = new DoubleAttack();
        AttackBehaviour result = StrategyFactory.getAttackStrategy(card);
        assertEquals(expResult.getClass(), result.getClass());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getHealStrategy method, of class StrategyFactory.
     */
    @Test
    public void testGetHealStrategy() {
        System.out.println("getHealStrategy");
        BeastCard card =  new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal());
        HealBehaviour expResult = new HealByTwo();
        HealBehaviour result = StrategyFactory.getHealStrategy(card);
        assertEquals(expResult.getClass(), result.getClass());
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }
    
}
