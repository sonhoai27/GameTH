package com.sonhoai.sonho.gameth;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.sonhoai.sonho.gameth.main.GameMainActivity;

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
        builder.setView(view);
        builder.show();
    }
}
