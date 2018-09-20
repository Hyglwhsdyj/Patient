package com.ais.patient.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImageUpload;
import com.ais.patient.been.UsrInfomation;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ConvertUtil;
import com.ais.patient.util.ImageCompress;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.CircleTransform;
import com.ais.patient.widget.UploadPopupwindow;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class RemakeUserInfoActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head_icon)
    ImageView ivHeadIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.radio_man)
    RadioButton radioMan;
    @BindView(R.id.radio_women)
    RadioButton radioWomen;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.mRadioGroup)
    RadioGroup mRadioGroup;

    private int PERMISSION_STATE = 200;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    List<String> mPermissionList = new ArrayList<>();
    private WindowManager.LayoutParams params;
    private UploadPopupwindow photopopupwindow;
    private String headImage;
    private String sex;
    private String name;
    private int age;

    // 检查权限
    public void showPermission() {
        /**
         * 判断哪些权限未授予
         */
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }

        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            // 不做处理
            showPhotoPopueWindow();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_STATE);
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_STATE) {
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            /**
             * 判断是否为空
             */
            if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
                // 不做处理
                showPhotoPopueWindow();
            } else {//请求权限方法
                // 没有获取到权限，做特殊处理
                ToastUtils.show(this, "请开启权限，拒绝将无法上传照片");
            }
        } else {
            // 没有获取到权限，做特殊处理
            ToastUtils.show(this, "请开启权限");
        }
    }


    private void showPhotoPopueWindow() {
        photopopupwindow = new UploadPopupwindow(this, R.layout.photo_pop_layout, R.id.ll_photographs, 1000);
        photopopupwindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        photopopupwindow.showAtLocation(findViewById(R.id.register_root_view), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        photopopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        TextView photo_pop_select = (TextView) photopopupwindow.getContentView().findViewById(R.id.photo_pop_select);
        TextView photo_pop_take = (TextView) photopopupwindow.getContentView().findViewById(R.id.photo_pop_take);
        TextView photo_pop_cancel = (TextView) photopopupwindow.getContentView().findViewById(R.id.photo_pop_cancel);
        photo_pop_select.setOnClickListener(this);
        photo_pop_take.setOnClickListener(this);
        photo_pop_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_pop_select:   //本地选取图片
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 0);
                }
                photopopupwindow.dismiss();
                break;
            case R.id.photo_pop_take:   //拍照
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 指定调用相机拍照后照片的储存路径
                    File appDir = new File(Environment.getExternalStorageDirectory(), "image");
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    File out = new File(Environment.getExternalStorageDirectory() + "/image/" +"photo.png");
                    Uri uri = Uri.fromFile(out);

                    int currentapiVersion = Build.VERSION.SDK_INT;
                    if (currentapiVersion < 24) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    } else { //解决Android 7.0 相机问题
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,
                                this.getPackageName() + ".android7.fileprovider", out));
                    }
                    startActivityForResult(intent, 1);
                }
                photopopupwindow.dismiss();
                break;
            case R.id.photo_pop_cancel:   //取消
                photopopupwindow.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (data != null) {
                        Uri selectedImage2 = data.getData();
                        if (selectedImage2 != null) {
                            String[] filePathColumns = {MediaStore.Images.Media.DATA};
                            Cursor c = getContentResolver().query(selectedImage2, filePathColumns, null, null, null);
                            c.moveToFirst();
                            int columnIndex = c.getColumnIndex(filePathColumns[0]);
                            String imagePath2 = c.getString(columnIndex);

                            upload(imagePath2);
                            c.close();
                        }
                    }
                    break;
                case 1:
                    String imagePath3 = Environment.getExternalStorageDirectory() + "/image/" +"photo.png";
                    upload(imagePath3);
                    break;
            }
        }
    }

    private void upload(String picturePath) {
        File file = ImageCompress.CompressPic(new File(picturePath), this);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<HttpBaseBean<ImageUpload>> call = RetrofitFactory.getInstance(this).uploadImage(body);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ImageUpload>() {

            @Override
            public void onSuccess(ImageUpload imageUpload, String info) {
                headImage = imageUpload.getUrl();
                if (!TextUtils.isEmpty(headImage)) {
                    Picasso.with(RemakeUserInfoActivity.this)
                            .load(headImage)
                            .transform(new CircleTransform())
                            .into(ivHeadIcon);
                }

            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_remake_user_info;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitle.setText("修改个人资料");
        UsrInfomation infomation = (UsrInfomation) getIntent().getSerializableExtra("useInfo");
        if (infomation!=null){
            headImage = infomation.getHeadImage();
            if (!TextUtils.isEmpty(headImage)){
                Picasso.with(RemakeUserInfoActivity.this)
                        .load(headImage)
                        .transform(new CircleTransform())
                        .into(ivHeadIcon);
            }

            name = infomation.getName();
            if (!TextUtils.isEmpty(name)){
                tvName.setText(name);
                etName.setText(name);
                etName.setSelection(etName.length());
            }
            age = infomation.getAge();
            etAge.setText(age+"");

            sex = infomation.getSex();
            if (sex.equals("男")){
                radioMan.setChecked(true);
            }else {
                radioWomen.setChecked(true);
            }
        }else {
            radioMan.setChecked(true);
            sex = "男";
        }

    }

    @Override
    protected void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.radio_man){
                    sex = "男";
                }else {
                    sex = "女";
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_back, R.id.ll_upload,R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_upload:
                showPermission();
                break;
            case R.id.tv_commit:
                name = etName.getText().toString().trim();
                String ages = etAge.getText().toString().trim();
                age = ConvertUtil.convertToInt(ages,-1);
                if (TextUtils.isEmpty(headImage)){
                    showToast("请上传头像");
                }else if (TextUtils.isEmpty(name)){
                    showToast("请输入姓名");
                }else if (TextUtils.isEmpty(ages)){
                    showToast("请输入年龄");
                }else {
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("headImage",headImage);
                    ajaxParams.put("name", name);
                    ajaxParams.put("sex",sex);
                    ajaxParams.put("age", age);
                    ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
                    Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).remakeuserInfo(urlParams);
                    new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                        @Override
                        public void onSuccess(Object o, String info) {
                            showToast("提交成功");
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFailure(String info) {
                            showToast(info);
                        }
                    });
                }

                break;
        }
    }
}
