package org.twinone.locker.pickup.pattern;


import android.util.Log;

import weka.core.Instance;

/**
 * Created by julie on 8/1/15.
 */
public class Feature {
    XYZFeature accelerometer;
    XYZFeature magnetic;
    //    XYZFeature rotation;
    String timestamp;
    String user;
    private String TAG = "======";

    public Feature() {
        accelerometer = new XYZFeature();
        magnetic = new XYZFeature();
//        rotation = new XYZFeature();
    }

    public void clear() {
        this.user = null;
        this.timestamp = null;
        this.accelerometer.clear();
        this.magnetic.clear();
//        this.rotation.clear();
    }

    public void setPrefix() {
        this.accelerometer.setHeaderPrefix("accelerometer");
        this.magnetic.setHeaderPrefix("magnetic");
//        this.rotation.setHeaderPrefix("rotation");
    }

    public void setUser(String user) {
        this.user = user;
    }


    public boolean getFeatureFromCSV(String dir, String timestamp) {
        this.timestamp = timestamp;
        String accFileName = dir + "/" + timestamp + "acc.csv";
        accelerometer = new XYZFeature();
        Log.d(TAG, "Going to read:" + accFileName);
        if (!accelerometer.readFromCSV(accFileName)) {
            System.out.println("can't read accelerometer from csv file");
            return false;
        }

        String magneticFileName = dir + "/" + timestamp + "magnetic.csv";
        Log.d(TAG, "Going to read:" + magneticFileName);
        magnetic = new XYZFeature();
        if (!magnetic.readFromCSV(magneticFileName)) {
            System.out.println("can't read magnetic from csv file");
            return false;
        }

//        String rotationFileName = dir + timestamp + "rotation.csv";
//        rotation = new XYZFeature();
//        if (!rotation.readFromCSV(rotationFileName)) {
//            System.out.println("can't read rotation from csv file");
//            return false;
//        }
        setPrefix();
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
//		sb.append(this.user + "_" + timestamp + ",");
        sb.append(this.accelerometer.toString());
        sb.append(this.magnetic.toString());
//        sb.append(this.rotation.toString());
        return sb.toString();
    }

    public String getHeader() {
        StringBuilder sb = new StringBuilder();
//		sb.append("timestamp,");
        sb.append(this.accelerometer.getHeader());
        sb.append(this.magnetic.getHeader());
//        sb.append(this.rotation.getHeader());
        sb.append("label");
        return sb.toString();
    }

    public XYZFeature getAccelerometer() {
        return accelerometer;
    }

    public XYZFeature getMagnetic() {
        return magnetic;
    }

//    public XYZFeature getRotation() {
//        return rotation;
//    }

    public boolean setInstance(Instance ins) {
        setPrefix();
        Log.d(TAG,"in setInstance");
        if (this.accelerometer.setInstance(ins)
                && this.magnetic.setInstance(ins)) {
//            Attribute label =  new Attribute("label");
            ins.setValue(104, 0);
            return true;
        } else {
            return false;
        }
    }
}
