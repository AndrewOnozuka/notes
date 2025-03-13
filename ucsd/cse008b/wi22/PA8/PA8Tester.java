///////////////////////////////////////////////////////////////////////////////
// Main Class File:    Assignment8.java
// File:               Item.java
// Quarter:            CSE 8B Winter 2022
//
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Tests written to check if other classes work as intended
 *
 * Bugs: None known
 *
 */
public class PA8Tester {
    public static void main (String[] args) {
        System.out.println("\n-------------------------------------------\n");

        /* PART 1 */
        
        // Create 2 pokeball object with different names and 
        //       performances. Then print the name, itemType, and 
        //       performance of the two objects on a separate line 
        //       using getter methods. 

        Pokeball pokeballTest1 = new Pokeball("Great Ball", 10);
        System.out.println("Name: " + pokeballTest1.getName());
        System.out.println("Performance: " + pokeballTest1.getPerformance() + "\n");

        Pokeball pokeballTest2 = new Pokeball("Ultra Ball", 30);
        System.out.println("Name: " + pokeballTest2.getName());
        System.out.println("Performance: " + pokeballTest2.getPerformance());


        System.out.println("\n-------------------------------------------\n");
        
        /* PART 2 */
        
        // Create 2 berry object with different names, 
        //       patienceIncrement, and speedDecrement. Then print the 
        //       name, itemType, patienceIncrement, and speedDecrement 
        //       of each berry on a separate line using getter methods.

        Berry berryTest1 = new Berry("Razz Berry", 10, 0);
        System.out.println("Name: " + berryTest1.getName());
        System.out.println("patienceIncrement: " + berryTest1.getPatienceIncrement());
        System.out.println("speedDecrement: " + berryTest1.getSpeedDecrement() + "\n");

        Berry berryTest2 = new Berry("Nanap Berry", 0, 10);
        System.out.println("Name: " + berryTest2.getName());
        System.out.println("patienceIncrement: " + berryTest2.getPatienceIncrement());
        System.out.println("speedDecrement: " + berryTest2.getSpeedDecrement());


        System.out.println("\n-------------------------------------------\n");

        /* PART 3 */
        
        // Create 2 PalPokemon objects with different names, 
        //       sounds, types, and pokeball names. Then make a function 
        //       call to comesOutFromBall() for each objects.

        PalPokemon palPokemonTest1 = new PalPokemon("Pikachu", "pikapika", 
                                                    "electric", "Ultra Ball");
        System.out.println("Name: " + palPokemonTest1.getName());
        System.out.println("Sound: " + palPokemonTest1.getSound());
        System.out.println(palPokemonTest1.getType());
        System.out.println(palPokemonTest1.getPokeballName());
        palPokemonTest1.comesOutFromBall();
        System.out.print("\n");

        PalPokemon palPokemonTest2 = new PalPokemon("Bulbasaur", "bulb", 
                                                    "grass", "Great Ball");
        System.out.println("Name: " + palPokemonTest2.getName());
        System.out.println("Sound: " + palPokemonTest2.getSound());
        System.out.println(palPokemonTest2.getType());
        System.out.println(palPokemonTest2.getPokeballName());
        palPokemonTest2.comesOutFromBall();


        System.out.println("\n-------------------------------------------\n");

        /* PART 4 */
        
        // Create 2 WildPokemon object with different names, sounds, 
        //       types, patience, and speed (They should be different 
        //       pokemons from part3 when testing palPokemons). Then print 
        //       the name, sound, type, patience, speed, and 
        //       timesEscapedFromBall of each WildPokemon on a separate 
        //       line using getter methods. Also make a function call to 
        //       appear() and disappear() for both of the objects

        WildPokemon wildPokeTest1 = new WildPokemon("Charmander", "char", "fire", 50, 20);
        System.out.println("Name: " + wildPokeTest1.getName());
        System.out.println("Sound: " + wildPokeTest1.getSound());
        System.out.println("Type: " + wildPokeTest1.getType());
        System.out.println("Patience: " + wildPokeTest1.getPatience());
        System.out.println("timesEscapedFromBall: " + wildPokeTest1.getTimesEscapedFromBall() + "\n");

        WildPokemon wildPokeTest2 = new WildPokemon("Mew", "mew", "psychic", 15, 50);
        System.out.println("Name: " + wildPokeTest2.getName());
        System.out.println("Sound: " + wildPokeTest2.getSound());
        System.out.println("Type: " + wildPokeTest2.getType());
        System.out.println("Patience: " + wildPokeTest2.getPatience());
        System.out.println("timesEscapedFromBall: " + wildPokeTest2.getTimesEscapedFromBall());


        System.out.println("\n-------------------------------------------\n");

        /* PART 5 */
        
        // Create and display the empty backpack

        Backpack testBackpack = new Backpack();
        testBackpack.display();


        // Add 2 pokeballs that were created in part 1 
        //       to the backpack and display the backpack
        testBackpack.add(pokeballTest1);
        testBackpack.add(pokeballTest2);
        testBackpack.display();

        // Add 2 berries that were created in part 2 
        //       to the backpack and display the backpack
        testBackpack.add(berryTest1);
        testBackpack.add(berryTest2);
        testBackpack.display();

        // Create an display the empty pokedex
        Pokedex testPokedex = new Pokedex();
        testPokedex.display();

        // Add 2 pal pokemons that were created in part 3
        //        to the pokedex and display the backpack
        testPokedex.add(palPokemonTest1);
        testPokedex.add(palPokemonTest2);
        testPokedex.display();

        // Add 2 wild pokemons that were created in part 4
        //        to the pokedex and display the backpack
        testPokedex.add(wildPokeTest1);
        testPokedex.add(wildPokeTest2);
        testPokedex.display();
    }
}
