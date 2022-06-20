/*
 * This is the BeastCards Class inherits from parent class Cards. This class contaisn the behaviours of main character cards.
 */
package uk.ac.tees.cis2001.pocketbeasts;

import uk.ac.tees.cis2001.pocketbeasts.factory.CardFactory;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.AttackBehaviour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealBehaviour;

/**
 *
 * @author JATIN ANEJA
 */
public class BeastCard extends Card implements Comparable<Card>, CardFactory{
    private final int tempAttack;
    private int tempHealth;
    private  AttackBehaviour a;
    private HealBehaviour h;
    
    /**
     * Creates new BeastCard with provided details
     * @param id The Id of the card
     * @param name The Name of the cards 
     * @param attack the attack behaviour of the card
     * @param manaCost  the manacost of the cards 
     * @param health  the health of the cards
     * @param a   -- attack behaviour strategy
     * @param h   -- health behaviour strategy
     */
    public BeastCard(String id, String name, int attack, int manaCost, int health, AttackBehaviour a, HealBehaviour h) {
        super(id, name, manaCost);
        this.a = a;
        this.h = h;
        this.tempHealth = health;
        this.tempAttack = attack;
        this.a.setAttack(attack);   // strategy attack
        this.h.setHealth(health);   // strategy health
    }

    /**
     *
     * @param beastCard  == constructor using beast card object
     */
    public BeastCard(BeastCard beastCard){
        super(beastCard.getId(), beastCard.name, beastCard.getManaCost());
        this.a = beastCard.a;
        this.h = beastCard.h;
        this.tempHealth = beastCard.tempHealth;
        this.tempAttack = beastCard.tempAttack;
        this.a.setAttack(beastCard.tempAttack);
        this.h.setHealth(beastCard.tempHealth);
    }
    
    /**
     * Getters and Setters 
     * -------------------------------------------------------------------------
     */
    
    @Override
    public void setAttackBehaviour(AttackBehaviour a){
        this.a = a;
        this.setAttack(tempAttack);
        
    }
        
    /**
     *  returning attack behaviour of the cards
     * @return
     */
    public AttackBehaviour getAttackBehaviour(){
        return this.a;
    }
    
      
    @Override
    public final void setAttack(int amount) {
         a.setAttack(amount);
    }
    
    @Override
    public int getAttack() {
        return a.getAttack();
    }
    
    @Override 
    public void setHealBehaviour(HealBehaviour h){
        this.h = h;
        this.setHealth(tempHealth);
    }
    
    /**
     *  returning health behaviour
     * @return
     */
    public HealBehaviour getHealBehaviour(){
        return this.h;
    }
   
    /**
     *   setteing up health of the card
     * @param amount
     */
    public void setHealth(int amount) {
        this.h.setHealth(amount);
    }
    @Override
    public int getHealth() {
         return this.h.getHealth();
    }
    
    @Override
    public void damage(int amount) {
        this.tempHealth -= amount;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.id + ") Mana Cost/" + this.manaCost + 
                " Attack/" + a.getAttack() + " Health/" + this.tempHealth;
    }

    @Override
    public int compareTo(Card o) {   // comparing mancost
        return Integer.compare(this.getManaCost(), o.getManaCost());
    }
    
}
