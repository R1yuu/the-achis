package ic20b106.util.javafx.eventhandler;

import ic20b106.manager.AudioManager;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ButtonSFXEventHandler<T extends Event> extends SFXEventHandler<T> {

    public ButtonSFXEventHandler() {
        this(null);
    }

    public ButtonSFXEventHandler(EventHandler<T> eventHandler) {
        super(AudioManager.BUTTON_CLICK, eventHandler);
    }
}
