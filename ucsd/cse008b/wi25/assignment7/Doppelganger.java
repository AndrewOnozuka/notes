import java.util.ArrayList;
import java.util.Random;

public class Doppelganger extends Humanoid implements Cloneable {
    private ArrayList<Doppelganger> clones;

    public Doppelganger() {
        super();
        this.clones = new ArrayList<>();
    }

    public Doppelganger(int armor, int vitality, double speed, int intelligence, String weapon) {
        super(armor, vitality, speed, intelligence, weapon);
        this.clones = new ArrayList<>();
    }

    @Override
    protected Doppelganger clone() throws CloneNotSupportedException {
        Doppelganger cloned = new Doppelganger(getArmor(), getVitality(), getSpeed(), getIntelligence(), getWeapon());
        cloned.clones = new ArrayList<>(); // Ensure clones start as empty
        return cloned;
    }

    public ArrayList<Doppelganger> getClones() {
        return clones;
    }

    @Override
    public void rest() {
        setVitality(Math.min(getVitality() + 10, 1000)); // Cap vitality to avoid infinite loops

        for (Doppelganger clone : clones) {
            if (clone.getVitality() < 1000) {  // Avoid unnecessary recursion
                clone.rest();
            }
        }
    }

    @Override
    public double calculatePower() {
        double initialPower = 0.0;
        String weapon = getWeapon();
        int vitality = getVitality();
        int intelligence = getIntelligence();
        double speed = getSpeed();
        int armor = getArmor();

        if (weapon.equals("Staff")) {
            initialPower = 0.35 * vitality + 0.3 * intelligence - 0.6 * speed;
        } else if (weapon.equals("Dagger")) {
            initialPower = 0.05 * vitality + 0.15 * intelligence + 0.8 * speed;
        } else if (weapon.equals("Rapier")) {
            initialPower = 0.4 * armor + 0.2 * intelligence + 0.5 * speed;
        }

        double totalPower = initialPower;
        for (Doppelganger clone : clones) {
            totalPower += clone.calculatePower();
        }

        return totalPower;
    }

    @Override
    public int strike(Monster monster) {
        double power = calculatePower();
        double minStrikeValue = power - (0.5 * getIntelligence());
        double maxStrikeValue = power + (0.5 * getIntelligence());

        Random rand = new Random();
        double strikeValue = minStrikeValue + (rand.nextDouble() * (maxStrikeValue - minStrikeValue));
        int strikeValueInt = (int) Math.floor(strikeValue);

        if (strikeValueInt <= 0) {
            return 0;
        }

        if (strikeValueInt <= monster.getArmor()) {
            monster.setArmor(monster.getArmor() - strikeValueInt);
        } else {
            int damage = strikeValueInt - monster.getArmor();
            monster.setVitality(monster.getVitality() - damage);
            monster.setArmor(0);
        }

        return strikeValueInt;
    }

    @Override
    public int attack(Monster monster) {
        return strike(monster);
    }
}