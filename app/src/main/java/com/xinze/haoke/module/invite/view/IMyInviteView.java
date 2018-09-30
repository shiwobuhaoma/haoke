package com.xinze.haoke.module.invite.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IMyInviteView extends BaseView {

    /**
     * 获取我的常用司机成功
     */
   void myCooperatedDriversSuccess(String msg);
   void myCooperatedDriversFailed(String msg);
}
