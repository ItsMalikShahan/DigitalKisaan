package com.sortscript.digitalkasaan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.AdminAdDesignBinding;
import com.sortscript.digitalkasaan.databinding.RecyclerviewDesignBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;

import java.util.ArrayList;

public class AdminAdAdapter extends RecyclerView.Adapter<AdminAdAdapter.ViewHolder> {

    AdminAdDesignBinding binding;
    Context context;
    ArrayList<AdModel> list;
    ItemClickListener listener;
    String screen;

    public AdminAdAdapter(Context context, ArrayList<AdModel> list, ItemClickListener listener ,String screen) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.screen = screen;
    }

    @NonNull
    @Override
    public AdminAdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = AdminAdDesignBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminAdAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.photo).into(binding.ivProductImage);
        binding.tvProductName.setText(list.get(position).getTitle());
        binding.tvProductStatus.setText(list.get(position).getStatus());
        binding.tvProductPrice.setText(list.get(position).getPrice());
        binding.tvLocation.setText(list.get(position).getAddress());
        binding.ivFavorite.setVisibility(View.GONE);
        binding.ivCall.setVisibility(View.GONE);
        if (screen.equals("MyAds")){
        binding.ivDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(AdminAdDesignBinding itemView) {
            super(itemView.getRoot());
            binding.clItem.setOnClickListener(v -> {
                listener.itemClick(list.get(getAdapterPosition()));
            });
            binding.ivDelete.setOnClickListener(v -> {
                listener.deleteAd(list.get(getAdapterPosition()));
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemClickListener {
        void itemClick(AdModel adModel);

        void deleteAd(AdModel adModel);
    }
}
