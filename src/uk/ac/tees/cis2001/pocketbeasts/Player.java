/*
 * This file is part of PocketBeasts.
 *
 * PocketBeasts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PocketBeasts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */
package uk.ac.tees.cis2001.pocketbeasts;

import java.io.Serializable;

/**
 *
 * @author James Fairbairn
 * @author Steven Mead
 */

// This is the Player Class impleneting methods for the a player like health , cards in hand and Inplay etc..
public class Player implements Serializable{
    private static final long serialVersionID = 1L;
    private final int MAX_MANA = 9;
    
    private final String name;
    
    private int manaAvailable;
    private int manaTicker;
    private int health;
    
    private final Deck deck;
    private final Hand hand;
    private InPlay inPlay;
    private Graveyard graveyard;

    /**
     *
     * @param name   == Player name
     * @param deck   == set of Deck for the player
     */
    public Player(String name, Deck deck) {
        this.name = name;
        this.manaAvailable = 0;
        this.manaTicker = 0;
        this.health = 15;
        this.deck = deck;
        this.hand = new Hand();
        this.inPlay = new InPlay();
        this.graveyard = new Graveyard();
    }

        /**
     * Getters and Setters 
     * -------------------------------------------------------------------------
     * @return player name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return mana available with the Player
     */
    public int getManaAvailable() {
        return this.manaAvailable;
    }

    /**
     *
     * @return  Health of the Player
     */
    public int getHealth() {
        return this.health;
    }

    /**
     *
     * @param health  sets the health of the player
     */
    public void setHealth(int health){
        this.health = health;
    }
    
    /**
     *
     * @return  set of deck with the player
     */
    public Deck getDeck() {
        return this.deck;
    }
    
    /**
     *
     * @return set of cards in the hand of the player
     */
    public Hand getHand() {
        return this.hand;
    }

    /**
     *
     * @return  the cards in the InPlay of the Player
     */
    public InPlay getInPlay() {
        return this.inPlay;
    }
    
    /**
     *
     * @param inPlay  ste the cards in the Inplay
     */
    public void setInPlay(InPlay inPlay){
        this.inPlay.removeAll(this.inPlay.getCards());
        for(Card card : inPlay.getCards()){
            this.inPlay.add(card);
        }
    }
    
    /**
     *
     * @return  the Graveyard cards in for the player
     */
    public Graveyard getGraveyard() {
        return this.graveyard;
    }
    
    /**
     *
     * @param graveyard  sets the players cards in the graveyard
     */
    public void setGraveYard(Graveyard graveyard){
        this.graveyard = graveyard;
    }
    
    /**
     *  Starts the new Game
     */
    public void newGame() {
        this.deck.shuffle();
        for (int i=0; i<4; i++) {
            this.hand.add(this.deck.draw());
        }
    }
    
    /**
     *   adding mana for the player
     */
    public void addMana() {
        if (this.manaTicker < this.MAX_MANA) {
            this.manaTicker++;
        }
        this.manaAvailable = manaTicker;
    }
    
    /**
     *  Use available mana for the player
     * @param amount
     */
    public void useMana(int amount) {
        this.manaAvailable = this.manaAvailable - amount;
    }
    
    public void drawCard() {
        this.hand.add(this.deck.draw());
    }
    
    /**
     *
     * @param amount
     * @return true if the cards get damage on health
     */
    public Boolean damage(int amount) {
        this.health -= amount;
        return this.health <= 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-9s HEALTH/%-5d MANA/%d\n", this.name, this.health, this.manaAvailable));

        for (int i=0; i<this.inPlay.count()+2; i++) {
            sb.append("+-------+ ");
        }
        sb.append("\n");
        
        for (int i=0; i<2; i++) {
            sb.append("|       | ");
        }
        for (int i=0; i<this.inPlay.count(); i++) {
            sb.append(String.format("|%7d| ", this.inPlay.getCard(i).getManaCost()));
        }
        sb.append("\n");
        
        sb.append("| DECK  | ");
        sb.append("| GRAVE | ");
        for (int i=0; i<this.inPlay.count(); i++) {
            sb.append(String.format("|  %-5s| ", this.inPlay.getCard(i).getId()));
        }
        sb.append("\n");
        
        sb.append(String.format("| %-6d| ", this.deck.count()));
        sb.append(String.format("| %-6d| ", this.graveyard.count()));
        for (int i=0; i<this.inPlay.count(); i++) {
            sb.append("|       | ");
        }
        sb.append("\n");
        
        for (int i=0; i<2; i++) {
            sb.append("|       | ");
        }
        for (int i=0; i<this.inPlay.count(); i++) {
            
   //        sb.append(String.format("|%-2d %4d| ", this.inPlay.getCard(i).getAttack(), this.inPlay.getCard(i).getHealth()));        
            if(this.inPlay.getCard(i).getClass().equals(BeastCard.class)){
                BeastCard card = (BeastCard) this.inPlay.getCard(i);
                sb.append(String.format("|%-2d %4d| ", card.getAttack(), card.getHealth()));
            }else{
                sb.append("       ");
            }
        }
        sb.append("\n");
        
        for (int i=0; i<this.inPlay.count()+2; i++) {
            sb.append("+-------+ ");
        }
        sb.append("\n");
        sb.append(String.format("%d card(s) in hand.\n", this.hand.count()));
        sb.append("\n");
        
        sb.append(this.hand.toString());
        
        return sb.toString();
    }
}
