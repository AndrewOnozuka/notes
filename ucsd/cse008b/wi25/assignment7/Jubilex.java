import java.util.Random;

public class Jubilex extends Ooze {
    private Random random;

    public Jubilex() {
        super();
        this.random = new Random();
    }

    public Jubilex(int armor, int vitality, double speed, int volume, int acidity) {
        super(armor, vitality, speed, volume, acidity);
        this.random = new Random();
    }

    @Override
    public void rest() {
        setArmor(getArmor() + 10000);
    }

    @Override
    public double calculatePower() {
        double initialPower = 70 * getVitality() + 350 * getVolume() + 100 * getAcidity();

        if (random.nextDouble() >= 0.01) { 
            initialPower *= 100;
        }

        return initialPower;
    }

    @Override
    public boolean corrode() {
        return random.nextDouble() < 0.95;
    }

    @Override
    public int attack(Monster monster) {
        double power = calculatePower();
        double minStrikeValue = power - (0.005 * getVolume());
        double maxStrikeValue = power + (0.5 * getVolume());

        double strikeValue = minStrikeValue + (maxStrikeValue - minStrikeValue) * random.nextDouble();
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
}