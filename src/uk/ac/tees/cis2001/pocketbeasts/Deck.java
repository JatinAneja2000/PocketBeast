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
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author James Fairbairn
 * @author Steven Mead\
 * @author Jatin Aneja 
 */

// origianl deck class extending Card_Functionalities and implenting count and Serializable
public class Deck extends Card_Functionalities implements CountInterface,Serializable{
    
    /**
     *
     * @param cards new arrayList for Deck
     */
    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }
    @Override
    public int count() {
        return this.cards.size();
    }
    
    /**
     *
     * @return Cards in the Game
     */
    public Card draw() {
        Card card = this.cards.get(0);
        this.cards.remove(0);
        return card;
    }
    
    /**
     * using Collection library to shuffle cards
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }
}
