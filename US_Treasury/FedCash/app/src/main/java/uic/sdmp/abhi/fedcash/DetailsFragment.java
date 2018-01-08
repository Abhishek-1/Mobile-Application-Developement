package uic.sdmp.abhi.fedcash;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Hp on 12/6/2017.
 */

public class DetailsFragment extends Fragment {

    private TextView mDetailView = null;
    private int mCurrIdx = -1;
    private int mDetailArrayLen;


    public int getShownIndex() {
        return mCurrIdx;
    }

    // Show the Details string at position newIndex
    public void showQuoteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mDetailArrayLen)
            return;
        mCurrIdx = newIndex;
        mDetailView.setText(SecondActivity.mDetailArray[mCurrIdx]);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout defined in details_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    // Set up some information about the mDetailsView TextView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDetailView = (TextView) getActivity().findViewById(R.id.detailView);
        mDetailArrayLen = SecondActivity.mDetailArray.length;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
