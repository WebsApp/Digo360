package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wapss.digo360.R;

public class AboutDigo extends AppCompatActivity {
    LinearLayout btn_about,btn_privacy,btn_data_share,btn_agreement;
    ImageView back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_digo);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.purple));

        back = findViewById(R.id.back);
        btn_about = findViewById(R.id.btn_about);
        btn_privacy = findViewById(R.id.btn_privacy);
        btn_agreement = findViewById(R.id.btn_agreement);
        btn_data_share = findViewById(R.id.btn_data_share);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_ID", "3");
                Intent i = new Intent(AboutDigo.this, Pages.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btn_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_ID", "2");
                Intent i = new Intent(AboutDigo.this, Pages.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btn_data_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutDigo.this, "Data Sharing Policy Upload Pending", Toast.LENGTH_SHORT).show();
            }
        });
        btn_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutDigo.this, "User Data Agreement Upload Pending", Toast.LENGTH_SHORT).show();
            }
        });
    }
}