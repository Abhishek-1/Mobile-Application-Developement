package uic.sdmp.abhi.galleryreceiver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import uic.sdmp.abhi.galleryreceiver.R;

/**
 * Created by Hp on 10/30/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    //private String [] StringNm;
    private int [] imageIDs;

    public CustomAdapter(Context context, int [] imageIDs) {

        this.context=context;
        this.imageIDs=imageIDs;
        //this.StringNm=app_name;
    }

    @Override
    public int getCount() {

        return imageIDs.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.gridcustom, null);
        //TextView name=(TextView)convertView.findViewById(R.id.textView1);
        ImageView icon=(ImageView)convertView.findViewById(R.id.imageView);
        //name.setText(StringNm[position]);
        //icon.setImageResource(imageIDs[position]);
        Bitmap imgBt = BitmapFactory.decodeResource(context.getResources(),imageIDs[position]);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imgBt, 500, 500, false);
        Log.i("Position in Thumb", Integer.toString(position));
        icon.setImageBitmap(resizedBitmap);
        return convertView;
    }



}