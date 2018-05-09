package com.sonhoai.sonho.gameth;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sonhoai.sonho.gameth.API.Get;
import com.sonhoai.sonho.gameth.API.Post;
import com.sonhoai.sonho.gameth.Interface.CallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private String token;
    private TextView txtLogin, txtRegister;
    private EditText edtMail, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        checkUser();
    }
    private void init(){
        SharedPreferencesHelper.getInstance(getApplicationContext());
        token = SharedPreferencesHelper.getInstance(getApplicationContext()).getToken();
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);
        txtRegister = findViewById(R.id.txtRegister);
        edtMail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
    }

    private void checkToken(String token,final CallBack<Boolean> res){
        new Post(new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("TOENJSSS", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if(object.getString("message").equals("error")){
                        res.onSuccess(false);
                    }else if(object.getString("message").equals("ok")){
                        res.onSuccess(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {

            }
        }, null).execute("/Users/Check?token="+token);
    }

    private void checkUser(){
        if(!token.isEmpty()){
            checkToken(token, new CallBack<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFail(Boolean result) {
                    showLogin();
                }
            });
        }else {
            showLogin();
        }
    }

    private void showLogin(){
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                txtRegister.setVisibility(View.GONE);
                txtLogin.setVisibility(View.VISIBLE);
            }
        });
        register();
       txtLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               btnLogin.setVisibility(View.VISIBLE);
               btnRegister.setVisibility(View.GONE);
               txtLogin.setVisibility(View.GONE);
               txtRegister.setVisibility(View.VISIBLE);
           }
       });
        login();
    }

    private void login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();
                try {
                    object.put("email", edtMail.getText().toString());
                    object.put("pass", edtPass.getText().toString());
                    object.put("iduser", SharedPreferencesHelper.getInstance(getApplicationContext()).getIdUser());
                    new Post(new CallBack<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("AAAAA", result);
                            try {
                                JSONObject res = new JSONObject(result);
                                if(res.getInt("status")==200){
                                    SharedPreferencesHelper.getInstance(
                                            getApplicationContext()).setSharePre(
                                            "USERINFO",
                                            Context.MODE_PRIVATE,
                                            "tokenUser",
                                            res.getString("message")
                                    );
                                    SharedPreferencesHelper.getInstance(
                                            getApplicationContext()).setSharePre(
                                            "USERINFO",
                                            Context.MODE_PRIVATE,
                                            "idUser",
                                            res.getString("idUser")
                                    );
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                    token = SharedPreferencesHelper.getInstance(getApplicationContext()).getToken();
                                    checkUser();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFail(String result) {
                            Log.i("AAAAA", result);
                        }
                    }, object).execute("/Users/Login");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void register(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();
                try {
                    object.put("email", edtMail.getText().toString());
                    object.put("pass", edtPass.getText().toString());
                    new Post(new CallBack<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("AAAAA", result);
                            try {
                                JSONObject res = new JSONObject(result);
                                if(res.getInt("status")==200){
                                    SharedPreferencesHelper.getInstance(
                                            getApplicationContext()).setSharePre(
                                            "USERINFO",
                                            Context.MODE_PRIVATE,
                                            "tokenUser",
                                            res.getString("message")
                                    );
                                    SharedPreferencesHelper.getInstance(
                                            getApplicationContext()).setSharePre(
                                            "USERINFO",
                                            Context.MODE_PRIVATE,
                                            "idUser",
                                            res.getString("idUser")
                                    );
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                    token = SharedPreferencesHelper.getInstance(getApplicationContext()).getToken();
                                    checkUser();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFail(String result) {
                            Log.i("AAAAA", result);
                        }
                    }, object).execute("/Users");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUser();
    }
}
