package svastek.marriage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Tatson on 12-01-2016.
 */
public class OtpVerify extends Activity {
    private static String TAG = OtpVerify.class.getSimpleName();

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
        setContentView(R.layout.otpverify);

        inputOtp = (EditText) findViewById(R.id.inputOtp);
        btnVerifyOtp = (Button) findViewById(R.id.btn_verify_otp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnEditMobile = (ImageView) findViewById(R.id.btn_edit_mobile);

        btnEditMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });


    }
    private void verifyOtp() {
        String otp = inputOtp.getText().toString().trim();

        if (!otp.isEmpty()) {
            Intent grapprIntent = new Intent(getApplicationContext(), HttpServiceReg.class);
            grapprIntent.putExtra("otp", otp);
            startService(grapprIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }
}
