/*
 * This Class is used to get the aproprite magic card.
 * for eg. if theres a Beast card with attack power of 4
 * if we add 3X power, it will be very unfair game.
 * To solve this problem we will use this class to get appropriate strategy behaviour.
 */
package uk.ac.tees.cis2001.pocketbeasts.factory;
import uk.ac.tees.cis2001.pocketbeasts.BeastCard;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.AttackBehaviour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.DoubleAttack;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealBehaviour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealByFour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealByTwo;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.TripleAttack;
/*
 *
 * @author JATIN ANEJA
 */
public class StrategyFactory {
    
    
    // Not allowing to double or Triple attack on cards with attack > 4

    /**
     *
     * @param card
     * @return  appropriate attack strategy behaviour on the basis of card attack
     */
    public static AttackBehaviour getAttackStrategy(BeastCard card){
        if(card.getAttack()<=3){
            return new DoubleAttack();
        }else if(card.getAttack()<=5 ){
            return new TripleAttack();
        }else{
            return null;
        }
    }
    
    /**
     * appropriate healing strategy behaviour on the basis of card health
     * @param card
     * @return
     */
    public static HealBehaviour getHealStrategy(BeastCard card){
        if(card.getHealth()<3){
            return new HealByTwo();
        }else if(card.getHealth()<1){
            return new HealByFour();
        }else{
            return null;
        }
    }
}
