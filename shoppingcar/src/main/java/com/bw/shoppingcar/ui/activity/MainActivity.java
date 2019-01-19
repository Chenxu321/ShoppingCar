package com.bw.shoppingcar.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.shoppingcar.R;
import com.bw.shoppingcar.data.BusinessAdapter;
import com.bw.shoppingcar.data.JsonBean;
import com.bw.shoppingcar.di.constant.ShopConstant;
import com.bw.shoppingcar.di.presenter.ShopPresenterImpi;
import com.bw.shoppingcar.ui.wedgit.Calculator;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShopConstant.ShopView {

    private ShopConstant.ShopPresenter presenterImpi;
    private RecyclerView recycle;
    private CheckBox all;
    private BusinessAdapter adapter;
    private List<JsonBean.DataBean> dataBeanList;
    private TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = (TextView) findViewById(R.id.text_count);
        all = (CheckBox) findViewById(R.id.checkboxall);
        recycle = (RecyclerView) findViewById(R.id.recycle);
        presenterImpi = new ShopPresenterImpi();
        presenterImpi.attachView(this);
        presenterImpi.requestData();


    }

    @Override
    public void showData(final List<JsonBean.DataBean> data) {
                    all.setOnCheckedChangeListener(null);
                    all.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i=0;i<dataBeanList.size();i++){
                                dataBeanList.get(i).setBusinessChecked(all.isChecked());
                                for (int j=0;j<dataBeanList.get(i).getList().size();j++){
                                    dataBeanList.get(i).getList().get(j).setGoodsChecked(all.isChecked());
                                }
                            }
                            adapter.notifyDataSetChanged();
                            //计算总价
                            CalculatortotalCount();
                        }
                    });
                    dataBeanList = new ArrayList<JsonBean.DataBean>();
                    dataBeanList.addAll(data);
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                    recycle.setLayoutManager(manager);
                    adapter = new BusinessAdapter(R.layout.business_item, dataBeanList);
                    recycle.setAdapter(adapter);
                    adapter.setOnBusinessItemClickLisenter(new BusinessAdapter.OnBusinessItemClickLisenter() {
                        @Override
                        public void onCallBack() {
                            boolean result=true;
                            for (int i=0;i<dataBeanList.size();i++){
                                boolean businessChecked = dataBeanList.get(i).getBusinessChecked();
                                result=result&businessChecked;
                                for (int j=0;j<dataBeanList.get(i).getList().size();j++){
                                    boolean goodsChecked = dataBeanList.get(i).getList().get(j).getGoodsChecked();
                                    result=result & goodsChecked;
                                }
                            }
                            all.setChecked(result);
                            //计算总价
                            CalculatortotalCount();
                        }
                    });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterImpi.detachView(this);
    }
    private void CalculatortotalCount(){
        double totalCount=0;
        //外层条目
        for (int i = 0; i < dataBeanList.size(); i++) {
            //内层条目
            for (int j = 0; j < dataBeanList.get(i).getList().size(); j++) {
                //判断内层条目是否勾选
                if (dataBeanList.get(i).getList().get(j).getGoodsChecked() == true){
                    //获取商品数据*商品价格
                    double price = dataBeanList.get(i).getList().get(j).getPrice();
                    int defalutNumber = dataBeanList.get(i).getList().get(j).getDefaultNumber();
                    double goodsPrice = price * defalutNumber;
                    totalCount = totalCount+goodsPrice;
                }
            }
        }
        count.setText("总价是："+String.valueOf(totalCount));
    }

}
