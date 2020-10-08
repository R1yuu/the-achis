package ic20b106.game;

import ic20b106.exceptions.ArgumentOutOfBoundsException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Cell Terrain with specific attributes
 */
public class Terrain {

    /**
     * Default Constructor
     */
    public Terrain() {
        this(1, 1);
    }

    /**
     * Constructor
     *
     * @param fertility Fertility of the Terrain (0-1)
     */
    public Terrain(double fertility) {
        this(fertility, 1);
    }

    /**
     * Constructor
     *
     * @param fertility Fertility of the Terrain (0-1)
     * @param speed Movement Speed on this Terrain
     */
    public Terrain(double fertility, double speed) {
        setFertility(fertility);
        setSpeed(speed);
    }

    /**
     * Fertility Getter
     *
     * @return Returns the Fertility
     */
    public double getFertility() {
        return fertility;
    }

    /**
     * Fertility Setter
     *
     * @param fertility Fertility of the Terrain (0-1)
     */
    public void setFertility(double fertility) {
        if (fertility < 0 || fertility > 1) {
            throw new ArgumentOutOfBoundsException("Fertility must be between 0 and 1.");
        }
        this.fertility = fertility;
    }

    /**
     * Speed Getter
     *
     * @return Returns the Speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Speed Setter
     *
     * @param speed Movement Speed on this Terrain
     */
    public void setSpeed(double speed) {
        if (speed < 0) {
            throw new ArgumentOutOfBoundsException("Speed must be greater or equal 0.");
        }
        this.speed = speed;
    }

    private double fertility;
    private double speed;
}