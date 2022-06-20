/*
 * Card Factory Interface used as abstraction for strategy factory
 */
package uk.ac.tees.cis2001.pocketbeasts.factory;

import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.AttackBehaviour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealBehaviour;

/**
 *
 * @author JATIN ANEJA
 */
public interface CardFactory {
   public void damage(int amount);
   public int getHealth();
   public int getAttack();
   public int getManaCost();
   public String getName();
   public String getId();
   public void setAttackBehaviour(AttackBehaviour a);
   public void setAttack(int amount);
   public void setHealBehaviour(HealBehaviour h);
}
