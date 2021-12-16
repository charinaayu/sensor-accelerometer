package android.example.sensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    protected SensorManager SM;
    protected Sensor lightSensor;
    protected TextView txCahaya,txLog;
    int delay = SensorManager.SENSOR_DELAY_NORMAL;
    Database database;
    public static MainActivity ma;

    @Override
    protected void onPause(){
        super.onPause();
        SM.unregisterListener(lightlistener);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(MainActivity.this, updateActivity.class);
                startActivity(pindah);
            }
        });
        ma = this;
        database= new Database(this );

    }
    @Override
    protected void onResume(){
        super.onResume();
        SM.registerListener(lightlistener, lightSensor, delay);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txCahaya = findViewById(R.id.txCahaya);
        txLog = findViewById(R.id.txLog);

        SM = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            lightSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            txCahaya.setText("0");
            txLog.setText("");

            findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //register
                    //delay interval sensing

                    SM.registerListener(lightlistener, lightSensor, delay);

                }
            });

            findViewById(R.id.bt_stop).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SM.unregisterListener(lightlistener);
                }
            });



        } else {
            txCahaya.setText("Sensor NA");
        }
    }

    private SensorEventListener lightlistener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            String s = String.valueOf(sensorEvent.values[0]);
            txCahaya.setText(s);
            String cTime = new SimpleDateFormat("HH:mm:ss:SSS: ").format(new Date());
            txLog.setText("Time"+cTime+s+"\n"+txLog.getText());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

}