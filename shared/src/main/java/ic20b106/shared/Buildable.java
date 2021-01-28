package ic20b106.shared;

import ic20b106.shared.utils.IntPair;

import java.io.Serializable;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Interface for all Buildable Objects
 */
public interface Buildable extends Serializable {
    IntPair getPosition();
    boolean isConstructionSite();
}
