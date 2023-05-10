package com.sortscript.digitalkasaan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sortscript.digitalkasaan.databinding.CategoryDesignBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    CategoryDesignBinding binding;
    Context context;
    ArrayList<CategoryModel> list;
    ItemClickListener listener;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> list, ItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CategoryDesignBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        binding.tvTitle.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getImage()).into(binding.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(CategoryDesignBinding itemView) {
            super(itemView.getRoot());
            binding.clCategory.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listener.itemClick(list.get(getAdapterPosition()));
        }
    }

    public interface ItemClickListener {
        public void itemClick(CategoryModel categoryModel);
    }
}
