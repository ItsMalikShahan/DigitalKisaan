package com.sortscript.digitalkasaan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.ItemRecyclerviewHomeBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;
import com.sortscript.digitalkasaan.datamodel.model.fan.CategoryModel;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    ItemRecyclerviewHomeBinding binding;
    Context context;
    ArrayList<AdModel> list;
    ItemClickListener listener;

    public HomeAdapter(Context context, ArrayList<AdModel> list, ItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemRecyclerviewHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.photo).into(binding.ivPicture);
        binding.tvTitle.setText(list.get(position).getTitle());
        binding.tvQuantity.setText(list.get(position).getQuantity());
        binding.tvPrice.setText(list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(ItemRecyclerviewHomeBinding itemView) {
            super(itemView.getRoot());
            binding.clAds.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.itemClick(list.get(getAdapterPosition()));
        }
    }

    public interface ItemClickListener {
        void itemClick(AdModel adModel);
    }
}
