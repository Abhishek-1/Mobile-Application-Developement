package uic.sdmp.abhi.landmarkdisplay;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// This code has some modifications to the original
// See http://developer.android.com/guide/components/fragments.html
// for a detailed discussion on the app
// I don't recommend toast as debug for flow but why not do that to get started.
// Better to use Log.d() which we introduced before. Toast is fleeting and logs
// will always in in the LogCat -- hence they are more useful and better practice;
// but you can't see them on the phone. It is sort cool to see onCreate() toast
// as you flip the phone's orientation. It reinforces the lifecycle and the
// automatic adjustment of the UI.
//
// ATC 2013

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
//import android.widget.Toast;package edu.dartmouth.cs.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
//import edu.dartmouth.cs.fragments.apis.Shakespeare;

// Demonstration of using fragments to implement different activity layouts.
// This sample provides a different layout (and activity flow) when run in
// landscape.

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10;
        switch (item.getItemId()) {
            case R.id.exitA1:
                MainActivity.this.finish();
                System.exit(0);
                return true;
            case R.id.launchA2:
                //Toast.makeText(MainActivity.this, "Before Permission ", Toast.LENGTH_SHORT).show();
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS);
                if (PackageManager.PERMISSION_GRANTED == permissionCheck)
                {
                    //Toast.makeText(MainActivity.this, "Permission Ok ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setAction("uic.sdmp.IMAGE_GALLERY");
                    sendBroadcast(intent);
                    return true;
                }
                else
                {
                    //Toast.makeText(MainActivity.this, "Permission Not Ok ---------------- ", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int x=10;
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Toast.makeText(MainActivity.this, "Permission Noooowwww Ok ---------------- ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setAction("uic.sdmp.IMAGE_GALLERY");
                    sendBroadcast(intent);
                }
                else
                {
                    //	permission	denied,	boo!	Disable	the
                    // 	funcYonality	that	depends	on	this	permission.
                    //

                }

            }

        }
    }
}