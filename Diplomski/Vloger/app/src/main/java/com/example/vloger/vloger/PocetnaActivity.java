package com.example.vloger.vloger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PocetnaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bUploadVideo;
    private Button bMyProfile;
    private String username, firstname, lastname, photo;
    public static final String VLOG_URL = "http://192.168.43.104/vloger/vlog.php";
    private TextView tv;
    RequestQueue requestQueue;
    String[] VideoID;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pocetna);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(PocetnaActivity.this);
        //adapter.VideoID=vratiListu();
        recyclerView.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();

        username=intent.getStringExtra("username");
        firstname=intent.getStringExtra("firstname");
        lastname=intent.getStringExtra("lastname");
        photo = intent.getStringExtra("photo");

        bUploadVideo = (Button) findViewById(R.id.bUploadVideoPocetna);
        bMyProfile = (Button) findViewById(R.id.bMyProfilePocetna);
        tv=(TextView) findViewById(R.id.tv);
        bUploadVideo.setOnClickListener(this);
        bMyProfile.setOnClickListener(this);

        //tv.setText(vratiListu()[1]);
    }




    public String[] vratiListu()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,VLOG_URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONArray jsonArray = response.getJSONArray("lista");
                            VideoID = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject vlog = jsonArray.getJSONObject(i);
                                String url = vlog.getString("url");
                                String naziv = vlog.getString("naziv");
                                VideoID[i] = url;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", "ERROR");
            }
        }

        );

        requestQueue.add(jsonObjectRequest);

        return VideoID;
    }


   @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bUploadVideoPocetna:
        Intent intentP = new Intent(PocetnaActivity.this, UploadActivity.class);
        intentP.putExtra("username",username);
        intentP.putExtra("firstname",firstname);
        intentP.putExtra("lastname",lastname);
        intentP.putExtra("photo",photo);
        PocetnaActivity.this.startActivity(intentP);
        break;
        case R.id.bMyProfilePocetna:
        Intent intentMP = new Intent(PocetnaActivity.this, MyProfileActivity.class);
        intentMP.putExtra("username",username);
        intentMP.putExtra("firstname",firstname);
        intentMP.putExtra("lastname",lastname);
        intentMP.putExtra("photo",photo);
        PocetnaActivity.this.startActivity(intentMP);
        break;
    }
    }
}
