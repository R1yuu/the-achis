package ic20b106.client.util.javafx.eventhandler;

import ic20b106.client.manager.AudioManager;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.net.URL;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Automatically sets the SFX before an Event gets Handled
 */
public class SFXEventHandler<T extends Event> implements EventHandler<T> {

    private EventHandler<T> eventHandler;
    private URL sfxResourceUrl;

    /**
     * Constructor
     * Sets only Sound
     *
     * @param sfxResourceUrl Sound Resource URL to play
     */
    public SFXEventHandler(URL sfxResourceUrl) {
        this(sfxResourceUrl, null);
    }

    /**
     * Constructor
     * Sets sound and Event
     *
     * @param sfxResourceUrl Sound Resource URL to play
     * @param eventHandler Event to handle
     */
    public SFXEventHandler(URL sfxResourceUrl, EventHandler<T> eventHandler) {
        this.sfxResourceUrl = sfxResourceUrl;
        this.eventHandler = eventHandler;
    }

    /**
     * Getter
     *
     * @return EventHandler
     */
    public EventHandler<T> getEventHandler() {
        return this.eventHandler;
    }

    /**
     * Setter
     *
     * @param eventHandler EventHandler
     */
    public void setEventHandler(EventHandler<T> eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Getter
     *
     * @return Resource URL of Sound Effect
     */
    public URL getSfx() {
        return this.sfxResourceUrl;
    }

    /**
     * Setter
     *
     * @param sfx Resource Url of Sound Effect
     */
    public void setSfx(URL sfx) {
        this.sfxResourceUrl = sfx;
    }

    /**
     * Handler
     *
     * @param event Event to Handle by EventHandler
     */
    @Override
    public void handle(T event) {
        AudioManager.getInstance().playClip(sfxResourceUrl);
        if (eventHandler != null) {
            eventHandler.handle(event);
        }
    }
}
