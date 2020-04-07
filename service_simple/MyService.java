package hello.itay.com.androidservice0704;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Itay kan on 4/7/2020.
 */

public class MyService extends Service {

    private MediaPlayer mediaPlayer;
    private boolean isRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (isRunning == false) {

            Toast.makeText(getBaseContext(), "Service started",Toast.LENGTH_LONG).show();

            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer.create(getBaseContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);

            mediaPlayer.start();
            isRunning = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i < 10 && isRunning; i++) {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.v("==== ", String.valueOf(i));
                    }
                    mediaPlayer.stop();
                    isRunning = false;
                    stopSelf();
                }
            }).start();
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getBaseContext(), "Service stopped",Toast.LENGTH_LONG).show();

        isRunning = false;
        mediaPlayer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
