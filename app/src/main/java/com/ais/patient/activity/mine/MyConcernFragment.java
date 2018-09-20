package com.ais.patient.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ais.patient.R;
import com.ais.patient.activity.main.DoctorInfomationActivity;
import com.ais.patient.adapter.DoctorDynamicAdapter;
import com.ais.patient.adapter.MyConcernAdapter;
import com.ais.patient.base.BaseFragment;
import com.ais.patient.been.DoctorDynamicRespone;
import com.ais.patient.been.HttpBaseBean;
import com.ais.patient.been.MultiItemView;
import com.ais.patient.been.MyConcernResponse;
import com.ais.patient.http.BaseCallback;
import com.ais.patient.http.RetrofitFactory;
import com.ais.patient.util.ToastUtils;
import com.ais.patient.widget.recycleview.DefineBAGRefreshWithLoadView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/8/9.
 */

public class MyConcernFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.define_sliding_bga)
    BGARefreshLayout defineSlidingBga;

    private String type;//订单类型
    private int pageNum = 1;
    private int pageSize = 10;
    private Context context;

    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView;
    private MyConcernAdapter adapter;
    private List<MultiItemView<MyConcernResponse>> datas = new ArrayList<>();
    private List<MultiItemView<DoctorDynamicRespone>> datas_DoctorDynamic = new ArrayList<>();
    private DoctorDynamicAdapter dynamicAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.my_concern_fragment_layout;
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        context = getActivity();
        type = getArguments().getString("type");


        Log.i("type", type);
        switch (type) {
            case "0"://关注的医生
                adapter = new MyConcernAdapter(context, datas);
                mRecycleView.setAdapter(adapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(context));
                adapter.setOnItemClickListener(this);
                adapter.setOnItemChildClickListener(this);

                datas.clear();
                getData();
                break;
            case "1"://医生动态
                dynamicAdapter = new DoctorDynamicAdapter(context, datas_DoctorDynamic);
                mRecycleView.setAdapter(dynamicAdapter);
                mRecycleView.setLayoutManager(new LinearLayoutManager(context));
                dynamicAdapter.setOnItemClickListener(this);
                dynamicAdapter.setOnItemChildClickListener(this);

                datas_DoctorDynamic.clear();
                getDoctorDynamic();
                break;
        }

        setBgaRefreshLayout();


        //设置刷新和加载监听
        defineSlidingBga.setDelegate(this);
    }

    /**
     * 获取关注的医生列表数据
     */
    private void getData() {
        Call<HttpBaseBean<List<MyConcernResponse>>> call = RetrofitFactory.getInstance(context).getMyConcernList(pageNum, pageSize);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<MyConcernResponse>>() {

            @Override
            public void onSuccess(List<MyConcernResponse> reservations, String info) {

                for (MyConcernResponse bean : reservations) {
                    MultiItemView<MyConcernResponse> multiItemViewBody = new MultiItemView<>(MultiItemView.BODY);
                    MyConcernResponse response = new MyConcernResponse();
                    response.setImage(bean.getImage());
                    response.setName(bean.getName());
                    response.setId(bean.getId());
                    response.setTitles(bean.getTitles());
                    response.setDepart(bean.getDepart());
                    response.setMedicalInstitutions(bean.getMedicalInstitutions());
                    response.setDiseaseExpertise(bean.getDiseaseExpertise());
                    multiItemViewBody.setBean(response);
                    datas.add(multiItemViewBody);

                    MultiItemView<MyConcernResponse> multiItemViewFooter = new MultiItemView<>(MultiItemView.FOOTER);
                    MyConcernResponse response1 = new MyConcernResponse();
                    response1.setFee(bean.getFee());
                    response1.setId(bean.getId());
                    multiItemViewFooter.setBean(response1);
                    datas.add(multiItemViewFooter);
                }
                adapter.notifyDataSetChanged();
                if (pageNum != 1) {
                    mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多了");
                }
            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context, info);
            }
        });
    }

    /**
     * 医生动态列表数据
     */
    private void getDoctorDynamic() {
        Call<HttpBaseBean<List<DoctorDynamicRespone>>> call = RetrofitFactory.getInstance(context).getDoctorDynamicList(pageNum, pageSize);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener<List<DoctorDynamicRespone>>() {

            @Override
            public void onSuccess(List<DoctorDynamicRespone> reservations, String info) {

                for (DoctorDynamicRespone bean : reservations) {
                    MultiItemView<DoctorDynamicRespone> multiItemViewBody = new MultiItemView<>(MultiItemView.BODY);
                    DoctorDynamicRespone response = new DoctorDynamicRespone();
                    response.setImage(bean.getImage());
                    response.setArticleId(bean.getArticleId());
                    response.setContent(bean.getContent());
                    response.setName(bean.getName());
                    response.setTitle(bean.getTitle());
                    response.setType(bean.getType());
                    response.setPics(bean.getPics());
                    multiItemViewBody.setBean(response);
                    datas_DoctorDynamic.add(multiItemViewBody);

                    MultiItemView<DoctorDynamicRespone> multiItemViewFooter = new MultiItemView<>(MultiItemView.FOOTER);
                    DoctorDynamicRespone response1 = new DoctorDynamicRespone();
                    response1.setArticleId(bean.getArticleId());
                    multiItemViewFooter.setBean(response1);
                    datas_DoctorDynamic.add(multiItemViewFooter);
                }
                dynamicAdapter.notifyDataSetChanged();
                if (pageNum != 1) {
                    mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多了");
                }
            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context, info);
            }
        });
    }

    /**
     * 取消订单
     */
    private void getCancelConcern(String orderId, final int position) {
        Call<HttpBaseBean<Object>> call = RetrofitFactory.getInstance(context).unSaveDoctor(orderId);
        new BaseCallback(call).handleResponse(new BaseCallback.ResponseListener() {
            @Override
            public void onSuccess(Object o, String info) {
                datas.clear();
                getData();
                adapter.notifyDataSetChanged();
                ToastUtils.show(context, "取消成功");
            }

            @Override
            public void onFailure(String info) {
                ToastUtils.show(context, info);
            }
        });
    }

    /**
     * 请求网络数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    switch (type) {
                        case "0":
                            datas.clear();
                            getData();
                            break;
                        case "1":
                            datas_DoctorDynamic.clear();
                            getDoctorDynamic();
                            break;
                    }
                    if (defineSlidingBga != null) {
                        defineSlidingBga.endRefreshing();
                    }
                    break;
                case 1:
                    switch (type) {
                        case "0":
                            getData();
                            break;
                        case "1":
                            getDoctorDynamic();
                            break;
                    }
                    if (defineSlidingBga != null) {
                        defineSlidingBga.endLoadingMore();
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置 BGARefreshLayout刷新和加载
     */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(context, true, true);
        //设置刷新样式
        defineSlidingBga.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载更多");
        mRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * 刷新
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        pageNum = 1;
        handler.sendEmptyMessage(0);
    }

    /**
     * 加载
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        pageNum++;
        handler.sendEmptyMessage(1);
        return true;
    }

    /**
     * 列表点击事件
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if ("0".equals(type)) {
            switch (datas.get(position).getItemType()) {
                case MultiItemView.BODY:
                    Intent intent = new Intent(context, DoctorInfomationActivity.class);
                    intent.putExtra("id", datas.get(position).getBean().getId());
                    startActivity(intent);
                    break;

            }
        }
    }

    /**
     * 列表子元素点击事件
     *
     * @param madapter
     * @param view
     * @param position
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter madapter, View view, int position) {

        switch (view.getId()) {
            case R.id.tv_cancel:
                getCancelConcern(datas.get(position).getBean().getId(),position);
                break;
            case R.id.tv_look_detail:
                Intent intent = new Intent(context, DoctorDetailActivity.class);
                intent.putExtra("id", datas_DoctorDynamic.get(position).getBean().getArticleId());
                startActivity(intent);
                break;
        }


    }
}
