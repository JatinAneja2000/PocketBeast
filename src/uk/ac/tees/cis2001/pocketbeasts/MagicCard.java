/*
 * This is the MagicCard Class inherits from parent class Cards. This class contaisn the behaviours of spell cards.
 * It only contails parent card classs behaviour.
*/
package uk.ac.tees.cis2001.pocketbeasts;


/**
 *
 * @author JATIN ANEJA
 */
public class MagicCard extends Card{

    /**
     * magic Card using super card class
     * @param id
     * @param name
     * @param manaCost
     */
    public MagicCard(String id, String name, int manaCost) {
        super(id,name,manaCost);
    }
}
