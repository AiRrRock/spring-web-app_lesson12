package com.geekbrains.webapp.api.dtos;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private Long id;
    private List<OrderItemDto> items;
    private String address;
    private String phone;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrderDto() {
    }

    public OrderDto(Long id, List<OrderItemDto> items, String address, String phone, BigDecimal price) {
        this.id = id;
        this.items = items;
        this.address = address;
        this.phone = phone;
        this.price = price;
    }
}
