package dev.medi.code.bis.game.calibrate;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import dev.medi.code.bis.config.DeviceSettings;

public class Accelerometer implements SensorEventListener {

    private AccelerometerDelegate delegate;

    private float currentAccelerationX;
    private float currentAccelerationY;

    private float calibratedAccelerationX;
    private float calibratedAccelerationY;

    private SensorManager sensorManager;

    private int calibrated;

    private boolean enableSensor;

    static Accelerometer sharedAccelerometer = null;

    public static Accelerometer sharedAccelerometer() {

        if (sharedAccelerometer == null) {
            sharedAccelerometer = new Accelerometer();
        }

        return sharedAccelerometer;

    }

    // Class Constructor
    public Accelerometer() {
        this.catchAccelerometer();
    }

    public void catchAccelerometer() {

        // [Recebe o sensor que foi passado para DeviceSettings e atribui à variável local]
        sensorManager = DeviceSettings.getSensorManager();
        // Register Sensor Listener [Registra o ouvidor do sensor]
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                sensorManager.SENSOR_DELAY_GAME
        );

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
        // Not in use
    }

    @Override
    public void onSensorChanged(SensorEvent acceleration) {

        if (calibrated < 100) {
            this.calibratedAccelerationX += acceleration.values[0];
            this.calibratedAccelerationY += acceleration.values[1];

            System.out.println(acceleration.values[0]); // Check
            System.out.println(acceleration.values[1]); // Check

            calibrated++;

            if (calibrated == 100) {
                this.calibratedAccelerationX /= 100;
                this.calibratedAccelerationY /= 100;
            }

            return;

        }

        // Read acceleration [Leitura do acelerômetro]
        this.currentAccelerationX = acceleration.values[0] - this.calibratedAccelerationX;
        this.currentAccelerationY = acceleration.values[1] - this.calibratedAccelerationY;

        // Dispatch Accelerometer Read [Envia Leitura do Acelerômetro]
        if (this.delegate != null && this.enableSensor) {
            this.delegate.accelerometerDidAccelerate(currentAccelerationX, currentAccelerationY);
        }

    }

    public void updateCalibration(int calibrated) {
        this.calibrated = calibrated;
    }

    // Enables or Disables the Sensor [Habilita ou Desabilita o Sensor]
    public void enableSensor(boolean enableSensor) {
        this.enableSensor = enableSensor;
    }

    public void setDelegate(AccelerometerDelegate delegate) {
        this.delegate = delegate;
    }

    public AccelerometerDelegate getDelegate() {
        return this.delegate;
    }

}
