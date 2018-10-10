package com.xinze.haoke.module.select.driver.view;

import com.xinze.haoke.mvpbase.BaseView;
/**
 * @author lxf
 */
public interface ISelectDriverView extends BaseView {

    void releaseTheBillOfGoodsSuccess(String msg);

    void releaseTheBillOfGoodsFailed(String msg);


    void getAlwaysDriverSuccess(String msg);

    void getAlwaysDriverFailed(String msg);
}
