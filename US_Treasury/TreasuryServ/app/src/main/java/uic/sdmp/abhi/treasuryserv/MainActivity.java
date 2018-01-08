package uic.sdmp.abhi.treasuryserv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static String currentstatus = "Not yet Bound";
    private static TextView tv1;


    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.textview);
        tv1.setText("Not yet Bound");

        Button button3 = (Button) findViewById(R.id.button);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv1.setText(currentstatus);


            }
        });
    }


}
