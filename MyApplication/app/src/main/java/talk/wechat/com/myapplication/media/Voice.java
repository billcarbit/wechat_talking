package talk.wechat.com.myapplication.media;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/17.
 */
public class Voice {
    private static File audioFile;
    private static MediaRecorder mediaRecorder;
    public static String filePath;

    public static void play(String url) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelRecording() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            try {
                audioFile = File.createTempFile("record_", ".amr");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("aaa", audioFile.getAbsolutePath() + "__________");
            filePath = audioFile.getAbsolutePath();
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void stopRecording() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
