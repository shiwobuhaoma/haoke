package com.xinze.haoke.module.preview;

import android.view.View;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.http.config.HttpConfig;
import com.xinze.haoke.widget.SimpleToolbar;

import butterknife.BindView;

/**
 * 图片预览
 *
 * @author lxf
 */
public class PhotoPreviewActivity extends BaseActivity {
    @BindView(R.id.preview_tool_bar)
    SimpleToolbar previewToolBar;
    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    protected int initLayout() {
        return R.layout.photo_preview_activity;
    }

    @Override
    protected void initView() {
        String path = getIntent().getStringExtra("path");
        previewToolBar.setLeftTitleVisible();
        previewToolBar.setMainTitle("图片预览");
        previewToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(PhotoPreviewActivity.this)
                .load(HttpConfig.IMAGE_BASE_URL.substring(0,HttpConfig.IMAGE_BASE_URL.length()-1) + path)
                .into(photoView);
    }
}
