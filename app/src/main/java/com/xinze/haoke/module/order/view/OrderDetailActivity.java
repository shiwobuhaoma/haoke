package com.xinze.haoke.module.order.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.module.certification.modle.CertificationRespones;
import com.xinze.haoke.module.order.adapter.PostImgAdapter;
import com.xinze.haoke.module.order.modle.OrderDetail;
import com.xinze.haoke.module.order.presenter.OrderDetailPresenterImp;
import com.xinze.haoke.module.preview.PhotoPreviewActivity;
import com.xinze.haoke.utils.BitmapUtils;
import com.xinze.haoke.utils.MessageEvent;
import com.xinze.haoke.utils.UIUtils;
import com.xinze.haoke.widget.BottomPopupMenu;
import com.xinze.haoke.widget.ImageItemDecoration;
import com.xinze.haoke.widget.SimpleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author lxf
 * 订单详情界面
 */
public class OrderDetailActivity extends BaseActivity implements IOrderDetailView, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.find_goods_id)
    TextView findGoodsId;
    @BindView(R.id.find_goods_contacts)
    TextView findGoodsContacts;
    @BindView(R.id.goods_driver)
    TextView goodsDriver;
    @BindView(R.id.car_number)
    TextView carNumber;
    @BindView(R.id.car_type)
    TextView carType;
    @BindView(R.id.find_tool_bar)
    SimpleToolbar findToolBar;

    @BindView(R.id.order_upload_evidence)
    TextView orderUploadEvidence;
    @BindView(R.id.upload_evidence_list)
    RecyclerView uploadEvidenceList;
    @BindView(R.id.reject_order)
    Button rejectOrder;
    @BindView(R.id.agree_order)
    Button agreeOrder;
    private String driverMobile;
    private String orderId;
    private String truckOwnerMobile;
    /**
     * 已接单
     */
    public final String TAKE_ORDER = "0";
    /**
     * 取货中
     */
    public final String PICK_UP = "1";
    /**
     * 送货中
     */
    public final String DELIVER_GOODS = "2";
    /**
     * 已到货,已送达
     */
    public final String GOODS_ARRIVE = "3";
    /**
     * 已签收
     */
    public final String GOODS_SIGNED_IN = "4";
    /**
     * 已拒绝
     */
    public final String GOODS_REFUSE = "R";
    /**
     * 已撤单
     */
    public final String GOODS_REVOKE = "B";
    /**
     * 已过期
     */
    private final String GOODS_OVERDUE = "X";
    /**
     * 已确定
     */
    public final String GOODS_CONFIRM = "C";

    private boolean isRefresh;

    private BottomPopupMenu mBottomPopupMenu;
    private OrderDetailPresenterImp mPresenter;
    private String orderStatus;
    private String remarks;
    private int mImageRowsCount = 3;
    /**
     * 所要申请的权限
     */
    String[] mRequestPermissionList = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private List<String> mDeniedPermissionList = new ArrayList<>();
    /**
     * 拍照
     */
    public static final int PHOTO_REQUEST_CAREMA = 1;
    /**
     * 从相册选照片
     */
    public static final int CHOOSE_PHOTO = 2;
    private File photoFile;
    private String filePath;
    private List<File> files = new ArrayList<>();
    private ArrayList<String> filePaths = new ArrayList<>();
    private ArrayList<String> imgPaths = new ArrayList<>();
    private PostImgAdapter mAdapter;
    private String confirmFlag;


    @Override
    protected int initLayout() {
        return R.layout.order_list_activity;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        remarks = intent.getStringExtra("remarks");
        findGoodsContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + truckOwnerMobile)));
            }
        });

        goodsDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + driverMobile)));
            }
        });
        rejectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderStatus = GOODS_REVOKE;
                showBottomMenu("确定撤销订单?", GOODS_REVOKE);
            }
        });
        agreeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TAKE_ORDER.equals(orderStatus)) {
                    mPresenter.changeBillOrderStatus(orderId, GOODS_CONFIRM, "");
                } else {
                    showBottomMenu("签收后该订单结束，确认要签收？", GOODS_SIGNED_IN);
                }

            }
        });
        initTitleBar();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        uploadEvidenceList.setLayoutManager(layoutManager);
        uploadEvidenceList.addItemDecoration(new ImageItemDecoration((int) (10 * UIUtils.getDensity())));
        uploadEvidenceList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mAdapter = new PostImgAdapter(this);
        // 设置一行显示的图片数量，屏幕宽度超过600的，显示四张，否则显示三张
        if (UIUtils.getScreenWidth() >= 600) {
            mImageRowsCount = 4;
        }
        int imageWidth = (int) ((UIUtils.getScreenWidth() - 10 * UIUtils.getDensity() * (mImageRowsCount + 1))
                / mImageRowsCount);
        ViewGroup.LayoutParams layoutParams = uploadEvidenceList.getLayoutParams();
        layoutParams.height = imageWidth;
        mAdapter.setImageWidth(imageWidth);
        mAdapter.setCallback(new PostImgAdapter.Callback() {
            @Override
            public void onDeleteImage(int position) {
                mAdapter.removeItem(position);
            }

            @Override
            public void jumpShowImagePage(String path) {
                openActivity(PhotoPreviewActivity.class,"path",path);
            }

        });
        uploadEvidenceList.setAdapter(mAdapter);
        mAdapter.updateData(imgPaths);
    }

    private void checkPermissions() {
        //检查是否获取该权限
        if (EasyPermissions.hasPermissions(this, mRequestPermissionList)) {
            if (mBottomPopupMenu == null) {
                initBottomPopupMenu();
            } else {
                mBottomPopupMenu.showMenu();
            }

        } else {
            requestPermissions();
        }
    }

    private void initBottomPopupMenu() {
        mBottomPopupMenu = new BottomPopupMenu(this);
        mBottomPopupMenu.addItem(1, "拍照");
        mBottomPopupMenu.addItem(2, "相册");
        mBottomPopupMenu.setOnMenuClickListener(new BottomPopupMenu.MenuClickListener() {

            @Override
            public void onMenuItemClick(View itemView, int itemId) {
                switch (itemId) {
                    // 拍照
                    case 1:
                        openCamera();
                        break;
                    // 相册
                    case 2:
                        choosePhoto();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void showBottomMenu(String s, final String orderStatus) {
        BottomPopupMenu bottomPopupMenu = new BottomPopupMenu(OrderDetailActivity.this);
        bottomPopupMenu.addItem(1, s);
        bottomPopupMenu.addItem(2, "确定", R.color.themeOrange);
        bottomPopupMenu.showMenu();
        bottomPopupMenu.setOnMenuClickListener(new BottomPopupMenu.MenuClickListener() {

            @Override
            public void onMenuItemClick(View itemView, int itemId) {
                switch (itemId) {
                    case 1:
                        return;
                    // 确定撤销订单?/确定取货?/确定送货?/确定送达?
                    case 2:
                        if (mPresenter == null) {
                            mPresenter = new OrderDetailPresenterImp(OrderDetailActivity.this, OrderDetailActivity.this);
                        }
                        if (filePaths.size() == 0 && GOODS_REVOKE.equals(orderStatus)) {
                            mPresenter.revoke(orderId, imgPaths, remarks, orderStatus);
                        } else if (filePaths.size() == 0 && GOODS_SIGNED_IN.equals(orderStatus)) {
                            mPresenter.changeBillOrderStatus(orderId, GOODS_SIGNED_IN, remarks);
                        } else {
                            //filePath 图片地址集合
                            for (String path : filePaths) {
                                files.add(new File(path));
                            }
                            List<MultipartBody.Part> parts = new ArrayList<>(files.size());
                            for (int i = 0; i < files.size(); i++) {
                                File file = files.get(i);
                                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                MultipartBody.Part part = MultipartBody.Part.
                                        createFormData("file" + i, file.getName(), imageBody);
                                parts.add(part);
                            }
                            mPresenter.uploadImages(parts);
                        }


                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initTitleBar() {
        findToolBar.setMainTitle(R.string.order_detail);
        findToolBar.setLeftTitleDrawable(R.mipmap.ic_back);
        findToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRefresh) {
                    EventBus.getDefault().post(new MessageEvent(AppConfig.UPDATE_ORDER));
                }

                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new OrderDetailPresenterImp(this, this);
        mPresenter.getOrderDetail(orderId);
    }

    @Override
    public void getOrderDetailSuccess(String msg) {
        isRefresh = true;
    }

    @Override
    public void getOrderDetailFailed(String msg) {
        isRefresh = false;
        shotToast(msg);
    }

    @Override
    public void revokeSuccess(String message, String orderStatus) {
        shotToast(message);
        changeState(orderStatus);
        EventBus.getDefault().post(new MessageEvent(AppConfig.UPDATE_ORDER));
        finish();
    }

    @Override
    public void revokeFailed(String message) {
        shotToast(message);
    }

    @Override
    public void uploadImagesSuccess(String msg) {
        if (GOODS_REVOKE.equals(orderStatus)) {
            mPresenter.revoke(orderId, imgPaths, remarks, orderStatus);
        } else {
            mPresenter.changeBillOrderStatus(orderId, GOODS_SIGNED_IN, "");
        }

    }

    @Override
    public void uploadImagesFailed(String msg) {

    }

    public void setData(OrderDetail data) {
        //订单号
        String id = data.getId();
        //接单人姓名
        String truckOwnerName = data.getTruckownername();
        //联系人电话
        truckOwnerMobile = data.getTruckownermobile();
        //司机姓名
        String driverName = data.getDrivername();
        //司机电话
        driverMobile = data.getDrivermobile();
        //车牌号
        String truckNumber = data.getTrucknumber();
        //车型
        String truckName = data.getTruckName();
        //订单状态
        orderStatus = data.getOrderStatus();

        confirmFlag = data.getConfirmflag();
        String orderId = getString(R.string.order_id);
        findGoodsId.setText(String.format(orderId, id));

        //接单人姓名
        String orderContacts = getString(R.string.order_contacts);
        findGoodsContacts.setText(String.format(orderContacts, truckOwnerName));

        //司机姓名
        String driver = getString(R.string.driver);
        goodsDriver.setText(String.format(driver, driverName));

        //车牌号
        String carNumber1 = getString(R.string.carNumber);
        carNumber.setText(String.format(carNumber1, truckNumber));

        //车型
        String carType1 = getString(R.string.carType);
        carType.setText(String.format(carType1, truckName));

        ArrayList<String> files = data.getFiles();
        mAdapter.updateData(files);
        changeState(orderStatus);


    }

    @OnClick(R.id.agree_order)
    public void onClick() {
        switch (agreeOrder.getText().toString().trim()) {
            case "送货":
                orderStatus = DELIVER_GOODS;
                showBottomMenu("确认送货?", DELIVER_GOODS);
                break;
            case "确认送达":
                orderStatus = GOODS_ARRIVE;
                showBottomMenu("确认送达?", GOODS_ARRIVE);
                break;
            case "确认签收":
                orderStatus = GOODS_SIGNED_IN;
                showBottomMenu("确认签收?", GOODS_SIGNED_IN);
                break;
            default:
                orderStatus = PICK_UP;
                showBottomMenu("确认取货?", PICK_UP);
                break;
        }

    }

    private void changeState(String orderStatus) {
        if (TAKE_ORDER.equals(orderStatus) && "1".equals(confirmFlag)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_robbing);
            setDrawable(drawable);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.VISIBLE);
            rejectOrder.setVisibility(View.VISIBLE);
        }else if(TAKE_ORDER.equals(orderStatus) && "0".equals(confirmFlag)){
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_robbing);
            setDrawable(drawable);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.GONE);
            rejectOrder.setVisibility(View.GONE);
        } else if (PICK_UP.equals(orderStatus)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_picking);
            setDrawable(drawable);
            orderUploadEvidence.setVisibility(View.VISIBLE);
            uploadEvidenceList.setVisibility(View.VISIBLE);
            rejectOrder.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.GONE);
        } else if (DELIVER_GOODS.equals(orderStatus)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_deliver);
            setDrawable(drawable);
            rejectOrder.setVisibility(View.GONE);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.GONE);

        } else if (GOODS_ARRIVE.equals(orderStatus)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_arrived);
            setDrawable(drawable);
            agreeOrder.setText(getResources().getString(R.string.order_confirm_arrive));
            orderUploadEvidence.setVisibility(View.VISIBLE);
            orderUploadEvidence.setText(getResources().getString(R.string.upload_voucher));
            uploadEvidenceList.setVisibility(View.VISIBLE);
            agreeOrder.setVisibility(View.VISIBLE);
            rejectOrder.setVisibility(View.GONE);
        } else if (GOODS_SIGNED_IN.equals(orderStatus)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_signed);
            setDrawable(drawable);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
            rejectOrder.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.GONE);
        } else if (GOODS_REFUSE.equals(orderStatus)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_refuse);
            setDrawable(drawable);
            rejectOrder.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.GONE);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
        } else if (GOODS_CONFIRM.equals(orderStatus)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_picking);
            setDrawable(drawable);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
            rejectOrder.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.GONE);
        } else if (GOODS_REVOKE.equals(orderStatus)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_cancle);
            setDrawable(drawable);
            rejectOrder.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.GONE);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
        } else if (GOODS_OVERDUE.equals(orderStatus)) {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_picking);
            setDrawable(drawable);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.goods_detail_cancle);
            setDrawable(drawable);
            orderUploadEvidence.setVisibility(View.GONE);
            uploadEvidenceList.setVisibility(View.GONE);
            agreeOrder.setVisibility(View.GONE);
            rejectOrder.setVisibility(View.GONE);
        }
    }


    private void setDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        findGoodsId.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isRefresh && keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(new MessageEvent(AppConfig.UPDATE_ORDER));
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        for (String s : mRequestPermissionList) {
            if (ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED) {
                mDeniedPermissionList.add(s);
            } else {
                mDeniedPermissionList.clear();
            }
        }
        //未授予的权限为空，表示都授予了
        if (mDeniedPermissionList.isEmpty()) {
            // 用户已经同意该权限
            openCamera();
        } else {
            //将List转为数组
            String[] permissions = mDeniedPermissionList.toArray(new String[mDeniedPermissionList.size()]);
            //再次请求权限
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限", PHOTO_REQUEST_CAREMA, permissions);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
//         若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。这时候，需要跳转到设置界面去，让用户手动开启。

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .setTitle("温馨提示")
                    .setRationale("此功能需要拍照权限，否则无法正常使用，是否打开设置")
                    .setPositiveButton("是")
                    .setNegativeButton("否")
                    .build()
                    .show();
        } else {
            //将List转为数组
            String[] permissions = mDeniedPermissionList.toArray(new String[perms.size()]);
            //再次请求权限
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限", PHOTO_REQUEST_CAREMA, permissions);
        }
    }

    private void openCamera() {
        //獲取系統版本
        int currentApiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CHINESE);
            String filename = timeStampFormat.format(new Date());
            photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    filename + ".jpg");
            if (currentApiVersion < 24) {
                // 从文件中创建uri
                Uri imageUri = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {

                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    shotToast("请开启存储权限");
                    return;
                }

                //兼容android7.0 使用共享文件的形式
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUriFromCamera = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }


    /**
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, CHOOSE_PHOTO);

    }


    private void requestPermissions() {
        //第二个参数是被拒绝后再次申请该权限的解释
        //第三个参数是请求码
        //第四个参数是要申请的权限
        EasyPermissions.requestPermissions(this, "拍照需要摄像头权限", PHOTO_REQUEST_CAREMA, mRequestPermissionList);

    }

    /**
     * 4.4及以上的系统使用这个方法处理图片
     *
     * @param data 意图
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {

        Uri uri = data.getData();
        if (uri != null && DocumentsContract.isDocumentUri(this, uri)) {
            //如果document类型的Uri,则通过document来处理
            String docID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docID.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;

                filePath = getDataColumn(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, null);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {

                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docID));

                filePath = getDataColumn(this, contentUri, null, null);

            }

        } else if (uri != null && "content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式使用
            filePath = getDataColumn(this, uri, null, null);

        } else if (uri != null && "file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取路径即可
            filePath = uri.getPath();
        }

        displayImage(filePath);
    }

    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try {

            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                String picturePath = cursor.getString(columnIndex);
                if (TextUtils.isEmpty(picturePath)) {
                    shotToast("找不到图片");
                    return null;
                }
                return picturePath;
            } else {
                File file = new File(uri.getPath());
                if (!file.exists()) {
                    shotToast("找不到图片");
                    return null;
                }
                return file.getAbsolutePath();
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            filePaths.add(imagePath);
            Bitmap bitmap = BitmapUtils.compressBySize(imagePath);
            File file = BitmapUtils.saveBitmapFile(bitmap, imagePath);
            files.add(file);
            mAdapter.updateData(filePaths);
        } else {
            shotToast("获取照片失败");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    onTakePhoto(data);
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //4.4及以上的系统使用这个方法处理图片；
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        //4.4及以下的系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
            default:
                break;
        }
    }

    /**
     * 拍照页返回
     */
    private void onTakePhoto(final Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                filePath = uri.getPath();
            }
            if (filePath == null) {
                shotToast("拍照失败");
            }

        } else {
            Uri uri = Uri.fromFile(photoFile);
            filePath = uri.getPath();
        }
        Bitmap bitmap = BitmapUtils.compressBySize(filePath);
        File file = BitmapUtils.saveBitmapFile(bitmap, filePath);
        files.add(file);
        filePaths.add(filePath);
        mAdapter.updateData(filePaths);
        scrollToEnd();
    }

    private void scrollToEnd() {
        if (mAdapter.getItemCount() >= mImageRowsCount) {
            uploadEvidenceList.postDelayed(new Runnable() {

                @Override
                public void run() {
                    uploadEvidenceList.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                }

            }, 800);
        }
    }


    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getDataColumn(this, uri, null, null);
        displayImage(imagePath);
    }

    public void setUploadImagesData(List<CertificationRespones> data) {
        if (data != null) {
            for (CertificationRespones c : data) {
                String url = c.getUrl();
                imgPaths.add(url);
            }
        }
    }


}
