package com.xinze.haoke.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinze.haoke.R;

public class FromDetailsInfoView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private TextView title;

    private TextView ordinaryFrom;
    private EditText from;
    private EditText detailsAddress;
    private EditText contact;
    private EditText phone;



    public FromDetailsInfoView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public FromDetailsInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public FromDetailsInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void setTitle(String mTitle) {
        title.setText(mTitle);
    }

    public void setOrdinaryFrom(TextView ordinaryFrom) {
        this.ordinaryFrom = ordinaryFrom;
    }

    private void init() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.from_details_info_view, this);
        title = mView.findViewById(R.id.title_detail_info);
        ordinaryFrom = mView.findViewById(R.id.ordinary_from);
        from = mView.findViewById(R.id.ordinary_from_et);
        detailsAddress = mView.findViewById(R.id.ordinary_from_address_et);
        contact = mView.findViewById(R.id.ordinary_from_contact_et);
        phone = mView.findViewById(R.id.ordinary_from_phone_et);

        from.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mShowAddressDialog != null) {
            mShowAddressDialog.showAddressDialog();
        }
    }


    public String getFromText() {
        return from.getText().toString();
    }

    public String getDetailsAddress() {
        return detailsAddress.getText().toString();
    }

    public String getContact() {
        return contact.getText().toString();
    }


    public String getPhone() {
        return phone.getText().toString();
    }

    public void setFrom(String mFrom) {
        from.setText(mFrom);
    }

    public void setDetailsAddress(String mDetailsAddress) {
        detailsAddress.setText(mDetailsAddress);
    }

    public void setContact(String mContact) {
        contact.setText(mContact);
    }

    public void setPhone(String mPhone) {
        phone.setText(mPhone);
    }



    public interface ShowAddressDialog {
        void showAddressDialog();
    }

    public interface JumpSelectDriver{
        void jumpSelectDriverPage();
    }

    public void setShowAddressDialog(ShowAddressDialog mShowAddressDialog) {
        this.mShowAddressDialog = mShowAddressDialog;
    }
    public void setJumpSelectDriver(JumpSelectDriver jumpSelectDriver) {
        this.mJumpSelectDriver = jumpSelectDriver;
    }

    public ShowAddressDialog mShowAddressDialog;
    public JumpSelectDriver mJumpSelectDriver;

}
