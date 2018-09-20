package com.ais.patient.activity.main;

import android.Manifest;
import android.content.Context;
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
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.adapter.PhotoGridviewAdapter;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImageUpload;
import com.ais.patient.been.Patient;
import com.ais.patient.been.PatientInfo;
import com.ais.patient.been.PatientType;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ConvertUtil;
import com.ais.patient.util.ImageCompress;
import com.ais.patient.util.LogUtils;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.util.UserUtils;
import com.ais.patient.widget.NetstedGridView;
import com.ais.patient.widget.UploadPopupwindow;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;

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

public class PatientInfomationActivity extends MYBaseActivity {

    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_remake_patient)
    TextView tvRemake;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_patient)
    TextView tvPatient;
    @BindView(R.id.et_zhusu)
    EditText etZhusu;
    @BindView(R.id.tv_num1)
    TextView tvNum1;
    @BindView(R.id.et_now_status)
    EditText etNowStatus;
    @BindView(R.id.rl_patient)
    RelativeLayout rlPatient;
    @BindView(R.id.tv_num2)
    TextView tvNum2;
    @BindView(R.id.view_title)
    LinearLayout llViewTitle;
    @BindView(R.id.gv_shop_photo)
    NetstedGridView gvShopHead;
    @BindView(R.id.gv_introl_photo)
    NetstedGridView gvIntrolPhoto;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_bl)
    TextView tvBl;
    @BindView(R.id.tv_other)
    TextView tvOther;
    private UploadPopupwindow popupwindow, photopopupwindow;
    private Context context;

    private int index = -1;
    private int index2 = -1;

    List<String> imgsHead = new ArrayList<>();
    List<String> imgIntrol = new ArrayList<>();
    private List<String> dataHead = new ArrayList<>();
    private List<String> dataIntrol = new ArrayList<>();
    private PhotoGridviewAdapter adapterHead;
    private PhotoGridviewAdapter adapterIntrol;



    private WindowManager.LayoutParams params;
    private ListView mmListView;
    private String doctorId;
    private String recordId;
    private String patientId;
    private String appeal;
    private String symptom;
    private String patientSex;
    private int age;

    private int PERMISSION_STATE = 200;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    List<String> mPermissionList = new ArrayList<>();
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
                    startActivityForResult(intent, index);
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
                    File out = new File(Environment.getExternalStorageDirectory() + "/image" + "/" + "photo1" + index2 + ".png");
                    Uri uri = Uri.fromFile(out);

                    int currentapiVersion = Build.VERSION.SDK_INT;
                    if (currentapiVersion < 24) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    } else { //解决Android 7.0 相机问题
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,
                                this.getPackageName() + ".android7.fileprovider", out));
                    }
                    startActivityForResult(intent, index2);
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
                    String imagePath3 = Environment.getExternalStorageDirectory() + "/image" + "/" + "photo1" + index2 + ".png";
                    //files.add(CompressPic(new File(Environment.getExternalStorageDirectory() + "/image" + "/" + photo + (datas.size() - 1) + "photo" + ".png")));
                    upload(imagePath3);
                    break;
                case 2:
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
                case 3:
                    String imagePath4 = Environment.getExternalStorageDirectory() + "/image" + "/" + "photo1" + index2 + ".png";
                    upload(imagePath4);
                    break;
                case 4:
                    toShowPatient(patientId);
                    loadPatientMsgList();
                    break;
                case 5:
                    loadPatientMsgList();
                    break;
            }
        }
    }

    private void upload(String picturePath) {
        showProgressDialog();
        File file = ImageCompress.CompressPic(new File(picturePath), this);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<HttpBaseBean<ImageUpload>> call = RetrofitFactory.getInstance(this).uploadImage(body);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<ImageUpload>() {

            @Override
            public void onSuccess(ImageUpload imageUpload, String info) {

                if (imageUpload.getUrl() != null) {
                    if (index == 0) {
                        String fileId = imageUpload.getFileId();
                        imgsHead.add(fileId);
                        dataHead.add(dataHead.size() - 2, imageUpload.getUrl());
                        //files.add(CompressPic(new File(imagePath)));
                        adapterHead.SetData(dataHead);
                        gvShopHead.setAdapter(adapterHead);
                        adapterHead.notifyDataSetChanged();
                    } else if (index == 2) {
                        String fileId = imageUpload.getFileId();
                        imgIntrol.add(fileId);

                        dataIntrol.add(dataIntrol.size() - 1, imageUpload.getUrl());
                        //files.add(CompressPic(new File(imagePath)));
                        adapterIntrol.SetData(dataIntrol);
                        gvIntrolPhoto.setAdapter(adapterIntrol);
                        adapterIntrol.notifyDataSetChanged();
                    }
                }

                if (isShowingProgressDialog()){
                    dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
                if (isShowingProgressDialog()){
                    dismissProgressDialog();
                }
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_infomation;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;
        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");
        recordId = intent.getStringExtra("recordId");
        /*if (TextUtils.isEmpty(doctorId)){
            doctorId = UserUtils.getChatOnLineDoctorId(this);
            recordId= UserUtils.getChatOnLineRecordId(this);
        }*/
        tvTitle.setText("患者信息");
        showPopueWindow();
        initGridView();
    }


    private void initGridView() {
        //封面图片
        adapterHead = new PhotoGridviewAdapter(this);
        gvShopHead.setAdapter(adapterHead);
        if (dataHead.size() == 0) {
            dataHead.add("");
            dataHead.add("1");
        }
        adapterHead.SetData(dataHead);
        adapterHead.SetOnCLickItme(new PhotoGridviewAdapter.SetItmeOnClickListener() {

            @Override
            public void ItmeonClickListener(int i) {
                index = 0;
                index2 = 1;
                if (imgsHead.size() <= 5) {
                    showPermission();
                } else {
                    showToast("最多只能上传6张");
                }
            }

            @Override
            public void ItmeonDelete(int i) {
                dataHead.remove(i);
                imgsHead.remove(i);
                adapterHead.SetData(dataHead);
                gvShopHead.setAdapter(adapterHead);
            }

            @Override
            public void showDialog() {
                showTempDialogw();
            }
        });

        //描述图片
        adapterIntrol = new PhotoGridviewAdapter(this);
        gvIntrolPhoto.setAdapter(adapterIntrol);
        if (dataIntrol.size() == 0) {
            dataIntrol.add("");
        }
        adapterIntrol.SetData(dataIntrol);
        adapterIntrol.SetOnCLickItme(new PhotoGridviewAdapter.SetItmeOnClickListener() {
            @Override
            public void ItmeonClickListener(int i) {
                index = 2;
                index2 = 3;
                if (imgIntrol.size() <= 5) {
                    showPermission();
                } else {
                    showToast("最多只能上传6张");
                }
            }

            @Override
            public void ItmeonDelete(int i) {
                dataIntrol.remove(i);
                imgIntrol.remove(i);
                adapterIntrol.SetData(dataIntrol);
                gvIntrolPhoto.setAdapter(adapterIntrol);
            }

            @Override
            public void showDialog() {

            }
        });
    }

    @Override
    protected void initEvent() {
        etZhusu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = etZhusu.getText().length();
                tvNum1.setText(length + "/" + 25);
                if (etZhusu.getText().length() > 25) {
                    String newShopTitle = etZhusu.getText().toString().substring(0, 25);
                    etZhusu.setText(newShopTitle);
                    etZhusu.setSelection(etZhusu.length());
                    showToast("字数25个以内");
                } else {

                }
            }
        });
        etNowStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = etNowStatus.getText().length();
                tvNum2.setText(length + "/" + 250);
                if (etNowStatus.getText().length() > 250) {
                    String newShopTitle = etNowStatus.getText().toString().substring(0, 250);
                    etNowStatus.setText(newShopTitle);
                    etNowStatus.setSelection(etNowStatus.length());
                    showToast("字数250个以内");
                } else {

                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_back,R.id.tv_look1, R.id.rl_patient, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_look1:
                showTempDialog();
                break;
            case R.id.rl_patient:
                popupwindow.showAtLocation(rlPatient, Gravity.NO_GRAVITY, 0, rlPatient.getHeight() + llViewTitle.getHeight() + 50);
                break;
            case R.id.tv_next:
                appeal = etZhusu.getText().toString().trim();
                symptom = etNowStatus.getText().toString().trim();
                if (TextUtils.isEmpty(doctorId)){
                    showToast("doctorId为空");
                }else if (TextUtils.isEmpty(recordId)){
                    showToast("recordId为空");
                }else if (TextUtils.isEmpty(patientId)){
                    showToast("请选择患者");
                }else if (TextUtils.isEmpty(appeal)){
                    showToast("请输入主诉");
                }else if (imgsHead.size()<1){
                    showToast("请上传舌苔和舌底照片");
                }else if (imgIntrol.size()<1){
                    showToast("上传病患处照片、检查报告、化验单");
                }else {
                    reQuestUpdateFirst();
                }
                break;
        }
    }

    /**
     * 第一步提交
     */
    private void reQuestUpdateFirst() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("doctorId",doctorId);
        ajaxParams.put("recordId",recordId);
        ajaxParams.put("patientId",patientId);
        ajaxParams.put("appeal",appeal);
        ajaxParams.put("tongueImages",imgsHead);
        ajaxParams.put("otherImages",imgIntrol);
        if (!TextUtils.isEmpty(symptom)){
            ajaxParams.put("symptom",symptom);
        }
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        LogUtils.e(urlParams.toString());
        final Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reQuestUpdateFirst(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("提交完成");
                Call<HttpBaseBean<PatientType>> patient = RetrofitFactory.getInstance(context).judgePatient(recordId);
                new BaseCallback(patient).handleResponse(new BaseCallback.ResponseListener<PatientType>() {

                    @Override
                    public void onSuccess(PatientType patientType, String info) {
                        if (patientType!=null){
                            String result = patientType.getTemplate();
                            if (!TextUtils.isEmpty(result)){
                                if (result.equals("成年")){
                                    Intent intent = new Intent(context, WriteFromFirstActivity.class);
                                    intent.putExtra("recordId",recordId);
                                    intent.putExtra("doctorId",doctorId);
                                    startActivity(intent);
                                    finish();
                                }else if (result.equals("儿童")){
                                    //走儿童模式
                                    Intent intent = new Intent(context, WriteChildFromFirstActivity.class);
                                    intent.putExtra("recordId",recordId);
                                    intent.putExtra("doctorId",doctorId);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(String info) {
                        ToastUtils.show(context,info);
                    }
                });
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }

    //照片
    private void showTempDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_look1, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void showTempDialogw() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_look2, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showPopueWindow() {
        popupwindow = new UploadPopupwindow(this, R.layout.popupwindow_choose_patient, R.id.ll_type, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.setAnimationStyle(R.style.style_pop_animation);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        View view = popupwindow.getContentView();
        mmListView = (ListView) view.findViewById(R.id.mListView);
        final TextView tvAddPatient = (TextView) view.findViewById(R.id.tv_add_patient);
        tvAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddPatientActivity.class);
                startActivityForResult(intent,5 );
            }
        });
        loadPatientMsgList();
    }

    private void loadPatientMsgList() {
        Call<HttpBaseBean<List<Patient>>> call = RetrofitFactory.getInstance(this).getPatientList();
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<Patient>>() {

            @Override
            public void onSuccess(List<Patient> patients, String info) {
                if (patients != null && patients.size() > 0) {
                    Adapter<Patient> adapter = new Adapter<Patient>(context, R.layout.patient_item, patients) {
                        @Override
                        protected void convert(AdapterHelper helper, final Patient item) {
                            helper.setText(R.id.tv_patient_info, item.getName() + item.getSex() + item.getAge());
                            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tvPatient.setText(item.getName());
                                    patientId = item.getId();
                                    toShowPatient(patientId);
                                    popupwindow.dismiss();
                                }
                            });
                        }
                    };
                    mmListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String info) {

            }
        });
    }

    private void toShowPatient(String id) {
        String baseUrl ="/api/patient/" +id;
        Call<HttpBaseBean<PatientInfo>> call = RetrofitFactory.getInstance(this).getPatientInfo(baseUrl);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<PatientInfo>() {

            @Override
            public void onSuccess(final PatientInfo patientInfo, String info) {
                if (patientInfo!=null){
                    patientSex = patientInfo.getSex();

                    String patientAge = patientInfo.getAge();
                    String substring = patientAge.substring(0, patientAge.length() - 1);
                    age = ConvertUtil.convertToInt(substring,-1);

                    tvHeight.setText(patientInfo.getHeight()+"cm");
                    tvWeight.setText(patientInfo.getWeight()+"Kg");
                    tvPhone.setText(patientInfo.getPhoneNumber());
                    tvAddress.setText(patientInfo.getAddress());
                    String medicalHistory = patientInfo.getMedicalHistory();
                    if (TextUtils.isEmpty(medicalHistory)){
                        tvBl.setText("无");
                    }else {
                        tvBl.setText(medicalHistory);
                    }
                    String remarks = patientInfo.getRemarks();
                    if (TextUtils.isEmpty(remarks)){
                        tvOther.setText("无");
                    }else {
                        tvOther.setText(remarks);
                    }
                    llInfo.setVisibility(View.VISIBLE);
                    tvRemake.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,AddPatientActivity.class);
                            intent.putExtra("patientInfo",patientInfo);
                            startActivityForResult(intent,4);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }

}
