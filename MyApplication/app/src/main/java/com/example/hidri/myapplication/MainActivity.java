package com.example.hidri.myapplication;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    static String MQTTHOST="tcp://mqtt.dioty.co:1883";
    static String USERNAME ="safa.hidri@ensi-uma.tn";
    static String PASSWORD="360c3768";
    static String StrTopic="/safa.hidri@ensi-uma.tn/led";
     MqttAndroidClient client;
    Button buttonPub;
    Switch switch1;
    TextView subtext;
    Vibrator vibarator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subtext=(TextView)findViewById(R.id.subtext);
        vibarator =(Vibrator)getSystemService(VIBRATOR_SERVICE);


        String clientId = MqttClient.generateClientId();
        client =new MqttAndroidClient(this.getApplicationContext(),MQTTHOST, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());


        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_LONG).show();
                    sub();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_LONG).show();

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
              subtext.setText(new String(message.getPayload()));
              vibarator.vibrate(500);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

       buttonPub=(Button)findViewById(R.id.buttonPub);
       buttonPub.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               pub(v);
           }
       });


        switch1 = (Switch) findViewById(R.id.switch1);
       switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   Toast.makeText(MainActivity.this, "Led is ON", Toast.LENGTH_LONG).show();
                   try {
                       client.publish(StrTopic,"T".getBytes(),0,false);
                   } catch (MqttException e) {
                       e.printStackTrace();
                   }
               }else{
                   Toast.makeText(MainActivity.this, "Led is OFF", Toast.LENGTH_LONG).show();
                   try {
                       client.publish(StrTopic,"F".getBytes(),0,false);
                   } catch (MqttException e) {
                       e.printStackTrace();
                   }


               }
           }
       });


    }
    public void pub(View v){
        String topic = StrTopic;
        String message = "hello";
        try {

            client.publish(topic, message.getBytes(),0,false);
        } catch ( MqttException e) {
            e.printStackTrace();
        }
    }

    public void sub() {
       try{
           client.subscribe("/safa.hidri@ensi-uma.tn/temperature",0);
         }
        catch (MqttException e) {
           e.printStackTrace();
       }
    }







}
