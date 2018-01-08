package uic.sdmp.abhi.fedcash;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends Activity implements
        TitlesFragment.ListSelectionListener{

    public static String[] mTitleArray;;
    public static String[] mDetailArray;
    private DetailsFragment mDetailsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the string arrays with the titles and qutoes

        int length = MainActivity.listcall.size();
        int i = 0;
        String[] str1 = new String[length];
        for(ArrayList al:MainActivity.listcall)
        {
            for (Object s:al)
            {
                str1[i] = s.toString();
                i++;
            }
        }


        String[] str2 = new String[length];
        int j = 0;
        int k = 0;

        for(ArrayList a2:MainActivity.listresult)
        {
            String str="";
            if(str1[k].toString().equals("MonthlyCash")) {
                str = "The opening balance for each month(From Jan-Dec) are: \n";
            }
            else if(str1[k].toString().equals("YearlyAvg"))
            {
                str = "The yearly average is: ";
            }
            else if(str1[k].toString().equals("DailyCash"))
            {
                str = "The daily cash for given period is is: ";
            }

            for (Object s:a2)
            {
                str = str + s.toString()+" \n";
            }
            str2[j] = str;
            j++ ;
            i++ ;
        }



       // mTitleArray = getResources().getStringArray(R.array.Titles);
        //mDetailArray = getResources().getStringArray(R.array.Quotes);
        mTitleArray = str1;
        mDetailArray = str2;

        setContentView(R.layout.activity_second);

        // Get a reference to the QuotesFragment
        mDetailsFragment =
                (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
    }

    // Implement ListSelectionListener interface
    // Called by TitlesFragment when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {
        if (mDetailsFragment.getShownIndex() != index) {

            // Tell the DetailFragment to show the quote string at position index
            mDetailsFragment.showQuoteAtIndex(index);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
       super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

