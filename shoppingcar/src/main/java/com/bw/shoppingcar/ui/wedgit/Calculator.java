package com.bw.shoppingcar.ui.wedgit;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.shoppingcar.R;


public class Calculator extends LinearLayout implements View.OnClickListener {

    private Button jia;
    private Button jian;
    private TextView text_calculator;
    private String numberstring;


    public Calculator(Context context) {
        super(context);
    }

    public Calculator(Context context,  AttributeSet attrs) {
        super(context, attrs);
        View rootview = LayoutInflater.from(context).inflate(R.layout.calculator_item, this);
        jia = rootview.findViewById(R.id.button_jia);
        jian = rootview.findViewById(R.id.button_jian);
        text_calculator = rootview.findViewById(R.id.text_calculator);
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        numberstring = text_calculator.getText().toString();
        int number = Integer.parseInt(numberstring);
        switch (v.getId()){
            case R.id.button_jia:
                number=number+1;
                text_calculator.setText(String.valueOf(number));
                oncalculatorListener.jia(number);
                break;
            case R.id.button_jian:
                number=number-1;
                if (number<0){
                    number=0;
                    text_calculator.setText(String.valueOf(number));
                }
                text_calculator.setText(String.valueOf(number));
                oncalculatorListener.jian(number);
                break;
        }
    }
    OncalculatorListener oncalculatorListener;
    public interface OncalculatorListener{
        public void jia(int number);
        public void jian(int number);
    }
    public void setOncalculatorListener(OncalculatorListener oncalculatorListener){
        this.oncalculatorListener=oncalculatorListener;
    }
}
