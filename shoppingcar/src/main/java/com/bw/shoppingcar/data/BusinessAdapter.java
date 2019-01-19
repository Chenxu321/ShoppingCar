package com.bw.shoppingcar.data;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.bw.shoppingcar.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BusinessAdapter extends BaseQuickAdapter<JsonBean.DataBean,BaseViewHolder> {

    OnBusinessItemClickLisenter onBusinessItemClickLisenter;
    private RecyclerView recycle_goods;
    public interface OnBusinessItemClickLisenter{
        public void onCallBack();
    }
    public void setOnBusinessItemClickLisenter(OnBusinessItemClickLisenter onBusinessItemClickLisenter){
        this.onBusinessItemClickLisenter=onBusinessItemClickLisenter;
    }

    public BusinessAdapter(int layoutResId, @Nullable List<JsonBean.DataBean> dataBeanList) {
        super(layoutResId, dataBeanList);
    }

    @Override
    protected void convert(BaseViewHolder helper, final JsonBean.DataBean item) {
        helper.setText(R.id.business_name,item.getSellerName());
        final CheckBox business_checkbox = helper.getView(R.id.business_checkbox);
       business_checkbox.setOnClickListener(null);
        business_checkbox.setChecked(item.getBusinessChecked());
        recycle_goods = helper.getView(R.id.recycle_goods);
        List<JsonBean.DataBean.ListBean> list = item.getList();
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recycle_goods.setLayoutManager(manager);
        final GoodsAdapter adapter = new GoodsAdapter(R.layout.goods_item, list);
        recycle_goods.setAdapter(adapter);
        adapter.setOnGoodsItemClickListener(new GoodsAdapter.OnGoodsItemClickListener() {
            @Override
            public void onCallBack() {
                boolean result = true;
                for (int i = 0; i < item.getList().size(); i++) {
                    result = result & item.getList().get(i).getGoodsChecked();
                }
                business_checkbox.setChecked(result);
                //刷新适配器
                adapter.notifyDataSetChanged();
                //把最后的状态进行回传
                onBusinessItemClickLisenter.onCallBack();
            }
        });
        business_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取商品类别勾选状态
                //外层商品类别条目获取的关键：cb_business.isChecked()
                for (int i = 0; i < item.getList().size(); i++) {
                    item.getList().get(i).setGoodsChecked(business_checkbox.isChecked());
                }
                item.setBusinessChecked(business_checkbox.isChecked());
                notifyDataSetChanged();
                //把最后的状态进行回传
                onBusinessItemClickLisenter.onCallBack();
            }
        });
    }
}
