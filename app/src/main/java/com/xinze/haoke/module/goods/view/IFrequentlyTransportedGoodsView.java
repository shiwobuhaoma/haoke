package com.xinze.haoke.module.goods.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IFrequentlyTransportedGoodsView extends BaseView {
   /**
    * 获取常拉货品接口
    * @param msg
    */
  void myUsualCargoSuccess(String msg);
  void myUsualCargoFailed(String msg);
  void myUsualCargoAddSuccess(String msg);
  void myUsualCargoAddFailed(String msg);
  void myUsualCargoDelSuccess(String msg);
  void myUsualCargoDelFailed(String msg);
}
