///////////////////////////////////////////////////////////////////////////////
// Main Class File:    Assignment8.java
// File:               Pokedex.java
// Quarter:            CSE 8B Winter 2022
//
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;

/**
 * This is Pokedex class that you can add pokemons which can be wild or pal
 * pokemons. You can display your pokedex with display() method
 *
 * Bugs: None known
 *
 * @author Your Name
 */

public class Pokedex {

    // ArrayList for pokemons (WildPokemons, PalPokemons)
    ArrayList<Pokemon> myPokedex;
    
    /**
     * Creates a resizable-array ArrayLists of Pokemons
     * 
     */
    public Pokedex() {
        myPokedex = new ArrayList<Pokemon>();
    }

    /**
     * Adds the pokemon to myPokedex
     * 
     */
    public void add(Pokemon pokemon) {
        myPokedex.add(pokemon);
    }

    /**
     * Displays the elements in the myPokedex ArrayList
     * 
     */
    public void display() {
        System.out.println("Pokemons in the pokedex: \n");

        if (myPokedex.size() == 0) {
            System.out.println("None");
        }

        else {
            for (int i = 0; i < myPokedex.size(); i++) {
                System.out.println(myPokedex.get(i).toString() + "\n");
            }
        }
    }    
}
