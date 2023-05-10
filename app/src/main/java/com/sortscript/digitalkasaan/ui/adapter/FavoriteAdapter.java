package com.sortscript.digitalkasaan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.ItemFavDesignBinding;
import com.sortscript.digitalkasaan.databinding.ItemRecyclerviewHomeBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;

import java.util.ArrayList;

public class FavoriteAdapter  extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    ItemFavDesignBinding binding;
    Context context;
    ArrayList<AdModel> list;
    ItemClickListener listener;

    public FavoriteAdapter(Context context, ArrayList<AdModel> list, ItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemFavDesignBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.photo).into(binding.ivPicture);
        binding.tvTitle.setText(list.get(position).getTitle());
        binding.tvQuantity.setText(list.get(position).getQuantity());
        binding.tvPrice.setText(list.get(position).getPrice());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(ItemFavDesignBinding itemView) {
            super(itemView.getRoot());
            binding.ivFavorite.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listener.unFavItem(list.get(getAdapterPosition()));
        }
    }

    public interface ItemClickListener {
        void unFavItem(AdModel adModel);
    }
}
