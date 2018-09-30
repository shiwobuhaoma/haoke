package com.xinze.haoke.module.certification.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.module.certification.modle.CertificationRespones;
import com.xinze.haoke.module.certification.presenter.CompanyCertificationPresenterImp;
import com.xinze.haoke.module.certification.presenter.InformationDepartmentPresenterImp;
import com.xinze.haoke.utils.BitmapUtils;
import com.xinze.haoke.utils.GlideRoundTransform;
import com.xinze.haoke.widget.BottomPopupMenu;
import com.xinze.haoke.widget.SimpleToolbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 信息部认证
 *
 * @author lxf
 */
public class InformationDepartmentCertificationActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener, ICertificationView, AddressPicker.OnAddressPickListener {


    @BindView(R.id.certification_bt)
    Button certificationBt;
    @BindView(R.id.certification_tool_bar)
    SimpleToolbar certificationToolBar;
    @BindView(R.id.certification_name)
    EditText certificationName;
    @BindView(R.id.certification_number_of_card)
    EditText certificationNumberOfCard;
    @BindView(R.id.certification_address)
    EditText certificationAddress;
    @BindView(R.id.certification_detail_address)
    EditText certificationDetailAddress;
    @BindView(R.id.certification_face_of_id_card)
    ImageView certificationFaceOfIdCard;
    @BindView(R.id.certification_face_of_driver_card)
    ImageView certificationFaceOfDriverCard;

    @BindView(R.id.certification_information_name)
    EditText certificationInformationName;
    @BindView(R.id.legal_person_handheld_identity_card)
    ImageView legalPersonHandheldIdentityCard;
    @BindView(R.id.iv_business_license)
    ImageView ivBusinessLicense;
    /**
     * 拍照
     */
    public static final int PHOTO_REQUEST_CAREMA = 1;
    /**
     * 从相册选照片
     */
    public static final int CHOOSE_PHOTO = 2;
    /**
     * 剪切照片
     */
    public static final int CROP_PHOTO = 3;

    /**
     * 所要申请的权限
     */
    String[] mRequestPermissionList = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    private List<String> mDeniedPermissionList = new ArrayList<>();
    private List<String> filePaths = new ArrayList<>();


    /**
     * 是否隐藏省、区列表
     */
    private boolean hideProvince = false;
    private boolean hideCounty = false;
    /**
     * 选择的省份、城市、区
     */
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";

    private BottomPopupMenu mBottomPopupMenu;
    private int resourceId;
    private File photoFile;
    private String filePath;
    private List<File> files;
    private InformationDepartmentPresenterImp cpi;
    private ArrayList<Province> provinces;
    private AddressPicker picker;
    private String areaId;

    @Override
    protected int initLayout() {
        return R.layout.certification_information_activity;
    }

    @Override
    protected void initView() {
        files = new ArrayList<>(2);
        certificationToolBar.setMainTitle("公司认证");
        certificationToolBar.setLeftTitleDrawable(R.mipmap.ic_back);
        certificationToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        certificationBt.setOnClickListener(this);
        certificationAddress.setOnClickListener(this);
        certificationAddress.setInputType(InputType.TYPE_NULL);
        certificationFaceOfIdCard.setOnClickListener(this);
        certificationFaceOfDriverCard.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        cpi = new InformationDepartmentPresenterImp(this, this);
        cpi.getAreaList("0");
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
        mBottomPopupMenu.addItem(1, "我的相册 ");
        mBottomPopupMenu.addItem(2, "拍照");
        mBottomPopupMenu.setOnMenuClickListener(new BottomPopupMenu.MenuClickListener() {

            @Override
            public void onMenuItemClick(View itemView, int itemId) {
                switch (itemId) {
                    // 相册
                    case 1:
                        choosePhoto();
                        break;
                    // 拍照
                    case 2:
                        openCamera();
                        break;
                    default:
                        break;
                }
            }
        });
        mBottomPopupMenu.showMenu();
    }

    private void requestPermissions() {
        //第二个参数是被拒绝后再次申请该权限的解释
        //第三个参数是请求码
        //第四个参数是要申请的权限
        EasyPermissions.requestPermissions(this, "拍照需要摄像头权限", PHOTO_REQUEST_CAREMA, mRequestPermissionList);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, CHOOSE_PHOTO);

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

    private void showAddressDialog() {
        if (provinces != null) {
            if (provinces.size() > 0) {
                picker = new AddressPicker(this, provinces);
                picker.setHideProvince(hideProvince);
                picker.setHideCounty(hideCounty);
                if (hideCounty) {
                    //将屏幕分为3份，省级和地级的比例为1:2
                    picker.setColumnWeight(1 / 3.0f, 2 / 3.0f);
                } else {
                    //省级、地级和县级的比例为2:3:3
                    picker.setColumnWeight(2 / 8.0f, 3 / 8.0f, 3 / 8.0f);
                }
                picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
                picker.setOnAddressPickListener(this);
                picker.show();
            }
        }
    }

    /**
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    @Override
    @OnClick({R.id.legal_person_handheld_identity_card, R.id.iv_business_license, R.id.certification_face_of_id_card,
            R.id.certification_face_of_driver_card, R.id.certification_address, R.id.certification_bt})
    public void onClick(View v) {
        //上传身份证照片及驾驶证照片
        switch (v.getId()) {
            case R.id.certification_face_of_id_card:
                resourceId = v.getId();
                checkPermissions();
                break;
            case R.id.certification_face_of_driver_card:
                resourceId = v.getId();
                checkPermissions();
                break;
            case R.id.certification_address:
                hideKeyboard();
                showAddressDialog();
                break;
            case R.id.certification_bt:

                if (filePaths.size() == 0) {
                    shotToast("请添加身份证正、反面、法人手持身份证及营业执照信息");
                    return;
                } else if (filePaths.size() < 4) {
                    shotToast("缺少相关信息");
                    return;
                }
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
                cpi.uploadImages(parts);
                break;
            case R.id.legal_person_handheld_identity_card:
                resourceId = v.getId();
                checkPermissions();
                break;
            case R.id.iv_business_license:
                resourceId = v.getId();
                checkPermissions();
                break;
            default:
                break;
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

        setImageViewDrawable();
    }

    private void setImageViewDrawable() {
        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .transform(new GlideRoundTransform(this, 5));
        if (resourceId == R.id.certification_face_of_id_card) {
            Glide.with(this).load(filePath).apply(myOptions).into(certificationFaceOfIdCard);
        } else if (resourceId == R.id.certification_face_of_driver_card) {
            Glide.with(this).load(filePath).apply(myOptions).into(certificationFaceOfDriverCard);
        } else if (resourceId == R.id.legal_person_handheld_identity_card) {
            Glide.with(this).load(filePath).apply(myOptions).into(legalPersonHandheldIdentityCard);
        } else {
            Glide.with(this).load(filePath).apply(myOptions).into(ivBusinessLicense);
        }
    }


    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getDataColumn(this, uri, null, null);
        displayImage(imagePath);
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
            setImageViewDrawable();
        } else {
            shotToast("获取照片失败");
        }
    }


    @Override
    public void uploadImagesSuccess(String msg) {

    }

    @Override
    public void uploadImagesFailed(String msg) {
        shotToast(msg);
    }

    @Override
    public void certificationSuccess(String msg) {
        shotToast(msg);
//        openActivity(DriverSubmitSuccessActivity.class);
        finish();
    }

    @Override
    public void certificationFailed(String msg) {
        shotToast(msg);
    }

    @Override
    public void getAreaListSuccess(String msg) {

    }

    @Override
    public void getAreaListFailed(String msg) {

    }

    public void setData(List<CertificationRespones> data) {

        String companyName = certificationInformationName.getText().toString();
        if (TextUtils.isEmpty(companyName)) {
            shotToast("公司名称不能为空");
            return;
        }

        String driverName = certificationName.getText().toString();
        if (TextUtils.isEmpty(driverName)) {
            shotToast("姓名不能为空");
            return;
        }

        String idNumber = certificationNumberOfCard.getText().toString();
        if (TextUtils.isEmpty(idNumber)) {
            shotToast("身份证号码不能为空");
            return;
        } else if (idNumber.length() < 18) {
            shotToast("身份证号码格式错误");
            return;
        }
        String driverAddress = certificationAddress.getText().toString();
        if (TextUtils.isEmpty(driverAddress)) {
            shotToast("公司所在区域不能为空");
            return;
        }
        String driverDetailAddress = certificationDetailAddress.getText().toString();
        if (TextUtils.isEmpty(driverDetailAddress)) {
            shotToast("详细地址不能为空");
            return;
        }
        if (filePaths.size() < 4) {
            shotToast("上传照片数量不足");
            return;
        }
        //0代表公司，1代表信息部
        cpi.certifitcation("1",
                companyName,
                "",
                driverName,
                idNumber,
                areaId,
                driverDetailAddress,
                data.get(0).getUrl() + "|" + data.get(1).getUrl(),
                data.get(2).getUrl(),
                data.get(3).getUrl());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cpi != null) {
            cpi.onDestroy();
        }
    }

    public void setAreaList(ArrayList<Province> result) {
        this.provinces = result;
    }

    @Override
    public void onAddressPicked(Province province, City city, County county) {
        String address = province.getAreaName() + city.getAreaName() + county.getAreaName();
        areaId = county.getAreaId();

        certificationAddress.setText(address);
        certificationAddress.setTextColor(ContextCompat.getColor(this, R.color.black));
        certificationAddress.setSelection(address.length());
    }


}