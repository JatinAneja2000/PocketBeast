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
 * @author Steven Mead
 * @author Jatin Aneja
 */
// origianl Hand class extending Card_Functionalities and implenting count and Serializable
public class Hand extends Card_Functionalities implements CountInterface,Serializable {
    
    /** 
     * adding new arrayList for the Cards in Hand
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }
    @Override
    public int count() {
        return this.cards.size();
    }
    
    
     /**
     *  This is the Simple Bubble sort to sort the cards applications.
     *  this class accepts Beast card and Magic Card
     */
    public void sort() {
        //int n = this.CardList.size();
        int n = this.cards.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Card card1 = cards.get(j);
                Card card2 = cards.get(i);
                if((card1.getClass() == BeastCard.class) && (card2.getClass() == BeastCard.class)){
                    if (((BeastCard) card1).getAttack() > ((BeastCard) card2).getAttack()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));          //  both are beast cards 
                    cards.set(j + 1, temp);
                    }
                }else if((card1.getClass() == MagicCard.class) && (card2.getClass() == MagicCard.class)){
                    if (((MagicCard) card1).getManaCost()> ((MagicCard) card2).getManaCost()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));          // both are Magic Cards 
                    cards.set(j + 1, temp);
                    }
                }else if((card1.getClass() == MagicCard.class) && (card2.getClass() == MagicCard.class)){
                    if (((BeastCard) card1).getManaCost() > ((MagicCard) card2).getManaCost()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));          // card1 is Beast and Card2 is Magic
                    cards.set(j + 1, temp);
                    }
                }else if((card1.getClass() == MagicCard.class) && (card2.getClass() == MagicCard.class)){
                    if (((MagicCard) card1).getManaCost() > ((BeastCard) card2).getManaCost()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));         // card2 is Beast and Card1 is Magic
                    cards.set(j + 1, temp);
                    }
                }
                
            }
        }
    }


    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        for (int i=0; i<this.count(); i++) {
            sb.append("+-------+ ");
        }
        sb.append("\n");
        
        for (Card card : this.cards) {
            sb.append(String.format("|%7d| ", card.getManaCost()));
        }
        sb.append("\n");
        
        for (Card card : this.cards) {
            sb.append(String.format("|  %-5s| ", card.getId()));
        }
        sb.append("\n");
        
        for (int i=0; i<this.count(); i++) {
            sb.append("|       | ");
        }
        sb.append("\n");
        
        for (Card card : this.cards) {
            if(card.getClass() == BeastCard.class){
                sb.append(String.format("|%-2d %4d| ", ((BeastCard)card).getAttack(), ((BeastCard)card).getHealth()));
            }else{
                sb.append("       ");
            }
        }
        sb.append("\n");
        
        for (int i=0; i<this.count(); i++) {
            sb.append("+-------+ ");
        }
        sb.append("\n");
        
        return sb.toString();
    }
}
