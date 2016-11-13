package com.example.vloger.vloger;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "http://192.168.43.104/vloger/uploadVlog.php";

    private String username, firstname, lastname, photo;
    private Button bHomeU, bProfileU,bUploadVlog;
    private EditText nameVlog,urlVlog;

    public static final String KEY_USERNAME = "username";
    public static final String KEY_URL = "url";
    public static final String KEY_NAZIV = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bHomeU = (Button) findViewById(R.id.bHomeU);
        bProfileU = (Button) findViewById(R.id.bProfileU);
        bUploadVlog = (Button) findViewById(R.id.bUploadVlog);
        nameVlog = (EditText) findViewById(R.id.etNazivVloga);
        urlVlog = (EditText) findViewById(R.id.etUrlVidea);

        Intent intent = getIntent();

        username = intent.getStringExtra("username");
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        photo = intent.getStringExtra("photo");

        bHomeU.setOnClickListener(this);
        bProfileU.setOnClickListener(this);
        bUploadVlog.setOnClickListener(this);
    }
    private void UploadVlog() {
        final String name = nameVlog.getText().toString();
        final String url = urlVlog.getText().toString().substring(urlVlog.getText().toString().lastIndexOf("=")+1);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(UploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UploadActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_URL, url);
                params.put(KEY_NAZIV, name);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == bHomeU) {
            Intent intent = new Intent(UploadActivity.this, PocetnaActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("firstname", firstname);
            intent.putExtra("lastname", lastname);
            intent.putExtra("photo", photo);
            UploadActivity.this.startActivity(intent);
        }
        if (v == bProfileU) {
            Intent intent = new Intent(UploadActivity.this, MyProfileActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("firstname", firstname);
            intent.putExtra("lastname", lastname);
            intent.putExtra("photo", photo);
            UploadActivity.this.startActivity(intent);
        }
        if (v==bUploadVlog)
        {
            UploadVlog();
        }

    }
}
