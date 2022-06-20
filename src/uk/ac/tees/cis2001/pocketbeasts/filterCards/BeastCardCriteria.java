/*
 * This is the Beast cards filtering class , which returns only beast cards in the list of cardss
 */
package uk.ac.tees.cis2001.pocketbeasts.filterCards;

import uk.ac.tees.cis2001.pocketbeasts.filterCards.CardCriteria;
import java.util.ArrayList;
import java.util.List;
import uk.ac.tees.cis2001.pocketbeasts.BeastCard;
import uk.ac.tees.cis2001.pocketbeasts.Card;

/**
 *
 * @author JATIN ANEJA
 */
public class BeastCardCriteria implements CardCriteria{

    @Override
    public List<Card> meetCriteria(List<Card> cards) {
         List<Card> BeastCards = new ArrayList<Card>(); 
      
      for (Card card : cards) {
          if(card.getClass() == BeastCard.class){
            BeastCards.add(card);
         }
      }
      return BeastCards;
    }
    
}
