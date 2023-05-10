package com.sortscript.digitalkasaan.datamodel.source;

import com.sortscript.digitalkasaan.datamodel.model.fan.BaseModel;

public interface UserDatasource {

    interface  RegisterCallBack{
        public void onRegistered(BaseModel baseModel);
        public void onError(String mes);
    }
}
