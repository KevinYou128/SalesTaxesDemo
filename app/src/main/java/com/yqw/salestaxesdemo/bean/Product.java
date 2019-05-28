package com.yqw.salestaxesdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品
 */
public class Product implements Parcelable {
    private Long id;
    private String name;//商品名称
    private double priceTag;//商品成交价格标价
    private double priceTransaction;//商品成交价格
    private double salesTax;//商品销售税
    private int type;//商品税费类型
    private int count;//商品数量

    public Product(int count, String name, double priceTag, int type) {
        this.name = name;
        this.type = type;
        this.priceTag = priceTag;
        this.count = count;
    }

    public Product(Long id, String name, double priceTag, int type) {
        this.id = id;
        this.name = name;
        this.priceTag = priceTag;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceTag() {
        return priceTag;
    }

    public void setPriceTag(double priceTag) {
        this.priceTag = priceTag;
    }

    public double getPriceTransaction() {
        return priceTransaction;
    }

    public void setPriceTransaction(double priceTransaction) {
        this.priceTransaction = priceTransaction;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.priceTag);
        dest.writeDouble(this.priceTransaction);
        dest.writeDouble(this.salesTax);
        dest.writeInt(this.type);
        dest.writeInt(this.count);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.priceTag = in.readDouble();
        this.priceTransaction = in.readDouble();
        this.salesTax = in.readDouble();
        this.type = in.readInt();
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
