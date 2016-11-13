package com.example.vloger.vloger;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


public class ThreeFragment extends Fragment {

    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstname";

    private String username;
    private String firstname;

    public ThreeFragment() {
    }

    public static ThreeFragment newInstance(String username, String firstname) {
        ThreeFragment f = new ThreeFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(FIRSTNAME, firstname);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
            firstname = getArguments().getString(FIRSTNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_three, container, false);

        String username = getActivity().getIntent().getStringExtra("username");
        String firstname = getActivity().getIntent().getStringExtra("firstname");
        TextView tvImePrezime = (TextView) view.findViewById(R.id.tvImePrezime);
        TextView tvUsername = (TextView) view.findViewById(R.id.tvUsername);


        tvImePrezime.setText(firstname);
        tvUsername.setText(username);


        return view;

    }


}