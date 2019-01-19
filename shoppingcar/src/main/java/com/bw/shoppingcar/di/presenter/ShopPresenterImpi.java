package com.bw.shoppingcar.di.presenter;

import com.bw.shoppingcar.data.JsonBean;
import com.bw.shoppingcar.di.constant.ShopConstant;
import com.bw.shoppingcar.di.model.ShopModelImpi;

import java.lang.ref.SoftReference;
import java.util.List;

public class ShopPresenterImpi implements ShopConstant.ShopPresenter<ShopConstant.ShopView> {
    ShopConstant.ShopView shopView;
    private ShopConstant.ShopModel modelImpi;
    private SoftReference<ShopConstant.ShopView> reference;

    @Override
    public void attachView(ShopConstant.ShopView shopView) {
        this.shopView=shopView;
        reference = new SoftReference<>(shopView);
        modelImpi = new ShopModelImpi();
    }

    @Override
    public void detachView(ShopConstant.ShopView shopView) {
        reference.clear();
    }

    @Override
    public void requestData() {
        modelImpi.containData(new ShopConstant.ShopModel.CallBack() {
            @Override
            public void CallBack(List<JsonBean.DataBean> data) {
                shopView.showData(data);
            }
        });
    }
}
