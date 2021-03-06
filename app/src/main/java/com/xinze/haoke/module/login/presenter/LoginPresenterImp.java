package com.xinze.haoke.module.login.presenter;


import android.content.Context;

import com.xinze.haoke.App;
import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.login.modle.LoginResponse;
import com.xinze.haoke.module.login.modle.UserEntity;
import com.xinze.haoke.module.login.view.ILoginView;
import com.xinze.haoke.module.login.view.LoginActivity;
import com.xinze.haoke.mvpbase.BasePresenterImpl;
import com.xinze.haoke.utils.ACache;
import com.xinze.haoke.utils.Base64Util;

/**
 * @author lxf
 * 登陆主持人的实现类
 * Created by Administrator on 2018/1/4.
 */

public class LoginPresenterImp extends BasePresenterImpl<ILoginView> implements ILoginPresenter {
    private LoginActivity iLoginView;

    public LoginPresenterImp(ILoginView iLoginView, Context mContext) {
        super(iLoginView,mContext);
        this.iLoginView = (LoginActivity) iLoginView;
    }

    @Override
    public void login(String name, String pwd,String userType) {
        RetrofitFactory.getInstence().Api().login(
                name,
                Base64Util.getBase64(pwd),
                userType)
                .compose(this.<BaseEntity<LoginResponse>>setThread())
                .subscribe(new BaseObserver<LoginResponse>(mContext) {
                    @Override
                    protected void onSuccess(BaseEntity<LoginResponse> t)  {
                        if (t != null){
                            LoginResponse data = t.getData();
                            if (data != null){
                                String sessionId = data.getSessionid();
                                App.mUser.setSessionid(sessionId);
                                App.mUser.setPhoto(data.getPhoto());
                                App.mUser.setId(data.getId());
                                App.mUser.setLogin(true);
                                App.mUser.setVertifyFlag(data.getVertifyFlag());
                                App.mUser.setVertifyDescription(data.getVertifyDescription());
                                App.mUser.setLogin_name(data.getLoginName());
                                ACache.get(mContext).put("user",App.mUser);
                                iLoginView.loginSuccess(t.getMsg());
                            }else {
                                iLoginView.loginFailed(t.getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(String msg){
                        iLoginView.shotToast(msg);

                    }
                });
    }


}
