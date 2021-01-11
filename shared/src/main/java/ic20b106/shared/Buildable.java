package ic20b106.shared;

import ic20b106.shared.utils.IntPair;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Interface for all Buildable Objects
 */
public interface Buildable {
    IntPair getPosition();
    boolean isConstructionSite();
}
