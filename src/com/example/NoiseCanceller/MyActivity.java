package com.example.NoiseCanceller;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.nio.ByteBuffer;


public class MyActivity extends Activity {



    private static final int REQUEST_PICKER = 1;
    private static final int PICK_FROM_FILE = 1;
    final static int RQS_RECORDING = 1;
    MediaRecorder recorder = new MediaRecorder();
    MediaRecorder recorder2 = new MediaRecorder();
    //private String desired = "/sdcard/sample.mp4";
    String path = Environment.getExternalStorageDirectory().toString() + "/" + "Download";
    File desired = new File(path, "sample.mp4");
    String path2 = Environment.getExternalStorageDirectory().toString() + "/" + "Download";
    File input = new File(path, "sample2.mp4");
    File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaa.mp3");

    //String audioname = "sample.mp4";
    private MediaPlayer mPlayer = null;
    Uri savedUri;
    Button b;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
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
        b7 = (Button) findViewById(R.id.btn7);
        b8 = (Button) findViewById(R.id.btn8);

        b2.setEnabled(false);
        b3.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(String.valueOf(desired));

        recorder2 = new MediaRecorder();
        recorder2.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder2.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder2.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder2.setOutputFile(String.valueOf(input));

        mPlayer = new MediaPlayer();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    recorder.prepare();

                    recorder.start();

                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
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

                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
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
                } catch (IllegalStateException e) {

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
                } catch (IllegalStateException e) {

                    e.printStackTrace();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();

                MediaPlayer m = new MediaPlayer();
                int bytesRead;
                try {
                    m.setDataSource(String.valueOf(desired));


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
                                m.setDataSource(String.valueOf(input));


                                try {
                        m.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    m.start();
                    Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

                    m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer m) {
                            m.release();

                        };
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {

            double MU = 0.01;
            double fl = 200;

            @Override
            public void onClick(View v) {
                FileInputStream is = null;
                int bytesRead;

                try {
                    is = new FileInputStream(new File(String.valueOf(input)));
                    FileInputStream is2 = new FileInputStream(new File(String.valueOf(desired)));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] b2 = new byte[1024];

                    while ((bytesRead = is2.read(b2)) != -1) {

                        bos.write(b2, 0, bytesRead);
                    }

                    byte co[] = new byte[1025];
                    byte[] d = new byte[1025];
                    byte[] output = new byte[ 1025];
                    byte error[] = new byte[1025];

                    ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                    byte[] b = new byte[1024];


                    while ((bytesRead = is.read(b)) != -1) {

                        bos2.write(b, 0, bytesRead);
                    }

                    byte[] bytes = bos.toByteArray();
                    for(int j=0;j<b.length ;j++){
                        for(int i=0;i<b2.length ;i++)
                            co[j] = (byte) (b2[i] + error[j] * MU * d[j]);

                            d[j + 1] = d[j];
                             output[j] += (d[j] * co[j]);
                            error[j] = (byte)( b[j] - output[j]);


                    }


                    try {
                        f.createNewFile();
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(error);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Context context = v.getContext();
                int bytesRead;
                FileInputStream fis = null;
                MediaPlayer m = new MediaPlayer();



                try {
                    fis = new FileInputStream(f);
                    m.setDataSource(fis.getFD());
                    m.setAudioStreamType(AudioManager.STREAM_MUSIC);



                    try {
                        m.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    m.start();
                    Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

                    m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer m) {
                            m.release();

                        };
                    });
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







