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
public class InPlayTest {
    
    public Card[] STARTER_CARDS = new Card[] {
        new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal()),
        new BeastCard ("SP", "Scampering Pup", 2, 2, 1,new NormalAttack(), new NoHeal()),
        new BeastCard ("HB", "Hardshell Beetle", 2, 1, 2,new NormalAttack(), new NoHeal()),
    };
    public ArrayList<Card> starterDeck;
    public InPlayTest() {
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
     * Test of getCard method, of class InPlay.
     */
    //@Ignore
    @Test
    public void testGetCard() {
        System.out.println("getCard");
        int index = 1;
        InPlay instance = new InPlay(starterDeck);
        // have to check any value because the instances are not same
        Card expCard = new BeastCard ("SP", "Scampering Pup", 2, 2, 1,new NormalAttack(), new NoHeal());
        Card resultCard = instance.getCard(index);
        String expResult , result;
        expResult = expCard.getId();
        result = resultCard.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class InPlay.
     */
    //@Ignore
    @Test
    public void testCount() {
        System.out.println("count");
        InPlay instance = new InPlay(starterDeck);
        int expResult = 3;
        int result = instance.count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
