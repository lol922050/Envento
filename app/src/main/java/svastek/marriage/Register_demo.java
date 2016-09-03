package svastek.marriage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Tatson on 20-01-2016.
 */
public class Register_demo extends Activity {

    String nav,tuzoemail,tuzophone;
    Button ok;
    EditText name,email,phone;
    final String ROOT_URL = "http://svastek.in/";


    public static final String UserNum = "UserNum";
    public static final String uu = "uu";
    String check="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_demo);

        ok=(Button)findViewById(R.id.button_reg_demo);
        name=(EditText)findViewById(R.id.editText_name_regdemo);
        email=(EditText)findViewById(R.id.editText_email_regdemo);
        phone=(EditText)findViewById(R.id.editText_phone_reg_demo);

        //shared
        SharedPreferences set = getSharedPreferences (UserNum, 0);
        String number = set.getString(uu, "");
        if(!(number.equals(check))){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

ok.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        nav=name.getText().toString();
        tuzoemail=email.getText().toString();
        tuzophone=phone.getText().toString();

        if (nav == null || tuzophone == null || tuzoemail == null) {
            Toast.makeText(Register_demo.this, "Fill Details", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(tuzoemail)) {
            email.setError("Invalid Email");
        }
        else if (tuzophone.length()<10) {
            phone.setError("Invalid Phone");
        }
        else {
            senddata();
        }
    }
});



    }
    private boolean isValidEmail(String email) {
        // TODO Auto-generated method stub
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public  void senddata(){



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
        Register_inter api = adapter.create(Register_inter.class);


        api.uploadprofile(nav, tuzoemail, tuzophone, new Callback<retrofit.client.Response>(){



            @Override
            public void success(Response response, Response response2) {
                Log.d("SUCCESS ", "SUCCESS RETURN " + response);
                Toast.makeText(Register_demo.this, "Profile Created !! ", Toast.LENGTH_LONG).show();
                SharedPreferences set = getSharedPreferences (UserNum, 0);
                set.edit().putString(uu, phone.getText().toString()).commit();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Failure ", "Failure RETURN " + error);

            }
        });

    }
}