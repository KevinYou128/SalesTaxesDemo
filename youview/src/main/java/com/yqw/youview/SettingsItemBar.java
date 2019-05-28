package com.yqw.youview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 设置界面的item
 * @Author youqinwei
 */
public class SettingsItemBar extends ConstraintLayout {
    ConstraintLayout cl_settings_item_bar;
    TextView tv_left;
    TextView tv_right;
    ImageView iv_left;
    ImageView iv_right;
    View v_line_top;
    View v_line_bottom;

    private int backColor;
    private Drawable leftImageSrc;
    private Drawable rightImageSrc;
    private int leftTextColor;
    private String leftText;
    private float leftTextSize;
    private int rightTextColor;
    private String rightText;
    private float rightTextSize;

    private float topLineMarginLeft;//
    private float topLineMarginRight;
    private float topLineHeight;
    private boolean topLineShow;//显示线条与否
    private int topLineColor;//
    private float bottomLineMarginLeft;
    private float bottomLineMarginRight;
    private float bottomLineHeight;
    private boolean bottomLineShow;//
    private int bottomLineColor;//

    public SettingsItemBar(Context context) {
        super(context);
        initView(context);
    }

    public SettingsItemBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context,attrs);
        initView(context);
    }

    public SettingsItemBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context,attrs);
        initView(context);
    }

    /**
     * 解析attrs.xml自定义的值
     * @param context
     * @param attrs
     */
    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingsItemBar);
//        backColor = mTypedArray.getResourceId(R.styleable.SettingsItemBar_itemBackground,
//                getResources(R.drawable.select_white));
        leftTextColor = mTypedArray.getColor(R.styleable.SettingsItemBar_leftTextColor,
                getResources().getColor(R.color.black_text));
        leftText = mTypedArray.getString(R.styleable.SettingsItemBar_leftText);
        leftTextSize = mTypedArray.getDimension(R.styleable.SettingsItemBar_leftTextSize,getResources().getDimension(R.dimen.blog_title));

        rightTextColor = mTypedArray.getColor(R.styleable.SettingsItemBar_rightTextColor,
                getResources().getColor(R.color.colorPrimary));
        rightText = mTypedArray.getString(R.styleable.SettingsItemBar_rightText);
        rightTextSize = mTypedArray.getDimension(R.styleable.SettingsItemBar_rightTextSize,getResources().getDimension(R.dimen.blog_content));

        leftImageSrc = mTypedArray.getDrawable(R.styleable.SettingsItemBar_leftImageSrc);
        rightImageSrc = mTypedArray.getDrawable(R.styleable.SettingsItemBar_rightImageSrc);

        topLineMarginLeft = mTypedArray.getDimension(R.styleable.SettingsItemBar_topLine_marginLeft,0);
        topLineMarginRight = mTypedArray.getDimension(R.styleable.SettingsItemBar_topLine_marginRight,0);
        topLineHeight = mTypedArray.getDimension(R.styleable.SettingsItemBar_topLine_height,1);
        topLineShow = mTypedArray.getBoolean(R.styleable.SettingsItemBar_topLine_show, false);
        topLineColor = mTypedArray.getColor(R.styleable.SettingsItemBar_topLine_color, getResources().getColor(R.color.divider_color));
        bottomLineMarginLeft = mTypedArray.getDimension(R.styleable.SettingsItemBar_bottomLine_marginLeft,20);
        bottomLineMarginRight = mTypedArray.getDimension(R.styleable.SettingsItemBar_bottomLine_marginRight,0);
        bottomLineHeight = mTypedArray.getDimension(R.styleable.SettingsItemBar_bottomLine_height,1);
        bottomLineShow = mTypedArray.getBoolean(R.styleable.SettingsItemBar_bottomLine_show, false);
        bottomLineColor = mTypedArray.getColor(R.styleable.SettingsItemBar_bottomLine_color, getResources().getColor(R.color.divider_color));
        backColor = mTypedArray.getColor(R.styleable.SettingsItemBar_itemBackground, getResources().getColor(R.color.white));

        //获取资源后要及时回收
        mTypedArray.recycle();
    }
    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_settings_item,this,true);
        cl_settings_item_bar = findViewById(R.id.cl_settings_item_bar);
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        iv_right = findViewById(R.id.iv_right);
        iv_left = findViewById(R.id.iv_left);
        v_line_top = findViewById(R.id.v_line_top);
        v_line_bottom = findViewById(R.id.v_line_bottom);

        cl_settings_item_bar.setBackgroundColor(backColor);
        tv_left.setTextColor(leftTextColor);
        tv_left.setTextSize(TypedValue.COMPLEX_UNIT_PX,leftTextSize);//因为获取到的是px值，所以这样设置字体大小
        tv_left.setText(leftText);
        tv_right.setTextColor(rightTextColor);
        tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX,leftTextSize);//因为获取到的是px值，所以这样设置字体大小
        tv_right.setText(rightText);
        if (null == leftImageSrc){
            iv_left.setVisibility(View.GONE);
        }else {
            iv_left.setVisibility(View.VISIBLE);
            iv_left.setImageDrawable(leftImageSrc);
        }
        if (null == rightImageSrc){
            iv_right.setVisibility(View.GONE);
        }else {
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setImageDrawable(rightImageSrc);
        }
        //处理分割线
        LayoutParams topParams = (LayoutParams) v_line_top.getLayoutParams();
        topParams.leftMargin = (int) topLineMarginLeft;
        topParams.rightMargin = (int) topLineMarginRight;
        topParams.height = (int) topLineHeight;
        v_line_top.setLayoutParams(topParams);
        if (topLineShow){
            v_line_top.setVisibility(VISIBLE);
        }else {
            v_line_top.setVisibility(GONE);
        }
        v_line_top.setBackgroundColor(getResources().getColor(R.color.divider_color));
        LayoutParams bottomParams = (LayoutParams) v_line_bottom.getLayoutParams();
        bottomParams.leftMargin = (int) bottomLineMarginLeft;
        bottomParams.rightMargin = (int) bottomLineMarginRight;
        bottomParams.height = (int) bottomLineHeight;
        v_line_bottom.setLayoutParams(bottomParams);
        if (bottomLineShow){
            v_line_bottom.setVisibility(VISIBLE);
        }else {
            v_line_bottom.setVisibility(GONE);
        }
        v_line_bottom.setBackgroundColor(getResources().getColor(R.color.divider_color));
    }

    public void setLeftText(String text){
        if (!TextUtils.isEmpty(text)){
            tv_left.setText(text);
        }
    }
    public void setRightText(String text){
        if (!TextUtils.isEmpty(text)){
            tv_right.setText(text);
        }
    }

}
