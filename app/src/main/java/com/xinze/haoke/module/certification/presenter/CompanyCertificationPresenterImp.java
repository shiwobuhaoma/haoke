package com.xinze.haoke.module.certification.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.certification.modle.CertificationRespones;
import com.xinze.haoke.module.certification.view.CompanyCertificationActivity;
import com.xinze.haoke.module.certification.view.ICertificationView;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.qqtheme.framework.entity.Province;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class CompanyCertificationPresenterImp extends BasePresenterImpl<ICertificationView> implements ICertificationPresenter {
    private CompanyCertificationActivity mCertificationActivity;
    public CompanyCertificationPresenterImp(ICertificationView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mCertificationActivity = (CompanyCertificationActivity)mPresenterView;

    }

    @Override
    public void uploadImages(List<MultipartBody.Part> partList) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().imagesUpload(headers,partList).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<CertificationRespones>>(mContext){

                    @Override
                    protected void onSuccees(BaseEntity<List<CertificationRespones>> t) throws Exception {
                        if (t != null){
                            if (t.isSuccess()){
                                List<CertificationRespones> data = t.getData();
                                mCertificationActivity.setData(data);
                                mCertificationActivity.uploadImagesSuccess(t.getMsg());
                            }else{
                                  mCertificationActivity.uploadImagesSuccess(t.getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(String msg) throws Exception {
                        mCertificationActivity.uploadImagesFailed(msg);
                    }
                });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void certifitcation(String ownerType,
                               String companyName,
                               String businessLicense,
                               String name,
                               String idCard,
                               String areaId,
                               String detailAddress,
                               String legalIdcardImg,
                               String legalHandIdcardImg,
                               String businessLicenceImg) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().companyCertification(headers,
                ownerType,
                companyName,
                businessLicense,
                name,
                idCard,
                areaId,
                detailAddress,
                legalIdcardImg,
                legalHandIdcardImg,
                businessLicenceImg)
                .compose(this.<BaseEntity>setThread()).subscribe(new BaseObserver(mContext){

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        mCertificationActivity.certificationSuccess(t.getMsg());
                    }else{
                        mCertificationActivity.certificationFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mCertificationActivity.certificationFailed(msg);
            }
        });
    }


    @Override
    public void getAreaList(String extId) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getAreaList(headers,extId)
                .compose(this.<BaseEntity<List<Province>>>setThread()).subscribe(new BaseObserver<List<Province>>(){

            @Override
            protected void onSuccees(BaseEntity<List<Province>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        ArrayList<Province> data = (ArrayList<Province>) t.getData();
                        if (data != null){
                            mCertificationActivity.setAreaList(data);
                        }
                        mCertificationActivity.getAreaListSuccess(t.getMsg());
                    }else {
                        mCertificationActivity.getAreaListFailed(t.getMsg());
                    }
                }

            }

            @Override
            protected void onFailure(String msg) throws Exception {

            }
        });
    }
}
