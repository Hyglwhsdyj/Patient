package com.ais.patient.activity.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.JsonBean;
import com.ais.patient.been.PatientInfo;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.JsonFileReader;
import com.ais.patient.util.LogUtils;
import com.ais.patient.widget.FlowGroupView;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class AddPatientActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_brithday)
    TextView tvBrithday;
    @BindView(R.id.et_tall)
    EditText etTall;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout tagFlowlayout;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private String sex;
    private String months;
    private String dayOfMonths;
    private String birthday;
    private List<String> historys = new ArrayList<>();
    private String name;
    private String height;
    private String weight;
    private String phoneNumber;
    private String remarks;
    private String medicalHistory="";

    private String id;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String address;
    private List<String> flowString;
    private Context context;

    TagAdapter mAdapter1;
    private PatientInfo patientInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_patient;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        context = this;

        flowString = new ArrayList<>();
        flowString.add("高血压");
        flowString.add("高血脂");
        flowString.add("高血糖");
        flowString.add("心脏病");
        flowString.add("脑淤血");
        flowString.add("脑梗");
        flowString.add("肿瘤");
        flowString.add("无");
        initFlow1();

        Intent intent = getIntent();
        patientInfo = (PatientInfo) intent.getSerializableExtra("patientInfo");
        if (patientInfo !=null){
            tvTitle.setText("修改患者信息");
            id = patientInfo.getId();
            etName.setText(patientInfo.getName());
            etName.setSelection(etName.getText().length());

            sex = patientInfo.getSex();
            tvSex.setText(sex);
            if (sex.equals("男")){
                sex="M";
            }else {
                sex="女";
            }
            birthday = patientInfo.getBirthday();
            tvBrithday.setText(birthday);

            double height = patientInfo.getHeight();
            if (height>0){
                etTall.setText(height+"");
            }

            double weight = patientInfo.getWeight();
            if (weight>0){
                etWeight.setText(weight+"");
            }
            etPhone.setText(patientInfo.getPhoneNumber());
            address = patientInfo.getAddress();
            tvAddress.setText(address);

            String medicalHistory2 = patientInfo.getMedicalHistory();
            String[] number = medicalHistory2.split("\\,");
            historys.clear();
            if (number.length>0){
                for (int i = 0; i < number.length; i++) {
                    if (number[i].equals(flowString.get(0)) ){
                        mAdapter1.setSelectedList(0);//默认选中第一个
                        historys.add(number[i]);
                    }else if (number[i].equals(flowString.get(1)) ){
                        mAdapter1.setSelectedList(1);
                        historys.add(number[i]);
                    }else if (number[i].equals(flowString.get(2)) ){
                        mAdapter1.setSelectedList(2);
                        historys.add(number[i]);
                    }else if (number[i].equals(flowString.get(3)) ){
                        mAdapter1.setSelectedList(3);
                        historys.add(number[i]);
                    }else if (number[i].equals(flowString.get(4)) ){
                        mAdapter1.setSelectedList(4);
                        historys.add(number[i]);
                    }else if (number[i].equals(flowString.get(5)) ){
                        mAdapter1.setSelectedList(5);
                        historys.add(number[i]);
                    }else if (number[i].equals(flowString.get(6)) ){
                        mAdapter1.setSelectedList(6);
                        historys.add(number[i]);
                    }else if (number[i].equals(flowString.get(7)) ){
                        mAdapter1.setSelectedList(7);
                        historys.add(number[i]);
                    }

                }
            }
            remarks = patientInfo.getRemarks();
            if (!TextUtils.isEmpty(remarks)){
                etRemarks.setText(remarks);
            }
        }else {
            tvTitle.setText("填写患者信息");
        }

        initJsonData();
    }

    @Override
    protected void initEvent() {
        etRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = etRemarks.getText().length();
                tvNum.setText(length + "/" + 250);
                if (etRemarks.getText().length() > 250) {
                    String newShopTitle = etRemarks.getText().toString().substring(0, 250);
                    etRemarks.setText(newShopTitle);
                    etRemarks.setSelection(etRemarks.length());
                    showToast("字数250个以内");
                } else {

                }
            }
        });
    }

    @Override
    protected void initData() {

    }
    private void initFlow1() {
        tagFlowlayout.setAdapter(mAdapter1=new TagAdapter<String>(flowString)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv,
                        tagFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                historys.clear();
                for (Integer position : selectPosSet){
                    historys.add(flowString.get(position));
                }
                LogUtils.e("choose:" + selectPosSet.toString());
            }
        });
    }

    @OnClick({R.id.tv_back, R.id.rl_sex, R.id.rl_brithday, R.id.rl_address,R.id.tv_Add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.rl_sex:
                showSexDialog();
                break;
            case R.id.rl_brithday:
                showBrithDayDialog();
                break;
            case R.id.rl_address:
                showPickerView();
                break;
            case R.id.tv_Add:
                name = etName.getText().toString().trim();
                height = etTall.getText().toString().trim();
                weight = etWeight.getText().toString().trim();
                phoneNumber = etPhone.getText().toString().trim();
                remarks = etRemarks.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    showToast("请输入姓名");
                }else if (TextUtils.isEmpty(sex)){
                    showToast("请选择性别");
                }else if (TextUtils.isEmpty(birthday)){
                    showToast("请选择出生年月");
                }else if (TextUtils.isEmpty(phoneNumber)){
                    showToast("请输入手机号码");
                }else if (TextUtils.isEmpty(address)){
                    showToast("请选择地区");
                }else if (historys.size()<1){
                    showToast("请选择既往病史");
                }else {
                    if (TextUtils.isEmpty(id)){
                        requestAddPatient();
                    }else {
                        requestRemakePatient();
                    }
                }
                break;
        }
    }

    /**
     * 修改患者
     */
    private void requestRemakePatient() {
        String baseUrl = "/api/patient/update/"+id;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("name",name);
        ajaxParams.put("sex",sex);
        ajaxParams.put("birthday",birthday);
        if (!TextUtils.isEmpty(height)){
            ajaxParams.put("height",height);
        }
        if (!TextUtils.isEmpty(weight)){
            ajaxParams.put("weight",weight);
        }
        ajaxParams.put("phoneNumber",phoneNumber);
        ajaxParams.put("address",address);
        for (int i = 0; i < historys.size(); i++) {
            medicalHistory+= historys.get(i)+",";
        }
        medicalHistory = medicalHistory.substring(0,medicalHistory.length()-1);
        ajaxParams.put("medicalHistory",medicalHistory);
        if (!TextUtils.isEmpty(remarks)){
            ajaxParams.put("remarks",remarks);
        }
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        LogUtils.e(urlParams.toString());
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).remakePatient(urlParams,baseUrl);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("修改成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }

    /**
     * 添加患者
     */
    private void requestAddPatient() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("name",name);
        ajaxParams.put("sex",sex);
        ajaxParams.put("birthday",birthday);
        if (!TextUtils.isEmpty(height)){
            ajaxParams.put("height",height);
        }
        if (!TextUtils.isEmpty(weight)){
            ajaxParams.put("weight",weight);
        }
        ajaxParams.put("phoneNumber",phoneNumber);
        ajaxParams.put("address",address);
        for (int i = 0; i < historys.size(); i++) {
            medicalHistory+= historys.get(i)+",";
        }
        medicalHistory = medicalHistory.substring(0,medicalHistory.length()-1);
        ajaxParams.put("medicalHistory",medicalHistory);
        if (!TextUtils.isEmpty(remarks)){
            ajaxParams.put("remarks",remarks);
        }
        ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
        LogUtils.e(urlParams.toString());
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).addPatient(urlParams);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                showToast("添加成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(String info) {
                showToast(info);
            }
        });
    }

    /**
     * 地区选择？
     */
    /**
     * 弹出城市三级选择器
     */
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                address = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                tvAddress.setText(address);
            }
        }).setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY)
                .setContentTextSize(18)
                .setOutSideCancelable(false)
                .build();

               /* .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(false)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(19, 10, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .build();*/

          /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {   //解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(this, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    /**
     * 时间选择器
     */
    private void showBrithDayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.startime_endtime_dialog, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        datePicker.setCalendarViewShown(false); // 是否显示右边的日历
        builder.setTitle("选择出生年月");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int dayOfMonth = datePicker.getDayOfMonth();

                if (month < 10) {
                    months = "0" + month;
                } else {
                    months = month + "";
                }
                if (dayOfMonth < 10) {
                    dayOfMonths = "0" + dayOfMonth;
                } else {
                    dayOfMonths = dayOfMonth + "";
                }
                birthday = year + "-" + months + "-" + dayOfMonths;
                tvBrithday.setText(birthday);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
    }


    /**
     * 性别选择
     */
    private void showSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sex, null, false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.mRadioGroup);
        RadioButton rdb_man = (RadioButton) view.findViewById(R.id.rdb_man);
        RadioButton rdb_women = (RadioButton) view.findViewById(R.id.rdb_women);
        TextView tvOK = (TextView) view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancal);
        if (patientInfo!=null){
            if (sex.equals("M")){
                rdb_man.setChecked(true);
                rdb_women.setChecked(false);
            }else {
                rdb_women.setChecked(true);
                rdb_man.setChecked(false);
            }
        }else {
            if (TextUtils.isEmpty(sex)){
                rdb_man.setChecked(true);
                sex = "M";
                tvSex.setText("男");
            }else {
                if (sex.equals("M")){
                    rdb_man.setChecked(true);
                }else {
                    rdb_women.setChecked(true);
                }
            }

        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rdb_man){
                    sex = "M";
                }else {
                    sex = "F";
                }
            }
        });
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sex.equals("M")){
                    tvSex.setText("男");
                }else {
                    tvSex.setText("女");
                }
                alertDialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

}
