package com.yqw.salestaxesdemo.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yqw.salestaxesdemo.CONSTANT;
import com.yqw.salestaxesdemo.R;
import com.yqw.salestaxesdemo.bean.MessageEvent;
import com.yqw.salestaxesdemo.bean.Product;
import com.yqw.youview.AddSubtractEditText;
import com.yqw.youview.utils.NumUtils;

import org.greenrobot.eventbus.EventBus;

public class ProductListAdapter extends BaseQuickAdapter<Product, BaseViewHolder> {

    public ProductListAdapter() {
        super(R.layout.item_product, null);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Product item) {
//        helper.setIsRecyclable(false);//解除缓存
        final AddSubtractEditText addSubtractEditText = helper.getView(R.id.et_count);
        TextView tv_name = helper.getView(R.id.tv_name);
        EditText et_price = helper.getView(R.id.et_price);

        addSubtractEditText.setText(String.valueOf(item.getCount()));
        tv_name.setText(String.valueOf(item.getName()));
        et_price.setText(String.valueOf(item.getPriceTag()));

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //避免输入框数据错乱的处理：获取焦点时才做数据更新处理，避免了没有获取焦点的输入框也更新数据
                if (addSubtractEditText.hasFocus()){
                    //如果获取焦点，设置item改变后的值
                    item.setCount(NumUtils.string2int(editable.toString()));
                    //通知主页刷新
                    EventBus.getDefault().post(new MessageEvent(CONSTANT.MESSAGE_EVENT_UPDATE));
                }
            }
        };
        addSubtractEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //避免输入框数据错乱的处理
                if (b){
                    //获取焦点时设置监听
                    addSubtractEditText.addTextChangedListener(textWatcher);
                }else {
                    //失去焦点时取消监听
                    addSubtractEditText.removeTextChangedListener(textWatcher);
                }
            }
        });

    }
}
