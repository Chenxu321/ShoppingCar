package com.bw.shoppingcar.di.constant;

import com.bw.shoppingcar.data.JsonBean;

import java.util.List;

public interface ShopConstant {
    public interface ShopView{
        public void showData(List<JsonBean.DataBean> data);
    }
    public interface ShopPresenter<ShopView>{
        public void attachView(ShopView shopView);
        public void detachView(ShopView shopView);
        public void requestData();
    }
    public interface ShopModel{
        public void containData(CallBack callBack);
        public interface CallBack{
            public void CallBack(List<JsonBean.DataBean> data);
        }
    }
}
