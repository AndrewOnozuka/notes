public abstract class Ooze extends Monster {
    private int volume;
    private int acidity;

    protected Ooze() {
        super();
        this.volume = 0;
        this.acidity = 0;
    }

    protected Ooze(int armor, int vitality, double speed, int volume, int acidity) {
        super(armor, vitality, speed);
        this.volume = Math.max(0, volume);
        this.acidity = Math.max(0, acidity);
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = Math.max(0, volume);
    }

    public int getAcidity() {
        return acidity;
    }

    public void setAcidity(int acidity) {
        this.acidity = Math.max(0, acidity);
    }

    public abstract boolean corrode();
}