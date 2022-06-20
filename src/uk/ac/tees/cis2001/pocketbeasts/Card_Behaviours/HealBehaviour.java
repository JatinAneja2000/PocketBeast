/*
 * This is the Heal behaviour strategy class. This sets the Healing power of cards.
 */
package uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours;

import java.io.Serializable;

/**
 *
 * @author JATIN ANEJA
 */
public abstract class HealBehaviour implements Serializable{
    protected int health;
    
    /**
     * set the health of the card
     * @param health
     */
    public void setHealth(int health){
        this.health = health;
    }

    /**
     * 
     * @return the health of cards
     */
    public int getHealth(){
        return this.health;
    }
}
