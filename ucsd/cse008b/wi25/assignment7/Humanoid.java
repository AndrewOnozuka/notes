public abstract class Humanoid extends Monster {
    private int intelligence;
    private String weapon;

    protected Humanoid() {
        super();
        this.intelligence = 0;
        this.weapon = "None"; // Prevents NullPointerException
    }

    protected Humanoid(int armor, int vitality, double speed, int intelligence, String weapon) {
        super(armor, vitality, speed);
        this.intelligence = intelligence;
        this.weapon = weapon;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public String getWeapon() {
        return this.weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public abstract int strike(Monster monster);
}