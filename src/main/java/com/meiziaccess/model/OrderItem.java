package com.meiziaccess.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Created by user-u1 on 2016/5/6.
 */
@Entity
@Table(name = "order_item")
public class OrderItem  {

    @Id
    private Integer Id;

    @NotNull
    private Integer order_id ;

    @NotNull
    private Integer sub_item_id ;

    private Integer status;

    private String url;

    public OrderItem() {}

    public OrderItem(Integer id, Integer order_id, Integer sub_item_id, Integer status, String url) {
        Id = id;
        this.order_id = order_id;
        this.sub_item_id = sub_item_id;
        this.status = status;
        this.url = url;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getsub_item_id() {
        return sub_item_id;
    }

    public void setsub_item_id(Integer sub_item_id) {
        this.sub_item_id = sub_item_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
