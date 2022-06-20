/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours;

/**
 *
 * @author JATIN ANEJA
 */
public class NoHeal extends HealBehaviour{
    @Override
    public void setHealth(int amount) {
        this.health = amount;
    }
}

