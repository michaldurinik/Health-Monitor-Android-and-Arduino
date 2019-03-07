package ie.dcu.healthmonitor;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.util.UUID;

public class MonitorActivity extends AppCompatActivity {

    private static final String TAG = "BluetoothGattActivity";
    private static final String DEVICE_NAME = "SensorTag";
    private static final String BLUETOOTH_SERVICE = "bluetooth";

    // Heart Rate Monitor Service
    private static final UUID HEART_RATE_MEASUREMENT = UUID.fromString("0x2A37");
    private static final UUID HEART_RATE_CONTROL_POINT = UUID.fromString("0x2A39");
    // Temperature Service
    private static final UUID TEMPERATURE_MEASUREMENT = UUID.fromString("0x2A1C");
    private static final UUID TEMPERATURE = UUID.fromString("0x2A6E");
    // CLient configuration descriptor
    private static final UUID CONFIG_DESCRIPTOR = UUID.fromString("");

    private BluetoothAdapter mBluetoothAdapter;
    private SparseArray<BluetoothDevice> mDevices;
    private BluetoothGatt mConnectedGatt;
    private TextView mTemperature, mHeartRate;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_monitor);

        //Display the results in text fields
        mTemperature = (TextView) findViewById(R.id.temperatureTextView);
        mHeartRate = (TextView) findViewById(R.id.heartTextView);

        // Access bluetooth through BluetoothManager from Android 4.3
        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = manager.getAdapter();
        mDevices = new SparseArray<BluetoothDevice>();

        // Progress dialog while connection is taking place
        mProgress = new ProgressDialog(this);
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);
    }

    @Override
    protected void onResume(){
        super.onResume();

        // Check if bluetooth is enabled
        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
            //Bluetooth is disabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            finish();
            return;
        }

        // Check for le bluetooth is declared in Manifest but we will check again
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            Toast.makeText(this, "No LE Support", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        clearDisplayValues();
    }

    @Override
    protected  void onPause(){
        super.onPause();
        // Hide dialog
        mProgress.dismiss();
        // Cancel scans
        mHandler.removeCallbacks(mStopRunnable);
        mHandler.removeCallbacks(mStartRunnable);
        mBluetoothAdapter.stopLeScan(this);
    }

    @Override
    protected void onStop(){
        super.onStop();
        // Disconnect from active tag connections
        if(mConnectedGatt != null){
            mConnectedGatt.disconnect();
            mConnectedGatt = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Add option to menu
        getMenuInflater().inflate(R.menu.monitor, menu);
        for(int i = 0; i < mDevices.size(); i++){
            BluetoothDevice device = mDevices.valueAt(i);
            menu.add(0, mDevices.keyAt(i), 0, device.getName());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_scan:
                mDevices.clear();
                startScan();
                return true;
            default:
                // Obtain discovered device
                BluetoothDevice device = mDevices.get(item.getItemId());
                Log.i(TAG, "Connecting to " + device.getName());
                // Make connection with LE-Specific Gatt method, passing a callback for events
                mConnectedGatt = device.connectGatt(this, true, mGattCallback);
                // Display progress
                mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Connecting to " + device.getName() + "..."));
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearDisplayValues(){
        mTemperature.setText("---");
        mHeartRate.setText("---");
    }

    private Runnable mStopRunnable = new Runnable() {
        @Override
        public void run() {
            stopScan();
        }
    };
    private Runnable mStartRunnable = new Runnable() {
        @Override
        public void run() {
            startScan();
        }
    };

    private void startScan(){
        mBluetoothAdapter.startLeScan(this);
        setProgressBarIndeterminateVisibility(true);

        mHandler.postDelayed(mStopRunnable, 2500);
    }

    private void stopScan(){
        mBluetoothAdapter.stopLeScan(this);
        setProgressBarIndeterminateVisibility(false);
    }

    // Bluetooth le callback
    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord){
        Log.i(TAG, "New LE Device: " + device.getName() + " @ " + rssi);
        // Looking for SensorTag devices only so validate name before adding to collection
        if(DEVICE_NAME.equals(device.getName())){
            mDevices.put(device.hashCode(), device);
            // Update overflow menu
            invalidateOptionsMenu();
        }
    }
    /*
     * In this callback, a state machine is created to enforce that only one characteristic
     * be read or written at a time
     */
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        // State machine tracking
        private int mState = 0;

        private void reset() {
            mState = 0;
        }

        private void advance() {
            mState++;
        }

        /*
         * Send an enable command to each sensor with configuration characteristic.
         * Needed to get power low by disabling sensors not used
         */
        private void enableNextSensor(BluetoothGatt gatt) {
            BluetoothGattCharacteristic characteristic;
            switch (mState) {
                case 0:
                    Log.d(TAG, "Enabling Heart Rate");
                    characteristic = gatt.getService(HEART_RATE_MEASUREMENT).getCharacteristic(HEART_RATE_MEASUREMENT);
                    characteristic.setValue(new byte[]{0x01});
                    break;
                case 1:
                    Log.d(TAG, "Enabling Temperature");
                    characteristic = gatt.getService(TEMPERATURE).getCharacteristic(TEMPERATURE_MEASUREMENT);
                    characteristic.setValue(new byte[]{0x01});
                default:
                    mHandler.sendEmptyMessage(MSG_DISMISS);
                    Log.d(TAG, "All sensors enabled");
                    return;
            }
            gatt.writeCharacteristic(characteristic);
        }

        // Read the datas characteristics value for each sensor
        private void readNextSensor(BluetoothGatt gatt) {
            BluetoothGattCharacteristic characteristic;
            switch (mState) {
                case 0:
                    Log.d(TAG, "Reading temperature");
                    characteristic = gatt.getService(TEMPERATURE).getCharacteristic(TEMPERATURE_MEASUREMENT);
                    break;
                case 1:
                    Log.d(TAG, "Reading Heart rate");
                    characteristic = gatt.getService(HEART_RATE_MEASUREMENT).getCharacteristic(HEART_RATE_CONTROL_POINT);
                    break;
                default:
                    return;
            }
        }

        /*
         * Enable notifications of changes on the data characteristic for each sensor
         * by writing the ENABLE_NOTIFICATION_VALUE flag to that of the characteristics
         * configuration descriptor
         */
        private void setNotifyNextSensor(BluetoothGatt gatt) {
            BluetoothGattCharacteristic characteristic;
            switch (mState) {
                case 0:
                    Log.d(TAG, "Set notify on Heart Rate");
                    characteristic = gatt.getService(HEART_RATE_MEASUREMENT).getCharacteristic(HEART_RATE_CONTROL_POINT);
                    break;
                case 1:
                    Log.d(TAG, "Set notify on Temperature");
                    characteristic = gatt.getService(TEMPERATURE).getCharacteristic(TEMPERATURE_MEASUREMENT);
                default:
                    mHandler.sendEmptyMessage(MSG_DISMISS);
                    Log.i(TAG, "All Sensors Enabled");
                    return;
            }

            // Enable local notification
            gatt.setCharacteristicNotification(characteristic, true);
            // Enable remote notification
            BluetoothGattDescriptor desc = characteristic.getDescriptor(CONFIG_DESCRIPTOR);
            desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(desc);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.d(TAG, "Connection State Change: " + status + " -> " + connectionState(newState));
            if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
                // Once connected, discover services on device
                gatt.discoverServices();
                mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Discovering services..."));
            } else if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_DISCONNECTED) {
                // if disconnected send message to clear values
                mHandler.sendEmptyMessage(MSG_CLEAR);
            } else if (status != BluetoothGatt.GATT_SUCCESS) {
                // any failure disconnect
                gatt.disconnect();
                ;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.d(TAG, "Services discovered: " + status);
            mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Enabling Sensors..."));
            // Discovered services now reset state machine and start enabling sensors
            reset();
            enableNextSensor(gatt);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            // For each read pass data to ui thread
            if (TEMPERATURE.equals(characteristic.getUuid()))
                mHandler.sendMessage(Message.obtain(null, MSG_TEMPERATURE, characteristic));
            if (HEART_RATE_MEASUREMENT.equals(characteristic.getUuid()))
                mHandler.sendMessage(Message.obtain(null, MSG_HEART_RATE, characteristic));

            // After reading value enable notifications
            setNotifyNextSensor(gatt);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            // after writing the enable fla read the initial value
            readNextSensor(gatt);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            // After notifications are enabled all updates on changes will appear here
            // Hand these over to the UI
            if(TEMPERATURE.equals(characteristic.getUuid()))
                mHandler.sendMessage(Message.obtain(null, MSG_TEMPERATURE, characteristic));
            if(HEART_RATE_MEASUREMENT.equals(characteristic.getUuid()))
                mHandler.sendMessage(Message.obtain(null, MSG_HEART_RATE, characteristic));
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status){
            Log.d(TAG, "Remote RSSI: " + rssi);
        }

        private String connectionState(int status){
            switch(status){
                case BluetoothProfile.STATE_CONNECTED:
                    return "Connected";
                case BluetoothProfile.STATE_DISCONNECTED:
                    return "Disconnected";
                case BluetoothProfile.STATE_CONNECTING:
                    return "Connecting";
                case BluetoothProfile.STATE_DISCONNECTING:
                    return "Disconnecting";
                default:
                    return String.valueOf(status);
            }
        }
    };

    // Here is a handler to process results onto the main thread
    private static final int MSG_TEMPERATURE = 101;
    private static final int MSG_HEART_RATE = 102;
    private static final int MSG_PROGRESS = 201;
    private static final int MSG_DISMISS = 202;
    private static final int MSG_CLEAR = 301;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            BluetoothGattCharacteristic characteristic;
            switch (msg.what) {
                case MSG_TEMPERATURE:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaing temperature value");
                        return;
                    }
                    updateTemperatureValues(characteristic);
                    break;
                case MSG_HEART_RATE:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining Heart rate value");
                        return;
                    }
                    updateHeartRateValues(characteristic);
                    break;
                case MSG_PROGRESS:
                    mProgress.setMessage((String) msg.obj);
                    ;
                    if (!mProgress.isShowing())
                        mProgress.show();
                    break;
                case MSG_DISMISS:
                    mProgress.hide();
                    break;
                case MSG_CLEAR:
                    clearDisplayValues();
                    break;
            }
        }
    };

    // Methods to extract data and update UI

    private void updateTemperatureValues(BluetoothGattCharacteristic characteristic){
        double temperature = SensorData.extractTemperature(characteristic);
        mTemperature.setText(String.format("%.1f\u00B0C", temperature));
    }
    private void updateHeartRateValues(BluetoothGattCharacteristic characteristic){
        int heartRate = SensorData.extractHeartRate(characteristic);
        mHeartRate.setText(String.format("%%%bpm", heartRate));
    }
}
