package com.sortscript.digitalkasaan.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.FragmentHomeBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;
import com.sortscript.digitalkasaan.datamodel.model.fan.CategoryModel;
import com.sortscript.digitalkasaan.ui.activity.ViewSingleAdActivity;
import com.sortscript.digitalkasaan.ui.adapter.CategoryHomeAdapter;
import com.sortscript.digitalkasaan.ui.adapter.HomeAdapter;
import com.sortscript.digitalkasaan.ui.base.BaseFragment;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements HomeAdapter.ItemClickListener, CategoryHomeAdapter.ItemClickListener {

    private FragmentHomeBinding binding;
    ArrayList<AdModel> listRice = new ArrayList<>();
    ArrayList<AdModel> listWheat = new ArrayList<>();
    ArrayList<CategoryModel> listCategory = new ArrayList<>();
    ArrayList<String> listFavorite = new ArrayList<>();
    Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listCategory.add(new CategoryModel("Rice", getResources().getDrawable(R.drawable.rice)));
        listCategory.add(new CategoryModel("Wheat", getResources().getDrawable(R.drawable.wheat)));
        listCategory.add(new CategoryModel("Cotton", getResources().getDrawable(R.drawable.cotton)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvCategorySeeAll.setOnClickListener(v -> {
            Fragment categoryFragment = new CategoryFragment();
            replaceFragmentMethod(categoryFragment, CategoryFragment.class.getSimpleName(), true, false, getActivity());
        });

        binding.tvWheatSeeAll.setOnClickListener(v -> {
            Fragment seeAllAdsFragment = new SeeAllAdsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TYPE", "Wheat");
            seeAllAdsFragment.setArguments(bundle);
            replaceFragmentMethod(seeAllAdsFragment, SeeAllAdsFragment.class.getSimpleName(), true, false, getActivity());
        });

        binding.tvRiceSeeAll.setOnClickListener(v -> {
            Fragment seeAllAdsFragment = new SeeAllAdsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TYPE", "Rice");
            seeAllAdsFragment.setArguments(bundle);
            replaceFragmentMethod(seeAllAdsFragment, SeeAllAdsFragment.class.getSimpleName(), true, false, getActivity());
        });

        showLoading();
        getAds();
        setCategoryAdapter();
    }

    private void getAds() {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan");

        reference.child("Ads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AdModel adModel = dataSnapshot.getValue(AdModel.class);
                    assert adModel != null;
                    if (adModel.getCategory().equals("Rice")) {
                        if (listRice.size() < 3) {
                            if (adModel.getStatus().equals("Approved")) {
                                listRice.add(adModel);
                            }
                        }
                        setRiceAdapter();
                    } else if (adModel.getCategory().equals("Wheat")) {
                        if (listWheat.size() < 3) {
                            if (adModel.getStatus().equals("Approved")) {
                                listWheat.add(adModel);
                            }
                        }
                        setWheatAdapter();
                    }
                }
                hideLoading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child("Favorite")
                .child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AdModel adModel = dataSnapshot.getValue(AdModel.class);
                    assert adModel != null;
                    listFavorite.add(adModel.getAdId());
                }
                setRiceAdapter();
                setWheatAdapter();
                hideLoading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setRiceAdapter() {
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), listRice, this);
        binding.rvRice.setAdapter(homeAdapter);
    }

    private void setWheatAdapter() {
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), listWheat, this);
        binding.rvWheat.setAdapter(homeAdapter);
    }

    private void setCategoryAdapter() {
        CategoryHomeAdapter categoryAdapter = new CategoryHomeAdapter(getContext(), listCategory, this);
        binding.rvCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void itemClick(AdModel adModel) {
//        Intent intent = new Intent(getContext() , ViewSingleAdActivity.class);
//        Gson gson = new Gson();
//        String myJson = gson.toJson(adModel);
//        intent.putExtra("AdModel", myJson);
//        startActivity(intent);
        showAlertBox(adModel);
    }

    @Override
    public void itemClick(CategoryModel categoryModel) {
        Fragment seeAllAdsFragment = new SeeAllAdsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", categoryModel.getName());
        seeAllAdsFragment.setArguments(bundle);
        replaceFragmentMethod(seeAllAdsFragment, SeeAllAdsFragment.class.getSimpleName(), true, false, getActivity());
    }

    void showAlertBox(AdModel adModel) {
        dialog = new Dialog(getContext() );
        dialog.setContentView(R.layout.ad_detail_dialogbox);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tvName = dialog.findViewById(R.id.tv_product_name_db);
        TextView tvQuantity = dialog.findViewById(R.id.tv_product_quantity_db);
        TextView tvPrice = dialog.findViewById(R.id.tv_product_price_db);
        TextView tvLocation = dialog.findViewById(R.id.tv_location_db);

        ImageView ivFavorite = dialog.findViewById(R.id.iv_favorite_db);
        ImageView ivCall = dialog.findViewById(R.id.iv_call_db);
        ImageView ivProductImage = dialog.findViewById(R.id.iv_product_image_db);

        if (listFavorite.contains(adModel.getAdId())){
            Glide.with(getContext()).load(R.drawable.heart).into(ivFavorite);
        }
        tvName.setText(adModel.getTitle());
        tvQuantity.setText(adModel.getQuantity());
        tvPrice.setText(adModel.getPrice());
        tvLocation.setText(adModel.getAddress());
        Glide.with(getContext()).load(adModel.getImageUrl()).into(ivProductImage);

        ivFavorite.setOnClickListener(v->{
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Favorite");

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (!listFavorite.contains(adModel.getAdId())) {
                listFavorite.add(adModel.getAdId());
                reference.child(userId).child(adModel.getAdId()).setValue(adModel);
                Glide.with(getContext()).load(R.drawable.heart).into(ivFavorite);
            }else {
                showToast("This ad is already favourite");
            }
        });

        ivCall.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+adModel.getNumber() ));
            startActivity(intent);
        });

        dialog.show();
    }
}