package svastek.marriage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
import android.util.Log;
import android.view.Menu;

import svastek.marriage.fragment.HomeFragment;
import svastek.marriage.fragment.ProductDetailFragment;
/**
 * Created by Tatson on 09-01-2016.
 */
public class Detail_vendor extends FragmentActivity {

    private Fragment contentFragment;
    HomeFragment homeFragment;
    ProductDetailFragment pdtDetailFragment;

    String name,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_vendor);


        Bundle b = getIntent().getExtras();
        if (b != null){
            name = b.getString("name");
            address=b.getString("address");

        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(ProductDetailFragment.ARG_ITEM_ID)) {
                    if (fragmentManager
                            .findFragmentByTag(ProductDetailFragment.ARG_ITEM_ID) != null) {
                        contentFragment = fragmentManager
                                .findFragmentByTag(ProductDetailFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(HomeFragment.ARG_ITEM_ID) != null) {
                homeFragment = (HomeFragment) fragmentManager
                        .findFragmentByTag(HomeFragment.ARG_ITEM_ID);
                contentFragment = homeFragment;
            }
        } else {
            homeFragment = new HomeFragment();
            switchContent(homeFragment, HomeFragment.ARG_ITEM_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof HomeFragment) {
            outState.putString("content", HomeFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", ProductDetailFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            // Only ProductDetailFragment is added to the back stack.
            if (!(fragment instanceof HomeFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof HomeFragment
                || fm.getBackStackEntryCount() == 0) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public String yo1()
    {
        return name;
    }

    public String yo2()
    {
        return address;
    }
}
