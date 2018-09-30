package com.xinze.haoke.module.about.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IAboutUsView extends BaseView {
   void getAboutUsSuccess(String msg);
   void getAboutUsFailed(String msg);
   void getProtocolByTypeSuccess(String msg);
   void getProtocolByTypeFailed(String msg);
}
