package com.wapss.digo360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wapss.digo360.R;

public class ReferPage extends AppCompatActivity {

    String messageToShare = "Hello, this is the text to share.";
    ImageView back,btn_faq;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_page);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.refer));
        back = findViewById(R.id.back);
        btn_faq = findViewById(R.id.btn_faq);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "REFERED");
                Intent i = new Intent(ReferPage.this, HelpPage.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        refer_bottom_sheet();
    }
    private void refer_bottom_sheet() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Download this app." + " "+"https://play.google.com/store/apps/details?id=com.wapss.digo360&pcampaignid=web_share"+" " +
                    "Join Us Now"  );
            startActivity(Intent.createChooser(shareIntent, "Refer and Share"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}