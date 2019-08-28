package com.example.ejemplousosensorproximidad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtActivado;
    private TextView txtDesactivado;

    SensorManager sensorManager;

    Sensor sensor;

    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtActivado = findViewById(R.id.lblTituloFinal);
        txtDesactivado = findViewById(R.id.lblTituloInicial);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensor =sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(sensor.equals(null)){
            finish();
        }

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent)
            {
                if(sensorEvent.values[0] < sensor.getMaximumRange()){
                    System.out.println("valor sensorEvent -> " + sensorEvent.values[0]);
                    System.out.println("Rango del sensor -> " + sensor.getMaximumRange());
                    txtActivado.setVisibility(View.VISIBLE);
                    txtDesactivado.setVisibility(View.INVISIBLE);
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255, 255));
                    Toast.makeText(getApplicationContext(), "Sensor se activo",Toast.LENGTH_LONG).show();
                }
                else{
                    txtActivado.setVisibility(View.INVISIBLE);
                    txtDesactivado.setVisibility(View.VISIBLE);
                    getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 2, 2));
                    Toast.makeText(getApplicationContext(), "Sensor se desactivo",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();

    }

    public void start(){
        sensorManager.registerListener(sensorEventListener, sensor, 2000*1000);
    }

    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }
}
