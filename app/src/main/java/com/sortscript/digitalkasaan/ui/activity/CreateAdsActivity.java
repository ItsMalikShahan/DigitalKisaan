package com.sortscript.digitalkasaan.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sortscript.digitalkasaan.databinding.ActivityCreateAdsBinding;
import com.sortscript.digitalkasaan.databinding.ActivitySettingBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.AdModel;
import com.sortscript.digitalkasaan.datamodel.model.fan.CategoryModel;
import com.sortscript.digitalkasaan.datamodel.model.fan.SessionManager;
import com.sortscript.digitalkasaan.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.UUID;

public class CreateAdsActivity extends BaseActivity {

    private ActivityCreateAdsBinding binding;
    String[] category = {"Select an Category", "Rice", "Wheat", "Cotton", "Vegetables", "Fruit", "Corn", "Sugar Cane", "Other"};
    String unit = "Select an Category";
    int SELECT_PICTURE = 200;
    Uri resultUri = null;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        sessionManager = new SessionManager(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategory.setAdapter(ad);

        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.ivProductImage.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });

        binding.btnCreateAd.setOnClickListener(v -> {
            String adTitle = binding.etAdTitle.getText().toString();
            String adQuantity = binding.etAdQuantity.getText().toString();
            String adPrice = binding.etAdPrice.getText().toString();
            String adNumber = binding.etAdNumber.getText().toString();
            String adAddress = binding.etAdAddress.getText().toString();
            if (resultUri == null) {
                showToast("Please Select Image");
            } else if (adTitle.equals("")) {
                binding.tfAdTitle.setError("Please Enter Title of Ad");
            } else if (adQuantity.equals("")) {
                binding.tfAdTitle.setError("");
                binding.tfAdQuantity.setError("Please Enter Title of Ad");
            } else if (adPrice.equals("")) {
                binding.tfAdQuantity.setError("");
                binding.tfAdPrice.setError("Please Enter Title of Ad");
            } else if (adNumber.equals("")) {
                binding.tfAdPrice.setError("");
                binding.tfAdNumber.setError("Please Enter Title of Ad");
            } else if (adAddress.equals("")) {
                binding.tfAdNumber.setError("");
                binding.tfAdAddress.setError("Please Enter Title of Ad");
            }else if (unit.equals("Select an Category")) {
                binding.tfAdAddress.setError("");
                showToast("Please Select Category");
            } else {
                showLoading();
                createAd(adTitle, adQuantity, adPrice, adNumber, adAddress);
            }
        });
    }

    private void createAd(String adTitle, String adQuantity, String adPrice, String adNumber, String adAddress) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Ads");
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String adId = reference.push().getKey();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Ads Images/" + UUID.randomUUID().toString());
        storageReference.putFile(resultUri)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> a = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    a.addOnSuccessListener(uri -> {
                        AdModel adModel = new AdModel(adId, uri.toString(),
                                adTitle, adQuantity, adPrice, adNumber, adAddress, unit, "Pending",
                                sessionManager.getobject().getUserName(), userID
                        );
                        assert adId != null;
                        reference.child(adId).setValue(adModel).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                hideLoading();
                                showToast("Ad Created");
                                finish();
                            } else {
                                hideLoading();
                                showToast("Failed");
                            }
                        });
                    });
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                resultUri = data.getData();
                if (null != resultUri) {
                    binding.ivProductImage.setImageURI(resultUri);
                }
            }
        }
    }

}