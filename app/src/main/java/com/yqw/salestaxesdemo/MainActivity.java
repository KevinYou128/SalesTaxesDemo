package com.yqw.salestaxesdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yqw.salestaxesdemo.bean.Order;
import com.yqw.salestaxesdemo.bean.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    final String TAG = "MainActivity";
    final int EXEMPTIONS = 0;//免税
    final int BASIC_SALES_TAX = 1;//基本税
    final int IMPORT_DUTY = 2;//仅进口税
    final int BASIC_SALES_TAX_AND_IMPORT_DUTY = 3;//基本税+进口税

    TextView textView;

    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        //初始化数据
        initData();
        //output
        output();
    }

//    //java运行测试
//    public static void main(String[] args) {
//        //初始化数据
//        initData();
//        //output
//        output();
//    }

    /**
     * 初始化数据
     */
    private void initData() {
        orders = new ArrayList<>();
        orders.add(order1());
        orders.add(order2());
        orders.add(order3());
    }

    /**
     * 输出结果
     */
    private void output() {
        StringBuilder output = new StringBuilder();
        //遍历订单
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (i>0){
                //第二行开始需要加一个换行符
                output.append("\n");
            }
            output.append("Output:")
                    .append(order.getId());
            //遍历每个订单的所有商品
            for (int j = 0; j < order.getProducts().size(); j++) {
                Product product = order.getProducts().get(j);
                output.append("\n")
                        .append(product.getCount())
                        .append(" ")
                        .append(product.getName())
                        .append(":")
                        .append(double2String(product.getPriceTransaction()));
            }
            output.append("\nSales Taxes:")
                    .append(double2String(order.getSalesTaxes()))
                    .append("\nTotal:")
                    .append(double2String(order.getTotal()));
        }
        System.out.print(output);
        textView.setText(output);
    }
    /**
     * output1 第一个输出内容（即是模拟第一个订单）
     *
     * @return 包含所有需要输出内容的Order
     */
    private Order order1() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "book", 12.49, EXEMPTIONS));
        products.add(new Product(2L, "music CD", 14.99, BASIC_SALES_TAX));
        products.add(new Product(3L, "chocolate bar", 0.85, EXEMPTIONS));
        return dataOrder(1L, products);
    }

    /**
     * output2 第二个输出内容（即是模拟第二个订单）
     *
     * @return 包含所有需要输出内容的Order
     */
    private Order order2() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "imported box of chocolates", 10.00, IMPORT_DUTY));
        products.add(new Product(2L, "imported bottle of perfume", 47.50, BASIC_SALES_TAX_AND_IMPORT_DUTY));
        return dataOrder(2L, products);
    }

    /**
     * output3 第三个输出内容（即是模拟第三个订单）
     *
     * @return 包含所有需要输出内容的Order
     */
    private Order order3() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "imported bottle of perfume", 27.99, BASIC_SALES_TAX_AND_IMPORT_DUTY));
        products.add(new Product(2L, "bottle of perfume", 18.99, BASIC_SALES_TAX));
        products.add(new Product(3L, "packet of headache pills", 9.75, EXEMPTIONS));
        products.add(new Product(4L, "box of imported chocolates", 11.25, IMPORT_DUTY));
        return dataOrder(3L, products);
    }

    /**
     * 订单数据
     *
     * @param id       订单id
     * @param products 初始化的商品信息
     * @return 完整信息的订单
     */
    private Order dataOrder(Long id, List<Product> products) {
        List<Product> productsFull = new ArrayList<>();
        //完整信息的商品
        for (Product product : products) {
            product = productCalculator(product);
            productsFull.add(product);
        }
        //完整信息的订单
        return orderCalculator(new Order(id, productsFull));
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
        product.setSalesTax(salesTax(type, priceTag));
        product.setCount(1);//商品数量，这里题目默认为1
        product.setPriceTransaction(string2double(transactionPrice(type, priceTag)));
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
    private String transactionPrice(int type, double priceTag) {
        double salesTax = salesTax(type, priceTag);
        return double2String(priceTag + salesTax);
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
}
