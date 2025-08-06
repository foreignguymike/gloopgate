package com.distraction.gloopgate.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioHandler {

    private final Map<String, Music> music;
    private final Map<String, Sound> sounds;

    private MusicConfig currentlyPlaying;

    public AudioHandler() {
        music = new HashMap<>();
        addMusic("bg", "music/noworries.ogg");

        sounds = new HashMap<>();
        addSound("cheer", "sfx/DennisH18/crowd-cheering.wav");

        addSound("select", "sfx/select.wav");
        addSound("enter", "sfx/enter.wav");
        addSound("enter2", "sfx/enter.ogg");
        addSound("click", "sfx/click.ogg");
        addSound("tick", "sfx/shift.wav");
        addSound("finish", "sfx/finish.wav");
    }

    private void addMusic(String key, String fileName) {
        music.put(key, Gdx.audio.newMusic(Gdx.files.internal(fileName)));
    }

    private void addSound(String key, String fileName) {
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(fileName)));
    }

    public boolean isMusicPlaying() {
        if (currentlyPlaying == null) return false;
        if (currentlyPlaying.getMusic() == null) return false;
        return currentlyPlaying.getMusic().isPlaying();
    }

    public void playMusic(String key, float volume, boolean looping) {
        Music newMusic = music.get(key);
        if (newMusic == null) {
            System.out.println("unknown music: " + key);
            return;
        }
        if (currentlyPlaying != null && newMusic != currentlyPlaying.getMusic()) {
//            stopMusic();
        }
        currentlyPlaying = new MusicConfig(music.get(key), volume, looping);
        currentlyPlaying.play();
    }

    public void stopMusic() {
        if (currentlyPlaying != null) {
            currentlyPlaying.stop();
            currentlyPlaying = null;
        }
    }

    public void playSound(String key) {
        playSound(key, 1, false);
    }

    public void playSound(String key, float volume) {
        playSound(key, volume, false);
    }

    public void playSoundCut(String key, float volume) {
        playSound(key, volume, true);
    }

    public void playSound(String key, float volume, boolean cut) {
        for (Map.Entry<String, Sound> entry : sounds.entrySet()) {
            if (entry.getKey().equals(key)) {
                if (cut) entry.getValue().stop();
                entry.getValue().play(volume);
            }
        }
    }

}
