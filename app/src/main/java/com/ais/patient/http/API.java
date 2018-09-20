package com.ais.patient.http;

/**
 * Created by Administrator on 2018/8/9.
 */

public class API {
    public  static String ORDONNANCE_DETAIL_URL ="/api/order/my/drug_detail/";//处方单详情-已完成
    public  static String ORDONTRANING_DETAIL_URL ="/api/order/my/to_collect_goods/";//处方单详情-待收货
    public  static String ORDONNANCE_DETAIL_WAIT_PAY_URL ="/api/order/my/to_pay_order/";//处方单-待支付
    public  static String ORDONNANCE_DETAIL_CANCEL_URL ="/api/order/my/cancel/";//处方单-取消订单
}
