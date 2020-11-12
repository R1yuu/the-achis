package ic20b106.util.javafx.eventhandler;

import ic20b106.manager.AudioManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.media.AudioClip;

public class SFXEventHandler<T extends Event> implements EventHandler<T> {

    public SFXEventHandler(AudioClip sfx) {
        this(sfx, null);
    }

    public SFXEventHandler(AudioClip sfx, EventHandler<T> eventHandler) {
        this.sfx = sfx;
        this.eventHandler = eventHandler;
    }

    public EventHandler<T> getEventHandler() {
        return this.eventHandler;
    }

    public void setEventHandler(EventHandler<T> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public AudioClip getSfx() {
        return this.sfx;
    }

    public void setSfx(AudioClip sfx) {
        this.sfx = sfx;
    }

    @Override
    public void handle(T event) {
        AudioManager.getInstance().playClip(sfx);
        if (eventHandler != null) {
            eventHandler.handle(event);
        }
    }

    private EventHandler<T> eventHandler;
    private AudioClip sfx;
}
