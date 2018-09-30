package com.xinze.haoke.module.invite.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IInviteDriverView extends BaseView {

    /**
     * 获取我的常用司机成功
     */
   void inviteDriversSuccess(String msg);
   void inviteDriversFailed(String msg);
}
