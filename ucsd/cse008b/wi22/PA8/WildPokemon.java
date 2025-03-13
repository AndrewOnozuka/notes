///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:    PA8Tester.java
// File:               WildPokemon.java
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
 * The WildPokemon class extends the Pokemon class and allows us to set and get
 * the necessary information associated with Pokemon in the wild that are not
 * yet registered in the Pokedex.
 *
 * Bugs: N/A
 *
 * @author Kameron Gano
 * @author Andrew Onozuka
 */
public class WildPokemon extends Pokemon {
    private int patience;
    private int speed;
    private int timesEscapedFromBall;

    /**
     * WildPokemon constructor. Calls the constructor from the
     * parent class, initializes patience to 100, speed &
     * timesEscapedFromBall to 0.
     *
     * @return N/A
     */
    public WildPokemon() {
        super();
        this.patience = 100;
        this.speed = 0;
        this.timesEscapedFromBall = 0;
    }

    /**
     * WildPokemon constructor. Still calls the parent class
     * constructor, initializes values with given parameters, and sets
     * timesEscapedFromBall to 0.
     *
     * @param pokemonName name of WildPokemon
     * @param pokemonSound sound of WildPokemon
     * @param pokemonType type of WildPokemon
     * @param patienceIn patience value of WildPokemon
     * @param speedIn speed value of WildPokemon
     * @return N/A
     */
    public WildPokemon(String pokemonName, String pokemonSound, 
                       String pokemonType, int patienceIn, int speedIn) {
        super(pokemonName, pokemonSound, pokemonType); // superclass
        this.patience = patienceIn; // initializes with patienceIn
        this.speed = speedIn; // initializes with speedIn
        this.timesEscapedFromBall = 0; // initializes with 0
    }

    /**
     * getPatience method with no parameters. 
     *
     * @return patience of implicit reference WildPokemon
     */
    public int getPatience() {
        return this.patience; // returns patience
    }

    /**
     * getSpeed method with no parameters. 
     *
     * @return speed of implicit reference WildPokemon
     */
    public int getSpeed() {
        return this.speed; //returns speed
    }

    /**
     * getTimesEscapedFromBall method with no parameters. 
     *
     * @return timesEscapedFromBall of implicit reference WildPokemon
     */
    public int getTimesEscapedFromBall() {
        return this.timesEscapedFromBall; // returns times escaped
    }

    /**
     * setPatience method with parameter newPatience. 
     *
     * @return patience updated value of implicit reference
     */
    public int setPatience(int newPatience) {
        this.patience = newPatience; // initializes with newPatience
        return this.patience; // returns patience
    }

    /**
     * setSpeed method with parameter newSpeed.
     *
     * @param newSpeed of implicit reference
     * @return N/A
     */
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed; // intitializes with newSpeed
    }

    /**
     * incrementTimesEscapedFromBall method with no parameters.
     *
     * @return N/A
     */
    public void incrementTimeEscapedFromBall() {
        this.timesEscapedFromBall++; // increments the implicit reference
    }

    /**
     * appear method with no parameters. Prints out a statement letting you
     * know that you have encountered a WildPokemon.
     *
     * @return N/A
     */
    public void appear() {
        System.out.println("You encounter a wild " + this.getName() + "!");
        this.speak(); // calls speak method from Pokemon.java
    }

    /**
     * disappear method with no parameters. Returns true if the Pokemon
     * should disappear after failed attempes to catch it, false otherwise.
     *
     * @return N/A
     */
    public boolean disappear() {
        if (this.patience <= 0 || 
            this.timesEscapedFromBall > 3) {
            return true;
        }
        return false;
    }

    /**
     * isCaught method with parameters berry and pokeball.
     *
     * @return true boolean
     */
    public boolean isCaught(Berry berry, Pokeball pokeball) {
        return true; // returns boolean true when caught
    }

    /**
     * Override toString method that returns information about the given
     * WildPokemon.
     *
     * @return showWildPokemonName name of WildPokemon
     * @return showType type of WildPokemon
     * @return showWildPokePatience patience of WildPokemon
     * @return showWildPokeSpeed speed of WildPokemon
     * @return showTimesEscaped number of times WildPokemon escaped
     */
    @Override
    public String toString() {
        String showWildPokemonName = this.getName() + ", WildPokemon" + "\n";
        String showType = "type: " + this.getType() + "\n";
        String showWildPokePatience = "patience: " + this.getPatience() + "\n";
        String showWildPokeSpeed = "speed: " + this.getSpeed() + "\n";
        String showTimesEscaped = "timeEscapedFromBall: " + 
                                   this.getTimesEscapedFromBall() + "\n";
        return showWildPokemonName + showType + showWildPokePatience 
               + showWildPokeSpeed + showTimesEscaped; // returns string
    }
}
