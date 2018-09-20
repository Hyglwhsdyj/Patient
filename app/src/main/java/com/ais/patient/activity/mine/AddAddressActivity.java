package com.ais.patient.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ais.patient.R;
import com.ais.patient.base.MYBaseActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.JsonBean;
import com.ais.patient.been.MyAddress;
import com.ais.patient.been.SingleAddress;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.JsonFileReader;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class AddAddressActivity extends MYBaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_addd_detail)
    EditText etAdddDetail;
    private String patientName;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String provinceName;
    private String cityName;
    private String countyName;
    private String phone;
    private String address;
    private String id;
    private Call<HttpBaseBean<MyAddress>> call;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initViews(final Bundle savedInstanceState) {
        Intent intent = getIntent();
        patientName = intent.getStringExtra("patientName");
        if (!TextUtils.isEmpty(patientName)){
            etName.setText(patientName);
        }

        id = intent.getStringExtra("id");
        if (!TextUtils.isEmpty(id)){
            tvTitle.setText("编辑收货人");
            String baseUrl = "/api/shippingaddress/"+ id;
            Call<HttpBaseBean<SingleAddress>> call = RetrofitFactory.getInstance(this).getMyAdressforId(baseUrl);
            new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<SingleAddress>() {

                @Override
                public void onSuccess(SingleAddress singleAddress, String info) {
                    if (singleAddress!=null){
                        patientName = singleAddress.getName();

                        provinceName = singleAddress.getProvinceName();
                        cityName = singleAddress.getCityName();
                        countyName = singleAddress.getCountyName();
                        address = singleAddress.getAddress();
                        phone = singleAddress.getPhoneNumber();

                        etName.setText(patientName);
                        etPhone.setText(phone);
                        etAdddDetail.setText(address);
                        tvAddress.setText(provinceName+cityName+countyName);
                    }
                }

                @Override
                public void onFailure(String info) {

                }
            });
        }else {
            tvTitle.setText("新增收货人");
        }

        /**
         * 获取三级城市列表JSON数据
         */
        initJsonData();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_back, R.id.rl_address,R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.rl_address:
                //弹出城市选择器
                showPickerView();
                break;
            case R.id.tv_save:
                phone = etPhone.getText().toString();
                address = etAdddDetail.getText().toString();
                patientName = etName.getText().toString();
                if (TextUtils.isEmpty(patientName)) {
                    showToast("请输入收货人");
                }else if (TextUtils.isEmpty(phone)) {
                    showToast("请输入联系方式");
                } else if (TextUtils.isEmpty(address)) {
                    showToast("请输入详细地址");
                }else if (TextUtils.isEmpty(provinceName)){
                    showToast("请选择地址");
                } else {
                    AjaxParams ajaxParams = new AjaxParams();
                    if(!TextUtils.isEmpty(id)){
                        ajaxParams.put("id",id);
                    }
                    ajaxParams.put("name",patientName);
                    ajaxParams.put("provinceName",provinceName);
                    ajaxParams.put("cityName",cityName);
                    ajaxParams.put("countyName",countyName);
                    ajaxParams.put("address", address);
                    ajaxParams.put("phoneNumber", phone);
                    ConcurrentHashMap<String, Object> urlParams = ajaxParams.getUrlParams();
                    if (TextUtils.isEmpty(id)){
                        Call<HttpBaseBean<MyAddress>> call = RetrofitFactory.getInstance(this).addAddress(urlParams);new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<MyAddress>() {

                            @Override
                            public void onSuccess(MyAddress myAddress, String info) {
                                if (myAddress!=null){
                                    Intent intent = new Intent();
                                    intent.putExtra("MyAddress",myAddress);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(String info) {
                                showToast(info);
                            }
                        });

                    }else {
                        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(this).reSetAddress(urlParams);
                        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
                            @Override
                            public void onSuccess(Object o, String info) {
                                Intent intent = new Intent();
                                setResult(RESULT_OK,intent);
                                finish();
                            }

                            @Override
                            public void onFailure(String info) {

                            }
                        });
                    }

                }
                break;
        }
    }

    /**
     * 弹出城市三级选择器
     */
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String text = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                tvAddress.setText(text);
                provinceName = options1Items.get(options1).getPickerViewText();
                cityName = options2Items.get(options1).get(options2);
                countyName = options3Items.get(options1).get(options2).get(options3);
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
}
