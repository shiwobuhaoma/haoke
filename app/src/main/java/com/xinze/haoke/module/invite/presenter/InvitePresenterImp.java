package com.xinze.haoke.module.invite.presenter;

import com.xinze.haoke.App;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.module.invite.fragment.DriverInviteFragment;
import com.xinze.haoke.module.invite.fragment.OwnerInviteFragment;
import com.xinze.haoke.module.invite.model.OwnerDriverVO;
import com.xinze.haoke.module.invite.model.TruckownerDriverVO;
import com.xinze.haoke.module.invite.view.InviteDetailActivity;
import com.xinze.haoke.utils.ReturnResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by feibai on 2018/5/14.
 * desc:
 */

public class InvitePresenterImp implements IInvitePresenter {
    private DriverInviteFragment driverInviteFragment;
    private OwnerInviteFragment ownerInviteFragment;
    private BaseActivity mActivity;

    public InvitePresenterImp(DriverInviteFragment driverInviteFragment) {
        this.driverInviteFragment = driverInviteFragment;
    }

    public InvitePresenterImp(InviteDetailActivity inviteDetailActivity) {
        this.mActivity =inviteDetailActivity;
    }

    public InvitePresenterImp(OwnerInviteFragment ownerInviteFragment) {
        this.ownerInviteFragment = ownerInviteFragment;
    }

    @Override
    public void getMyTruckOwnerInvitation(int pageNum, int pageSize, String inviteFlag) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getTruckOwnerInvitation(headers, pageNum, pageSize, inviteFlag).enqueue(new Callback<ReturnResult<List<TruckownerDriverVO>>>() {

            @Override
            public void onResponse(Call<ReturnResult<List<TruckownerDriverVO>>> call, Response<ReturnResult<List<TruckownerDriverVO>>> response) {
                // 请求成功
                ReturnResult returnResult = response.body();
                if (returnResult.getStatus()!=(AppConfig.REQUEST_STATUS_SUCESS)) {
                    driverInviteFragment.shotToast(returnResult.getMsg() == null ? AppConfig.COMMON_FAILURE_RESPONSE : returnResult.getMsg());
                    return;
                }
                final Object obj = returnResult.getData();
                if (obj!=null) {
                    List<TruckownerDriverVO> data = (List<TruckownerDriverVO>) returnResult.getData();
                    if (data!=null&&data.size()==AppConfig.PAGE_SIZE) {
                        driverInviteFragment.setPageEndFlag(false);
                        driverInviteFragment.setData(data);
                        driverInviteFragment.getInitDataSuccess();
                    }else if (data!=null&&data.size()<AppConfig.PAGE_SIZE) {
                        // 获取到最后一页
                        driverInviteFragment.setPageEndFlag(true);
                        driverInviteFragment.setData(data);
                        driverInviteFragment.getInitDataSuccess();
                    }else{
                        driverInviteFragment.setPageEndFlag(true);
                    }
                }else{
                    // 响应数据为空
                    driverInviteFragment.setPageEndFlag(true);
                    driverInviteFragment.getInitDataSuccess();
                }
            }

            @Override
            public void onFailure(Call<ReturnResult<List<TruckownerDriverVO>>> call, Throwable t) {
                // 请求失败
                driverInviteFragment.shotToast(AppConfig.COMMON_FAILURE_RESPONSE);
            }
        });
    }

    @Override
    public void getMyOwnerInvitation(int pageNum, int pageSize, String inviteFlag) {
        Map<String, String> headers = new HashMap<>(2);
        headers.put("sessionid", App.mUser.getSessionid());
        headers.put("userid", App.mUser.getId());
        RetrofitFactory.getInstence().Api().getOwnerInvitation(headers, pageNum, pageSize, inviteFlag).enqueue(new Callback<ReturnResult<List<OwnerDriverVO>>>() {

            @Override
            public void onResponse(Call<ReturnResult<List<OwnerDriverVO>>> call, Response<ReturnResult<List<OwnerDriverVO>>> response) {
                // 请求成功
                ReturnResult returnResult = response.body();
                if (returnResult.getStatus()!=(AppConfig.REQUEST_STATUS_SUCESS)) {
                    ownerInviteFragment.shotToast(returnResult.getMsg() == null ? AppConfig.COMMON_FAILURE_RESPONSE : returnResult.getMsg());
                    return;
                }
                final Object obj = returnResult.getData();
                if (obj!=null) {
                    List<OwnerDriverVO> data = (List<OwnerDriverVO>) returnResult.getData();
                    if (data!=null&&data.size()==AppConfig.PAGE_SIZE) {
                        ownerInviteFragment.setPageEndFlag(false);
                        ownerInviteFragment.setData(data);
                        ownerInviteFragment.getInitDataSuccess();
                    }else if (data!=null&&data.size()<AppConfig.PAGE_SIZE) {
                        // 获取到最后一页
                        ownerInviteFragment.setPageEndFlag(true);
                        ownerInviteFragment.setData(data);
                        ownerInviteFragment.getInitDataSuccess();
                    }else{
                        ownerInviteFragment.setPageEndFlag(true);
                    }
                }else{
                    // 响应数据为空
                    ownerInviteFragment.setPageEndFlag(true);
                    ownerInviteFragment.getInitDataSuccess();
                }
            }

            @Override
            public void onFailure(Call<ReturnResult<List<OwnerDriverVO>>> call, Throwable t) {
                // 请求失败
                ownerInviteFragment.shotToast(AppConfig.COMMON_FAILURE_RESPONSE);
            }
        });

    }

    @Override
    public void responseInvitation(String itemId, String inviteFlag, String inviteResponseType, String content) {
        Map<String, String> headers = new HashMap<>(2);
        headers.put("sessionid", App.mUser.getSessionid());
        headers.put("userid", App.mUser.getId());
        RetrofitFactory.getInstence().Api().responseInvitation(headers,itemId,inviteResponseType,inviteFlag,content).enqueue(new Callback<ReturnResult>() {
            @Override
            public void onResponse(Call<ReturnResult> call, Response<ReturnResult> response) {
                // 请求成功
                ReturnResult returnResult = response.body();
                if (returnResult.getStatus()!=(AppConfig.REQUEST_STATUS_SUCESS)) {
                    mActivity.shotToast(returnResult.getMsg() == null ? AppConfig.COMMON_FAILURE_RESPONSE : returnResult.getMsg());

                }
            }

            @Override
            public void onFailure(Call<ReturnResult> call, Throwable t) {
                mActivity.shotToast(AppConfig.COMMON_FAILURE_RESPONSE);
            }
        });

    }
}
