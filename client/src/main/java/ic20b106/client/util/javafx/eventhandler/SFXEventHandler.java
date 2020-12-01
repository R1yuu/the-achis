package ic20b106.client.util.javafx.eventhandler;

import ic20b106.client.manager.AudioManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.media.AudioClip;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class SFXEventHandler<T extends Event> implements EventHandler<T> {

    public SFXEventHandler(AudioInputStream sfxInputStream) {
        this(sfxInputStream, null);
    }

    public SFXEventHandler(AudioInputStream sfxInputStream, EventHandler<T> eventHandler) {
        this.sfxInputStream = sfxInputStream;
        this.eventHandler = eventHandler;
    }

    public EventHandler<T> getEventHandler() {
        return this.eventHandler;
    }

    public void setEventHandler(EventHandler<T> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public AudioInputStream getSfx() {
        return this.sfxInputStream;
    }

    public void setSfx(AudioInputStream sfx) {
        this.sfxInputStream = sfx;
    }

    @Override
    public void handle(T event) {
        AudioManager.getInstance().playClip(sfxInputStream);
        if (eventHandler != null) {
            eventHandler.handle(event);
        }
    }

    private EventHandler<T> eventHandler;
    private AudioInputStream sfxInputStream;
}
