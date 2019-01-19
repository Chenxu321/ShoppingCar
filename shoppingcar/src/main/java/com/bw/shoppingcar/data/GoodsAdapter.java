package com.bw.shoppingcar.data;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.shoppingcar.R;
import com.bw.shoppingcar.ui.wedgit.Calculator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class GoodsAdapter extends BaseQuickAdapter<JsonBean.DataBean.ListBean,BaseViewHolder> {
    OnGoodsItemClickListener onGoodsItemClickListener;

    public interface OnGoodsItemClickListener{
        public void onCallBack();
    }
    public void setOnGoodsItemClickListener(OnGoodsItemClickListener onGoodsItemClickListener) {
        this.onGoodsItemClickListener = onGoodsItemClickListener;
    }

    public GoodsAdapter(int layoutResId, @Nullable List<JsonBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final JsonBean.DataBean.ListBean item) {
        helper.setText(R.id.goods_name,item.getTitle());
        helper.setText(R.id.goods_price,"￥"+item.getPrice());
        CheckBox goods_checkbox = helper.getView(R.id.goods_checkbox);
        goods_checkbox.setOnClickListener(null);
        goods_checkbox.setChecked(item.getGoodsChecked());
        String imagesString = item.getImages();
        String[] imagesStr = imagesString.split("\\|");
        Glide.with(mContext).load(imagesStr[0]).into((ImageView) helper.getView(R.id.goods_image));
        goods_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setGoodsChecked(isChecked);
                onGoodsItemClickListener.onCallBack();
            }
        });
        Calculator calculator=helper.getView(R.id.calculator);
        calculator.setOncalculatorListener(new Calculator.OncalculatorListener() {
            @Override
            public void jia(int number) {
                item.setDefaultNumber(number);
                onGoodsItemClickListener.onCallBack();
            }

            @Override
            public void jian(int number) {
                item.setDefaultNumber(number);
                onGoodsItemClickListener.onCallBack();
            }
        });
    }
}
