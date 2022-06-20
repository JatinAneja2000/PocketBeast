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

// This is the Main for the Console based Pocketbeast Application
// We can run test harness and the main gain for this class  only
package uk.ac.tees.cis2001.pocketbeasts.GamePlay;


/**
 *
 * @author James Fairbairn
 * @author Steven Mead
 * @author Jatin Aneja
 */
public class Main {
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Instructions start = new Instructions();
       
        //main game
       GameBegins game = new GameBegins();
       game.getInstance();

       //test harness
//        PocketBeastsTestHarness test = new PocketBeastsTestHarness();
//        test.letsTest();
        
    }
}
