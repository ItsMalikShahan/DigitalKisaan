package com.sortscript.digitalkasaan.datamodel.source;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.sortscript.digitalkasaan.datamodel.model.fan.BaseModel;

public class DataRepository {

    Context context;

    public DataRepository(Context context) {
        this.context = context;
    }

    FirebaseAuth firebaseAuth= null;
    DatabaseReference reference;

    public void registerUser(BaseModel baseModel , UserDatasource.RegisterCallBack  callBack){

    }

}
