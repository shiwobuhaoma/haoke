package com.xinze.haoke.module.main.presenter;

import com.xinze.haoke.module.main.view.IOrderView;
import com.xinze.haoke.mvpbase.BasePresenter;

/**
 * @author lxf
 *
 */
public interface IOrderPresenter extends BasePresenter<IOrderView>{
    void getOderList(int pageNo,int pageSize,String remark);
}
