public class Assignment7 {

    public static boolean unitTests() {
        boolean allTestsPassed = true;

        System.out.println("Running unit tests...\n");

        // Test 1: clone() method (only for Cloneable classes like Doppelganger or Ochre)
        try {
            Doppelganger originalMonster = new Doppelganger(50, 10, 5, 20, "Dagger");
            Doppelganger clonedMonster = originalMonster.clone();
            if (clonedMonster.getArmor() != originalMonster.getArmor() ||
                clonedMonster.getVitality() != originalMonster.getVitality() ||
                clonedMonster.getSpeed() != originalMonster.getSpeed() ||
                clonedMonster.getIntelligence() != originalMonster.getIntelligence() ||
                !clonedMonster.getWeapon().equals(originalMonster.getWeapon()) ||
                clonedMonster.getClones().size() != 0) {
                System.out.println("Test 1 failed: clone() method incorrect");
                allTestsPassed = false;
            } else {
                System.out.println("Test 1 passed: clone() method works correctly");
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("Test 1 failed: CloneNotSupportedException thrown");
            allTestsPassed = false;
        }

        // Test 2: compareTo() method
        Monster monster1 = new Bandit(50, 10, 5, 15, "Axe");
        Monster monster2 = new Bandit(40, 12, 4, 12, "Crossbow");
        int comparisonResult = monster1.compareTo(monster2);
        if (comparisonResult != 1) { // Expecting monster1 to be stronger
            System.out.println("Test 2 failed: compareTo() method incorrect");
            allTestsPassed = false;
        } else {
            System.out.println("Test 2 passed: compareTo() method works correctly");
        }

        // Test 3: calculateBettingOdds() method
        double odds = Dungeon.calculateBettingOdds(monster1, monster2);
        if (odds <= 0 || Double.isNaN(odds) || Double.isInfinite(odds)) {
            System.out.println("Test 3 failed: calculateBettingOdds() method incorrect");
            allTestsPassed = false;
        } else {
            System.out.println("Test 3 passed: calculateBettingOdds() method works correctly");
        }

        // Edge case: both monsters with 0 power
        Monster emptyMonster1 = new Bandit(0, 0, 0, 0, "None");
        Monster emptyMonster2 = new Bandit(0, 0, 0, 0, "None");
        double zeroOdds = Dungeon.calculateBettingOdds(emptyMonster1, emptyMonster2);
        if (!Double.isNaN(zeroOdds)) { // Expecting NaN or an error handling case
            System.out.println("Test 3 edge case failed: calculateBettingOdds() division by zero issue");
            allTestsPassed = false;
        } else {
            System.out.println("Test 3 edge case passed: Proper handling of zero-power monsters");
        }

        // Test 4: armory() method
        Dungeon.armory(monster1);
        if (monster1 instanceof Humanoid) {
            Humanoid humanoidMonster = (Humanoid) monster1;
            if (humanoidMonster.getWeapon() == null) {
                System.out.println("Test 4 failed: armory() method incorrect");
                allTestsPassed = false;
            } else {
                System.out.println("Test 4 passed: armory() method works correctly");
            }
        }

        // Test 5: showdown() method
        int result = Dungeon.showdown(monster1, monster2);
        if (result == 0) {
            System.out.println("⚠️ Test 5: Monsters tied, checking for correct tie-handling...");
        } else if (result != 1 && result != 2) {
            System.out.println("Test 5 failed: showdown() method incorrect");
            allTestsPassed = false;
        } else {
            System.out.println("Test 5 passed: showdown() method works correctly");
        }

        return allTestsPassed;
    }

    public static void main(String[] args) {
        // Perform unitTests
        if (unitTests()) {
            System.out.println("\nAll unit tests passed successfully!\n");
        } else {
            System.out.println("\nSome unit tests failed. Check the logs above.\n");
        }
    }
}