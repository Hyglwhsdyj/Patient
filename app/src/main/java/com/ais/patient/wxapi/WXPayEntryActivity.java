package com.ais.patient.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.ais.patient.activity.main.MainActivity;
import com.ais.patient.activity.main.PatientInfomationActivity;
import com.ais.patient.activity.mine.MyOrdonnanceActivity;
import com.ais.patient.activity.mine.MyReservationActivity;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.constant.UserConstant;
import com.ais.patient.http.AjaxParams;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.util.UserUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";
	private IWXAPI api;
    private String businessType;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		api = WXAPIFactory.createWXAPI(this, UserConstant.WX_APP_ID, false);
		try {
			api.handleIntent(getIntent(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e(TAG, "onResp: "+resp.errCode);
		Log.e(TAG, "onResp: "+resp.errStr );
		if (resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
			if (resp.errCode==0){
				Intent intent = new Intent();
				intent.setAction("com.ais.pay");
				intent.putExtra("errCode",0);
				sendBroadcast(intent);
			}else if (resp.errCode==-2){
				Intent intent = new Intent();
				intent.setAction("com.ais.pay");
				intent.putExtra("errCode",-2);
				sendBroadcast(intent);
			}else {
				Intent intent = new Intent();
				intent.setAction("com.ais.pay");
				intent.putExtra("errCode",-2);
				sendBroadcast(intent);
				Toast.makeText(this,"支付失败",Toast.LENGTH_LONG).show();
			}
			finish();
		}
	}

}