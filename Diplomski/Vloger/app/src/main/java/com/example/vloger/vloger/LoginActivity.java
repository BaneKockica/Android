package com.example.vloger.vloger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOGIN_URL = "http://192.168.43.104/vloger/login.php";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button bLogin;
    private Button bRegister;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);

        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);

    }


    private void userLogin() {
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (response.trim().equals("failure")) {
                                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                            } else {


                                String username = jsonObject.getString("username");
                                String password = jsonObject.getString("password");
                                String firstname = jsonObject.getString("firstname");
                                String lastname = jsonObject.getString("lastname");
                                String email = jsonObject.getString("email");
                                String photo = jsonObject.getString("photo");


                                Intent intent = new Intent(LoginActivity.this, MyProfileActivity.class);
                                /*Bundle b = new Bundle();
                                b.putString("username", username);
                                b.putString("firstname", firstname);
                                b.putString("lastname", lastname);
                                intent.putExtra("ufl", b);*/
                                intent.putExtra("username",username);
                                intent.putExtra("password",password);
                                intent.putExtra("firstname",firstname);
                                intent.putExtra("lastname",lastname);
                                intent.putExtra("email",email);
                                intent.putExtra("photo",photo);


                                LoginActivity.this.startActivity(intent);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, username);
                map.put(KEY_PASSWORD, password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                userLogin();
                break;
            case R.id.bRegister:
                Intent loginIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(loginIntent);
                break;
        }
    }
}