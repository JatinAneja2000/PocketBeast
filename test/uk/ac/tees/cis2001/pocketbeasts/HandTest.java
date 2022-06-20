/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.cis2001.pocketbeasts;

import java.util.ArrayList;
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
public class HandTest {
    
    public Card[] STARTER_CARDS = new Card[] {
        new BeastCard ("BR", "Barn Rat", 2, 3, 1,new NormalAttack(), new NoHeal()),
        new BeastCard ("SP", "Scampering Pup", 3, 2, 1,new NormalAttack(), new NoHeal()),
        new BeastCard ("HB", "Hardshell Beetle", 1, 5, 2,new NormalAttack(), new NoHeal()),
    };
    public ArrayList<Card> starterDeck;
    
    public HandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        starterDeck = new ArrayList<>();
        
        for (int i=0; i<1; i++) {
            for (Card card : STARTER_CARDS) {
                if(card.getClass() == BeastCard.class){
                    System.out.println(((BeastCard)card).getAttack() + " " + card.getName());
                    starterDeck.add(new BeastCard(((BeastCard)card)));
                }
                
            }
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of count method, of class Hand.
     */
    //@Ignore
    @Test
    public void testCount() {
        System.out.println("count");
        Hand instance = new Hand(starterDeck);
        int expResult = 3;
        int result = instance.count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
    /**
     * Test of sort method, of class Hand.
     */
    //@Ignore
    @Test
    public void testSort() {
        System.out.println("sort");
        Hand instance = new Hand(starterDeck);
        instance.sort();
        int expResult = 1;
        int result = ((BeastCard)instance.cards.get(0)).getAttack();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Hand.
     */
    @Test
    @Ignore
    public void testToString() {
        System.out.println("toString");
        Hand instance = new Hand();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
