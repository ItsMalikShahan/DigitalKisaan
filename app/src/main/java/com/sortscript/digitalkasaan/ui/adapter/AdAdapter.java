package com.sortscript.digitalkasaan.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.ItemAdDesignBinding;
import com.sortscript.digitalkasaan.databinding.ItemFavDesignBinding;
import com.sortscript.digitalkasaan.databinding.RecyclerviewDesignBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;

import java.util.ArrayList;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.ViewHolder> {

    ItemAdDesignBinding binding;
    Context context;
    ArrayList<AdModel> list;
    ArrayList<String> listFavorite;
    ItemClickListener listener;

    public AdAdapter(Context context, ArrayList<AdModel> list, ArrayList<String> listFavorite, ItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listFavorite = listFavorite;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemAdDesignBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.photo).into(binding.ivPicture);
        binding.tvTitle.setText(list.get(position).getTitle());
        binding.tvQuantity.setText(list.get(position).getQuantity());
        binding.tvPrice.setText(list.get(position).getPrice());
//        binding.tv.setText(list.get(position).getAddress());
        Log.d("FAV" , listFavorite.toString());

        if (listFavorite.contains(list.get(position).getAdId())){
            Glide.with(context).load(R.drawable.heart).into(binding.ivFavorite);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(ItemAdDesignBinding itemView) {
            super(itemView.getRoot());

            binding.ivFavorite.setOnClickListener(v-> listener.addFavorite(list.get(getAdapterPosition())));
            binding.clAds.setOnClickListener(v-> listener.itemClick(list.get(getAdapterPosition())));
        }

        @Override
        public void onClick(View v) {
        }
    }

    public interface ItemClickListener {
        void itemClick(AdModel adModel);
        void addFavorite(AdModel adModel);
    }
}
