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
import com.example.payfromhome.databinding.ActivityRegisterBinding;
import com.example.payfromhome.helper.SharedPreferenceHelper;
import com.example.payfromhome.model.User;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        user = new User();
        binding.setActivity(this);
        binding.setUser(user);
    }

    public View.OnClickListener onRegisterButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (user.getName().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                Toast.makeText(RegisterActivity.this, getString(R.string.mohon_lengkapi_form), Toast.LENGTH_SHORT).show();
            } else {
                register(user.getName(), user.getUsername(), user.getPassword());
            }
        }
    };

    public View.OnClickListener onLoginButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    };

    private void register(String name, String username, String password) {
        SharedPreferenceHelper preferenceHelper = new SharedPreferenceHelper(this);
        preferenceHelper.setRegister(name, username, password);
        Toast.makeText(RegisterActivity.this, getString(R.string.registrasi_berhasil), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}