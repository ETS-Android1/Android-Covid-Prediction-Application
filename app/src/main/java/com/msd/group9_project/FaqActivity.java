// FAQ Activity
package com.msd.group9_project;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// Class For fields and Views
public class FaqActivity extends AppCompatActivity {
    TextView textlink,txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        textlink = findViewById(R.id.textViewLink);
        textlink.setMovementMethod(LinkMovementMethod.getInstance());
        txt=findViewById(R.id.textViLink);
        txt.setMovementMethod(LinkMovementMethod.getInstance());
        ImageView imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
