package ic20b106.client.util.javafx.eventhandler;

import ic20b106.client.manager.AudioManager;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Automatically sets the SFX for Buttons
 */
public class ButtonSFXEventHandler<T extends Event> extends SFXEventHandler<T> {

    /**
     * Constructor
     * Sets no EventHandler; Just the Sound
     */
    public ButtonSFXEventHandler() {
        this(null);
    }

    /**
     * Constructor
     * Sets SFX and Event
     *
     * @param eventHandler EventHandler to Set
     */
    public ButtonSFXEventHandler(EventHandler<T> eventHandler) {
        super(AudioManager.BUTTON_CLICK, eventHandler);
    }
}
