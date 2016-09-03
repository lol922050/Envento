package svastek.marriage;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by Tatson on 12-01-2016.
 */
public class New_event extends Activity {
Context context;
    EditText etext;
    Button c;
    int colorFrom = Color.WHITE;
    int colorTo = Color.RED;
    int duration = 15000;
    Handler handler = new Handler();
    RelativeLayout relativeLayout;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);
        relativeLayout = (RelativeLayout) findViewById(R.id.real);

        (new Thread(){
            @Override
            public void run(){
                for(int i=0; i<255; i++){
                    handler.post(new Runnable(){
                        public void run(){
                            ObjectAnimator.ofObject(relativeLayout, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo)
                                    .setDuration(duration)
                                    .start();
                        }
                    });
                    // next will pause the thread for some time
                    try{
                        try {
                            sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    catch(Exception e)
                    {}
                    { break; }
                }
            }
        }).start();

    }
}
