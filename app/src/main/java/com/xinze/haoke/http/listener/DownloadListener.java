package com.xinze.haoke.http.listener;

import java.io.File;

public interface DownloadListener {


    void onProgress(int progress);

    void onFinishDownload(File file);

    void onFail(Throwable ex);

}
