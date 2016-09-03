package svastek.marriage;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Tatson on 08-01-2016.
 */
public class Vendor extends AppCompatActivity {


    String url , cat;
    //Creating a List of superheroes
    private List<Vendor_items> listSuperHeroes;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Evento");


        getOverflowMenu();
        Bundle b = getIntent().getExtras();
        cat = b.getString("cat");
        Log.d("vendor", cat);
        url="http://svastek.in/sevento/php/sub_cat.php?cat="+cat;
        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our superheroes list
        listSuperHeroes = new ArrayList<>();

        //Calling method to get data
        getData();
    }

    //This method will get data from the web api
    private void getData(){
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                        loading.dismiss();

                        //calling method to parse json array
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    //This method will parse json data
    private void parseData(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            Vendor_items superHero = new Vendor_items();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL_work));
                superHero.setwork(json.getString(Config.TAG_IMAGE_URL_logo));
              superHero.setName(json.getString(Config.TAG_NAME));
                superHero.setAdd(json.getString(Config.TAG_Add));



            } catch (JSONException e) {
                e.printStackTrace();
            }
            listSuperHeroes.add(superHero);
        }

        //Finally initializing our adapter
        adapter = new CardAdapter(listSuperHeroes, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }


    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_yourevents) {
          //  Intent i = new Intent(this,New_event.class);
          //  startActivity(i);

            return true;
        }
        if (id == R.id.action_contact) {
          //  Intent i = new Intent(this,Contact_us.class);
          //  startActivity(i);

            return true;
        }

        if (id == R.id.action_about) {


            return true;
        }
        return super.onOptionsItemSelected(item);
    }
        }