package com.example.payfromhome.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.payfromhome.R;
import com.example.payfromhome.databinding.ActivityElectricityBinding;
import com.example.payfromhome.db.TransactionDatabase;
import com.example.payfromhome.helper.SharedPreferenceHelper;
import com.example.payfromhome.model.Transaction;

import java.util.Date;
import java.util.Objects;

public class ElectricityActivity extends AppCompatActivity {
    private SharedPreferenceHelper preferenceHelper;

    private ActivityElectricityBinding binding;
    private Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_electricity);

        preferenceHelper = new SharedPreferenceHelper(this);

        transaction = new Transaction();
        binding.setActivity(this);
        binding.setTransaction(transaction);

        setSupportActionBar(binding.toolbarElectricity);
        setSupportActionBar(binding.toolbarElectricity);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.listrik);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] paymentMethods = {getString(R.string.transfer_bank), getString(R.string.saldo)};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, paymentMethods
        );
        binding.spinnerPayment.setAdapter(spinnerAdapter);
    }

    public View.OnClickListener onPayButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int paymentPos = binding.spinnerPayment.getSelectedItemPosition();
            String payment = "";
            if (paymentPos == 0) {
                payment = "transfer";
            } else {
                payment = "balance";
            }

            if (transaction.getStringNominal().isEmpty()) {
                Toast.makeText(ElectricityActivity.this, getString(R.string.mohon_lengkapi_form), Toast.LENGTH_SHORT).show();
                return;
            }

            if (paymentPos == 1) {
                if (transaction.getNominal() > preferenceHelper.getBalance()) {
                    Toast.makeText(ElectricityActivity.this, getString(R.string.saldo_tidak_cukup), Toast.LENGTH_SHORT).show();
                } else {
                    int remainBalance = preferenceHelper.getBalance() - transaction.getNominal();
                    preferenceHelper.setBalance(remainBalance);
                    saveTransaction(transaction.getSerialId(), transaction.getNominal(), payment);
                }
            } else {
                saveTransaction(transaction.getSerialId(), transaction.getNominal(), payment);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTransaction(String id, int nominal, String payment) {
        class AddTransaction extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                String date = new Date().toString();
                Transaction transaction = new Transaction("electricity", id, nominal, payment, date);

                TransactionDatabase.getInstance(ElectricityActivity.this)
                        .getDatabase()
                        .transactionDao()
                        .insert(transaction);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                startActivity(new Intent(ElectricityActivity.this, LandingActivity.class));
                finish();
            }
        }

        AddTransaction addTransaction = new AddTransaction();
        addTransaction.execute();
    }
}