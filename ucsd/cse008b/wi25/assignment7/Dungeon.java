import java.util.Random;

public class Dungeon {
    private Dungeon() { }

    public static double calculateBettingOdds(Monster monster1, Monster monster2) {
        double power1 = monster1.calculatePower();
        double power2 = monster2.calculatePower();

        if (power1 + power2 == 0) {
            return Double.NaN; // Prevent division by zero
        }

        double monster1Wins = power1 / (power1 + power2);

        if (monster1Wins == 1.0) {
            return Double.POSITIVE_INFINITY; // Handle 100% win probability
        }

        double odds = monster1Wins / (1 - monster1Wins);

        if (monster1.compareTo(monster2) < 0) {
            odds *= 0.8;
        } else if (monster1.compareTo(monster2) > 0) {
            odds *= 1.2;
        }
        return odds;
    }

    public static void armory(Monster monster) {
        Random rand = new Random();
    
        if (monster instanceof Humanoid humanoidMonster) {
            String[] weapons = (monster instanceof Bandit) ?
                    new String[]{"Axe", "Crossbow", "Shield", "Stick"} :
                    new String[]{"Staff", "Dagger", "Rapier", "Stick"};
            String weapon = weapons[rand.nextInt(weapons.length)];
    
            if (!(monster instanceof Doppelganger doppel) || doppel.getClones().isEmpty()) {
                humanoidMonster.setWeapon(weapon);
            }
        }
    
        if (monster instanceof Ooze) {
            ((Ooze) monster).setVolume(((Ooze) monster).getVolume() * 2);
        }
    
        if (monster instanceof Doppelganger doppel) {
            int numClones = rand.nextInt(6);
            for (int i = 0; i < numClones; i++) {
                try {
                    doppel.getClones().add(doppel.clone());
                } catch (CloneNotSupportedException e) {
                    System.out.println("Clone operation failed for Doppelganger.");
                }
            }
        }

        if (!(monster instanceof Cloneable)) {
            int statChoice = rand.nextInt(3);
            switch (statChoice) {
                case 0 -> monster.setArmor(monster.getArmor() * 2);
                case 1 -> monster.setVitality(monster.getVitality() * 2);
                case 2 -> monster.setSpeed(monster.getSpeed() * 2);
            }
        }
    }

    public static int showdown(Monster monster1, Monster monster2) {
        int round = 0;
        boolean poisoned = false;
        int lastDamageDealt = 0;

        while (monster1.getVitality() > 0 && monster2.getVitality() > 0) {
            printRound(round);
            printBothMonsters(monster1, monster2);

            boolean monster1Poisoned = monster1 instanceof Ooze && ((Ooze) monster1).corrode();
            boolean monster2Poisoned = monster2 instanceof Ooze && ((Ooze) monster2).corrode();
            poisoned = monster1Poisoned || monster2Poisoned;

            int damage1 = monster1.attack(monster2);
            int damage2 = monster2.attack(monster1);

            if (damage1 == 0 && damage2 == 0) {
                lastDamageDealt++;
                if (lastDamageDealt > 5) {
                    System.out.println("Neither monster is dealing damage. Declaring a tie.");
                    return 0;
                }
            } else {
                lastDamageDealt = 0;
            }

            round++;
            if (round > 500) {
                System.out.println("Max rounds reached. Declaring a tie.");
                return 0;
            }
        }

        return (monster1.getVitality() <= 0) ? 2 : 1;
    }

    /* Below are helper methods to make showdown() work */
    private final static int SPACING = 17;
    private final static String LEFT = "Left";
    private final static String RIGHT = "Right";

    public static void printBothMonsters(Monster monster1, Monster monster2) {
        int ageSpacing = calcSpacing(Integer.toString(monster1.getArmor()));
        int healthSpacing = calcSpacing(Integer.toString(monster1.getVitality()));
        int strSpacing = calcSpacing(String.format("%.2f", monster1.getSpeed()));
        int monsterSpacing = calcSpacing(monster1.getClass().getName());

        String str = String.format("( %s ) %s ( %s )\n" +
                "----------" + " " + "----------\n" +
                "A: %d %s A: %d\n" +
                "V: %d %s V: %d\n" +
                "S: %.2f %s S: %.2f\n", monster1.getClass().getName(),
                " ".repeat(monsterSpacing), monster2.getClass().getName(),
                monster1.getArmor(), " ".repeat(ageSpacing), monster2.getArmor(),
                monster1.getVitality(), " ".repeat(healthSpacing),
                monster2.getVitality(), monster1.getSpeed(),
                " ".repeat(strSpacing), monster2.getSpeed()
        );
        System.out.println(str);
    }

    public static int calcSpacing(String str) {
        int totalWidth = SPACING;
        int strWidth = str.length();
        int spacing = (totalWidth - strWidth);
        return Math.max(spacing, 0);
    }

    public static void printRound(int round) {
        System.out.println();
        System.out.println("Round " + round + ":");
    }

    public static void printAttack(String side, int damage) {
        System.out.printf("%s does %d damage!\n", side, damage);
    }

    public static void printFinalStats(Monster monster1, Monster monster2, boolean poisoned) {
        System.out.println();
        printBothMonsters(monster1, monster2);
        if (poisoned) {
            System.out.println("A monster was poisoned.");
        }
    }

    public static void printTieGame() {
        System.out.println("-------GAME OVER-------");
        System.out.println("TIE: Both monsters died!");
    }

    public static void printWinner(String side) {
        System.out.println("-------GAME OVER-------");
        System.out.println(side + " monster wins!");
    }
}