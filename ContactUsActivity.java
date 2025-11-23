package com.swe3.miniproject1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        TextView emailTextView = findViewById(R.id.text_contact_email);
        TextView phoneTextView = findViewById(R.id.text_contact_phone);

        emailTextView.setText(getString(R.string.email_label));
        phoneTextView.setText(getString(R.string.phone_label));

        emailTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + getString(R.string.email_label)));
            startActivity(intent);
        });

        phoneTextView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + getString(R.string.phone_label)));
            startActivity(intent);
        });
    }
}
