/*
 * This file is part of PocketBeasts.
 *
 * PocketBeasts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PocketBeasts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */
package uk.ac.tees.cis2001.pocketbeasts;

import java.io.Serializable;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.AttackBehaviour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.DoubleAttack;

/**
 *  SUPER CLASS
 * @author James Fairbairn
 * @author Steven Mead
 * @author Jatin Aneja
 */

// Main card Class
public abstract class Card implements Serializable{

    protected final String id;
    protected final String name;
    protected final int manaCost;
    
    /**
     *
     * @param id  
     * @param name
     * @param manaCost
     */
    public Card(String id, String name, int manaCost) {
        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
    }
    
    /**
     *
     * @param card using card onject
     */
    public Card(Card card) {
        this.id = card.id;
        this.name = card.name;
        this.manaCost = card.manaCost;
       
    }

    /**
     *   getting card Id
     * @return
     */
    public String getId() {
        return this.id;
    }
    
    /**
     *   getting card name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * getting Manacost
     * @return
     */
    public int getManaCost() {
        return this.manaCost;
    } 
   
}
