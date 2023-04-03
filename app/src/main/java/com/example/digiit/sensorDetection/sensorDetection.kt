package com.example.digiit.sensorDetection

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class ShakeDetector : SensorEventListener {

    private var shakeTimestamp: Long = 0

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0].toDouble()
            val y = event.values[1]
            val z = event.values[2]

            val acceleration = Math.sqrt(x * x + y * y + z * z)
            val now = System.currentTimeMillis()

            if (acceleration > SHAKE_THRESHOLD_GRAVITY) {
                if (shakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return
                }

                shakeTimestamp = now
                onShakeDetected()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    private fun onShakeDetected() {
        // When the phone is shaking
        // TODO
    }

    companion object {
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7 // adjust this based on your needs
        private const val SHAKE_SLOP_TIME_MS = 500 // adjust this based on your needs
    }
}
