package com.xinze.haoke.module.invite.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.invite.model.OwnerDriverVO;
import com.xinze.haoke.module.invite.view.IMyInviteView;
import com.xinze.haoke.module.invite.view.MyInviteActivity;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;

public class MyInvitePresenterImp extends BasePresenterImpl<IMyInviteView> implements IMyInvitePresenter {

    private MyInviteActivity mMyInviteActivity;

    public MyInvitePresenterImp(IMyInviteView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mMyInviteActivity = (MyInviteActivity) mPresenterView;
    }

    @Override
    public void myCooperatedDrivers(int pageNum, int pageSize, String inviteFlag) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getMyInviteDriver(headers,pageNum, pageSize, inviteFlag).compose(this.<BaseEntity<List<OwnerDriverVO>>>setThread()).subscribe(new BaseObserver<List<OwnerDriverVO>>(mContext) {
            @Override
            protected void onSuccess(BaseEntity<List<OwnerDriverVO>> t) throws Exception {
                if (t != null) {
                    if (t.isSuccess()) {
                        mMyInviteActivity.setData(t.getData());
                    } else {
                        mMyInviteActivity.myCooperatedDriversFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mMyInviteActivity.myCooperatedDriversFailed(msg);
            }
        });
    }
    @SuppressWarnings("unchecked")
    @Override
    public void inviteDrivers(String mobile) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().inviteDriver(headers,mobile).
                compose(this.<BaseEntity>setThread()).subscribe(new BaseObserver(mContext) {
            @Override
            protected void onSuccess(BaseEntity t) throws Exception {
                if (t != null) {
                    if (t.isSuccess()) {
                        mMyInviteActivity.myCooperatedDriversSuccess(t.getMsg());
                    } else {
                        mMyInviteActivity.myCooperatedDriversFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mMyInviteActivity.myCooperatedDriversFailed(msg);
            }
        });
    }
}
