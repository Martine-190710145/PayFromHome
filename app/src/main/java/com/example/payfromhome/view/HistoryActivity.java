package com.example.payfromhome.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.payfromhome.R;
import com.example.payfromhome.databinding.ActivityHistoryBinding;
import com.example.payfromhome.db.TransactionDatabase;
import com.example.payfromhome.model.Transaction;

import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {
    private HistoryAdapter adapter;
    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);

        setSupportActionBar(binding.toolbarHistory);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.riwayat));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTransactions();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getTransactions() {
        class GetTransactions extends AsyncTask<Void, Void, List<Transaction>> {

            @Override
            protected List<Transaction> doInBackground(Void... voids) {
                return TransactionDatabase.getInstance(HistoryActivity.this)
                        .getDatabase()
                        .transactionDao()
                        .getAll();
            }

            @Override
            protected void onPostExecute(List<Transaction> transactions) {
                super.onPostExecute(transactions);
                adapter = new HistoryAdapter(HistoryActivity.this, transactions);
                binding.recyclerTransaction.setAdapter(adapter);
            }
        }

        GetTransactions getTransactions = new GetTransactions();
        getTransactions.execute();
    }
}