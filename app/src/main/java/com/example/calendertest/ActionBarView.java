package com.example.calendertest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActionBarView extends RelativeLayout {
    private RelativeLayout action_bar_layout;
    private TextView txt_title;
    private ImageView img_left;
    private ImageView img_right;
    private TextView txt_right;
    private int mBackgroudColor= getResources().getColor(R.color.colorAccent);
    private int mTextColor= Color.WHITE;
    private int mTitleSize= 20;
    private int mTextSize= 16;
    public ActionBarView(Context context) {
        super(context);
        initView(context);
    }
    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public ActionBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.actionbar, this, true);
        txt_title= (TextView) findViewById(R.id.txt_title);
        img_left= (ImageView) findViewById(R.id.img_left);
        img_right= (ImageView) findViewById(R.id.img_right);
        txt_right= (TextView) findViewById(R.id.txt_right);
        action_bar_layout= (RelativeLayout) findViewById(R.id.action_bar_layout);
        action_bar_layout.setBackgroundColor(mBackgroudColor);
        txt_title.setTextColor(mTextColor);
        txt_right.setTextColor(mTextColor);
        txt_title.setTextSize(mTitleSize);
        txt_right.setTextSize(mTextSize);
    }
    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            txt_title.setText(title);
        }
    }
    public void setLeftListener(OnClickListener onClickListener){
        img_left.setOnClickListener(onClickListener);
    }
    public void setRightListener(OnClickListener onClickListener){
        img_right.setOnClickListener(onClickListener);
    }
    public void setRightTextListener(OnClickListener onClickListener){
        txt_right.setOnClickListener(onClickListener);
    }
    public void setRightText(boolean isshow,String text){
        if(isshow){
            txt_right.setVisibility(VISIBLE);
            img_right.setVisibility(GONE);
            if(!TextUtils.isEmpty(text)){
                txt_right.setText(text);
            }
        }
    }
    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray mTypedArray=context.obtainStyledAttributes(attrs,R.styleable.ActionBarView);
        mBackgroudColor=mTypedArray.getColor(R.styleable.ActionBarView_background_color,getResources().getColor(R.color.colorAccent));
        mTextColor=mTypedArray.getColor(R.styleable.ActionBarView_txt_color, Color.WHITE);
        mTextSize=mTypedArray.getInteger(R.styleable.ActionBarView_txt_size,16);
        mTitleSize=mTypedArray.getInteger(R.styleable.ActionBarView_title_size,20);
        //获取资源后要及时回收
        mTypedArray.recycle();
    }
}
