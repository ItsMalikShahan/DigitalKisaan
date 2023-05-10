package com.sortscript.digitalkasaan.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sortscript.digitalkasaan.databinding.FragmentFavoriteBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;
import com.sortscript.digitalkasaan.ui.adapter.FavoriteAdapter;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavoriteAdapter.ItemClickListener {

    private FragmentFavoriteBinding binding;
    ArrayList<AdModel> list = new ArrayList<>();
    FavoriteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();
    }

    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Favorite");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                binding.rvFavorite.removeAllViews();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AdModel adModel = dataSnapshot.getValue(AdModel.class);
                    assert adModel != null;
                    list.add(adModel);
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setAdapter() {
        adapter = new FavoriteAdapter(getContext(), list, this);
        binding.rvFavorite.setAdapter(adapter);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void unFavItem(AdModel adModel) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Favorite");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child(userId).child(adModel.getAdId()).removeValue();
        adapter.notifyDataSetChanged();
    }
}