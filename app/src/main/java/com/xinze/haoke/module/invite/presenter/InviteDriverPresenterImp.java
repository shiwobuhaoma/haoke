package com.xinze.haoke.module.invite.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.invite.view.IInviteDriverView;
import com.xinze.haoke.module.invite.view.InviteDriverActivity;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;

public class InviteDriverPresenterImp extends BasePresenterImpl<IInviteDriverView> implements IInviteDriverPresenter {

    private InviteDriverActivity activity;
    public InviteDriverPresenterImp(IInviteDriverView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        activity = (InviteDriverActivity) mPresenterView;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void inviteDriver(String phone) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().inviteDriver(headers,phone).compose(this.<BaseEntity>setThread()).subscribe(new BaseObserver(mContext) {
            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                if (t != null) {
                    if (t.isSuccess()) {
                        activity.inviteDriversSuccess(t.getMsg());
                    } else {
                        activity.inviteDriversFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                activity.inviteDriversFailed(msg);
            }
        });
    }
}
