package com.ais.patient.http;


import com.ais.patient.been.About;
import com.ais.patient.been.Appraise;
import com.ais.patient.been.AppraiseDetail;
import com.ais.patient.been.AppraiseDetial;
import com.ais.patient.been.AppraiseList;
import com.ais.patient.been.BuyMedicine;
import com.ais.patient.been.ChatOnLineList;
import com.ais.patient.been.ChatOnLinePaper;
import com.ais.patient.been.ChatOnlineMsg;
import com.ais.patient.been.CheckPay;
import com.ais.patient.been.CheckStatus;
import com.ais.patient.been.Coupons;
import com.ais.patient.been.Customer;
import com.ais.patient.been.DoctorDynamicRespone;
import com.ais.patient.been.DoctorMeetingTime;
import com.ais.patient.been.DoctorMsg;
import com.ais.patient.been.DoctorNewDetial;
import com.ais.patient.been.DoctorNewList;
import com.ais.patient.been.DoctorTime;
import com.ais.patient.been.FactFee;
import com.ais.patient.been.FindDoctor;
import com.ais.patient.been.HaveMeet;
import com.ais.patient.been.HealthTemp;
import com.ais.patient.been.HealthTempDetail;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.ImInfo;
import com.ais.patient.been.ImageUpload;
import com.ais.patient.been.Jump;
import com.ais.patient.been.LastCode;
import com.ais.patient.been.Login;
import com.ais.patient.been.MainAllInfo;
import com.ais.patient.been.MainDotcor;
import com.ais.patient.been.MeetingMsg;
import com.ais.patient.been.MyAddress;
import com.ais.patient.been.MyAdressResponse;
import com.ais.patient.been.Message;
import com.ais.patient.been.MyConcernResponse;
import com.ais.patient.been.MyCpupons;
import com.ais.patient.been.MyPatient;
import com.ais.patient.been.OrdonnanceDetailRespone;
import com.ais.patient.been.OrdonnanceListRespone;
import com.ais.patient.been.Patient;
import com.ais.patient.been.PatientInfo;
import com.ais.patient.been.PatientType;
import com.ais.patient.been.Prescription;
import com.ais.patient.been.PrescriptionDetail;
import com.ais.patient.been.Refund;
import com.ais.patient.been.Reservation;
import com.ais.patient.been.ReservationRecord;
import com.ais.patient.been.ShareMsg;
import com.ais.patient.been.SingleAddress;
import com.ais.patient.been.SymptomReback;
import com.ais.patient.been.UpdateStatus;
import com.ais.patient.been.UsrInfomation;
import com.ais.patient.been.WetChat;
import com.ais.patient.been.WichStep;
import com.ais.patient.util.UpdateUtil;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IRetrofitServer {

    /**
     * 验证码
     *
     * @param phoneNumber
     * @param businessType
     * @return
     */
    @FormUrlEncoded
    @POST("/api/user/send_captcha.json")
    Call<HttpBaseBean<Object>> getCode(@Field("phoneNumber") String phoneNumber, @Field("businessType") String businessType);

    /**
     * 注册
     */
    @POST("/api/user/register.json")
    Call<Login> register(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 找回密码
     *
     * @return
     */
    @PUT("/api/user/reset_password.json")
    Call<HttpBaseBean<Object>> resetPassword(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 登录
     *
     * @param
     * @return
     */
    @POST("/api/user/login.json")
    Call<Login> login(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 修改手机号 第一步
     * @param urlParams
     * @return
     */
    @POST("/api/user/reset_phone_number_one_step.json")
    Call<HttpBaseBean<LastCode>> retPassWord(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 修改手机
     * @param urlParams
     * @return
     */
    @PUT("/api/user/reset_phone_number.json")
    Call<HttpBaseBean<Object>> retPassWord2(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 推荐我们
     * @param
     * @return
     */
    @GET("/api/home/recommend_page.json")
    Call<HttpBaseBean<ShareMsg>> getShareMsg();

    /**
     * 获取IM账号 token
     *
     * @param recordId
     * @return
     */
    @GET("/api/inquiry/im_accid_token.json")
    Call<HttpBaseBean<ImInfo>> getImInfo(@Query("inquiryId") String recordId);

    /**
     * 返回相关症状
     * @return
     */
    @GET("/api/doctor/query_symptom.json")
    Call<HttpBaseBean<List<String>>> getSymptom(@Query("searchWord") String searchWord);

    /**
     * 根据返回相关症状返回数据
     * @param symptom
     * @return
     */
    @GET("/api/doctor/query_disease.json")
    Call<HttpBaseBean<SymptomReback>> getSymptomReback(@Query("symptom") String symptom);


    /**
     * 获取不适列表
     *
     * @return
     */
    @GET("/api/common/list_disease.json")
    Call<HttpBaseBean<List<MainAllInfo.DiseaesBean>>> getNotGoodList();

    /**
     * 获取全部科室
     *
     * @return
     */
    @GET("/api/common/list_depart.json")
    Call<HttpBaseBean<List<MainAllInfo.DepartsBean>>> getDepartmentList();

    /**
     * 按疾病找医生列表
     *
     * @param pageNum
     * @param pageSize
     * @param sortType
     * @param
     * @return
     */
    @GET("/api/doctor/list_disease.json")
    Call<HttpBaseBean<List<FindDoctor>>> getDiseaseDoctorList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("sortType") String sortType, @Query("searchWord") String searchWord);

    @GET("/api/doctor/list_disease.json")
    Call<HttpBaseBean<List<FindDoctor>>> getDiseaseDoctorList2(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("sortType") String sortType, @Query("diseaseId") String diseaseId, @Query("searchWord") String searchWord);

    /**
     * 按科室找医生列表
     *
     * @param pageNum
     * @param pageSize
     * @param sortType
     * @param
     * @return
     */
    @GET("/api/doctor/list_depart.json")
    Call<HttpBaseBean<List<FindDoctor>>> getDepartDoctorList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("sortType") String sortType, @Query("departId") String departId);

    @GET("/api/doctor/list_depart.json")
    Call<HttpBaseBean<List<FindDoctor>>> getDepartDoctorList2(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("sortType") String sortType, @Query("departId") String departId, @Query("searchWord") String searchWord);

    /**
     * 获取推荐医生列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/doctor/list_recommend.json")
    Call<MainDotcor> getMainDoctorList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 获取其他医生列表
     * @return
     */
    @GET("/api/order/inquiry/get_other_doctors.json")
    Call<MainDotcor> getOtherDoctorList(@Query("doctorId") String doctorId);


    /**
     * 首页全部数据
     *
     * @return
     */
    @GET("/api/home/index.json")
    Call<MainAllInfo> getMainAllInfo();

    /**
     * 医生个人信息数据
     *
     * @param baseUrl
     * @return
     */
    @GET
    Call<HttpBaseBean<DoctorMsg>> getDoctorImformation(@Url String baseUrl);

    /**
     * 医生评价列表
     *
     * @param baseUrl
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET
    Call<AppraiseDetail> getDoctorAppraiseList(@Url String baseUrl, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * 关注医生
     *
     * @param doctorId
     * @return
     */
    @POST("/api/follow/save_relation.json")
    Call<HttpBaseBean<Object>> saveDoctor(@Query("doctorId") String doctorId);

    /**
     * 取消关注
     *
     * @param doctorId
     * @return
     */
    @PUT("/api/follow/cancel_relation.json")
    Call<HttpBaseBean<Object>> unSaveDoctor(@Query("doctorId") String doctorId);

    /**
     * 判断和该医生是否有正在进行的问诊
     *
     * @param doctorId
     * @return
     */
    @GET("/api/order/inquiry/get_going_inquiry.json")
    Call<HttpBaseBean<HaveMeet>> isHaveMeeting(@Query("doctorId") String doctorId);

    /**
     * 获取在线问诊购买服务页面
     *
     * @param doctorId
     * @return
     */
    @GET("/api/inquiry/lately_settings.json")
    Call<HttpBaseBean<ChatOnlineMsg>> getChatOnlineMsg(@Query("doctorId") String doctorId);

    /**
     * 获取线下问诊购买服务页面
     *
     * @param doctorId
     * @return
     */
    @GET("/api/appointment/lately_appointment.json")
    Call<HttpBaseBean<List<MeetingMsg>>> getMeetingMsg(@Query("doctorId") String doctorId);

    /**
     * 优惠券数量
     *
     * @param businessType
     * @param fee
     * @return
     */
    @GET("/api/coupon/available_quantity.json")
    Call<HttpBaseBean<Integer>> getQuantity(@Query("businessType") String businessType, @Query("fee") double fee);
    @GET("/api/coupon/available_quantity.json")
    Call<HttpBaseBean<Integer>> getQuantity2(@Query("businessType") String businessType, @Query("fee") double fee, @Query("businessId") String businessId);

    /**
     * 优惠券列表
     *
     * @param businessType
     * @param fee
     * @return
     */
    @GET("/api/coupon/available_coupons.json")
    Call<HttpBaseBean<List<Coupons>>> getCouponsList(@Query("businessType") String businessType, @Query("fee") double fee);
    @GET("/api/coupon/available_coupons.json")
    Call<HttpBaseBean<List<Coupons>>> getCouponsList2(@Query("businessType") String businessType, @Query("fee") double fee, @Query("businessId") String businessId);

    /**
     * 选择日期
     *
     * @param doctorId
     * @return
     */
    @GET("/api/inquiry/list_settings.json")
    Call<HttpBaseBean<List<DoctorTime>>> getDoctorTimeList(@Query("doctorId") String doctorId);

    @GET("/api/appointment/list_appointment.json")
    Call<HttpBaseBean<List<DoctorMeetingTime>>> getDoctorMeetingTimeList(@Query("doctorId") String doctorId);

    /**
     * 提交在线问诊订单订单
     *
     * @param urlParams
     * @return
     */
    @POST("/api/order/inquiry/save_apply.json")
    Call<HttpBaseBean<WetChat>> requestWechat(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 提交在线问诊订单(加急)
     * @param urlParams
     * @return
     */
    @POST("/api/order/inquiry/save_urgent_apply.json")
    Call<HttpBaseBean<WetChat>> requestExpressWechat(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 请求该接口判断是否真正支付成功 加急
     * @param
     * @return
     */
    @POST("/api/order/inquiry/check_pay.json")
    Call<HttpBaseBean<CheckPay>> makeSureExpress(@Query("recordId") String recordId);

    //
    @GET("/api/order/inquiry/check_urgent_status.json")
    Call<HttpBaseBean<CheckStatus>> CheckExpressStatus(@Query("recordId") String recordId);

    /**
     * 提交在线下面诊订单订单
     *
     * @param urlParams
     * @return
     */
    @POST("/api/order/appointment/save_apply.json")
    Call<HttpBaseBean<WetChat>> requestWechat2(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 秘方列表
     *
     * @return
     */
    @GET("/api/secret_recipe/list.json")
    Call<HttpBaseBean<List<Prescription>>> getPrescriptionList(@Query("searchWord") String searchWord);

    /**
     * 秘方详情
     *
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<PrescriptionDetail>> getPrescriptionDetail(@Url String url);

    /**
     * 患者列表
     *
     * @return
     */
    @GET("/api/patient/list.json")
    Call<HttpBaseBean<List<Patient>>> getPatientList();

    /**
     * 患者详细信息
     *
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<PatientInfo>> getPatientInfo(@Url String url);

    /**
     * 用户提交问诊单第二步前，需要判断使用成年模板还是儿童模板，患者是男性还是女性
     * @param recordId
     * @return
     */
    @GET("/api/interrogation/judge_patient.json")
    Call<HttpBaseBean<PatientType>> judgePatient(@Query("recordId") String recordId);

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("/api/common/upload_image.json")
    Call<HttpBaseBean<ImageUpload>> uploadImage(@Part MultipartBody.Part file);

    /**
     * 新增患者
     *
     * @param urlParams
     * @return
     */
    @POST("/api/patient/save.json")
    Call<HttpBaseBean<Object>> addPatient(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 修改患者
     *
     * @param urlParams
     * @param url
     * @return
     */
    @PUT
    Call<HttpBaseBean<Object>> remakePatient(@Body ConcurrentHashMap<String, Object> urlParams, @Url String url);

    /**
     * 问诊提交第一步
     *
     * @param urlParams
     * @return
     */
    @POST("/api/interrogation/save_one_step.json")
    Call<HttpBaseBean<Object>> reQuestUpdateFirst(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 问诊提交第二步
     *
     * @param urlParams
     * @return
     */
    @PUT("/api/interrogation/save_template.json")
    Call<HttpBaseBean<Object>> reQuestUpdateSecond(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 获取评价列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/appraise/list.json")
    Call<HttpBaseBean<List<Appraise>>> getAppraiseListALL(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
    @GET("/api/appraise/list.json")
    Call<HttpBaseBean<List<Appraise>>> getAppraiseList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("businessType") String businessType);

    /**
     * 提交评价
     * @param
     * @return
     */
    @POST("/api/appraise/save.json")
    Call<HttpBaseBean<Object>> commitAppraise(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 是否可以修改评价
     * @param orderId
     * @return
     */
    @GET("/api/appraise/update_status.json")
    Call<HttpBaseBean<UpdateStatus>> isUpdateAppraise(@Query("orderId") String orderId);

    /**
     * 查看评价详情
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<AppraiseDetial>> seeAppraiseDetail(@Url String url);

    /**
     * 取消预约是否退钱
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<Refund>> isBackMoneyReservation(@Url String url);
    /**
     * 我的预约-最新预约
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/order/appointment/list_new.json")
    Call<HttpBaseBean<List<Reservation>>> getReservationNewList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 我的预约-预约记录
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/order/appointment/list_end.json")
    Call<HttpBaseBean<List<ReservationRecord>>> getReservationRecordList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 取消支付
     *
     * @param url
     * @return
     */
    @PUT
    Call<HttpBaseBean<Object>> cancalPay(@Url String url);

    /**
     * 取消预约
     *
     * @param url
     * @return
     */
    @PUT
    Call<HttpBaseBean<Object>> cancalOrder(@Url String url);

    /**
     * 支付预约订单
     *
     * @param businessId
     * @param businessType
     * @return
     */
    @POST("/api/order/to_pay.json")
    Call<HttpBaseBean<WetChat>> toPay(@Query("businessId") String businessId, @Query("businessType") String businessType);


    /**
     * 我的问诊列表-最新问诊
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/order/inquiry/list_new.json")
    Call<HttpBaseBean<List<ChatOnLineList>>> getChatOnlineNewList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 我的问诊列表-问诊记录
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/order/inquiry/list_end.json")
    Call<HttpBaseBean<List<ChatOnLineList>>> getChatOnlineRecordList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 我的问诊列表-最新问诊 支付问诊
     *
     * @param businessId
     * @param businessType
     * @return
     */
    @POST("/api/pay_order/to_pay.json")
    Call<HttpBaseBean<WetChat>> toPayChatOnline(@Query("businessId") String businessId, @Query("businessType") String businessType, @Query("clientType") String clientType);

    /**
     * 中药调理服务订单支付
     * @param urlParams
     * @return
     */
    @POST("/api/order/my/pay_drug.json")
    Call<HttpBaseBean<WetChat>> requestWechatDrug(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 我的处方单-列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/order/my/list.json")
    Call<HttpBaseBean<List<OrdonnanceListRespone>>> getOrdonnanceList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("type") String type);

    /**
     * 我的处方单-详情
     *
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<OrdonnanceDetailRespone>> getOrdonnanceDetail(@Url String url);

    /**
     * 我的处方单-取消订单
     *
     * @param url
     * @return
     */
    @PUT
    Call<HttpBaseBean<Object>> getOrdonnanceCancel(@Url String url);

    /**
     * 我的关注-关注的医生
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/follow/list_doctor.json")
    Call<HttpBaseBean<List<MyConcernResponse>>> getMyConcernList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 我的处方单-取消订单
     *
     * @param doctorId
     * @return
     */
    @PUT("/api/follow/cancel_relation.json")
    Call<HttpBaseBean<Object>> getConcernCancel(@Query("doctorId") String doctorId);

    /**
     * 我的关注-医生动态
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/follow/list_news.json")
    Call<HttpBaseBean<List<DoctorDynamicRespone>>> getDoctorDynamicList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 消息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/message/list.json")
    Call<HttpBaseBean<List<Message>>> getMessageList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
    /**
     * 用户地址列表
     *
     * @return
     */
    @GET("/api/shippingaddress/list.json")
    Call<HttpBaseBean<List<MyAdressResponse>>> getMyAdressList();

    /**
     * 设置默认地址
     * @param id
     * @return
     */
    @PUT("/api/shippingaddress/set_preferred.json")
    Call<HttpBaseBean<Object>> setMyAdressMand(@Query("id") String id);

    /**
     * 提供获取指定ID的配送地址
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<SingleAddress>> getMyAdressforId(@Url String url);


    /**
     * 标记单个已读
     * @param url
     * @return
     */
    @PUT
    Call<HttpBaseBean<Object>> toReadSingle(@Url String url);

    /**
     * 删除单个
     * @param url
     * @return
     */
    @PUT
    Call<HttpBaseBean<Object>> toDeleteSingle(@Url String url);


    /**
     * 标记已读
     * @return
     */
    @PUT("/api/message/read_all.json")
    Call<HttpBaseBean<Object>> toReadAll();

    /**
     * 清空已读
     * @return
     */
    @PUT("/api/message/empty_all.json")
    Call<HttpBaseBean<Object>> toClearIsRead();


    /**
     * 获取消息数量
     * @return
     */
    @GET("/api/message/unread_num.json")
    Call<HttpBaseBean<Integer>> getMessageCount();

    /**
     * 用户基本信息
     * @return
     */
    @GET("/api/user/user_info.json")
    Call<HttpBaseBean<UsrInfomation>> getUserInfo();

    /**
     * 修改保存用户信息
     * @param urlParams
     * @return
     */
    @PUT("/api/user/update_user_info.json")
    Call<HttpBaseBean<Object>> remakeuserInfo(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 新增地址
     * @param urlParams
     * @return
     */
    @POST("/api/shippingaddress/save.json")
    Call<HttpBaseBean<MyAddress>> addAddress(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 修改地址
     * @param urlParams
     * @return
     */
    @PUT("/api/shippingaddress/update.json")
    Call<HttpBaseBean<Object>> reSetAddress(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 删除
     * @param id
     * @return
     */
    @DELETE("/api/shippingaddress/delete.json")
    Call<HttpBaseBean<Object>> deleteAddress(@Query("id") String id);

    /**
     * 我的优惠券列表
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    @GET("/api/coupon/list.json")
    Call<HttpBaseBean<List<MyCpupons>>> getMyCouponsList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("type") String type);

    /**
     * 选择优惠券后实际价格
     * @param couponId
     * @param fee
     * @return
     */
    @POST("/api/pay_order/get_fact_fee.json")
    Call<HttpBaseBean<FactFee>> getRealyPrice(@Query("couponId") String couponId, @Query("fee") double fee);

    /**
     * 动态详情
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<DoctorNewDetial>> getDoctorNewsDetial(@Url String url);

    /**
     * 动态列表
     * @param url
     * @param pageNum
     *@param num @return
     */
    @GET
    Call<HttpBaseBean<List<DoctorNewList>>> getDoctorNewsList(@Url String url, @Query("pageNum") int pageNum, @Query("pageSize") int num);

    /**
     * 购买中药服务
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<OrdonnanceDetailRespone>> getBuyMedicineDetial(@Url String url);

    /**
     * 问诊单详情
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<ChatOnLinePaper>> getChatOnLinePaper(@Url String url);

    /**
     * 提交售后
     * @param urlParams
     * @return
     */
    @POST("/api/order/my/after_sales_apply.json")
    Call<HttpBaseBean<Object>> afterSales(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 意见反馈
     * @param urlParams
     * @return
     */
    @POST("/api/feedback/save.json")
    Call<HttpBaseBean<Object>> feedBack(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 关于我们提交信息
     * @param urlParams
     * @return
     */
    @POST("/api/home/save_contact_us.json")
    Call<HttpBaseBean<Object>> requestCommit(@Body ConcurrentHashMap<String, Object> urlParams);

    /**
     * 获取实际支付费用
     * @param couponId
     * @param fee
     * @return
     */
    @POST("/api/pay_order/get_fact_fee.json")
    Call<HttpBaseBean<FactFee>> getFactMoney(@Query("couponId") String couponId, @Query("fee") double fee);

    /**
     * 问诊单填写第几步
     * @param url
     * @param recordId
     * @return
     */
    @GET
    Call<HttpBaseBean<WichStep>> getWhichStep(@Url String url, @Query("recordId") String recordId);

    /**
     * 支付成功后确认
     * @param
     * @return
     */
    @GET("/api/pay_order/check_pay.json")
    Call<HttpBaseBean<Object>> makePaySure(@Query("businessId") String businessId, @Query("businessType") String businessType);

    /**
     * 我的处方单列表等入口
     * @param url
     * @return
     */
    @GET
    Call<HttpBaseBean<Jump>> getJumpType(@Url String url);

    /**
     * 关于我们
     * @return
     */
    @GET("/api/home/about_us.json")
    Call<HttpBaseBean<About>> getAboutMsg();

    // 二期接口

    /**
     * 我的患者  健康档案
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/patient/list_page.json")
    Call<HttpBaseBean<List<MyPatient>>> gteMyPatientList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 健康档案列表
     * @param pageNum
     * @param pageSize
     * @param patientId
     * @param customTime
     * @param startTime
     * @param endTime
     * @return
     */
    @GET("/api/order/inquiry/list_record.json")
    Call<HttpBaseBean<List<ChatOnLineList>>> gteHealthRecord(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
    @Query("patientId") String patientId, @Query("customTime") int customTime, @Query("startTime") String startTime, @Query("endTime") String endTime);

    @GET("/api/order/inquiry/list_record.json")
    Call<HttpBaseBean<List<ChatOnLineList>>> gteHealthRecord2(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
                                                             @Query("patientId") String patientId, @Query("customTime") int customTime);
    /**
     * 客服 id
     * @return
     */
    @GET("/api/user/im_info.json")
    Call<HttpBaseBean<Customer>> getServiceId();

    /**
     * 医生主页--康复病例
     * @param doctorId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("/api/doctor/list_cases_cure.json")
    Call<HttpBaseBean<List<HealthTemp>>> gteHealthTempList(@Query("doctorId") String doctorId, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 康复病例 详情
     * @param id
     * @return
     */
    @GET("/api/doctor/cases_cure_info.json")
    Call<HttpBaseBean<HealthTempDetail>> gteHealthTempDetail(@Query("id") String id);

    /**
     * 保存选择
     * @param recordId
     * @param chooseType
     * @return
     */
    @PUT("/api/order/inquiry/save_urgent_choose")
    Call<HttpBaseBean<Object>> requsetExpressChoose(@Query("recordId") String recordId, @Query("chooseType") String chooseType);
}
