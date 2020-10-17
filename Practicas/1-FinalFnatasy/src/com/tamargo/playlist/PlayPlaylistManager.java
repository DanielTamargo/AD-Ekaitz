package com.tamargo.playlist;

public class PlayPlaylistManager extends Thread {

    private String[] playlist;
    private int index = 0;
    private float volumen;

    @Override
    public void run() {
        super.run();
        PlayPlaylist pp = new PlayPlaylist(playlist, index, volumen);
        pp.playSound(playlist[index], volumen);
    }

    public PlayPlaylistManager(String[] playlist, int index, float volumen) {
        this.playlist = playlist;
        if (index > playlist.length)
            index = 0;
        this.index = index;
        this.volumen = volumen;
    }


}
