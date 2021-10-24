package com.example.payfromhome.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.payfromhome.R;
import com.example.payfromhome.databinding.ActivityLoginBinding;
import com.example.payfromhome.helper.SharedPreferenceHelper;
import com.example.payfromhome.model.User;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        user = new User();
        binding.setActivity(this);
        binding.setUser(user);
    }

    public View.OnClickListener onLoginButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                Toast.makeText(LoginActivity.this, getString(R.string.username_dan_password_tidak_boleh_kosong), Toast.LENGTH_SHORT).show();
            } else {
                login(user.getUsername(), user.getPassword());
            }
        }
    };

    public View.OnClickListener onRegisterButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    };

    private void login(String uname, String pass) {
        SharedPreferenceHelper preferenceHelper = new SharedPreferenceHelper(this);
        if (uname.equals(preferenceHelper.getUser().getUsername()) && pass.equals(preferenceHelper.getUser().getPassword())) {
            Toast.makeText(LoginActivity.this, getString(R.string.login_berhasil), Toast.LENGTH_SHORT).show();
            preferenceHelper.setIsLogin(true);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.username_atau_password_salah), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (new SharedPreferenceHelper(this).isLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

}