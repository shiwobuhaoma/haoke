package com.xinze.haoke.module.main.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IMainView extends BaseView {
    void checkUpdateSuccess();

    void checkUpdateFailed();

    void showForceDialogUpdate(String des, String downloadUrl);

    void showCommonDialogUpdate(String des, String downloadUrl);

    void onStartDownload();


    void startInstallPermissionSettingActivity();

}
