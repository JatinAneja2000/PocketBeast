/*
 * This is the attack behaviour strategy abstract class to set the ttack for the cards
 */
package uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours;

import java.io.Serializable;

/**
 *
 * @author JATIN ANEJA
 */
public abstract class AttackBehaviour implements Serializable{

    protected int attack;
    
    /**
     * set the attack of the cards 
     * @param amount
     */
    public void setAttack(int amount){
        this.attack = amount;
    }

    /**
     *  
     * @return the attack amount of the cards 
     */
    public int getAttack(){
        return this.attack;
    }
        
}
