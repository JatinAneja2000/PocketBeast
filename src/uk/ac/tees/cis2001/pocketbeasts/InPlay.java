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

/**
 *
 * @author James Fairbairn
 * @author Steven Mead
 * @author Jatin Aneja
 */
// origianl InPlay class extending Card_Functionalities and implenting count and Serializable
public class InPlay extends Card_Functionalities implements CountInterface,Serializable{
    
    
    InPlay() {
     this.cards = new ArrayList<>();
    }
    public InPlay(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public Card getCard(int index) {
        return cards.get(index);
    }
    @Override
    public int count() {
        return this.cards.size();
    }
}
