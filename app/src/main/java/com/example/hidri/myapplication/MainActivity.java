package com.example.hidri.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MainActivity extends AppCompatActivity {

    public MqttAndroidClient client;

    Button b1, b2, b3,b4,buconnect,b5,b6;

    Switch lampe1,lampe2,lampe3,switch6;
    SeekBar seekbar1,seekbar2,seekbar3;
    TextView subtext,subtext2,subtext5,Username;
    EditText Password1;
    Vibrator vibarator;
    ImageView img2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        subtext= findViewById(R.id.subtext);
        subtext2= findViewById(R.id.subtext2);
        subtext5=findViewById(R.id.subtext5);
        Username=findViewById(R.id.Username);
       // Password=findViewById(R.id.Constants.Password);
       // Username1=findViewById(R.id.Constants.Username1);
        Password1=findViewById(R.id.Password1);
        vibarator =(Vibrator)getSystemService(VIBRATOR_SERVICE);





        String clientId = MqttClient.generateClientId();
        client =new MqttAndroidClient(this.getApplicationContext(),Constants.MQTTHOST, clientId);


        LinearLayout linearLayout0 = (LinearLayout)findViewById(R.id.layout0);
        linearLayout0.setVisibility(View.VISIBLE);
        LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.layout1);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.layout2);
        linearLayout2.setVisibility(View.GONE);

        LinearLayout linearLayout3 = (LinearLayout)findViewById(R.id.layout3);
        linearLayout3.setVisibility(View.GONE);

        LinearLayout linearLayout4 = (LinearLayout)findViewById(R.id.layout4);
        linearLayout4.setVisibility(View.GONE);

        LinearLayout linearLayout5 = (LinearLayout)findViewById(R.id.layout5);
        linearLayout5.setVisibility(View.GONE);

        LinearLayout linearLayout6 = (LinearLayout)findViewById(R.id.layout6);
        linearLayout6.setVisibility(View.GONE);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(Constants.USERNAME);
        options.setPassword(Constants.PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {


                        subgaz();
                        subtemp();
                        submac();
                        subalertegaz();
                        subporte();

                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Connecting ...", Toast.LENGTH_LONG).show();
                    WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                        if (topic.equals(Constants.StrTopicGaz))
                        {
                            subtext2.setText( new String(message.getPayload())) ;
                        }

                        if (topic.equals(Constants.StrTopicTemperature))
                        {
                            subtext.setText( new String(message.getPayload())) ;
                        }
                        if (topic.equals(Constants.StrTopicMac))
                        {
                            subtext5.setText( new String(message.getPayload())) ;
                        }
                if (topic.equals(Constants.StrTopicAlerte))
                {

                    notification();
                }
                vibarator.vibrate(500);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
         buconnect=findViewById(R.id.buconnect);
         buconnect.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_LONG).show();

                 LinearLayout linearLayout0 = (LinearLayout) findViewById(R.id.layout0);
                 linearLayout0.setVisibility(View.GONE);

                 LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout1);
                 linearLayout.setVisibility(View.VISIBLE);

                 LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.layout2);
                 linearLayout2.setVisibility(View.GONE);

                 LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.layout3);
                 linearLayout3.setVisibility(View.GONE);

                 LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.layout4);
                 linearLayout4.setVisibility(View.GONE);

                 LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.layout5);
                 linearLayout5.setVisibility(View.GONE);

                 LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.layout6);
                 linearLayout6.setVisibility(View.GONE);}



         });


        b1=findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout0 = (LinearLayout)findViewById(R.id.layout0);
                linearLayout0.setVisibility(View.GONE);

                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout1);
                linearLayout.setVisibility(View.GONE);

                LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.layout2);
                linearLayout2.setVisibility(View.GONE);

                LinearLayout linearLayout3 = (LinearLayout)findViewById(R.id.layout3);
                linearLayout3.setVisibility(View.GONE);

                LinearLayout linearLayout4 = (LinearLayout)findViewById(R.id.layout4);
                linearLayout4.setVisibility(View.VISIBLE);

                LinearLayout linearLayout5 = (LinearLayout)findViewById(R.id.layout5);
                linearLayout5.setVisibility(View.GONE);

                LinearLayout linearLayout6 = (LinearLayout)findViewById(R.id.layout6);
                linearLayout6.setVisibility(View.GONE);

            }
        });

        b2=(Button)findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout0 = (LinearLayout)findViewById(R.id.layout0);
                linearLayout0.setVisibility(View.GONE);

                LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.layout1);
                linearLayout1.setVisibility(View.GONE);

                LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.layout2);
                linearLayout2.setVisibility(View.GONE);

                LinearLayout linearLayout3 = (LinearLayout)findViewById(R.id.layout3);
                linearLayout3.setVisibility(View.VISIBLE);

                LinearLayout linearLayout4 = (LinearLayout)findViewById(R.id.layout4);
                linearLayout4.setVisibility(View.GONE);

                LinearLayout linearLayout5 = (LinearLayout)findViewById(R.id.layout5);
                linearLayout5.setVisibility(View.GONE);

                LinearLayout linearLayout6 = (LinearLayout)findViewById(R.id.layout6);
                linearLayout6.setVisibility(View.GONE);

            }
        });

       b3=(Button)findViewById(R.id.b3);
       b3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               LinearLayout linearLayout0 = (LinearLayout)findViewById(R.id.layout0);
               linearLayout0.setVisibility(View.GONE);

               LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.layout1);
               linearLayout1.setVisibility(View.GONE);

               LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.layout2);
               linearLayout2.setVisibility(View.VISIBLE);

               LinearLayout linearLayout3 = (LinearLayout)findViewById(R.id.layout3);
               linearLayout3.setVisibility(View.GONE);

               LinearLayout linearLayout4 = (LinearLayout)findViewById(R.id.layout4);
               linearLayout4.setVisibility(View.GONE);

               LinearLayout linearLayout5 = (LinearLayout)findViewById(R.id.layout5);
               linearLayout5.setVisibility(View.GONE);

               LinearLayout linearLayout6 = (LinearLayout)findViewById(R.id.layout6);
               linearLayout6.setVisibility(View.GONE);


           }
       });




        b4=(Button)findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout0 = (LinearLayout)findViewById(R.id.layout0);
                linearLayout0.setVisibility(View.GONE);

                LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.layout1);
                linearLayout1.setVisibility(View.GONE);

                LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.layout2);
                linearLayout2.setVisibility(View.GONE);

                LinearLayout linearLayout3 = (LinearLayout)findViewById(R.id.layout3);
                linearLayout3.setVisibility(View.GONE);

                LinearLayout linearLayout4 = (LinearLayout)findViewById(R.id.layout4);
                linearLayout4.setVisibility(View.GONE);

                LinearLayout linearLayout5 = (LinearLayout)findViewById(R.id.layout5);
                linearLayout5.setVisibility(View.VISIBLE);

                LinearLayout linearLayout6 = (LinearLayout)findViewById(R.id.layout6);
                linearLayout6.setVisibility(View.GONE);


            }
        });
        b5=(Button)findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://10.42.0.2:8081"));
                startActivity(browserIntent);
            }
        });

        b6=(Button) findViewById(R.id.b6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout0 = (LinearLayout)findViewById(R.id.layout0);
                linearLayout0.setVisibility(View.GONE);

                LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.layout1);
                linearLayout1.setVisibility(View.GONE);

                LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.layout2);
                linearLayout2.setVisibility(View.GONE);

                LinearLayout linearLayout3 = (LinearLayout)findViewById(R.id.layout3);
                linearLayout3.setVisibility(View.GONE);

                LinearLayout linearLayout4 = (LinearLayout)findViewById(R.id.layout4);
                linearLayout4.setVisibility(View.GONE);

                LinearLayout linearLayout5 = (LinearLayout)findViewById(R.id.layout5);
                linearLayout5.setVisibility(View.GONE);

                LinearLayout linearLayout6 = (LinearLayout)findViewById(R.id.layout6);
                linearLayout6.setVisibility(View.VISIBLE);
            }
        });



        lampe1 = findViewById(R.id.switch1);
        lampe1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


               if(isChecked){
                   Toast.makeText(MainActivity.this, "Lampe allumée", Toast.LENGTH_LONG).show();
                   try {
                       client.publish(Constants.StrTopicLumiereChambre1,"T".getBytes(),0,false);
                   } catch (MqttException e) {
                       e.printStackTrace();
                   }
               }else{
                   Toast.makeText(MainActivity.this, "Lampe  eteinte", Toast.LENGTH_LONG).show();
                   try {
                       client.publish(Constants.StrTopicLumiereChambre1,"F".getBytes(),0,false);
                   } catch (MqttException e) {
                       e.printStackTrace();
                   }


               }
           }
       });
        lampe2 = findViewById(R.id.switch2);
        lampe2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    Toast.makeText(MainActivity.this, "Lampe allumée", Toast.LENGTH_LONG).show();
                    try {
                        client.publish(Constants.StrTopicLumiereChambre2,"T".getBytes(),0,false);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Lampe eteinte", Toast.LENGTH_LONG).show();
                    try {
                        client.publish(Constants.StrTopicLumiereChambre2,"F".getBytes(),0,false);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
        lampe3 = findViewById(R.id.switch3);
        lampe3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    Toast.makeText(MainActivity.this, "Lampe allumée", Toast.LENGTH_LONG).show();
                    try {
                        client.publish(Constants.StrTopicLumiereChambre2,"T".getBytes(),0,false);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Lampe eteinte", Toast.LENGTH_LONG).show();
                    try {
                        client.publish(Constants.StrTopicLumiereChambre2,"F".getBytes(),0,false);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        switch6 = findViewById(R.id.switch6);
        switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    Toast.makeText(MainActivity.this, "la porte est ouverte", Toast.LENGTH_LONG).show();
                    try {
                        client.publish(Constants.StrTopiccontrolporte,"o".getBytes(),0,false);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "la porte est fermée ", Toast.LENGTH_LONG).show();
                    try {
                        client.publish(Constants.StrTopiccontrolporte,"c".getBytes(),0,false);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        seekbar1=findViewById(R.id.seekBar1);
        seekbar1.setMax(3);
        seekbar1.setProgress(1);
        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "L'intensité de la lumiére est à :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();

                try {
                    if(progressChangedValue==1){
                        client.publish(Constants.StrTopicLumierevariation,"1".getBytes(),0,false);
                    }else if(progressChangedValue==0){
                        client.publish(Constants.StrTopicLumierevariation,"0".getBytes(),0,false);}
                    else if(progressChangedValue==2) {
                        client.publish(Constants.StrTopicLumierevariation,"2".getBytes(),0,false);
                    }
                        else{client.publish(Constants.StrTopicLumierevariation,"3".getBytes(),0,false);}
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        seekbar2=findViewById(R.id.seekBar2);
        seekbar2.setMax(3);
        seekbar2.setProgress(1);
        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "L'intensité de la lumiére est à :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                try {
                    if(progressChangedValue==1){
                        client.publish(Constants.StrTopicLumierevariation,"1".getBytes(),0,false);
                    }else if(progressChangedValue==0){
                        client.publish(Constants.StrTopicLumierevariation,"0".getBytes(),0,false);}
                    else if(progressChangedValue==2) {
                        client.publish(Constants.StrTopicLumierevariation,"2".getBytes(),0,false);}
                        else{client.publish(Constants.StrTopicLumierevariation,"3".getBytes(),0,false);}
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        seekbar3=findViewById(R.id.seekBar3);
        seekbar3.setMax(3);
        seekbar3.setProgress(1);
        seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "L'intensité de la lumiére est à :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                try {
                    if(progressChangedValue==1){
                        client.publish(Constants.StrTopicLumierevariation,"1".getBytes(),0,false);
                    }else if(progressChangedValue==0){
                        client.publish(Constants.StrTopicLumierevariation,"0".getBytes(),0,false);}
                    else if(progressChangedValue==2) {
                        client.publish(Constants.StrTopicLumierevariation,"2".getBytes(),0,false);}
                        else{client.publish(Constants.StrTopicLumierevariation,"3".getBytes(),0,false);}
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public void pub(View v){
        String topic = Constants.StrTopicLumiereChambre1;
        String message = "hello";
        try {

            client.publish(topic, message.getBytes(),0,false);
        } catch ( MqttException e) {
            e.printStackTrace();
        }
    }
    public void subgaz() {
        try {
                client.subscribe(Constants.StrTopicGaz, 0);

        }catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void subtemp() {
        try {

            client.subscribe(Constants.StrTopicTemperature, 0);

        }catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void submac() {
        try {

            client.subscribe(Constants.StrTopicMac, 0);

        }catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subporte() {
        try {

            client.subscribe(Constants.StrTopiccontrolporte, 0);

        }catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void subalertegaz() {
        try {

            client.subscribe(Constants.StrTopicAlerte, 0);

        }catch (MqttException e) {
            e.printStackTrace();
        }
    }
    int backpress=0;
    @Override
    public void onBackPressed() {

            LinearLayout linearLayout0 = (LinearLayout) findViewById(R.id.layout0);
            linearLayout0.setVisibility(View.GONE);

            LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.layout2);
            linearLayout2.setVisibility(View.GONE);

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout1);
            linearLayout.setVisibility(View.VISIBLE);

            LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.layout3);
            linearLayout3.setVisibility(View.GONE);

            LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.layout4);
            linearLayout4.setVisibility(View.GONE);

            LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.layout5);
            linearLayout5.setVisibility(View.GONE);

            LinearLayout linearLayout6 = (LinearLayout) findViewById(R.id.layout6);
            linearLayout6.setVisibility(View.GONE);

     backpress=backpress+1;
       if(backpress==7){
            finish();
            super.onBackPressed();}

    }


    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE);
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   public void notification(){

       NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
       Notification notify=new Notification.Builder
               (getApplicationContext()).setContentTitle("Le taux de gaz est elevé !!").setContentText("").
               setContentTitle("Le taux de gaz est elevé !!").setSmallIcon(R.drawable.alerte).build();

       notify.flags |= Notification.FLAG_AUTO_CANCEL;
       notif.notify(2, notify);
       playNotificationSound();



   }




}
