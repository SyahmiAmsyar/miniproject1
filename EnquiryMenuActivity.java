package com.swe3.miniproject1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.swe3.miniproject1.databinding.ActivityEnquiryMenuBinding;

public class EnquiryMenuActivity extends AppCompatActivity {

    private ActivityEnquiryMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnquiryMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cardAboutUs.setOnClickListener(v -> startActivity(new Intent(this, AboutUsActivity.class)));
        binding.cardContactUs.setOnClickListener(v -> startActivity(new Intent(this, ContactUsActivity.class)));
    }
}
