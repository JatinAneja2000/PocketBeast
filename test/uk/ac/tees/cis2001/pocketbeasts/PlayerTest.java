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
public class PlayerTest {
    
        public Card[] STARTER_CARDS = new Card[] {
        new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal()),
        new BeastCard ("SP", "Scampering Pup", 2, 2, 1,new NormalAttack(), new NoHeal()),
        new BeastCard ("HB", "Hardshell Beetle", 2, 1, 2,new NormalAttack(), new NoHeal()),
    };
    public ArrayList<Card> starterDeck;
    public PlayerTest() {
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
     * Test of getName method, of class Player.
     */
    //@Ignore
    @Test
    public void testGetName() {
        System.out.println("getName");
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        String expResult = "Jatin";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getManaAvailable method, of class Player.
     */
    @Test
   // @Ignore
    public void testGetManaAvailable() {
        System.out.println("getManaAvailable");
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        int expResult = 0;
        int result = instance.getManaAvailable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getHealth method, of class Player.
     */
    @Test
    //@Ignore
    public void testGetHealth() {
        System.out.println("getHealth");
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        int expResult = 15;
        int result = instance.getHealth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDeck method, of class Player.
     */
    @Test
    //@Ignore
    public void testGetDeck() {
        System.out.println("getDeck");
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        Deck expResult = deck;
        Deck result = instance.getDeck();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getHand method, of class Player.
     */
    @Test
    @Ignore
    public void testGetHand() {
        System.out.println("getHand");
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        Hand expResult = null;
        Hand result = instance.getHand();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInPlay method, of class Player.
     */
    @Test
    @Ignore
    public void testGetInPlay() {
        System.out.println("getInPlay");
        Player instance = null;
        InPlay expResult = null;
        InPlay result = instance.getInPlay();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGraveyard method, of class Player.
     */
    @Test
    @Ignore
    public void testGetGraveyard() {
        System.out.println("getGraveyard");
        Player instance = null;
        Graveyard expResult = null;
        Graveyard result = instance.getGraveyard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }



    /**
     * Test of addMana method, of class Player.
     */
    @Test
    //@Ignore
    public void testAddMana() {
        System.out.println("addMana");
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        instance.addMana();
        int expResult = 1;
        int result = instance.getManaAvailable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of useMana method, of class Player.
     */
    @Test
    //@Ignore
    public void testUseMana() {
        System.out.println("useMana");
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        instance.addMana();
        int amount = 1;
        instance.useMana(amount);
        int expResult = 0;
        int result = instance.getManaAvailable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of drawCard method, of class Player.
     */
    @Test
    //@Ignore
    public void testDrawCard() {
        System.out.println("drawCard");
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        Card expCard =  new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(), new NoHeal());
        instance.drawCard();
        String expResult , Result;
        expResult = expCard.getId();
        Result = "BR";
        assertEquals(expResult, Result);
        
       
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of damage method, of class Player.
     */
    @Test
    //@Ignore
    public void testDamagetrue() {
        System.out.println("damage");
        // max health of player is 15
        int amount = 15;
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        instance.drawCard();
        Boolean expResult = true;
        Boolean result = instance.damage(amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

        @Test
    //@Ignore
    public void testDamagefalse() { 
        // max health of player is 15
        //card not drawn not attacked
        System.out.println("damagefalse");
        int amount = 1;
        Deck deck = new Deck(starterDeck);
        Player instance = new Player("Jatin", deck);
        Boolean expResult = false;
        Boolean result = instance.damage(amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of toString method, of class Player.
     */
    @Test
    @Ignore
    public void testToString() {
        System.out.println("toString");
        Player instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
