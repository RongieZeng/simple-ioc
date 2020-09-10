package org.example.pojo;

import org.example.MyAutowired;
import org.example.MyComponent;

@MyComponent
public class ProductDetail {

    private String desc;

    @MyAutowired
    private Stock stockInfo;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Stock getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(Stock stockInfo) {
        this.stockInfo = stockInfo;
    }
}
