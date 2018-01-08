package uic.sdmp.abhi.treasuryserv;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import android.app.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uic.sdmp.abhi.fedcash.common.IMyAidlInterface;

/**
 * Created by Hp on 12/5/2017.
 */

public class serviceClass extends Service {

    // Set of already assigned IDs
    // Note: These keys are not guaranteed to be unique if the Service is killed
    // and restarted.

    private final static Set<UUID> mIDs = new HashSet<UUID>();

    // Implement the Stub for this Object
    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

        public List monthlyCash(int year)
        {
            MainActivity.currentstatus = "Bound and Running an API Method";
            String[] spam = new String[]{};
            String str;
            //String[] urlarr = new String[]{};
            //urlarr[0] = "";
            URL url = null;
            String result;
            String[] listcall = new String[12];

                result = "";
                String url1 = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT distinct(open_mo) from t1 where year = "+year+" and account = \"Federal Reserve Account\"";
                try {
                    //String str1 = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT avg(open_today) FROM t1 WHERE (\"date\" > '" + year + "-01-01' AND \"date\" < '" + year + "-12-31')";
                    //url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT avg(open_today) FROM t1 WHERE (\"date\" > '2005-01-01' AND \"date\" < '2016-12-31')");
                    url = new URL(url1);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    //con.setRequestProperty("User-Agent", USER_AGENT);
                    int responseCode = con.getResponseCode();
                    //System.out.println("GET Response Code :: " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) { // success
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        System.out.println(response.toString());

                        result = response.toString();
                        //Log.i("Sucess", result);

                        try {
                            String openbalance;
                            JSONArray jsonArray = new JSONArray(result);
                            for(int n = 0; n < jsonArray.length(); n++) {
                                JSONObject jObject = jsonArray.getJSONObject(n);
                                openbalance = jObject.getString("open_mo");
                                listcall[n] = openbalance;
                            }
                                //System.out.println("Addeddddd!!!!!"+openbalance);
                            //avg_val = Integer.parseInt(average_val.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        //System.out.println("GET request not worked");
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            MainActivity.currentstatus = "Bound to one or more Clients but Idle";
             return Arrays.asList(listcall);
        }

        public List dalyCash(int day, int month, int year, int numberofworkingdays)
        {
            MainActivity.currentstatus = "Bound and Running an API Method";
            String[] spam = new String[]{};
            String str;
            //String[] urlarr = new String[]{};
            //urlarr[0] = "";
            URL url = null;
            String result;
            String[] listcall = new String[numberofworkingdays+1];
            ArrayList al = new ArrayList();
            result = "";
            int dayx=0;
            int dayy=0;
            for(int i = 0;i <= numberofworkingdays; i++) {
                String url1 = "";
                if(dayy == 0)
                {
                    dayx = day + i;
                }
                else
                {
                    dayx = dayx + 1;
                }

                if(month < 10) {
                    if (dayx < 10) {
                        url1 = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT open_today from t1 WHERE \"date\" = '" + year + "-0" + month + "-0" + dayx + "' and account = \"Federal Reserve Account\"";
                    } else {
                        url1 = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT open_today from t1 WHERE \"date\" = '" + year + "-0" + month + "-" + dayx + "' and account = \"Federal Reserve Account\"";

                    }
                }
                else {
                    if(dayx < 10)
                    {
                        url1 = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT open_today from t1 WHERE \"date\" = '" + year + "-" + month + "-0" + dayx + "' and account = \"Federal Reserve Account\"";
                    }
                    else {
                        url1 = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT open_today from t1 WHERE \"date\" = '" + year + "-" + month + "-" + dayx + "' and account = \"Federal Reserve Account\"";
                    }
                }
                try {
                    //String str1 = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT avg(open_today) FROM t1 WHERE (\"date\" > '" + year + "-01-01' AND \"date\" < '" + year + "-12-31')";
                    //url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT avg(open_today) FROM t1 WHERE (\"date\" > '2005-01-01' AND \"date\" < '2016-12-31')");
                    url = new URL(url1);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    //con.setRequestProperty("User-Agent", USER_AGENT);
                    int responseCode = con.getResponseCode();
                    //System.out.println("GET Response Code :: " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) { // success
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        //System.out.println(response.toString());

                        result = response.toString();
                        if(result.equals("[]"))
                        {
                            i--;
                            dayy = 1;
                            if(dayx > 31)
                            {
                                month = month + 1;
                                dayx = 0;
                            }
                            if(month > 12)
                            {
                                year = year + 1;
                                month = 1;
                                dayx = 0;
                            }
                            continue;
                        }
                        //Log.i("Sucess", result);

                        try {
                            String openbalance;
                            JSONArray jsonArray = new JSONArray(result);
                            JSONObject jObject = jsonArray.getJSONObject(0);
                            openbalance = jObject.getString("open_today");
                            listcall[i] = openbalance;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        //System.out.println("GET request not worked");
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            MainActivity.currentstatus = "Bound to one or more Clients but Idle";
            return Arrays.asList(listcall);
        }

        public String yearlyaverage(int year) {

            MainActivity.currentstatus = "Bound and Running an API Method";
            URL url = null;
            String avg_val = "99";
            String result;

            try {
                String str = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT avg(open_today) FROM t1 WHERE (\"date\" > '"+year+"-01-01' AND \"date\" < '"+year+"-12-31') and account = \"Federal Reserve Account\"";
                //url = new URL("http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT avg(open_today) FROM t1 WHERE (\"date\" > '2005-01-01' AND \"date\" < '2016-12-31')");
                url = new URL(str);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                //con.setRequestProperty("User-Agent", USER_AGENT);
                int responseCode = con.getResponseCode();
                //System.out.println("GET Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //System.out.println(response.toString());

                    result = response.toString();
                    //Log.i("Sucess", result);

                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                JSONObject jObject = jsonArray.getJSONObject(0);
                                avg_val = jObject.getString("avg(open_today)");
                                //System.out.println(average_val+"Output!!!!!");
                                //avg_val = Integer.parseInt(average_val.toString());

                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                } else {
                    //System.out.println("GET request not worked");

                }





            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            MainActivity.currentstatus = "Bound to one or more Clients but Idle";
            return avg_val;
        }
    };

    @Override
    public void onDestroy() {
        MainActivity.currentstatus = "Destroyed";
        super.onDestroy();
    }

    // Return the Stub defined above
    @Override
    public IBinder onBind(Intent intent) {
        MainActivity.currentstatus = "Bound to one or more Clients but Idle";
        return mBinder;
    }
}
