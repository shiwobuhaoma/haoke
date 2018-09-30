package com.xinze.haoke.module.certification.presenter;

import com.xinze.haoke.module.certification.view.ICertificationView;
import com.xinze.haoke.mvpbase.BasePresenter;

import java.util.List;

import okhttp3.MultipartBody;

public interface ICertificationPresenter extends BasePresenter<ICertificationView> {
    void uploadImages(List<MultipartBody.Part> partList);

    void certifitcation(String ownerType,
                        String companyName,
                        String businessLicense,
                        String name,
                        String idCard,
                        String areaId,
                        String detailAddress,
                        String idCardImg,
                        String drivingImg,
                        String businessLicenceImg);

    void getAreaList(String extId);
}
