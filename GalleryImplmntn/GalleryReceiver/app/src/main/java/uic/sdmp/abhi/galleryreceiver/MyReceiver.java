package uic.sdmp.abhi.galleryreceiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context, "In new App: " + context.getPackageName(), Toast.LENGTH_SHORT).show();

            Intent intentnew = new Intent();
            intentnew.setClass(context, MainActivity.class);
            context.startActivity(intentnew);


    }
}
