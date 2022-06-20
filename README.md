# PocketBeast
*PocketBeast is a Console Based Trading card game written in Java.*

*I had Created a new Version of PocketBeast with the Use of Garphical User InterFace and Software Design Pattern and this version support Chat application between two players.*

# Game Rules
New players each start with 15 Health and 1 Mana to spend on playing cards. At the start of the game each player draws 4 cards from their deck to hand.

Players each take turns. Each turn consists four phases:

 1. Add mana (mana increases by one each turn and replenishes in full).
 2. Draw a card.
 3. There are 2 types of cards :
     * Beast Card : Cards with powers to attack another person's card.
     * Trainer Card : Cards which can increase powers of Beast card.
     * Note : You can't play Trainer card on its own, you can only play them on top of Beast card and only can be played once.
             Cycle through your cards in play (if any), choosing whether to attack.

 4. Attacking the other player directly with your card inflicts damage to their health equal to the attack power of the card.
 5. Attacking another players beast will damage both cards (equal to their attack values).
 6. Any card with <= 0 health is removed from the play field and placed into the graveyard.
 7. Play cards from hand.
 8. Details of cards

# Console Look of the Game 
![image](https://user-images.githubusercontent.com/79797338/174660933-bca4f933-c38b-4f16-a40f-087dfb116995.png)
![image](https://user-images.githubusercontent.com/79797338/174661087-160433c6-5d33-490f-857d-29e3e6e5733e.png)

# Design Pattern Used : Strategy Patter & Factory Pattern 

![stratgy_pattem_Ingame (2)](https://user-images.githubusercontent.com/79797338/174664127-27897a7a-a82d-4c95-9984-87bc5e8c43ee.jpg)


## Game Preview 

# New Game
  * Game is required to run Two GUI files:
      * ServerMain.java 
      * ClientMain.java
  * Games begins with the UI asking Users their Names 

![image](https://user-images.githubusercontent.com/79797338/174661913-cc644771-4d06-4186-8c75-fe19317c6dc5.png)

# Game Begins

Player 1 Joins

![image](https://user-images.githubusercontent.com/79797338/174662435-7746315a-8eec-43ea-85e9-2558c2f4c1c4.png)

Player 2 Joins

![image](https://user-images.githubusercontent.com/79797338/174662533-1d9e5cb7-cdf5-4931-bcc6-e0b1b5db3b7b.png)


You have "Hand" of a player which represents the card in the hand. Opponent can't see these cards. Then you have "InPlay" these are the card you've played and are ready to attack. Opponent can see these cards. At last you've got "Opponent's InPlay" which represents the cards opponent has played and are ready to attack.


# Chat Application 

![image](https://user-images.githubusercontent.com/79797338/174663072-7aa07fa9-5394-47b1-bd28-2a3fc4c42936.png)

It is a Simple Server Client Chat application, building a two way conversation using Websockets and Threads.

# GUI Rules Card

![image](https://user-images.githubusercontent.com/79797338/174663307-38b7f33c-3b9e-4cd3-9318-2470dc56ee17.png)


