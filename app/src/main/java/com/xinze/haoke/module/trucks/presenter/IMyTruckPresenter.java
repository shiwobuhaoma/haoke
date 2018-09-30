package com.xinze.haoke.module.trucks.presenter;

import com.xinze.haoke.module.trucks.view.IMyTruckView;
import com.xinze.haoke.mvpbase.BasePresenter;

/**
 *
 * @author feibai
 * @date 2018/5/14
 * desc:IMyTruckPresenter
 */
public interface IMyTruckPresenter extends BasePresenter<IMyTruckView>{
    /**
     * 获取我的车辆信息列表
     *
     * @param pageNum    页码
     * @param pageSize   页数
     * @param verifyFlag 查询条件 0审核失败1审核成功2审核中
     * @author feibai
     * @time 2018/5/14  21:51
     * @desc
     */
    void myTrucks(int pageNum, int pageSize, String verifyFlag);

    /**
     * 删除我的车辆
     *
     * @param itemId id
     * @author feibai
     * @time 2018/5/19  18:26
     * @desc
     */
    void delMyTruck(String itemId);




}
