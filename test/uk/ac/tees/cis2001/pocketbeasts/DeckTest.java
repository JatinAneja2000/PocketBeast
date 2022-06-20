/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.cis2001.pocketbeasts;

import java.util.ArrayList;
import java.util.Arrays;
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
public class DeckTest {

    public Card[] STARTER_CARDS = new Card[] {
        new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal()),
        new BeastCard ("SP", "Scampering Pup", 2, 2, 1,new NormalAttack(), new NoHeal()),
        new BeastCard ("HB", "Hardshell Beetle", 2, 1, 2,new NormalAttack(), new NoHeal()),
    };
    public ArrayList<Card> starterDeck;
    public DeckTest() {
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
     * Test of count method, of class Deck.
     */
    //@Ignore
    @Test
    public void testCount() {
        System.out.println("count");
        
        Deck instance = new Deck(starterDeck);
        int expResult = 3;
        int result = instance.count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of draw method, of class Deck.
     */
    //@Ignore
    @Test
    public void testDraw() {
        System.out.println("draw");
        Deck instance = new Deck(starterDeck);
        Card expCard =  new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal());
        Card resultCard =  instance.draw();
        String expResult , Result;
        expResult = expCard.getId();
        Result = resultCard.getId();
        assertEquals(expResult, Result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
}
