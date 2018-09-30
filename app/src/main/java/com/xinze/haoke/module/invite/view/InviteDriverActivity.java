package com.xinze.haoke.module.invite.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.module.invite.presenter.InviteDriverPresenterImp;
import com.xinze.haoke.utils.PhoneUtil;
import com.xinze.haoke.widget.SimpleToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 邀请司机
 *
 * @author lxf
 */
public class InviteDriverActivity extends BaseActivity implements IInviteDriverView {
    @BindView(R.id.invite_driver_tool_bar)
    SimpleToolbar inviteDriverToolBar;
    @BindView(R.id.invite_phone_number)
    EditText invitePhoneNumber;
    @BindView(R.id.invite_driver_bt)
    Button inviteDriverBt;
    private InviteDriverPresenterImp mPresenter;

    @Override
    protected int initLayout() {
        return R.layout.invite_driver_activity;
    }

    @Override
    protected void initView() {
        inviteDriverToolBar.setMainTitle("邀请司机");
        inviteDriverToolBar.setLeftClickListener(new SimpleToolbar.TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        inviteDriverToolBar.setTitleMarginTop();
        inviteDriverToolBar.setLeftTitleVisible();
    }

    @OnClick(R.id.invite_driver_bt)
    public void onClick() {
        String phone = invitePhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            shotToast("请输入手机号");
            return;
        }
        if (phone.length() != 11) {
            shotToast("请输入正确的手机号");
            return;
        }
//        if (PhoneUtil.isMobile(phone)){
//            shotToast("手机格式不正确");
//            return;
//        }
        mPresenter = new InviteDriverPresenterImp(this, this);
        mPresenter.inviteDriver(phone);
    }

    @Override
    public void inviteDriversSuccess(String msg) {
        shotToast(msg);
        finish();
    }

    @Override
    public void inviteDriversFailed(String msg) {
        shotToast(msg);
    }

}
