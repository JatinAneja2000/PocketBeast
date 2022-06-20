/*
 * This is the heal behaviour strategy class Increment the Health of the cards by 2
 */
package uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours;

/**
 *
 * @author JATIN ANEJA
 */
public class HealByTwo extends HealBehaviour{
    @Override
    public void setHealth(int health){
        this.health = health+2;
    }
    
}
