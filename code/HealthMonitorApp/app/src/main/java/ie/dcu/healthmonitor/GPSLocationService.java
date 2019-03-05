package ie.dcu.healthmonitorv12;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class GPSLocationService extends Service {
    public static final String GPS_SERVICE = "ie.dcu.healthmonitor.GPSLocationService.SERVICE";
    public static final String EXTRA_UPDATE_RATE = "update-rate";
    public static final String DEBUG_TAG = "GPSService";

    private LocationManager location = null;
    private int updateRate = -1;
    private Location firstLocation = null;
    private  Location lastLocation = null;
    private long firstTime = -1;
    private long lastTime = -1;

    @Override
    public void onCreate(){
        super.onCreate();
        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void  onStart(Intent intent, int startID){
        super.onStart(intent, startID);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        super.onStart(intent, startID);
        if(flags != 0)
            Log.w(DEBUG_TAG, "redelivered or retrying service start: " + flags);
        doServiceStart(intent, startID);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void doServiceStart(Intent intent, int startID){
        updateRate = intent.getIntExtra(EXTRA_UPDATE_RATE, -1);
        if(updateRate == -1)
            updateRate = 6000;

        Criteria criteria = new Criteria();
        criteria.setAccuracy((Criteria.NO_REQUIREMENT));
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String best = location.getBestProvider(criteria, true);
        location.requestLocationUpdates(best, updateRate, 0, trackListener);

    }

    @Override
    public void onDestroy(){
        Log.v(DEBUG_TAG, "onDestroy() called");
        if(location != null){
            location.removeUpdates(trackListener);
            location = null;
        }
        super.onDestroy();
    }

    private LocationListener trackListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            long thisTime = System.currentTimeMillis();
            long diffTime = lastTime - thisTime;
            Log.v(DEBUG_TAG, "diffTime = " + diffTime + "\n" + "updateRate = " + updateRate);
            if(diffTime < updateRate)
                return;
            lastTime = thisTime;
            String locInfo = String.format(Locale.getDefault(), "Current Location = (%f, %f) @ (%.1f meters up)", location.getLatitude(), location.getLongitude(), location.getAltitude());
            if(lastLocation != null) {
                float distance = location.distanceTo(lastLocation);
                locInfo += String.format("\n Distance from last = %.1f meters", distance);
                float lastSpeed = distance / diffTime;
                locInfo += String.format("\n\tSpeed: %.1fm/s", lastSpeed);
                if (location.hasSpeed()) {
                    float gpsSpeed = location.getSpeed();
                    locInfo += String.format(" (or %.1fm/s)", lastSpeed, gpsSpeed);
                }
            }
            if(firstLocation != null && firstTime != -1){
                float overallDistance = location.distanceTo(firstLocation);
                float overallSpeed = overallDistance / (thisTime - firstTime);
                locInfo += String.format("\n\tOverall speed: %.1fm/s over %.1f meters", overallSpeed, overallDistance);
            }
            lastLocation = location;
            if(firstLocation == null){
                firstLocation = location;
                firstTime = thisTime;
            }
            Toast.makeText(getApplicationContext(), locInfo, Toast.LENGTH_LONG).show();
            Log.v(DEBUG_TAG, "Test Time;");
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public IBinder onBind(Intent intent){
        return gpsRemoteInterfaceBinder;
    }

    private final IRemoteInterface.Stub gpsRemoteInterfaceBinder = new IRemoteInterface.Stub(){
        public Location getLastLocation(){
            Log.v("interface", "getLastLocation() call");
            return lastLocation;
        }

        public GPSPoint getPoint(){
            if(lastLocation == null)
                return null;
            else{
                Log.v("interface", "getPoint() call");
                GPSPoint point = new GPSPoint();
                point.elevation = lastLocation.getAltitude();
                point.latitude = (int) (lastLocation.getLatitude());
                point.longtitude = (int) (lastLocation.getLongitude());
                point.timestamp = new Date(lastLocation.getTime());
                return point;
            }
        }
    };

}
