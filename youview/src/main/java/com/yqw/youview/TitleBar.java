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
 * 通用toolbar
 * @Author youqinwei
 */
public class TitleBar extends ConstraintLayout{
    ImageView iv_left;
    TextView tv_title;
    ImageView iv_right;
    ConstraintLayout cl_toolbar;

    private int backColor;
    private int titleTextColor;
    private Drawable leftImageSrc;
    private Drawable rightImageSrc;
    private String titleText;
    private float titleTextSize;

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context,attrs);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
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
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        backColor = mTypedArray.getColor(R.styleable.TitleBar_titleBackground,
                getResources().getColor(R.color.colorPrimaryWhite));
        titleTextColor = mTypedArray.getColor(R.styleable.TitleBar_titleTextColor,
                getResources().getColor(R.color.black_text));
        titleText = mTypedArray.getString(R.styleable.TitleBar_titleText);
        leftImageSrc = mTypedArray.getDrawable(R.styleable.TitleBar_leftImageSrc);
        rightImageSrc = mTypedArray.getDrawable(R.styleable.TitleBar_rightImageSrc);
        titleTextSize = mTypedArray.getDimension(R.styleable.TitleBar_titleTextSize,getResources().getDimension(R.dimen.title_size));

        //获取资源后要及时回收
        mTypedArray.recycle();
    }
    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_title_bar,this,true);
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        tv_title = findViewById(R.id.tv_title);
        cl_toolbar = findViewById(R.id.cl_toolbar);

        cl_toolbar.setBackgroundColor(backColor);
        tv_title.setTextColor(titleTextColor);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        tv_title.setText(titleText);
        if (null == leftImageSrc){
            iv_left.setVisibility(View.INVISIBLE);
        }else {
            iv_left.setVisibility(View.VISIBLE);
            iv_left.setImageDrawable(leftImageSrc);
        }
        if (null == rightImageSrc){
            iv_right.setVisibility(View.INVISIBLE);
        }else {
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setImageDrawable(rightImageSrc);
        }
    }

    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
    }

    public void setLeftListener(View.OnClickListener onClickListener){
        iv_left.setOnClickListener(onClickListener);
    }

    public void setRightListener(View.OnClickListener onClickListener){
        iv_right.setOnClickListener(onClickListener);
    }

}
