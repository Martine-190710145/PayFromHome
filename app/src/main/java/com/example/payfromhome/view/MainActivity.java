package com.example.payfromhome.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.payfromhome.R;
import com.example.payfromhome.databinding.ActivityMainBinding;
import com.example.payfromhome.helper.SharedPreferenceHelper;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SharedPreferenceHelper sharedPreferenceHelper;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        binding.setActivity(this);
        binding.setBalance(NumberFormat
                .getCurrencyInstance(new Locale("in", "id"))
                .format(sharedPreferenceHelper.getBalance())
        );
    }

    public View.OnClickListener cardElectricityClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, ElectricityActivity.class));
        }
    };

    public View.OnClickListener cardWaterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, WaterActivity.class));
        }
    };

    public View.OnClickListener cardHistoryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, HistoryActivity.class));
        }
    };

    public View.OnClickListener cardAboutClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
    };

    public View.OnClickListener btnLogoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sharedPreferenceHelper.setIsLogin(false);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        binding.setBalance(NumberFormat
                .getCurrencyInstance(new Locale("in", "id"))
                .format(sharedPreferenceHelper.getBalance())
        );
    }
}