package svastek.marriage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tatson on 12-01-2016.
 */
public class SmsActivityRegister extends Activity implements View.OnClickListener {
    private static String TAG = SmsActivityReg.class.getSimpleName();

    private ViewPager viewPager;
    private Button btnRequestSms, btnVerifyOtp;
    private EditText inputName, inputEmail, inputMobile, inputOtp;
    private ProgressBar progressBar;
    private PrefManagerReg pref;
    private ImageView btnEditMobile;
    private TextView txtEditMobile;
    private LinearLayout layoutEditMobile;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);


       // viewPager = (ViewPager) findViewById(R.id.viewPagerVertical);
        inputName = (EditText) findViewById(R.id.inputName);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputMobile = (EditText) findViewById(R.id.inputMobile);
        inputOtp = (EditText) findViewById(R.id.inputOtp);
        btnRequestSms = (Button) findViewById(R.id.btn_request_sms);
        btnVerifyOtp = (Button) findViewById(R.id.btn_verify_otp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnEditMobile = (ImageView) findViewById(R.id.btn_edit_mobile);
        txtEditMobile = (TextView) findViewById(R.id.txt_edit_mobile);
       // layoutEditMobile = (LinearLayout) findViewById(R.id.layout_edit_mobile);

        // view click listeners
       // btnEditMobile.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
      //  btnVerifyOtp.setOnClickListener(this);

        // hiding the edit mobile number
       // layoutEditMobile.setVisibility(View.GONE);

        pref = new PrefManagerReg(this);

        // Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.isLoggedIn()) {
            Intent intent = new Intent(SmsActivityRegister.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }


        /**
         * Checking if the device is waiting for sms
         * showing the user OTP screen
         */
        if (pref.isWaitingForSms()) {
           // viewPager.setCurrentItem(1);
            //layoutEditMobile.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        email = inputEmail.getText().toString().trim();
        if (!isValidEmail(email)) {
            inputEmail.setError("Invalid Email");
        }
        else {
            validateForm();
        }
    }

    private boolean isValidEmail(String email) {
        // TODO Auto-generated method stub
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void validateForm() {
        String name = inputName.getText().toString().trim();

        String mobile = inputMobile.getText().toString().trim();

        // validating empty name and email
        if (name.length() == 0 || email.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile)) {

            // request for sms
            progressBar.setVisibility(View.VISIBLE);

            // saving the mobile number in shared preferences
            pref.setMobileNumber(mobile);

            // requesting for sms
            requestForSMS(name, email, mobile);

        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }
    }
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }
    private void requestForSMS(final String name, final String email, final String mobile) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.URL_REQUEST_SMS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONObject responseObj = new JSONObject(response);

                    // Parsing json object response
                    // response will be a json object
                    boolean error = responseObj.getBoolean("error");
                    String message = responseObj.getString("message");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    if (!error) {
                        // boolean flag saying device is waiting for sms
                        pref.setIsWaitingForSms(true);

                        // moving the screen to next pager item i.e otp screen
                        Intent intent = new Intent(SmsActivityRegister.this,OtpVerify.class);
                        startActivity(intent);
                      //  txtEditMobile.setText(pref.getMobileNumber());
                       // layoutEditMobile.setVisibility(View.VISIBLE);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Error: " + message,
                                Toast.LENGTH_LONG).show();
                    }

                    // hiding the progress bar
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("mobile", mobile);

                Log.e(TAG, "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppData.getInstance().addToRequestQueue(strReq);
    }
}
