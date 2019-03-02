package ie.dcu.healthmonitorv12;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import static android.os.UserHandle.readFromParcel;

public final class GPSPoint implements Parcelable {

    public int latitude;
    public int longtitude;
    public Date timestamp;
    public double elevation;

    public static final Creator<GPSPoint> CREATOR = new Creator<GPSPoint>() {
        @Override
        public GPSPoint createFromParcel(Parcel parcel) {
            return new GPSPoint(parcel);
        }

        @Override
        public GPSPoint[] newArray(int i) {
            return new GPSPoint[0];
        }
    };

    public GPSPoint(){}

    private GPSPoint(Parcel parcel){
        readFromParcel(parcel);
    }

    public void writeToParcel(Parcel desc, int flags){
        desc.writeInt(latitude);
        desc.writeInt(longtitude);
        desc.writeDouble(elevation);
        desc.writeLong(timestamp.getTime());
    }

    public void readFromParcel(Parcel par){
        latitude = par.readInt();
        longtitude = par.readInt();
        elevation = par.readDouble();
        timestamp = new Date(par.readLong());
    }

    public int describeContents(){
        return 0;
    }
}
