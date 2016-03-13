package com.example.NoiseCanceller;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;


public class MyActivity extends Activity {



    private static final int REQUEST_PICKER = 1;
    private static final int PICK_FROM_FILE = 1;
    final static int RQS_RECORDING = 1;
    MediaRecorder recorder = new MediaRecorder();
    MediaRecorder recorder2 = new MediaRecorder();
    //private String outputfile = "/sdcard/sample.mp4";
    String path = Environment.getExternalStorageDirectory().toString() + "/" + "Download";
    File outputfile = new File(path, "sample.mp4");
    String path2 = Environment.getExternalStorageDirectory().toString() + "/" + "Download";
    File outputfile2 = new File(path, "sample2.mp4");


    //String audioname = "sample.mp4";
    private MediaPlayer mPlayer = null;
    Uri savedUri;
    Button b;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    TextView txt;

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
        b4 = (Button) findViewById(R.id.btn4);
        b5 = (Button) findViewById(R.id.btn5);
        b6 = (Button) findViewById(R.id.btn6);
        txt = (TextView)findViewById(R.id.txt);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(String.valueOf(outputfile));

        recorder2 = new MediaRecorder();
        recorder2.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder2.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder2.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder2.setOutputFile(String.valueOf(outputfile2));

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
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    recorder2.prepare();

                    recorder2.start();

                }   catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                b4.setEnabled(false);
                b6.setEnabled(true);

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

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    recorder2.stop();
                    recorder2.release();
                    recorder2 = null;
                    b6.setEnabled(false);
                    b5.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                }catch (IllegalStateException e) {

                    e.printStackTrace();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                int bytesRead;
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(String.valueOf(outputfile));
                    FileInputStream is = new FileInputStream (new File(String.valueOf(outputfile)));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] b = new byte[1024];

                    while ((bytesRead = is.read(b)) != -1) {

                        bos.write(b, 0, bytesRead);
                    }
                    byte[] bytes = bos.toByteArray();
                    txt.append(String.valueOf(bytes[b.length]));

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                    m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                int bytesRead;
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(String.valueOf(outputfile2));
                    FileInputStream is = new FileInputStream (new File(String.valueOf(outputfile)));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] b = new byte[1024];

                    while ((bytesRead = is.read(b)) != -1) {

                        bos.write(b, 0, bytesRead);
                    }
                    byte[] bytes = bos.toByteArray();
                    txt.setText(String.valueOf(bytes[b.length]));

                    try {
                        m.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    m.start();
                    Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }

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







