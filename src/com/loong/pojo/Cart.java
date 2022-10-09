package com.loong.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 *
 * @author LoongKK
 * @create 2022/10/9-17:41
 */
public class Cart {
    //后面这两个数据会通过方法计算，不用了
    //private Integer totalCount;//总商品数量
    //private BigDecimal totalPrice;//总商品金额
    private Map<Integer, CartItem> items = new LinkedHashMap<Integer, CartItem>();//key商品编号，value商品信息

    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem) {
        // 先查看购物车中是否已经添加过此商品，如果已添加，则数量累加，商品总价更新;如果没有添加过，直接放到集合中即可
        CartItem item = items.get(cartItem.getId());//用Map的好处：不用自己遍历比较id 直接通过id获取商品信息
        if (item == null) {// 之前没添加过此商品
            items.put(cartItem.getId(), cartItem);
        } else {// 已经 添加过的情况
            item.setCount(item.getCount() + 1); // 数量累加
            //新知识：BigDecimal的乘法 a.multiply(b) 返回值类型也是BigDecimal
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount()))); // 更新商品总价(【该商品】的总金额)(单价x数量)
        }
    }

    /**
     * 删除商品项
     */
    public void deleteItem(Integer id) {
        items.remove(id);
    }

    /**
     * 清空购物车
     */
    public void clear() {
        items.clear();
    }

    /**
     * 修改商品数量
     */
    public void updateCount(Integer id, Integer count) {
        // 先查看购物车中是否有此商品。如果有，修改商品数量，更新商品总价
        CartItem cartItem = items.get(id);
        if (cartItem != null) {
            cartItem.setCount(count);// 修改商品数量
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount()))); // 更新商品总价
        }
    }

    /**
     * 获取总商品数量
     */
    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }

    /**
     * 获取总商品金额
     */
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }

    /**
     * 获取商品信息
     */
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    /**
     *设置商品信息
     */
    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}