package com.yqw.youview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yqw.youview.utils.NumUtils;

/**
 * 加减功能的EditText
 *
 * @Author youqinwei
 * <!--visibilityDown VISIBLE==0 GONE==8-->
 */
public class AddSubtractEditText extends ConstraintLayout {
    TextView tv_add;
    TextView tv_subtract;
    EditText editText;

    private CharSequence text;
    int num = 0;

    public AddSubtractEditText(Context context) {
        super(context);
        initView(context);
    }

    public AddSubtractEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initView(context);
    }

    public AddSubtractEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.AddSubtractEditText);
        //数字
        text = mTypedArray.getText(R.styleable.AddSubtractEditText_android_text);

        //获取资源后要及时回收
        mTypedArray.recycle();
    }

    public void initView(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_add_subtract_edittext, this, true);
        tv_add = findViewById(R.id.tv_add);
        tv_subtract = findViewById(R.id.tv_subtract);
        editText = findViewById(R.id.editText);

        num = NumUtils.string2int(text == null ? "" : text.toString());

        tv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框焦点
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();

                num++;
                editText.setText(String.valueOf(num));
            }
        });
        tv_subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框焦点
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();

                if (num > 0) {
                    num--;
                    editText.setText(String.valueOf(num));
                } else {
                    Toast.makeText(context, "不能再减了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editText.setText(String.valueOf(num));
        editText.setSelection(String.valueOf(num).length());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    num = 0;
                    editText.setText(String.valueOf(num));
                }
                editText.setSelection(editable.length());
            }
        });
    }

    public String getText() {
        return editText.getText().toString().trim();
    }

    public void setText(String text) {
        if (null != text) {
            num = NumUtils.string2int(text);
            editText.setText(String.valueOf(text));
        }
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }
    public void removeTextChangedListener(TextWatcher textWatcher){
        editText.removeTextChangedListener(textWatcher);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener){
        editText.setOnFocusChangeListener(onFocusChangeListener);
    }
}
