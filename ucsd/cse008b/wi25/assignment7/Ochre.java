import java.util.ArrayList;
import java.util.Random;

public class Ochre extends Ooze implements Cloneable {
    private ArrayList<Ochre> clones;
    private static final Random random = new Random();

    public Ochre() {
        super();
        this.clones = new ArrayList<>();
    }

    public Ochre(int armor, int vitality, double speed, int volume, int acidity) {
        super(armor, vitality, speed, volume, acidity);
        this.clones = new ArrayList<>();
    }

    @Override
    protected Ochre clone() throws CloneNotSupportedException {
        if (getVolume() == 1) {
            throw new CloneNotSupportedException("Volume is too small to clone.");
        }

        int newVolume = Math.max(1, getVolume() / 2);
        Ochre clone = new Ochre(getArmor(), getVitality(), getSpeed(), newVolume, getAcidity());

        setVolume(newVolume);
        clone.clones = new ArrayList<>();
        
        clones.add(clone);
        return clone;
    }

    public ArrayList<Ochre> getClones() {
        return clones;
    }

    @Override
    public void rest() {
        setArmor(Math.min(getArmor() + 20, 5000));

        for (Ochre clone : clones) {
            if (clone.getArmor() < 5000) {
                clone.rest();
            }
        }
    }

    @Override
    public double calculatePower() {
        double initialPower = 0.7 * getVitality() + 0.35 * getVolume() + getAcidity();
        double totalPower = initialPower;

        for (Ochre clone : clones) {
            totalPower += clone.calculatePower();
        }

        return totalPower;
    }

    @Override
    public boolean corrode() {
        if (random.nextDouble() < 0.095) {
            return true;
        }

        for (Ochre clone : clones) {
            if (clone.corrode()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int attack(Monster monster) {
        double power = calculatePower();
        double minStrikeValue = power - 0.5 * getVolume();
        double maxStrikeValue = power + 0.5 * getVolume();

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