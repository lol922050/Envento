package svastek.marriage.fragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import svastek.marriage.Adapter_vendor_detail;
import svastek.marriage.CardAdapter;
import svastek.marriage.Config;
import svastek.marriage.Detail_vendor;
import svastek.marriage.PrefManagerReg;
import svastek.marriage.R;
import svastek.marriage.Senddata;
import svastek.marriage.Vendor_deatil_items;
import svastek.marriage.Vendor_items;
import svastek.marriage.adapter.ImageSlideAdapter;
import svastek.marriage.bean.Product;
import svastek.marriage.json.GetJSONObject;
import svastek.marriage.json.JsonReader;
import svastek.marriage.utils.CheckNetworkConnection;
import svastek.marriage.utils.CirclePageIndicator;
import svastek.marriage.utils.PageIndicator;
import svastek.marriage.utils.TagName;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class HomeFragment extends Fragment {

	public static final String ARG_ITEM_ID = "home_fragment";

	private static final long ANIM_VIEWPAGER_DELAY = 2500;
	private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 8000;

	final String ROOT_URL = "http://svastek.in/";
	FloatingActionButton fab;

	// UI References
	private ViewPager mViewPager;
	TextView imgNameTxt;
	//PageIndicator mIndicator;
	AlertDialog alertDialog;
	private List<Vendor_deatil_items> movieList = new ArrayList<>();

	TextView owner,add1,add2,add3,phone1,phone2,email1,email2,link1,link2,link3,budget;

	String name,address;

	String user_mob,event,v_cat,v_name,v_loc;

	List<Product> products;
	RequestImgTask task;
	boolean stopSliding = false;
	String message;
	private ListView listView;
	private Adapter_vendor_detail adapter;

	private Runnable animateViewPager;
	private Handler handler;

	String url1 = "http://www.svastek.in/sevento/php/imageslider.php";
	String url;
	FragmentActivity activity;
	private PrefManagerReg pref;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();



		getOverflowMenu();
		Detail_vendor activity = (Detail_vendor) getActivity();
		name = activity.yo1();
		address = activity.yo2();

		 url =  "http://www.svastek.in/sevento/php/vendor_detail.php?name="+name+"&address="+ address;


		setRetainInstance(true);
		pref = new PrefManagerReg(getContext());
		HashMap<String, String> profile = pref.getUserDetails();

		user_mob =  profile.get("mobile");
		getData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		findViewById(view);
    owner=(TextView)view.findViewById(R.id.owner_nmae_text);
		add1=(TextView)view.findViewById(R.id.textView_add1);
		add3=(TextView)view.findViewById(R.id.textView_add2);
		add2=(TextView)view.findViewById(R.id.textView_add3);
		phone2=(TextView)view.findViewById(R.id.textView_phone2);
		phone1=(TextView)view.findViewById(R.id.textView_phone1);
		email1=(TextView)view.findViewById(R.id.textView_email);
		email2=(TextView)view.findViewById(R.id.textView_email1);
		budget=(TextView)view.findViewById(R.id.textView_budget);
		link1=(TextView)view.findViewById(R.id.textView_other);
		link2=(TextView)view.findViewById(R.id.textView_other2);
		link3=(TextView)view.findViewById(R.id.textView_other1);
		 fab = (FloatingActionButton)view.findViewById(R.id.button_add_vendor_list_fab);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				senddata();
			}
		});

		//mIndicator.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				switch (event.getAction()) {

				case MotionEvent.ACTION_CANCEL:
					break;

				case MotionEvent.ACTION_UP:
					// calls when touch release on ViewPager
					if (products != null && products.size() != 0) {
						stopSliding = false;
						runnable(products.size());
						handler.postDelayed(animateViewPager,
								ANIM_VIEWPAGER_DELAY_USER_VIEW);
					}
					break;

				case MotionEvent.ACTION_MOVE:
					// calls when ViewPager touch
					if (handler != null && stopSliding == false) {
						stopSliding = true;
						handler.removeCallbacks(animateViewPager);
					}
					break;
				}
				return false;
			}
		});

		return view;
	}




	private void getData(){
		//Showing a progress dialog
		final ProgressDialog loading = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);

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
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

		//Adding request to the queue
		requestQueue.add(jsonArrayRequest);
	}

	//This method will parse json data
	private void parseData(JSONArray array){


			JSONObject json = null;
			try {
				json = array.getJSONObject(0);
				owner.setText(json.getString("owner_name"));
				add1.setText(json.getString("add1"));
				add2.setText(json.getString("add2"));
				add3.setText(json.getString("add3"));
				phone1.setText(json.getString("phone1"));
				phone2.setText(json.getString("phone2"));
				email1.setText(json.getString("email1"));
				email2.setText(json.getString("email2"));
				budget.setText(json.getString("budget"));
				link1.setText(json.getString("link1"));
				link2.setText(json.getString("link2"));
				link3.setText(json.getString("link3"));



			} catch (JSONException e) {
				e.printStackTrace();
			}

	}


	private void getOverflowMenu() {

		try {
			ViewConfiguration config = ViewConfiguration.get(getActivity());
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void senddata(){
		//user_mob="8005647832";
		event = "Birthday";
		v_cat = "Photo";
		v_name="Roger";
		v_loc="Panjim";


     /*   if(g==1)
        {
            sendgender="male";
        }
        else if(g==0){
            sendgender="female";
        }*/
		RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(ROOT_URL) //Setting the Root URL
				.build(); //Finally building the adapter

		//Creating object for our interface
		Senddata api = adapter.create(Senddata.class);

		api.uploadprofile(user_mob, event, v_cat, v_name,v_loc, new Callback<retrofit.client.Response>(){






			@Override
			public void success(retrofit.client.Response response, retrofit.client.Response response2) {
				Log.d("SUCCESS ", "SUCCESS RETURN " + response);
				Toast.makeText(getActivity(), "Profile Created !! ", Toast.LENGTH_LONG).show();
				//Intent i = new Intent(getApplicationContext(), Login.class);
				//startActivity(i);
			}

			@Override
			public void failure(RetrofitError error) {
				Log.d("Failure ", "Failure RETURN " + error);

			}
		});

	}
















	private void findViewById(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		//mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		imgNameTxt = (TextView) view.findViewById(R.id.img_name);
	}

	public void runnable(final int size) {
		handler = new Handler();
		animateViewPager = new Runnable() {
			public void run() {
				if (!stopSliding) {
					if (mViewPager.getCurrentItem() == size - 1) {
						mViewPager.setCurrentItem(0);
					} else {
						mViewPager.setCurrentItem(
								mViewPager.getCurrentItem() + 1, true);
					}
					handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
				}
			}
		};
	}


	@Override
	public void onResume() {
		if (products == null) {
			sendRequest();
		} else {
			mViewPager.setAdapter(new ImageSlideAdapter(activity, products,
					HomeFragment.this));

		//	mIndicator.setViewPager(mViewPager);
			imgNameTxt.setText(""
					+ ((Product) products.get(mViewPager.getCurrentItem()))
							.getName());
			runnable(products.size());
			//Re-run callback
			handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
		}
		super.onResume();
	}

	
	@Override
	public void onPause() {
		if (task != null)
			task.cancel(true);
		if (handler != null) {
			//Remove callback
			handler.removeCallbacks(animateViewPager);
		}
		super.onPause();

	}

	private void sendRequest() {
		if (CheckNetworkConnection.isConnectionAvailable(activity)) {
			task = new RequestImgTask(activity);
			task.execute(url1);
		} else {
			message = getResources().getString(R.string.no_internet_connection);
			showAlertDialog(message, true);
		}
	}

	public void showAlertDialog(String message, final boolean finish) {
		alertDialog = new AlertDialog.Builder(activity).create();
		alertDialog.setMessage(message);
		alertDialog.setCancelable(false);

		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (finish)
							activity.finish();
					}
				});
		alertDialog.show();
	}

	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				if (products != null) {
					imgNameTxt.setText(""
							+ ((Product) products.get(mViewPager
									.getCurrentItem())).getName());
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private class RequestImgTask extends AsyncTask<String, Void, List<Product>> {
		private final WeakReference<Activity> activityWeakRef;
		Throwable error;

		public RequestImgTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<Product> doInBackground(String... urls) {
			try {
				JSONObject jsonObject = getJsonObject(urls[0]);
				if (jsonObject != null) {
					boolean status = true;

					if (status) {
						JSONObject jsonData = jsonObject
								.getJSONObject(TagName.TAG_DATA);
						if (jsonData != null) {
							products = JsonReader.getHome(jsonData);

						} else {
							message = jsonObject.getString(TagName.TAG_DATA);
						}
					} else {
						message = jsonObject.getString(TagName.TAG_DATA);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return products;
		}

		/**
		 * It returns jsonObject for the specified url.
		 * 
		 * @param url
		 * @return JSONObject
		 */
		public JSONObject getJsonObject(String url) {
			JSONObject jsonObject = null;
			try {
				jsonObject = GetJSONObject.getJSONObject(url);
			} catch (Exception e) {
			}
			return jsonObject;
		}

		@Override
		protected void onPostExecute(List<Product> result) {
			super.onPostExecute(result);

			if (activityWeakRef != null && !activityWeakRef.get().isFinishing()) {
				if (error != null && error instanceof IOException) {
					message = getResources().getString(R.string.time_out);
					showAlertDialog(message, true);
				} else if (error != null) {
					message = getResources().getString(R.string.error_occured);
					showAlertDialog(message, true);
				} else {
					products = result;
					if (result != null) {
						if (products != null && products.size() != 0) {

							mViewPager.setAdapter(new ImageSlideAdapter(
									activity, products, HomeFragment.this));

						//	mIndicator.setViewPager(mViewPager);
							imgNameTxt.setText(""
									+ ((Product) products.get(mViewPager
											.getCurrentItem())).getName());
							runnable(products.size());
							handler.postDelayed(animateViewPager,
									ANIM_VIEWPAGER_DELAY);
						} else {
							imgNameTxt.setText("No Products");
						}
					} else {
					}
				}
			}
		}
	}
}
