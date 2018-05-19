package com.sonhoai.sonho.gameth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sonhoai.sonho.gameth.API.Get;
import com.sonhoai.sonho.gameth.API.Post;
import com.sonhoai.sonho.gameth.API.Put;
import com.sonhoai.sonho.gameth.Adapter.ScoreAdapter;
import com.sonhoai.sonho.gameth.Interface.CallBack;
import com.sonhoai.sonho.gameth.main.GameMainActivity;
import com.sonhoai.sonho.gameth.model.Score;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private ImageButton btnSettings, btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetting();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, GameMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        btnSettings = findViewById(R.id.btnSettings);
        btnPlay = findViewById(R.id.btnPlay);
    }
    private void showSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(UserActivity.this, R.style.myDialog));
        LayoutInflater inflater = LayoutInflater.from(UserActivity.this);
        View view = inflater.inflate(R.layout.dialog_user, null);

        TextView btnMyProfile, btnMyScore, btnLogout;

        btnMyProfile = view.findViewById(R.id.btnMyProfile);
        btnMyScore = view.findViewById(R.id.btnMyScore);
        btnLogout = view.findViewById(R.id.btnLogout);

        changePass(btnMyProfile);
        showScore(btnMyScore);
        logOut(btnLogout);

        builder.setView(view);
        builder.show();
    }

    private void logOut(TextView btnLogout) {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(UserActivity.this, R.style.myDialog));
                dialog.setTitle("Log out?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferencesHelper.getInstance(
                                getApplicationContext()).setSharePre(
                                "USERINFO",
                                Context.MODE_PRIVATE,
                                "tokenUser",
                                ""
                        );
                        SharedPreferencesHelper.getInstance(
                                getApplicationContext()).setSharePre(
                                "USERINFO",
                                Context.MODE_PRIVATE,
                                "idUser",
                                ""
                        );
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }, 1000);
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogView, int which) {
                        dialogView.cancel();
                    }
                });

                dialog.show();
            }
        });
    }

    private void showScore(TextView btnMyScore) {
        btnMyScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  builder = new AlertDialog.Builder(new ContextThemeWrapper(UserActivity.this, R.style.myDialog));
                builder.setTitle("My Score");
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View view = inflater.inflate(R.layout.dialog_score, null);
                builder.setView(view);
                final List<Score> scores = new ArrayList<>();
                ListView listView = view.findViewById(R.id.lvScore);
                final ScoreAdapter scoreAdapter = new ScoreAdapter(UserActivity.this, scores);
                listView.setAdapter(scoreAdapter);

                getScores(new CallBack<String>() {
                    @Override
                    public void onSuccess(String result) {
                        JSONArray array = null;
                        try {
                            array = new JSONArray(result);
                            if (array.length()>0){
                                scores.clear();
                                for (int i = 0; i< array.length();i++){
                                    JSONObject object = array.getJSONObject(i);
                                    scores.add(new Score(object.getInt("score")));
                                }
                                scoreAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFail(String result) {

                    }
                });
                builder.show();
            }
        });
    }

    private void changePass(TextView btnMyProfile) {
        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "AAA", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder  builder = new AlertDialog.Builder(new ContextThemeWrapper(UserActivity.this, R.style.myDialog));
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View view = inflater.inflate(R.layout.dialog_change_pass, null );
                builder.setView(view);
                builder.setTitle("Change Pass");
                final EditText edtNewPass;
                edtNewPass = view.findViewById(R.id.edtNewPass);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final JSONObject object = new JSONObject();
                        getUserInfo(new CallBack<String>() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    object.put("iduser", SharedPreferencesHelper.getInstance(getApplicationContext()).getIdUser());
                                    object.put("pass", edtNewPass.getText().toString());
                                    object.put("email", result);
                                    new Put(new CallBack<String>() {
                                        @Override
                                        public void onSuccess(String result) {
                                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFail(String result) {

                                        }
                                    },object).execute("/Users/"+SharedPreferencesHelper.getInstance(getApplicationContext()).getIdUser()+"/?token="+SharedPreferencesHelper.getInstance(getApplicationContext()).getToken());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(String result) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void getUserInfo(final CallBack<String> callBack){
        new Get(new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    callBack.onSuccess(object.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String result) {

            }
        }).execute("/Users/"+SharedPreferencesHelper.getInstance(getApplicationContext()).getIdUser()+"/?token="+SharedPreferencesHelper.getInstance(getApplicationContext()).getToken());
    }

    private void getScores(final CallBack<String> callBack){
        new Get(new CallBack<String>() {
            @Override
            public void onSuccess(String result) {
               callBack.onSuccess(result);
            }

            @Override
            public void onFail(String result) {

            }
        }).execute("/Scores/"+SharedPreferencesHelper.getInstance(getApplicationContext()).getIdUser()+"/?token="+SharedPreferencesHelper.getInstance(getApplicationContext()).getToken());
    }
}