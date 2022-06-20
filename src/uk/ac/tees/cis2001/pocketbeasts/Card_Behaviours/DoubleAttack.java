/*
 * This is the attack behaviour strategy class Increment the attack of the cards by 2
 */
package uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours;

import uk.ac.tees.cis2001.pocketbeasts.Card;

/**
 *
 * @author JATIN ANEJA
 */
public class DoubleAttack extends AttackBehaviour{
    
   
    @Override
    public void setAttack(int amount) {
        this.attack = amount+2;
    }
}
