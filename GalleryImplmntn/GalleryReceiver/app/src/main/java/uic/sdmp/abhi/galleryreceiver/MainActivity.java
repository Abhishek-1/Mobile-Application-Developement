package uic.sdmp.abhi.galleryreceiver;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    private static int[] imageIDs = {
            R.drawable.artinstituteofchicago, R.drawable.bigben, R.drawable.fieldmuseum, R.drawable.fieldmuseum2, R.drawable.hancock, R.drawable.milenniumpark,
            R.drawable.navypierchicago, R.drawable.riverwalk, R.drawable.sheddaquarium, R.drawable.willistower
    };




    GridView androidGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        androidGridView = (GridView) findViewById(R.id.Grid1);

        androidGridView.setAdapter(new CustomAdapter(MainActivity.this, imageIDs));
        //registerForContextMenu(androidGridView);

        /*androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {

                Intent intent= new Intent(MainActivity.this, ImageFull.class);
                intent.putExtra("Id",position);
                startActivity(intent);
            }

        });*/

    }
}
