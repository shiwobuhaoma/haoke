package com.xinze.haoke.module.change.presenter;

import com.xinze.haoke.module.change.view.IChangePassWordView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IChangePassWordPresenter extends BasePresenter<IChangePassWordView> {
    void changePassWord(String oldPassWord,String newPassWord);
}
