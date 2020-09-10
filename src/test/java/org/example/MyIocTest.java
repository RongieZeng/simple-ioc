package org.example;

import org.example.pojo.Product;
import org.junit.Test;

import java.io.IOException;

public class MyIocTest {

    @Test
    public void findClasses() throws IOException, ClassNotFoundException {
        Class<?>[] classes =  ClassUtils.findClasses(
                new String[]{
                        "org.example",

                });
        for (Class<?> cls : classes){
            System.out.println(cls.getName());
        }
    }

    @Test
    public void initialContainer(){
        MyApplicationContext myApplicationContext = new MyApplicationContext();
        Product product = myApplicationContext.getBean(Product.class);
        System.out.println(product.getDetail().getStockInfo().getRemainingNum());
    }
}
