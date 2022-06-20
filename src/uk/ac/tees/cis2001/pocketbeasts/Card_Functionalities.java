/*
 * This is the cards functionalities class implementing the Common behaviours of Hand , Inplay , Deck and Graveyard
 */
package uk.ac.tees.cis2001.pocketbeasts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author JATIN ANEJA
 */
public abstract class Card_Functionalities implements Serializable{
    
    public ArrayList<Card> cards;
    
    /**
     *  Creating New ArrayList
     */
    public Card_Functionalities(){
        this.cards = new ArrayList<Card>();
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }
    
    /**
     *  Adding cards method in Hand and In InPlay
     * @param card
     */
    public void add(Card card) {
        this.cards.add(card);
        this.sort();
    }
    
    /**
     * Removing 1 card from Hand and InPlay
     * @param card
     */
    public void remove(Card card) {
        this.cards.remove(card);
    }
    
    /**
     * Removing all the Cards from Hand and InPlay
     * @param cards
     */
    public void removeAll(ArrayList<Card> cards) {
        this.cards.removeAll(cards);
    }

    /**
     *  //This is the Simple Bubble sort to sort the cards applications.
     * abstract  class Card_Functionalities won't take Collection.sort 
     */
    public void sort() {
        
        int n = this.cards.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Card card1 = cards.get(j);
                Card card2 = cards.get(i);
                if((card1.getClass() == BeastCard.class) || (card1.getClass() == BeastCard.class)){
                    if (((BeastCard) card1).getAttack() > ((BeastCard) card2).getAttack()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
                }
                
            }
        }
    }
    
}
