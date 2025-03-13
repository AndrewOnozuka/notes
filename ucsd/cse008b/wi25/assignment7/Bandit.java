import java.util.Random;

public class Bandit extends Humanoid {
    public Bandit() {
        super();
    }

    public Bandit(int armor, int vitality, double speed, int intelligence, String weapon) {
        super(armor, vitality, speed, intelligence, weapon);
    }

    @Override
    public void rest() {
        setVitality(Math.min(getVitality() + 30, 1000)); // Example cap to avoid infinite health
    }

    @Override
    public double calculatePower() {
        double initialPower = 0.0;
        String weapon = getWeapon();
        int vitality = getVitality();
        int intelligence = getIntelligence();
        double speed = getSpeed();
        int armor = getArmor();

        if (weapon.equals("Axe")) {
            initialPower = 0.65 * vitality + 0.35 * intelligence - 0.1 * speed;
        } else if (weapon.equals("Crossbow")) {
            initialPower = 0.25 * vitality + 0.5 * intelligence + 0.25 * speed;
        } else if (weapon.equals("Shield")) {
            initialPower = 0.7 * armor + 0.2 * vitality + 0.1 * speed - 0.2 * intelligence;
        }

        Random rand = new Random();
        double critChance = rand.nextDouble();
        if (critChance > 0.6) {
            initialPower *= 2;
        }

        return initialPower;
    }

    @Override
    public int strike(Monster monster) {
        double power = calculatePower();
        double minStrikeValue = power - 0.15 * getIntelligence();
        double maxStrikeValue = power + 0.25 * getIntelligence();
        Random rand = new Random();
        double strikeValue = minStrikeValue + (maxStrikeValue - minStrikeValue) * rand.nextDouble();
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