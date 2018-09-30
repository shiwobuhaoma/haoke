package com.xinze.haoke.module.add.presenter;

import com.xinze.haoke.module.add.view.IAddMyCarView;
import com.xinze.haoke.mvpbase.BasePresenter;

import okhttp3.MultipartBody;

public interface IAddMyCarPresenter extends BasePresenter<IAddMyCarView> {
    void addTruck(String truckName,String truckCode,String weight,String vehicleLicenseImg);

    void imageUpload(MultipartBody.Part img);
}
