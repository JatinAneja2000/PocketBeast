/*
 * This is the Test Harness for the PocketBeast Console Based Application.It randomly run the entire code giving random values for prompts leading the game to the end state.
 */
package uk.ac.tees.cis2001.pocketbeasts.GamePlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import uk.ac.tees.cis2001.pocketbeasts.BeastCard;
import uk.ac.tees.cis2001.pocketbeasts.Card;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NoHeal;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NormalAttack;
import uk.ac.tees.cis2001.pocketbeasts.Deck;
import uk.ac.tees.cis2001.pocketbeasts.Player;

/**
 *
 * @author JATIN ANEJA
 */
public class PocketBeastsTestHarness {

    /**
     *   Here the console leads from main
     */
    public void letsTest(){
        
        Player[] players = new Player[] {
            new Player("Jatin", new Deck(getTestingDeck())),
            new Player("Yash", new Deck(getTestingDeck()))
        }; 
        
        for (Player player : players) {
            player.newGame();
            System.out.println(player);
        }
        
        String winningMessage = "";
        Boolean run = true;
        int step = 1;
        while(run) {
            System.out.println(step);
            for (Player player : players) {
                // Add mana and draw card
                player.addMana();
                
                if(player.getDeck().count() > 0){
                    player.drawCard();
                }
                

                // Print initial play state
                System.out.println(player);

                // HACK assumes only one other player
                Player otherPlayer = null;
                for (Player iPlayer : players) {
                    if (iPlayer != player) {
                        otherPlayer = iPlayer;
                    }
                }
                if (otherPlayer == null){
                    winningMessage = "Something has gone terribly wrong...";
                    run = false;
                    break;
                }
                
                // Cycle through cards in play to attack
                for (Card card : player.getInPlay().getCards()) {
                    System.out.println(card.toString());
                    
                    String attack = getTestPrompt(
                            player.getName() + " attack with " + card.getName() + "? (Yes/No): ", 
                            new String[]{"Yes", "yes", "y", "No", "no", "n"});
                    if (attack.equals("Yes") || attack.equals("yes") || attack.equals("y")) {
                        // Choose who to attack, player directly or a player's beast
                         if (card.getClass() == BeastCard.class) {
                             
                        int attackChoice = 2;
                        System.out.println("Who would you like to attack? ");
                        System.out.println("1. " + otherPlayer.getName());
                        for (Card otherCard: otherPlayer.getInPlay().getCards()) {
                            System.out.println(attackChoice + ". " + otherCard);
                            attackChoice++;
                        }
                        ArrayList<String> prompts = new ArrayList<>();
                        for (int i=1; i<attackChoice; i++) {
                            prompts.add(String.valueOf(i));
                        }
                        String target = getTestPrompt("Choose a number: ", prompts.toArray(new String[0]));
                        if (target.equals("1")) { // Player
                            if (otherPlayer.damage(((BeastCard)card).getAttack())) {
                                // if true returned players health <= 0
                                winningMessage = player.getName() + " wins!";
                                run = false;
                                break;
                            }
                            System.out.println(otherPlayer.getName() + " is now at " + otherPlayer.getHealth());
                        }
                        else { // Beast, index is `target-2`
                            Card targetCard = otherPlayer.getInPlay().getCard(Integer.parseInt(target)-2);
                            ((BeastCard)targetCard).damage(((BeastCard)card).getAttack());
                            ((BeastCard)card).damage(((BeastCard)targetCard).getAttack());
                        }
                    }
                    }
                }
                
                if (!run) {
                    break;
                }
                
                // Cycle through cards in play remove "dead" cards (health <= 0)
                ArrayList<Card> toRemove = new ArrayList<>();
                for (Card card : player.getInPlay().getCards()) {
                    if(card.getClass() == BeastCard.class){
                        if (((BeastCard)card).getHealth() <= 0) {
                        toRemove.add(card);
                      
                        player.getGraveyard().add(card);
                    }
                    }
                    
                }
                player.getInPlay().removeAll(toRemove);
                
                toRemove = new ArrayList<>();
                for (Card card : otherPlayer.getInPlay().getCards()) {
                    if(card.getClass() == BeastCard.class){
                    if (((BeastCard)card).getHealth() <= 0) {
                        toRemove.add(card);
                        otherPlayer.getGraveyard().add(card);
                    }
                    }
                }
                otherPlayer.getInPlay().removeAll(toRemove);
                
                // Play cards from hand
                toRemove = new ArrayList<>();
                for (Card card : player.getHand().getCards()) {
                    if (card.getManaCost() <= player.getManaAvailable()) {
                        System.out.println(card.toString());

                        String play = getTestPrompt(
                                player.getName() + " play " + card.getName() + "? (Yes/No) ", 
                                new String[]{"Yes", "yes", "y", "No", "no", "n"});
                        if (play.equals("Yes") || play.equals("yes") || play.equals("y")) {
                            player.getInPlay().add(card);
                            player.useMana(card.getManaCost());
                            toRemove.add(card);
                        }
                    }
                }
                player.getHand().removeAll(toRemove);
                
                // Print final play state
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.println(player);
               
                
            }
            step++;
        }
        
        System.out.println(winningMessage);
        
        
    } 
    
       
    public final Card[] STARTER_CARDS = new Card[] {
            new BeastCard ("BR", "Barn Rat", 1, 1, 1,new NormalAttack(),new NoHeal()),
            new BeastCard ("SP", "Scampering Pup", 2, 2, 1,new NormalAttack(),new NoHeal()),
            new BeastCard ("HB", "Hardshell Beetle", 2, 1, 2,new NormalAttack(),new NoHeal()),
            new BeastCard ("VHC", "Vicious House Cat", 3, 3, 2,new NormalAttack(),new NoHeal()),
            new BeastCard ("GD", "Guard Dog", 3, 2, 3,new NormalAttack(),new NoHeal()),
            new BeastCard ("ARH", "All Round Hound", 3, 3, 3,new NormalAttack(),new NoHeal()),
            new BeastCard ("MO", "Moor Owl", 4, 4, 2,new NormalAttack(),new NoHeal()),
            new BeastCard ("HT", "Highland Tiger", 5, 4, 4,new NormalAttack(),new NoHeal())
        };
    
    public ArrayList<Card> getTestingDeck() {
        ArrayList<Card> starterDeck = new ArrayList<>();
        
        for (int i=0; i<2; i++) {
            for (Card card : STARTER_CARDS) {
                if(card.getClass() == BeastCard.class){
                    System.out.println(((BeastCard)card).getAttack() + " " + card.getName());
                    starterDeck.add(new BeastCard(((BeastCard)card)));
                }
                
            }
        }
        
        return starterDeck;
    }
    
    public String getTestPrompt(String prompt, String[] validResponse){
        System.out.print(prompt);
        
//        Scanner sc = new Scanner(System.in);
//        String response = sc.nextLine();
//         
//        if (Arrays.stream(validResponse).anyMatch(response::equals)) {
//            return response;
//        }
        Random random = new Random();
        int index = random.nextInt(validResponse.length);
        System.out.println(validResponse[index]);
        //return getTestPrompt(prompt, validResponse);
        return validResponse[index];
    }
    

}
