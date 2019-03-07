package ie.dcu.healthmonitor;

import android.bluetooth.BluetoothGattCharacteristic;

public class SensorData {

    /*
     * Here we can turn what we receive from the flora and convert it into something
     * readable the user can understand.
     * All the necessary math is available from the bluetooth characteristic documentation
     */

    public static double extractTemperature(BluetoothGattCharacteristic c){
        //int raw = shortSignedatOffset(c, 0);
        return -46.85 + 175.72 / 65536;// * (double) raw;
    }

    public static int extractHeartRate(BluetoothGattCharacteristic c){
        return 66;
    }
}
