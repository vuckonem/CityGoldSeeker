package com.example.citygoldseeker;

import android.util.Log;

public class CGS_Executors {


	// Checks if measured coordinates are in +/- 5 range of the recorded values
    public boolean closeCoordinatesCheck(int position, int latMeasured, int longMeasured){
    	boolean foundLocation = false;
    	// latitude for recorded location
    	final int latSaved = Ipsum.Coordinates_Lat[position];
    	// longitude for recorded location
    	final int longSaved = Ipsum.Coordinates_Long[position];
    	
    	for(int i = 0; i<10; i++){
    		if(((latMeasured == latSaved) || (latMeasured == latSaved-i) || (latMeasured == latSaved+i)) 
    				&& ((longMeasured == longSaved) || (longMeasured == longSaved-1) || (longMeasured == longSaved+1))){
    			foundLocation = true;
    			Log.e("CGS_EXECUTORS","Location Found on iteration: " + i);
    			break;
    		}
    	}
    	
    	// Log that location is not in range
    	if(!foundLocation){
    		Log.e("CGS_EXECUTORS","Location NOT Found");
    	}
    	
    	return foundLocation;
    }
}