package com.yqw.salestaxesdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 订单
 */
public class Order implements Parcelable {
    private Long id;
    private List<Product> products;//订单商品
    private double salesTaxes;//总共缴税额（商品销售税总和）
    private double total;//订单总价

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getSalesTaxes() {
        return salesTaxes;
    }

    public void setSalesTaxes(double salesTaxes) {
        this.salesTaxes = salesTaxes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeTypedList(this.products);
        dest.writeDouble(this.salesTaxes);
        dest.writeDouble(this.total);
    }

    public Order() {
    }

    public Order(Long id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    protected Order(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.products = in.createTypedArrayList(Product.CREATOR);
        this.salesTaxes = in.readDouble();
        this.total = in.readDouble();
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
