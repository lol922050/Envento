package svastek.marriage;

/**
 * Created by Tatson on 02-12-2015.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Contact_us extends Activity {

    View view;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);


        b = (Button)findViewById(R.id.button_con_email);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"business@svastek.in"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Query");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

    }
}
