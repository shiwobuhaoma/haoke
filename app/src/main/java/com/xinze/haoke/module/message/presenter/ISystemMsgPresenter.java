package com.xinze.haoke.module.message.presenter;

import com.xinze.haoke.module.message.view.ISystemMsgView;
import com.xinze.haoke.module.message.adapter.SystemMessageAdapter;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface ISystemMsgPresenter extends BasePresenter<ISystemMsgView>{
    void getSystemMsgList(int pageNo, int pageSize);
    void markReaded(String msgId, SystemMessageAdapter.ViewHolder holder);
}
