package com.xinze.haoke.module.certification.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface ICertificationView extends BaseView {

    void uploadImagesSuccess(String msg);

    void uploadImagesFailed(String msg);

    void certificationSuccess(String msg);

    void certificationFailed(String msg);

    void getAreaListSuccess(String msg);

    void getAreaListFailed(String msg);

}
