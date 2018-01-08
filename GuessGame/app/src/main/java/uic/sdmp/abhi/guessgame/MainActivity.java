package uic.sdmp.abhi.guessgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import java.io.InputStream;
import java.net.URL;

import uic.sdmp.abhi.guessgame.R;

public class MainActivity extends Activity {

    LooperThread looperThread;
    TeamRight teamRight;
    int[] correctguess = new int[]{99, 99, 99, 99};
    int pos = 0;
    int started = 0;
    private static Handler rHandler;
    private static Handler lHandler;
    private static int team1cnt;
    private static int team2cnt;
    private static int won = 0;
    private static int leftorignum;
    private static int rightorignum;
    private TextView tvleft;
    private TextView tvright;
    private TextView tvstatus;
    Random rand = new Random();
    // Values to be used by handleMessage()
    public static final int UPDATE_LEFT_SCROLL_RANDOM = 0;
    public static final int UPDATE_RIGHT_SCROLL_RANDOM = 1;
    public static final int CHECK_TEAM1_GUESS = 2;
    public static final int CHECK_TEAM2_GUESS = 3;
    public static final int UPDATE_NTFCTN_LEFT = 4;
    public static final int UPDATE_NTFCTN_RIGHT = 5;
    public static final int SEND_NTFCTN_TEAM1 = 6;
    public static final int SEND_NTFCTN_TEAM2 = 7;
    public static final int UPDATE_STATUS = 8;
    public static final int MAX_ROUND_COUNT_REACHED = 9;
    public static final int UPDATE_STATUS_TEAM1 = 10;
    public static final int UPDATE_STATUS_TEAM2 = 11;
    public static final int MAX_ROUND_COUNT_REACHED_TEAM1 = 12;
    public static final int MAX_ROUND_COUNT_REACHED_TEAM2 = 13;
    public static final int LEFT_RIGHT_RESTART = 14;
    public static final int TV_RIGHT = 15;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonMain = (Button) findViewById(R.id.button2);

        buttonMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                tvleft = (TextView) findViewById(R.id.textView4);
                tvright = (TextView) findViewById(R.id.textView5);
                tvstatus = (TextView) findViewById(R.id.textView);
                tvleft.setText("");
                tvright.setText("");
                tvstatus.setText("Game started");
                Thread t1 = new Thread(new TeamLeft());
                Thread t2 = new Thread(new TeamRight());


                if (started == 1) {

                    looperThread.mHandler.removeCallbacks(null);
                    rHandler.removeCallbacks(null);
                    lHandler.removeCallbacks(null);
                    rHandler.removeCallbacksAndMessages(null);
                    rHandler.removeCallbacksAndMessages(null);
                    looperThread.mHandler.removeCallbacksAndMessages(null);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                    ;
                    rHandler.getLooper().quit();
                    lHandler.getLooper().quit();
                    looperThread.mHandler.removeCallbacksAndMessages(null);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted!");
                    }
                    ;
                    correctguess = new int[]{99, 99, 99, 99};
                    pos = 0;
                    team2cnt = 0;
                    team1cnt = 0;
                    won = 0;
                    looperThread.mHandler.removeCallbacksAndMessages(null);

                }


                looperThread = new LooperThread();
                looperThread.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted!");
                }
                ;
                teamRight = new TeamRight();


                buttonMain.setText("Restart Game");
                tvleft.setText("");
                tvright.setText("");


                t2.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted!");
                }
                ;
                t1.start();
                started = 1;

            }

        });


    }

    public class LooperThread extends Thread {

        private Handler mHandler;

        public void run() {
            Looper.prepare();
            mHandler = new Handler(Looper.getMainLooper()) {
                public void handleMessage(Message msg) {
                    int what = msg.what;
                    switch (what) {
                        case UPDATE_LEFT_SCROLL_RANDOM:
                            tvleft.setText("Team 1 Area \n");
                            tvleft.append("Random Generated Number  : ");
                            int numleft = msg.arg1;
                            String numberleft = Integer.toString(numleft);
                            tvleft.append(numberleft);
                            tvleft.append("\n");

                            break;

                        case UPDATE_RIGHT_SCROLL_RANDOM:

                            tvright.setText("Team 2 Area \n");
                            tvright.append("Random Generated Number  : ");
                            int numright = msg.arg1;
                            String numberright = Integer.toString(numright);
                            tvright.append(numberright);
                            tvright.append("\n");


                            break;

                        case LEFT_RIGHT_RESTART:
                            tvleft.setText(" ");
                            tvright.setText(" ");
                            break;

                        case UPDATE_NTFCTN_LEFT:
                            tvleft.append("\n");
                            String str = msg.obj.toString();
                            tvleft.append(str);
                            break;


                        case UPDATE_NTFCTN_RIGHT:
                            tvright.append("\n");
                            String str1 = msg.obj.toString();
                            tvright.append(str1);
                            break;

                        case UPDATE_STATUS:
                            String str2 = msg.obj.toString();
                            tvstatus.setText(str2);

                            rHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    rHandler.getLooper().quit();
                                }
                            });
                            lHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    lHandler.getLooper().quit();
                                }
                            });

                            break;

                        case MAX_ROUND_COUNT_REACHED:
                            if (team1cnt == 20 && team2cnt == 20) {
                                tvstatus.setText("Game Drawn. No side could guess the number");

                                rHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        rHandler.getLooper().quit();
                                    }
                                });
                                lHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        lHandler.getLooper().quit();
                                    }
                                });

                            }

                            break;
                    }

                }
            }; // Handler is associated with UI Thread
            Looper.loop();
        }

    }

    public class TeamLeft implements Runnable {

        public void run() {
            String leftnum = MainActivity.Random4dig();
            leftorignum = Integer.parseInt(leftnum);
            Message msg = looperThread.mHandler.obtainMessage(MainActivity.UPDATE_LEFT_SCROLL_RANDOM);
            msg.arg1 = leftorignum;
            looperThread.mHandler.sendMessage(msg);


            String guessright = MainActivity.Random4dig();
            int guessrightnum = Integer.parseInt(guessright);


            msg = rHandler.obtainMessage(MainActivity.CHECK_TEAM1_GUESS);
            msg.arg1 = guessrightnum;
            rHandler.sendMessage(msg);

            team1cnt = 1;


            Looper.prepare();
            lHandler = new Handler() {

                int leftcount = 1;


                public void handleMessage(Message msg) {

                    int what = msg.what;
                    switch (what) {

                        case CHECK_TEAM2_GUESS:
                            int cp = 0;
                            int wp = 0;
                            int guessnum = msg.arg1;

                            msg = rHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM2);
                            msg.obj = "\nNumber Guessed by Team 2 is: " + guessnum;
                            rHandler.sendMessage(msg);


                            int orignum = leftorignum;
                            int[] guessnumarr = new int[4];
                            int[] orignumarr = new int[4];

                            if (guessnum == orignum) {
                                msg = rHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM2);
                                msg.obj = "The Guess is correct !!!!! Game won by Team 2 !!!!!!";
                                rHandler.sendMessage(msg);
                                msg = rHandler.obtainMessage(MainActivity.UPDATE_STATUS_TEAM2);
                                msg.obj = "Game Ended: Game won by Team 2";
                                rHandler.sendMessage(msg);
                                won = 1;

                            } else {
                                int i = 3;
                                while (guessnum >= 10) {
                                    guessnumarr[i] = guessnum % 10;
                                    guessnum = guessnum / 10;
                                    i--;
                                }
                                guessnumarr[0] = guessnum;

                                int j = 3;
                                while (orignum >= 10) {
                                    orignumarr[j] = orignum % 10;
                                    orignum = orignum / 10;
                                    j--;
                                }
                                orignumarr[0] = orignum;
                                for (i = 0; i < 4; i++) {
                                    for (j = 0; j < 4; j++) {
                                        if (guessnumarr[i] == orignumarr[j]) {
                                            if (i == j) {
                                                cp++;
                                                pos = 99;
                                                correctguess[i] = guessnumarr[i];

                                            } else {
                                                wp++;
                                            }
                                        }

                                    }
                                }

                                if (cp > 0) {
                                    msg = rHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM2);
                                    msg.obj = "Correct guess at -- Right Position: " + cp;
                                    rHandler.sendMessage(msg);

                                } else {
                                    int zer = 0;
                                    msg = rHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM2);
                                    msg.obj = "Correct guess at -- Right Position: " + zer;
                                    rHandler.sendMessage(msg);
                                }
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    System.out.println("Thread interrupted!");
                                }
                                ;
                                if (wp > 0) {
                                    msg = rHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM2);
                                    msg.obj = "Correct guess at -- Wrong Position: " + wp;
                                    rHandler.sendMessage(msg);
                                } else {
                                    int zer1 = 0;
                                    msg = rHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM2);
                                    msg.obj = "Correct guess at -- Wrong Position: " + zer1;
                                    rHandler.sendMessage(msg);
                                }

                            }

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupted!");
                            }
                            ;

                            if (team1cnt < 20 && won != 1) {
                                String guessright = MainActivity.Random4dig();
                                int guessrightnum = Integer.parseInt(guessright);

                                msg = rHandler.obtainMessage(MainActivity.CHECK_TEAM1_GUESS);
                                msg.arg1 = guessrightnum;
                                rHandler.sendMessage(msg);

                                team1cnt++;
                            } else {
                                msg = rHandler.obtainMessage(MainActivity.MAX_ROUND_COUNT_REACHED_TEAM1);
                                rHandler.sendMessage(msg);


                            }

                            break;

                        case SEND_NTFCTN_TEAM1:
                            Object obj = msg.obj;
                            msg = looperThread.mHandler.obtainMessage(MainActivity.UPDATE_NTFCTN_RIGHT);
                            msg.obj = obj;
                            looperThread.mHandler.sendMessage(msg);
                            break;

                        case UPDATE_STATUS_TEAM1:
                            Object obj1 = msg.obj;
                            msg = looperThread.mHandler.obtainMessage(MainActivity.UPDATE_STATUS);
                            msg.obj = obj1;
                            looperThread.mHandler.sendMessage(msg);
                            break;

                        case MAX_ROUND_COUNT_REACHED_TEAM2:
                            msg = looperThread.mHandler.obtainMessage(MainActivity.MAX_ROUND_COUNT_REACHED);
                            looperThread.mHandler.sendMessage(msg);

                            if (team1cnt < 20 && won != 1) {
                                String guessright = MainActivity.Random4dig();
                                int guessrightnum = Integer.parseInt(guessright);
                                msg = rHandler.obtainMessage(MainActivity.CHECK_TEAM1_GUESS);
                                msg.arg1 = guessrightnum;
                                rHandler.sendMessage(msg);
                                team1cnt++;
                            } else {
                                msg = rHandler.obtainMessage(MainActivity.MAX_ROUND_COUNT_REACHED_TEAM1);
                                rHandler.sendMessage(msg);


                            }

                            break;

                    }

                }
            };
            Looper.loop();


        }


    }

    public class TeamRight implements Runnable {

        public void run() {

            String rightnum = MainActivity.Random4dig();
            rightorignum = Integer.parseInt(rightnum);


            Message msg = looperThread.mHandler.obtainMessage(MainActivity.LEFT_RIGHT_RESTART);
            looperThread.mHandler.sendMessage(msg);


            msg = looperThread.mHandler.obtainMessage(MainActivity.UPDATE_RIGHT_SCROLL_RANDOM);
            msg.arg1 = rightorignum;
            looperThread.mHandler.sendMessage(msg);


            Looper.prepare();
            rHandler = new Handler() {

                public void handleMessage(Message msg) {

                    int rightcount = 0;
                    int firstrun = 0;
                    if (firstrun == 0) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted!");
                        }


                    }
                    int what = msg.what;
                    switch (what) {

                        case CHECK_TEAM1_GUESS:
                            int cp = 0;
                            int wp = 0;
                            int guessnum = msg.arg1;

                            msg = lHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM1);
                            msg.obj = "\nNumber Guessed by Team 1 is: " + guessnum;
                            lHandler.sendMessage(msg);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupted!");
                            }
                            ;

                            int orignum = rightorignum;
                            int[] guessnumarr = new int[4];
                            int[] orignumarr = new int[4];

                            if (guessnum == orignum) {
                                msg = lHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM1);
                                msg.obj = "The Guess is correct !!!!! Game won by Team 1 !!!!!!";
                                lHandler.sendMessage(msg);
                                msg = lHandler.obtainMessage(MainActivity.UPDATE_STATUS_TEAM1);
                                msg.obj = "Game Ended: Game won by Team 1";
                                lHandler.sendMessage(msg);
                                won = 1;

                            } else {
                                int i = 3;
                                while (guessnum >= 10) {
                                    guessnumarr[i] = guessnum % 10;// and save this into an array or whatever
                                    guessnum = guessnum / 10;
                                    i--;
                                }
                                guessnumarr[0] = guessnum;

                                int j = 3;
                                while (orignum >= 10) {
                                    orignumarr[j] = orignum % 10;// and save this into an array or whatever
                                    orignum = orignum / 10;
                                    j--;
                                }
                                orignumarr[0] = orignum;
                                for (i = 0; i < 4; i++) {
                                    for (j = 0; j < 4; j++) {
                                        if (guessnumarr[i] == orignumarr[j]) {
                                            if (i == j) {
                                                cp++;

                                            } else {
                                                wp++;
                                            }
                                        }

                                    }
                                }

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    System.out.println("Thread interrupted!");
                                }
                                ;

                                if (cp > 0) {
                                    msg = lHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM1);
                                    msg.obj = "Correct guess at -- Right Position: " + cp;
                                    lHandler.sendMessage(msg);

                                } else {
                                    int zer = 0;
                                    msg = lHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM1);
                                    msg.obj = "Correct guess at -- Right Position: " + zer;
                                    lHandler.sendMessage(msg);
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    System.out.println("Thread interrupted!");
                                }
                                ;
                                if (wp > 0) {
                                    msg = lHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM1);
                                    msg.obj = "Correct guess at -- Wrong Position: " + wp;
                                    lHandler.sendMessage(msg);
                                } else {
                                    int zer1 = 0;
                                    msg = lHandler.obtainMessage(MainActivity.SEND_NTFCTN_TEAM1);
                                    msg.obj = "Correct guess at -- Wrong Position: " + zer1;
                                    lHandler.sendMessage(msg);
                                }
                            }

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupted!");
                            }
                            ;

                            if (team2cnt < 20 && won != 1) {
                                String guessright = MainActivity.Random4dig();
                                int guessrightnum = Integer.parseInt(guessright);


                                if (pos == 99) {
                                    int[] fixguess = new int[4];
                                    int i = 3;
                                    while (guessrightnum >= 10) {
                                        if (correctguess[i] == 99) {
                                            int newnum = guessrightnum % 10;
                                            int loopn = 1;
                                            while (loopn == 1) {
                                                loopn = 2;
                                                for (int x : correctguess) {
                                                    if (newnum == x) {
                                                        newnum = rand.nextInt(10);
                                                        loopn = 1;
                                                    }
                                                }
                                            }
                                            fixguess[i] = newnum;
                                            guessrightnum = guessrightnum / 10;
                                            i--;
                                        } else {
                                            fixguess[i] = correctguess[i];
                                            guessrightnum = guessrightnum / 10;
                                            i--;
                                        }
                                    }
                                    if (correctguess[0] == 99) {
                                        int newnum = guessrightnum;
                                        int loopn = 1;
                                        while (loopn == 1) {
                                            loopn = 2;
                                            for (int x : correctguess) {
                                                if (newnum == x) {
                                                    newnum = 0;
                                                    while (newnum == 0) {
                                                        newnum = rand.nextInt(10);
                                                    }
                                                    loopn = 1;
                                                }
                                            }
                                        }
                                        fixguess[0] = newnum;
                                    } else {
                                        fixguess[0] = correctguess[i];
                                    }


                                    StringBuilder builder = new StringBuilder();
                                    for (int k : fixguess) {
                                        builder.append(k);
                                    }
                                    String fix = builder.toString();
                                    guessrightnum = Integer.parseInt(fix);
                                }


                                msg = lHandler.obtainMessage(MainActivity.CHECK_TEAM2_GUESS);
                                msg.arg1 = guessrightnum;
                                lHandler.sendMessage(msg);
                                team2cnt++;
                            } else {
                                msg = lHandler.obtainMessage(MainActivity.MAX_ROUND_COUNT_REACHED_TEAM2);
                                lHandler.sendMessage(msg);
                            }


                            break;

                        case SEND_NTFCTN_TEAM2:
                            Object obj = msg.obj;
                            msg = looperThread.mHandler.obtainMessage(MainActivity.UPDATE_NTFCTN_LEFT);
                            msg.obj = obj;
                            looperThread.mHandler.sendMessage(msg);
                            break;

                        case UPDATE_STATUS_TEAM2:
                            Object obj1 = msg.obj;
                            msg = looperThread.mHandler.obtainMessage(MainActivity.UPDATE_STATUS);
                            msg.obj = obj1;
                            looperThread.mHandler.sendMessage(msg);
                            break;

                        case MAX_ROUND_COUNT_REACHED_TEAM1:
                            msg = looperThread.mHandler.obtainMessage(MainActivity.MAX_ROUND_COUNT_REACHED);
                            looperThread.mHandler.sendMessage(msg);

                            if (team2cnt < 20 && won != 1) {
                                String guessright = MainActivity.Random4dig();
                                int guessrightnum = Integer.parseInt(guessright);

                                if (pos == 99) {
                                    int[] fixguess = new int[4];
                                    int i = 3;
                                    while (guessrightnum >= 10) {
                                        if (correctguess[i] == 99) {
                                            int newnum = guessrightnum % 10;
                                            int loopn = 1;
                                            while (loopn == 1) {
                                                loopn = 2;
                                                for (int x : correctguess) {
                                                    if (newnum == x) {
                                                        newnum = rand.nextInt(10);
                                                        loopn = 1;
                                                    }
                                                }
                                            }
                                            fixguess[i] = newnum;
                                            guessrightnum = guessrightnum / 10;
                                            i--;
                                        } else {
                                            fixguess[i] = correctguess[i];
                                            guessrightnum = guessrightnum / 10;
                                            i--;
                                        }
                                    }
                                    if (correctguess[0] == 99) {
                                        int newnum = guessrightnum;
                                        int loopn = 1;
                                        while (loopn == 1) {
                                            loopn = 2;
                                            for (int x : correctguess) {
                                                if (newnum == x) {
                                                    newnum = 0;
                                                    while (newnum == 0) {
                                                        newnum = rand.nextInt(10);
                                                    }
                                                    loopn = 1;
                                                }
                                            }
                                        }
                                        fixguess[0] = newnum;
                                    } else {
                                        fixguess[0] = correctguess[i];
                                    }


                                    StringBuilder builder = new StringBuilder();
                                    for (int k : fixguess) {
                                        builder.append(k);
                                    }
                                    String fix = builder.toString();
                                    guessrightnum = Integer.parseInt(fix);
                                }


                                msg = lHandler.obtainMessage(MainActivity.CHECK_TEAM2_GUESS);
                                msg.arg1 = guessrightnum;
                                lHandler.sendMessage(msg);
                                team2cnt++;
                            } else {
                                msg = lHandler.obtainMessage(MainActivity.MAX_ROUND_COUNT_REACHED_TEAM2);
                                lHandler.sendMessage(msg);
                            }

                            break;

                    }

                }
            };


            Looper.loop();


        }
    }

    public static String Random4dig() {
        String str = "";
        Random rand = new Random();
        int number = 0;
        int xval = 0;
        int[] numbe = new int[4];
        for (int i = 0; i < 4; i++) {
            xval = rand.nextInt(10);
            if (i == 0 && xval == 0) {
                while (xval != 0) {
                    xval = rand.nextInt(10);
                }
            }
            int count = 0;
            for (int x : numbe) {
                if (xval == x) {
                    count = 1;
                }
            }
            if (count == 0) {
                numbe[i] = xval;
            } else {
                i--;
            }
        }

        String ch = "";
        String finalStr = "";
        for (int i = 0; i < 4; i++) {
            ch = Integer.toString(numbe[i]);
            finalStr = finalStr + ch;

        }


        return finalStr;
    }


}




