package app.baking_app.listeners;

import android.media.MediaPlayer;
import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Created by n188851 on 02-05-2017.
 */

public class MySessionCallback extends MediaSessionCompat.Callback {
    private final SimpleExoPlayer mediaPlayer;

    public MySessionCallback(SimpleExoPlayer exoPlayer){
        this.mediaPlayer=exoPlayer;
    }

    @Override
    public void onPlay() {
        mediaPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        mediaPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onSkipToPrevious() {
        mediaPlayer.seekTo(0);
    }
}
