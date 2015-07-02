package com.example.citygoldseeker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends FragmentActivity 
        implements HeadlinesFragment.OnHeadlineSelectedListener,ClueFragment.OnPositionSelectedListener {
	
	public static Activity activity;
	public static Context context;
	public static String PACKAGE_NAME;
	static CGS_Executors cgsExecutors;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationslist_view);
       
        //getting the instance of the main activity
        activity = this;
        context = returnAppContext();
        PACKAGE_NAME = getApplicationContext().getPackageName();
        cgsExecutors = new CGS_Executors();
        
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }
    
    public void onClueSelected(int position) {
    	
    	Log.e("NEMANJA","positon in onClueSelected:" + position);
        // The user selected the headline of an article from the HeadlinesFragment
        // Capture the clue fragment from the activity layout
        ClueFragment clueFrag = (ClueFragment)
                getSupportFragmentManager().findFragmentById(R.id.clue_fragment);

        if (clueFrag != null) {
            // If clue frag is available, we're in two-pane layout...
            // Call a method in the ClueFragment to update its content
        	clueFrag.updateClueView(position);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags - save the state of the Headline fragment and create new clue fragment

            // Create fragment and give it an argument for the selected article
        	ClueFragment newFragment = new ClueFragment();
            Bundle args = new Bundle();
            args.putInt(ClueFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            transaction.replace(R.id.fragment_container, newFragment);
            // and add the transaction to the back stack so the user can navigate back
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
    
    public void onPositionCheckSelected(int position) {

    	//Log.e("NEMANJA","positon in onPositionCheckSelected:" + position);
    	ArticleFragment articleFrag = (ArticleFragment)
                 getSupportFragmentManager().findFragmentById(R.id.article_fragment);

		// GPSTracker class
		GPSTracker gps;
		//gps = new GPSTracker(getActivity().getApplicationContext());
		gps = new GPSTracker(MainActivity.this);
		
					
		// check if GPS enabled		
	    if(gps.canGetLocation()) {
	    	
	    	
	    	double latitude = gps.getLatitude();
	    	double longitude = gps.getLongitude();
	    	
	    	int latMeasured = (int)(latitude*10000);
	    	int longMeasured = (int)(longitude*10000);
	    	
	    	if((position == 4) || (cgsExecutors.closeCoordinatesCheck(position, latMeasured, longMeasured))){
	    	
	    		Toast.makeText(this, "Your at the location: " + position + 1, Toast.LENGTH_LONG).show();
	    		
	    		if (articleFrag != null) {
	                // If article frag is available, we're in two-pane layout...
	                // Call a method in the ArticleFragment to update its content
	           	 	articleFrag.updateArticleView(position);

	            } else {
	                // If the frag is not available, we're in the one-pane layout and must swap frags - save the state of the Headline fragment and create new clue fragment

	                // Create fragment and give it an argument for the selected article
	           	 	ArticleFragment newFragment = new ArticleFragment();
	                Bundle args = new Bundle();
	                args.putInt(ArticleFragment.ARG_POSITION, position);
	                newFragment.setArguments(args);
	                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	                // Replace whatever is in the fragment_container view with this fragment,
	                transaction.replace(R.id.fragment_container, newFragment);
	                // and add the transaction to the back stack so the user can navigate back
	                transaction.addToBackStack(null);

	                // Commit the transaction
	                transaction.commit();
	           }

	    	} else {

	    		Toast.makeText(this, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
	    	}
	    
	    } else {
	    	// can't get location
	    	// GPS or Network is not enabled
	    	// Ask user to enable GPS/network in settings
	    	gps.showSettingsAlert();
	    }
	
    }
    public Context returnAppContext() {
    	Context context = getApplicationContext();
    	return context;
    }
    
}