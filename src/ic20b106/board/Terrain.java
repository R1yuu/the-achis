package ic20b106.board;

import ic20b106.exceptions.ArgumentOutOfBoundsException;

public class Terrain {

    public Terrain() {
        this.fertility = 1;
        this.speed = 1;
    }

    public Terrain(int fertility) {
        setFertility(fertility);
        this.speed = 1;
    }

    public Terrain(int fertility, double speed) {
        setFertility(fertility);
        setSpeed(speed);
    }

    public int getFertility() {
        return fertility;
    }

    public void setFertility(int fertility) {
        if (fertility < 0 || fertility > 1) {
            throw new ArgumentOutOfBoundsException("Fertility must be between 0 and 1.");
        }
        this.fertility = fertility;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if (speed < 0) {
            throw new ArgumentOutOfBoundsException("Speed must be greater or equal 0.");
        }
        this.speed = speed;
    }

    private int fertility;
    private double speed;
}