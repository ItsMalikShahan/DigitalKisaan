package com.sortscript.digitalkasaan.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sortscript.digitalkasaan.R;
import com.sortscript.digitalkasaan.databinding.FragmentCategoryBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.CategoryModel;
import com.sortscript.digitalkasaan.ui.adapter.CategoryAdapter;
import com.sortscript.digitalkasaan.ui.base.BaseFragment;

import java.util.ArrayList;

public class CategoryFragment extends BaseFragment implements CategoryAdapter.ItemClickListener {

    private FragmentCategoryBinding binding;
    ArrayList<CategoryModel> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.add(new CategoryModel("Rice", getResources().getDrawable(R.drawable.rice)));
        list.add(new CategoryModel("Wheat", getResources().getDrawable(R.drawable.wheat)));
        list.add(new CategoryModel("Cotton", getResources().getDrawable(R.drawable.cotton)));
        list.add(new CategoryModel("Vegetables", getResources().getDrawable(R.drawable.vegetables)));
        list.add(new CategoryModel("Fruit", getResources().getDrawable(R.drawable.fruit)));
        list.add(new CategoryModel("Corn", getResources().getDrawable(R.drawable.corn)));
        list.add(new CategoryModel("Sugar Cane", getResources().getDrawable(R.drawable.sugar_cane)));
        list.add(new CategoryModel("Other", getResources().getDrawable(R.drawable.others)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();
    }

    private void setAdapter() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), list, this);
        binding.rvCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void itemClick(CategoryModel categoryModel) {
        Fragment seeAllAdsFragment = new SeeAllAdsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", categoryModel.getName());
        seeAllAdsFragment.setArguments(bundle);
        replaceFragmentMethod(seeAllAdsFragment, SeeAllAdsFragment.class.getSimpleName(), true, false, getActivity());
    }
}