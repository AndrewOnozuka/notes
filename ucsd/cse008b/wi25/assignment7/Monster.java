public abstract class Monster implements Comparable<Monster> {
    private int armor;
    private int vitality;
    private double speed;

    protected Monster() {
        this.armor = 0;
        this.vitality = 0;
        this.speed = 0.0;
    }

    protected Monster(int armor, int vitality, double speed) {
        this.armor = armor;
        this.vitality = vitality;
        this.speed = speed;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int compareTo(Monster monster) {
        double thisAverage = (this.armor + this.vitality + this.speed) / 3.0;
        double otherAverage = (monster.armor + monster.vitality + monster.speed) / 3.0;

        return Double.compare(thisAverage, otherAverage);
    }

    public boolean sameSpecies(Monster monster) {
        return this.getClass() == monster.getClass();
    }

    @Override
    public String toString() {
        return "(" + getClass().getSimpleName() + ") " + "armor: " + getArmor() + 
               "; vitality: " + getVitality() + "; speed: " + getSpeed();
    }

    public abstract void rest();
    public abstract double calculatePower();
    public abstract int attack(Monster monster);
}