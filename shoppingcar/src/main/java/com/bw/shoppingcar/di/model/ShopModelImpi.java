package com.bw.shoppingcar.di.model;

import com.bw.shoppingcar.data.Constarts;
import com.bw.shoppingcar.data.JsonBean;
import com.bw.shoppingcar.di.constant.ShopConstant;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

public class ShopModelImpi implements ShopConstant.ShopModel {
    @Override
    public void containData(final CallBack callBack) {
        OkGo.<String>get(Constarts.SHOPPINGCART_URL).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String responsedata=response.body().toString();
                Gson gson=new Gson();
                JsonBean jsonBean = gson.fromJson(responsedata, JsonBean.class);
                List<JsonBean.DataBean> data = jsonBean.getData();
                callBack.CallBack(data);
            }
        });
    }
}
