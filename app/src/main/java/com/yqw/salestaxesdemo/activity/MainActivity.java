package com.yqw.salestaxesdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yqw.salestaxesdemo.CONSTANT;
import com.yqw.salestaxesdemo.R;
import com.yqw.salestaxesdemo.adapter.ProductListAdapter;
import com.yqw.salestaxesdemo.bean.MessageEvent;
import com.yqw.salestaxesdemo.bean.Order;
import com.yqw.salestaxesdemo.bean.Product;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    final String TAG = "MainActivity";
    final int EXEMPTIONS = 0;//免税
    final int BASIC_SALES_TAX = 1;//基本税
    final int IMPORT_DUTY = 2;//仅进口税
    final int BASIC_SALES_TAX_AND_IMPORT_DUTY = 3;//基本税+进口税

    TextView tv_result;
    RecyclerView recyclerView;
    ProductListAdapter productListAdapter;

//    private List<Order> orders;
//    Order order;//当前订单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = findViewById(R.id.tv_result);
        recyclerView = findViewById(R.id.recyclerView);

        productListAdapter = new ProductListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productListAdapter);

        Order order = order();
        productListAdapter.setNewData(order.getProducts());

        output(order.getProducts());


//        //初始化数据
//        initData();
//        //output
//        output();
    }

    /**
     * 输出结果
     */
    private void output(List<Product> products) {
        //补全商品计算后信息
        Order order = dataOrder(products);
        products = order.getProducts();

        StringBuilder output = new StringBuilder();
            output.append("Output:");
            //遍每个订单的所有商品
            for (int j = 0; j < products.size(); j++) {
                Product product = products.get(j);
                if (product.getCount()>0){
                    output.append("\n")
                            .append(product.getCount())
                            .append(" ")
                            .append(product.getName())
                            .append(":")
                            .append(double2String(product.getPriceTransaction()));
                }
            }
            output.append("\nSales Taxes:")
                    .append(double2String(order.getSalesTaxes()))
                    .append("\nTotal:")
                    .append(double2String(order.getTotal()));
        System.out.print(output);
        tv_result.setText(output);
    }

    /**
     * 所有商品
     *
     * @return 包含所有需要输出内容的Order
     */
    private Order order() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "book", 12.49, EXEMPTIONS));
        products.add(new Product(1, "music CD", 14.99, BASIC_SALES_TAX));
        products.add(new Product(1, "chocolate bar", 0.85, EXEMPTIONS));

        products.add(new Product(0, "imported box of chocolates", 10.00, IMPORT_DUTY));
        products.add(new Product(0, "imported bottle of perfume", 47.50, BASIC_SALES_TAX_AND_IMPORT_DUTY));

        products.add(new Product(0, "imported bottle of perfume", 27.99, BASIC_SALES_TAX_AND_IMPORT_DUTY));
        products.add(new Product(0, "bottle of perfume", 18.99, BASIC_SALES_TAX));
        products.add(new Product(0, "packet of headache pills", 9.75, EXEMPTIONS));
        products.add(new Product(0, "box of imported chocolates", 11.25, IMPORT_DUTY));

        return dataOrder(products);
    }

    /**
     * 订单数据
     *
     * @param products 初始化的商品信息
     * @return 完整信息的订单
     */
    private Order dataOrder(List<Product> products) {
        List<Product> productsFull = new ArrayList<>();
        //完整信息的商品
        for (Product product : products) {
            product = productCalculator(product);
            productsFull.add(product);
        }
        //完整信息的订单
        return orderCalculator(new Order(productsFull));
    }

    /**
     * 计算补全商品信息（）
     *
     * @param product 携带初始数据的商品
     * @return 补全完整信息的商品
     */
    private Product productCalculator(Product product) {
        int type = product.getType();
        double priceTag = product.getPriceTag();
        product.setSalesTax(salesTax(type, priceTag*product.getCount()));
//        product.setCount(1);//商品数量，这里题目默认为1
        product.setPriceTransaction(string2double(transactionPrice(type, priceTag,product.getCount())));
        return product;
    }

    /**
     * 计算补全订单信息
     *
     * @param order 携带初始数据的订单
     * @return 补全完整信息的订单数据
     */
    private Order orderCalculator(Order order) {
        double salesTaxes = 0;
        double priceTotal = 0;
        for (Product product : order.getProducts()) {
            salesTaxes += product.getSalesTax();
            priceTotal += product.getPriceTransaction();
        }
        order.setSalesTaxes(salesTaxes);
        order.setTotal(priceTotal);
        return order;
    }

    /**
     * 成交价计算
     *
     * @param type     商品交税类型
     * @param priceTag 商品标价
     * @return 最终成交价格
     */
    private String transactionPrice(int type, double priceTag,int count) {
        double salesTax = salesTax(type, priceTag*count);
        return double2String(priceTag*count + salesTax);
    }

    /**
     * 销售税计算
     *
     * @param type     商品交税类型
     * @param priceTag 商品标价
     * @return 销售税
     */
    private double salesTax(int type, double priceTag) {
        double salesTax = 0;
        switch (type) {
            case EXEMPTIONS:
                salesTax = 0;
                break;
            case BASIC_SALES_TAX:
                salesTax = roundOff(priceTag * 0.1);
                break;
            case IMPORT_DUTY:
                salesTax = roundOff(priceTag * 0.05);
                break;
            case BASIC_SALES_TAX_AND_IMPORT_DUTY:
                salesTax = roundOff(priceTag * (0.1 + 0.05));
                break;
        }
        return salesTax;
    }

    /**
     * 进行上取整到0.05倍数
     *
     * @param d 任意double类型的数
     * @return 进行上取整到0.05倍数的结果
     */
    private double roundOff(double d) {
        return Math.ceil(d * 20) / 20.00;
    }

    /**
     * 通过double得到一个小数点后2位的字符串（DecimalFormat）
     *
     * @param d 传入任意double值
     * @return double小数点后两位计算后得值
     */
    private String double2String(double d) {
        return new DecimalFormat("#0.00").format(d);
    }

    /**
     * String转double数
     *
     * @param num String类型的数字
     * @return 转化后的double
     */
    public static double string2double(String num) {
        double d = 0;
        if (null != num && !"".equals(num) && !"null".equals(num)) {
            d = Double.valueOf(num);
        } else {
            d = 0;
        }
        return d;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (CONSTANT.MESSAGE_EVENT_UPDATA.equals(event.getMsg())) {
            output(productListAdapter.getData());
        }
    }
}
