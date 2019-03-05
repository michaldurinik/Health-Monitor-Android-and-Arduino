// IRemoteInterface.aidl
package ie.dcu.healthmonitorv12;

// Declare any non-default types here with import statements

interface IRemoteInterface {

    Location getLastLocation();
    GPSPoint getGPSPoint();
}
