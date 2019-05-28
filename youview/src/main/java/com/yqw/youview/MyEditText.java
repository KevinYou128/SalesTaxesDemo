package com.yqw.youview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 加了一个X点击清空输入框的功能的EditText
 * @Author youqinwei
 * <!--visibilityDown VISIBLE==0 GONE==8-->
 */
public class MyEditText extends ConstraintLayout {
    ImageView iv_clean;
    ImageView iv_down;
    EditText editText;

    private int inputType;
    private int maxLines;
    private int maxLength;
    private CharSequence text;
    //visibilityDown VISIBLE==0 GONE==8
    private int visibilityDown;

//    private int backColor;
//    private int titleTextColor;
//    private Drawable leftImageSrc;
//    private Drawable rightImageSrc;
//    private String titleText;

    public MyEditText(Context context) {
        super(context);
        initView(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initView(context);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        initView(context);
    }

    /**
     * 解析attrs.xml自定义的值
     *
     * @param context
     * @param attrs
     */
    private void initTypedArray(Context context, AttributeSet attrs) {
        //映射资源路径
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);
        //数字
        inputType = mTypedArray.getInt(R.styleable.MyEditText_android_inputType, InputType.TYPE_CLASS_TEXT);
        maxLines = mTypedArray.getInt(R.styleable.MyEditText_android_maxLines, 1);
        maxLength = mTypedArray.getInt(R.styleable.MyEditText_android_maxLength, 200);
        text = mTypedArray.getText(R.styleable.MyEditText_android_text);
        //visibilityDown VISIBLE==0 GONE==8
        visibilityDown = mTypedArray.getInt(R.styleable.MyEditText_visibilityDown, GONE);

        //获取资源后要及时回收
        mTypedArray.recycle();
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_my_edittext, this, true);
        iv_clean = findViewById(R.id.iv_clean);
        iv_down = findViewById(R.id.iv_down);
        editText = findViewById(R.id.editText);

        iv_down.setVisibility(visibilityDown);
        editText.setInputType(inputType);
        editText.setMaxLines(maxLines);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText.setText(text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() == 0) {
                    iv_clean.setVisibility(View.GONE);
                } else {
                    iv_clean.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_clean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clean();
            }
        });
    }
    private void clean() {
        editText.setText("");
    }

    public String getText() {
        return editText.getText().toString().trim();
    }

    public void setText(String text) {
        if (null != text) {
            editText.setText(String.valueOf(text));
        }
    }

    public void setIv_downVisibility(int visibility) {
        iv_down.setVisibility(visibility);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        editText.setOnFocusChangeListener(onFocusChangeListener);
    }
    public void setIv_downOnClickListener(OnClickListener onClickListener){
        iv_down.setOnClickListener(onClickListener);
    }

//    public void setTitle(String title){
//        if (!TextUtils.isEmpty(title)){
//            tv_title.setText(title);
//        }
//    }
//
//    public void setLeftListener(OnClickListener onClickListener){
//        iv_left.setOnClickListener(onClickListener);
//    }
//
//    public void setRightListener(OnClickListener onClickListener){
//        iv_right.setOnClickListener(onClickListener);
//    }

}
