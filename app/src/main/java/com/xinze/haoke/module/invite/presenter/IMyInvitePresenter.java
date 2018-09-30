package com.xinze.haoke.module.invite.presenter;

/**
 * Created by feibai on 2018/5/14.
 * desc:
 */

interface IMyInvitePresenter {
    /**
     * 获取我的车主邀请信息
     *  @author feibai
     *  @time 2018/5/14  21:51
     *  @desc
     *  @param pageNum 页码
     *  @param pageSize 页数
     *  @param inviteFlag 查询条件 0拒绝1成功2邀请中
     */
    void myCooperatedDrivers(int pageNum, int pageSize,String inviteFlag);

    /**
     * 邀请司机
     * @param mobile 手机号
     */
    void inviteDrivers(String mobile);


}
