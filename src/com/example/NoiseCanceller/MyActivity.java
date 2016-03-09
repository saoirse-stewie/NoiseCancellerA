package com.example.NoiseCanceller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MyActivity extends Activity {



    private static final int REQUEST_PICKER = 1;
    private static final int PICK_FROM_FILE = 1;
    final static int RQS_RECORDING = 1;
    MediaRecorder recorder = new MediaRecorder();
    //private String outputfile = "/sdcard/sample.mp4";
    String path = Environment.getExternalStorageDirectory().toString() + "/" + "Download";
    File outputfile = new File(path, "sample.mp4");


    //String audioname = "sample.mp4";
    private MediaPlayer mPlayer = null;
    Uri savedUri;
    Button b;
    Button b2;
    Button b3;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b = (Button) findViewById(R.id.btn1);
        b2 = (Button) findViewById(R.id.btn2);
        b3 = (Button) findViewById(R.id.btn3);
        b2.setEnabled(false);
        b3.setEnabled(false);

        //boolean exists = (new File(outputfile)).exists();
        //if (!exists){new File(outputfile).mkdirs();}


        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(String.valueOf(outputfile));

        mPlayer = new MediaPlayer();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    recorder.prepare();

                    recorder.start();

            }   catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             catch (IOException e) {
                e.printStackTrace();
            }

                b.setEnabled(false);
                b3.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                recorder.stop();
                recorder.release();
                recorder = null;
                b3.setEnabled(false);
                b2.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
               }catch (IllegalStateException e) {

                    e.printStackTrace();
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(String.valueOf(outputfile));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });



    }


    //      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //      intent.setType("image/*");
    //      startActivityForResult(Intent.createChooser(intent, "Complete action using"), REQUEST_PICKER);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            //return true;
        //}
        return super.onOptionsItemSelected(item);
    }

}







