package com.example.citygoldseeker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LocationFragment extends Fragment {
	final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
    	
    	// Inflating clueFragment View
    	View locationView = inflater.inflate(R.layout.location_view, container, false);
    	
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        //=================================================================
		Button butClue = (Button) locationView.findViewById(R.id.clue_button);
	    butClue.setOnClickListener(new View.OnClickListener() {

	    	
			@Override
			public void onClick(View arg0) {
				
				
			}
		});
	  //=================================================================
	    
	    // Inflate the layout for this fragment
        return locationView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        TextView article = (TextView) getActivity().findViewById(R.id.location_text);
        article.setText(Ipsum.Clues[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
