/*
 * This is the attack behaviour strategy class Increment the attack of the cards by 3
 */
package uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours;

/**
 *
 * @author JATIN ANEJA
 */
public class TripleAttack extends AttackBehaviour{
     @Override
    public void setAttack(int amount) {
        this.attack = amount+3;
    }
}
