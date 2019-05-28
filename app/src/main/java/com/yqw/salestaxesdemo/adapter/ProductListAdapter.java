package com.yqw.salestaxesdemo.adapter;

import android.text.Editable;
import android.text.TextWatcher;
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
        helper.setIsRecyclable(false);//
        AddSubtractEditText addSubtractEditText = helper.getView(R.id.et_count);
        TextView tv_name = helper.getView(R.id.tv_name);
        EditText et_price = helper.getView(R.id.et_price);

        addSubtractEditText.setText(String.valueOf(item.getCount()));
        tv_name.setText(String.valueOf(item.getName()));
        et_price.setText(String.valueOf(item.getPriceTag()));

        addSubtractEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.setCount(NumUtils.string2int(editable.toString()));
                //通知主页刷新
                EventBus.getDefault().post(new MessageEvent(CONSTANT.MESSAGE_EVENT_UPDATA));
            }
        });
    }
}
