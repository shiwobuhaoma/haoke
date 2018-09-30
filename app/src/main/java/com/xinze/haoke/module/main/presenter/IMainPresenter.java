package com.xinze.haoke.module.main.presenter;

import com.xinze.haoke.http.listener.DownloadListener;
import com.xinze.haoke.module.main.view.IMainView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IMainPresenter extends BasePresenter<IMainView> {
    void checkUpdate(String appType, String fileType);

    void downloadApk(String downloadUrl, String destDir, String fileName, DownloadListener listener);
}
