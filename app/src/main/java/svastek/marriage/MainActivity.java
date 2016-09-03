package svastek.marriage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.goka.kenburnsview.KenBurnsView;
import com.goka.kenburnsview.LoopViewPager;
import svastek.marriage.Splash_list;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializeKenBurnsView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, Cat_grid.class);
                MainActivity.this.startActivity(i);
                MainActivity.this.finish();
            }
        }, 3000);
    }
      /*  private void initializeKenBurnsView() {
            // KenBurnsView
            final KenBurnsView kenBurnsView = (KenBurnsView) findViewById(R.id.imageView_zoom);
            // kenBurnsView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // File path, or a uri or url
           // List<String> urls = Arrays.asList(SampleImages.IMAGES_URL);

      //  kenBurnsView.initResourceIDs((List<Integer>) ContextCompat.getDrawable(context,R.drawable.splash1));

            // ResourceID
           List<Integer> resourceIDs = Arrays.asList(Splash_list.IMAGES_RESOURCE);
           kenBurnsView.initResourceIDs(resourceIDs);

            // MIX (String & Integer)
            //List<Object> mixingList = Arrays.asList(SampleImages.IMAGES_MIX);
            //kenBurnsView.initMixing(mixingList);

            // LoopViewListener
            LoopViewPager.LoopViewPagerListener listener = new LoopViewPager.LoopViewPagerListener() {
                @Override
                public View OnInstantiateItem(int page) {
                    TextView counterText = new TextView(getApplicationContext());
                    counterText.setText(page + "");
                    return counterText;
                }

                @Override
                public void onPageScroll(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    kenBurnsView.forceSelected(position);
                }

                @Override
                public void onPageScrollChanged(int page) {
                }
            };

            // LoopView
        //   LoopViewPager loopViewPager = new LoopViewPager(this, urls.size(), listener);

            LoopViewPager loopViewPager = new LoopViewPager(this, resourceIDs.size(), listener);

            //LoopViewPager loopViewPager = new LoopViewPager(this, mixingList.size(), listener);


           FrameLayout viewPagerFrame = (FrameLayout) findViewById(R.id.view_pager_frame);
            viewPagerFrame.addView(loopViewPager);

           kenBurnsView.setPager(loopViewPager);
        }*/

    }

