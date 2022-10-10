package com.loong.service;

import com.loong.pojo.Cart;

/**
 * @author LoongKK
 * @create 2022/10/10-18:12
 */
//时间有限，仅完成”生成订单“功能
public interface OrderService {
    String createOrder(Cart cart, int userId);
}
