// IRemoteInterface.aidl
package ie.dcu.healthmonitor;

// Declare any non-default types here with import statements

interface IRemoteInterface {

    Location getLastLocation();
    GPSPoint getGPSPoint();
}
