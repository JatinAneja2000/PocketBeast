/*
 * Filter Pattern
 */
package uk.ac.tees.cis2001.pocketbeasts.filterCards;

import java.util.List;
import uk.ac.tees.cis2001.pocketbeasts.Card;

/**
 *
 * @author JATIN ANEJA
 */
public interface CardCriteria {

    /**
     *
     * @param cards
     * @return
     */
    public List<Card> meetCriteria(List<Card> cards);
}
