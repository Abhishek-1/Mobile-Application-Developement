package uic.sdmp.abhi.fedcash;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uic.sdmp.abhi.fedcash.common.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "FedCashUser";
    private IMyAidlInterface mqueryInvoker;
    private static String[] list1;
    private static String yearlyavg;
    private boolean mIsBound = false;
    private static int inputyear;
    private static String selection;
    private static int year;
    private static int month;
    private static int day;
    private static int listofdays;
    //private static List listresult;
    //private static List listcall;
    public static ArrayList<ArrayList<String>> listresult = new ArrayList<ArrayList<String>>();
    public static ArrayList<ArrayList<String>> listcall = new ArrayList<ArrayList<String>>();
    private static Spinner spinner;
    private static Spinner spinner2;
    private static Spinner spinner3;
    private static Spinner spinner4;
    private static Spinner spinner5;
    private static Handler rHandler;
    public static final int CHECK_API = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner1);
        //selection = spinner.getSelectedItem().toString();
        final TextView tv3 = (TextView) findViewById(R.id.textView3);
        final TextView tv4 = (TextView) findViewById(R.id.textView4);
        final TextView tv5 = (TextView) findViewById(R.id.textView5);
        //inputyear = Integer.parseInt(spinner2.getSelectedItem().toString());
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        final String[] choicearray = getResources().getStringArray(R.array.order);
        Button button1 = (Button) findViewById(R.id.Button1);
        Button button2 = (Button) findViewById(R.id.Button2);
        Button button3 = (Button) findViewById(R.id.Button3);

        Log.i(TAG, IMyAidlInterface.class.getName());

        // UB:  Stoooopid Android API-20 no longer supports implicit intents
        // to bind to a service #@%^!@..&**!@
        // Must make intent explicit or lower target API level to 19.
        //ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
        //i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t1 = new Thread(new Workerfirst());

                spinner = (Spinner) findViewById(R.id.spinner1);
                spinner2 = (Spinner) findViewById(R.id.spinner2);
                spinner3 = (Spinner) findViewById(R.id.spinner3);
                spinner4 = (Spinner) findViewById(R.id.spinner4);
                spinner5 = (Spinner) findViewById(R.id.spinner5);
                selection = spinner.getSelectedItem().toString();
                year = Integer.parseInt(spinner2.getSelectedItem().toString());
                month = Integer.parseInt(spinner3.getSelectedItem().toString());
                day = Integer.parseInt(spinner4.getSelectedItem().toString());
                listofdays = Integer.parseInt(spinner5.getSelectedItem().toString());

                if (mIsBound) {
                    t1.start();
                    //Message msg;
                    //msg = rHandler.obtainMessage(MainActivity.CHECK_API);
                    //rHandler.sendMessage(msg);
                    System.out.println("GET request not worked" + yearlyavg);

                } else {
                    Log.i(TAG, "Service was not Bound!!");
                }

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                if (choicearray[position].toString().equals("DailyCash")) {
                    selection = choicearray[position].toString();
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setVisibility(View.VISIBLE);
                    tv5.setVisibility(View.VISIBLE);
                    spinner3.setVisibility(View.VISIBLE);
                    spinner4.setVisibility(View.VISIBLE);
                    spinner5.setVisibility(View.VISIBLE);
                }
                if ((choicearray[position].toString().equals("MonthlyCash")) || (choicearray[position].toString().equals("YearlyAvg"))) {
                    selection = choicearray[position].toString();
                    tv3.setVisibility(View.INVISIBLE);
                    tv4.setVisibility(View.INVISIBLE);
                    tv5.setVisibility(View.INVISIBLE);
                    spinner3.setVisibility(View.INVISIBLE);
                    spinner4.setVisibility(View.INVISIBLE);
                    spinner5.setVisibility(View.INVISIBLE);
                }
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mIsBound) {

                    unbindService(mConnection);
                    mIsBound = false;


                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                startActivity(intent);


            }
        });


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.First:
                Toast.makeText(MainActivity.this, "First", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,QuoteViewerActivity.class);
                startActivity(intent);
                return true;
                 default:
                return super.onOptionsItemSelected(item);

        }

    }
*/
    protected void onResume() {
        super.onResume();

        if (!mIsBound) {

            boolean b = false;
            Intent i = new Intent("uic.sdmp.abhi.fedcash.common.lMyAidlInterface");
            //i.setPackage(this.getPackageName());
            Log.i(TAG, IMyAidlInterface.class.getName());

            // UB:  Stoooopid Android API-20 no longer supports implicit intents
            // to bind to a service #@%^!@..&**!@
            // Must make intent explicit or lower target API level to 19.\
            PackageManager pm = getPackageManager();
            List<ResolveInfo> resolveInfoList = pm.queryIntentServices(i, 0);
            if (resolveInfoList == null) {
                ;
            }
            ResolveInfo serviceinfo = resolveInfoList.get(0);
            //ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
            i.setComponent(new ComponentName(serviceinfo.serviceInfo.packageName, serviceinfo.serviceInfo.name));

            b = bindService(i, mConnection, BIND_AUTO_CREATE);
            if (b) {
                Log.i(TAG, "Abhi says bindService() succeeded!");
            } else {
                Log.i(TAG, "Abhi says bindService() failed!");
            }

        }
    }

    @Override
    protected void onPause() {



        /*if (mIsBound) {

            unbindService(mConnection);
            mIsBound = false;


        }*/

        super.onPause();


    }


    private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {

            mqueryInvoker = IMyAidlInterface.Stub.asInterface(iservice);

            mIsBound = true;

        }

        public void onServiceDisconnected(ComponentName className) {

            mqueryInvoker = null;

            mIsBound = false;

        }
    };


    public class Workerfirst implements Runnable {

        public void run() {

            /*Looper.prepare();
            rHandler = new Handler() {

                public void handleMessage(Message msg){
                    int what = msg.what;
                    switch (what) {

                        case CHECK_API:
                            try {

                                if(selection.toString().equals("YearlyAvg")) {
                                    ArrayList<String> arrayList = new ArrayList<>();
                                    String str1 = "Call for Yearly Average for year: "+year;
                                    arrayList.add(str1);
                                    yearlyavg = mqueryInvoker.yearlyaverage(year);
                                    ArrayList<String> avglist = new ArrayList<>();
                                    avglist.add(yearlyavg);
                                    listresult.add(avglist);
                                    listcall.add(arrayList);
                                }
                                if(selection.toString().equals("MonthlyCash")) {
                                    ArrayList<String> arrayList = new ArrayList<>();
                                    String str1 = "Call for Monthly Cash for year: "+year;
                                    arrayList.add(str1);
                                    List list1;
                                    list1 = mqueryInvoker.monthlyCash(year);
                                    listcall.add(arrayList);
                                    ArrayList<String> avglist = new ArrayList<>();
                                    String str2;
                                    for(Object s: list1)
                                    {
                                        str2 = s.toString();
                                        avglist.add(str2);

                                    }
                                    listresult.add(avglist);

                                }
                                if(selection.toString().equals("DailyCash")) {
                                    ArrayList<String> arrayList = new ArrayList<>();
                                    String str1 = "Call for Daily Cash for year: "+year;
                                    arrayList.add(str1);
                                    List list1;
                                    list1 = mqueryInvoker.dalyCash(day,month,year,listofdays);
                                    listcall.add(arrayList);
                                    ArrayList<String> avglist = new ArrayList<>();
                                    String str2;
                                    for(Object s: list1)
                                    {
                                        str2 = s.toString();
                                        avglist.add(str2);

                                    }
                                    listresult.add(avglist);

                                }


                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            break;


                    }
                }

            };*/

            try {

                if (selection.toString().equals("YearlyAvg")) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    String str1 = "Call for Yearly Average for year: " + year;
                    arrayList.add(str1);
                    yearlyavg = mqueryInvoker.yearlyaverage(year);
                    ArrayList<String> avglist = new ArrayList<>();
                    avglist.add(yearlyavg);
                    listresult.add(avglist);
                    listcall.add(arrayList);
                }
                if (selection.toString().equals("MonthlyCash")) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    String str1 = "Call for Monthly Cash for year: " + year;
                    arrayList.add(str1);
                    List list1;
                    list1 = mqueryInvoker.monthlyCash(year);
                    listcall.add(arrayList);
                    ArrayList<String> avglist = new ArrayList<>();
                    String str2;
                    for (Object s : list1) {
                        str2 = s.toString();
                        avglist.add(str2);

                    }
                    listresult.add(avglist);

                }
                if (selection.toString().equals("DailyCash")) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    String str1 = "Call for Daily Cash for date: " + year + "-" + month + "-" + day + " and number of days: " + listofdays;
                    arrayList.add(str1);
                    List list1;
                    list1 = mqueryInvoker.dalyCash(day, month, year, listofdays);
                    listcall.add(arrayList);
                    ArrayList<String> avglist = new ArrayList<>();
                    String str2;
                    for (Object s : list1) {
                        str2 = s.toString();
                        avglist.add(str2);

                    }
                    listresult.add(avglist);

                }


            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }//End of run method

    }//End of thread Class
}
