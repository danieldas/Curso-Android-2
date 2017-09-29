package com.das.daniel.shakeacelerometro;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener  {

    private long ultActualizacion = -1;
    private float x, y, z;
    private float ult_x, ult_y, ult_z;
    private static final int SHAKE_THRESHOLD = 800;
    private SensorManager sensorManager;
    private RelativeLayout relativeLayout;
    private TextView _tvTexto;
    private boolean isShaked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        if(isShaked){
            reiniciarPantalla();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();

            if ((curTime - ultActualizacion) > 100)
            {
                long diffTime = (curTime - ultActualizacion);
                ultActualizacion = curTime;

                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];

                float speed = Math.abs(x+y+z - ult_x - ult_y - ult_z) / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD)
                {
                    isShaked = true;
                    relativeLayout.setBackgroundColor(Color.RED);
                    _tvTexto.setText("Funciona el SHAKE, presione para repetir");
                    _tvTexto.setTextColor(Color.WHITE);
                }
                ult_x = x;
                ult_y = y;
                ult_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager == null) {
            relativeLayout = (RelativeLayout) findViewById(R.id.miLayout);
            relativeLayout.setOnClickListener(this);
            _tvTexto = (TextView) findViewById(R.id.tvTexto);
            reiniciarPantalla();

            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            boolean accelSupported = sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

            if (!accelSupported) {

                sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            }
        }
    }
    protected void onPause() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            sensorManager = null;
            reiniciarPantalla();
        }
        super.onPause();
    }
    private void reiniciarPantalla() {
        relativeLayout.setBackgroundColor(Color.WHITE);
        _tvTexto.setText(R.string.shake);
        _tvTexto.setTextColor(Color.BLACK);
    }
}
