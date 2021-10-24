package com.example.payfromhome.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payfromhome.R;
import com.example.payfromhome.databinding.TransactionItemBinding;
import com.example.payfromhome.model.Transaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<Transaction> transactionList;

    public HistoryAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(transactionList.get(position));
    }

    @Override
    public int getItemCount() {
        return transactionList == null ? 0 : transactionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TransactionItemBinding binding;

        public ViewHolder(@NonNull TransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Transaction transaction) {
            binding.setTransaction(transaction);
            if (transaction.getType().equalsIgnoreCase("electricity")) {
                binding.setImageRes(R.drawable.ic_electricity);
            } else {
                binding.setImageRes(R.drawable.ic_water);
            }
        }
    }
}
