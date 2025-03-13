///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:    PA8Tester.java
// File:               PalPokemon.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Kameron Gano
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
// Pair Partner:       Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          N/A
//
// Online sources:   N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * The PalPokemon class extends the Pokemon class and allows us to set and get
 * the necessary information associated with the previously caught Pokemon.
 *
 * Bugs: N/A
 *
 * @author Kameron Gano
 * @author Andrew Onozuka
 */
public class PalPokemon extends Pokemon {
    private String pokeballName;

    /**
     * PalPokemon method with no parameters. Calls the constructor from the
     * parent class, initializes pokeballName to "undefined".
     *
     * @return N/A
     */
    public PalPokemon() {
        super(); // accesses the superclass
        this.pokeballName = "undefined"; // initializes with "undefined"
    }

    /**
     * PalPokemon method with parameters. Still calls the parent class
     * constructor and initializes values with given parameters.
     *
     * @param pokemonName name of PalPokemon
     * @param pokemonSound sound of PalPokemon
     * @param pokemonType type of PalPokemon
     * @param pokeballName name of pokeball for pokemon
     * @return N/A
     */
    public PalPokemon (String pokemonName, String pokemonSound,
                       String pokemonType, String pokeballName) {
        super(pokemonName, pokemonSound, pokemonType); // superclass
        this.pokeballName = pokeballName; // initializes with pokeballName
    }

    /**
     * getPokeball method with no parameters. 
     *
     * @return pokeballName of implicit reference
     */
    public String getPokeballName() {
        return this.pokeballName; // return string
    }

    /**
     * comesOutFromBall method prints out the information of the Pokemon
     * using the implicit reference, and then plays the corresponding sound.
     *
     * @return N/A
     */
    public void comesOutFromBall() {
        System.out.println(this.getName() + " in " + this.getPokeballName()
                           + ", " + this.getType() + " type pokemon.");
        this.speak(); // pokemon sound
    }

    /**
     * Override toString method that returns information about the given
     * PalPokemon.
     *
     * @return showPalPokemon name of PalPokemon
     * @return showBallName name of PalPokemon Pokeball
     * @return showType type of PalPokemon
     */
    @Override
    public String toString() {
        String showPalPokemon = this.getName() + ", PalPokemon" + "\n";
        String showBallName = "pokeballName: " + this.getPokeballName() + "\n";
        String showType = "type: " + this.getType() + "\n";
        return showPalPokemon + showBallName + showType; // returns string
    }
}
