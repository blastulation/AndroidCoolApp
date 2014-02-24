package com.example.coolapp;


/*These imports are all necessary
 * You also must add the Google-play-services_lib library to your project
 * Check out http://developer.android.com/google/play-services/setup.html
 * for instructions
 * 
 * Outside of Eclipse you must get a Google API key for Google maps and place
 * it in your project manifest. Check out this link:
 * https://developers.google.com/maps/documentation/android/start
 * and this project manifest for examples
 * 
 * Other than this .java page, you must have a .xml layout page. Find it under
 * layout/activity_google_maps.xml
 */
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class GoogleMapsSample extends FragmentActivity
        {

    private GoogleMap sampleMap; //Google Map variable



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set xml layout
        setContentView(R.layout.activity_google_maps);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
       
    }

    

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (sampleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            sampleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (sampleMap != null) {
            	//Allow user to pinpoint their location
                sampleMap.setMyLocationEnabled(true); 
                
            }
       }
    }

   

  
}
