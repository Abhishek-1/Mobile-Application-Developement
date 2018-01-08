package uic.sdmp.abhi.landmarkdisplay;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class TitlesFragment extends ListFragment {
    boolean mDualPane;
    int mCurCheckPosition = 0;
    static FrameLayout layout1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                landmark.LANDMARK));

       layout1 = (FrameLayout) getActivity().findViewById(R.id.details);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Toast.makeText(getContext(), "Layout Fetched " + layout1, Toast.LENGTH_SHORT).show();
            }



        View detailsFrame = getActivity().findViewById(R.id.details);

        //View layout1 = (View) getActivity().findViewById(R.id.layout1);

        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;


        if (savedInstanceState != null) {

            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        setRetainInstance(true);

        if (mDualPane) {

            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mCurCheckPosition);
        } else
        {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            getListView().setItemChecked(mCurCheckPosition, true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

         showDetails(position);
    }


    void showDetails(int index) {
        mCurCheckPosition = index;

         if (mDualPane) {

            getListView().setItemChecked(index, true);


            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {
                details = DetailsFragment.newInstance(index);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
             if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
             {

                 //setContentView(R.layout.fragment_layout);
                 FragmentManager fragmentManager = getFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                 layout1.setVisibility(View.VISIBLE);
                 DetailsFragment details;
                 details = DetailsFragment.newInstance(index);
                 fragmentTransaction.replace(R.id.details, details);
                 fragmentTransaction.commit();

             }
             else {
                 Intent intent = new Intent();
                 intent.setClass(getActivity(), DetailsActivity.class);
                 intent.putExtra("index", index);
                 startActivity(intent);
             }
        }
    }
}
