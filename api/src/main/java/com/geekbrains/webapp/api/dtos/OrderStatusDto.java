package com.geekbrains.webapp.api.dtos;

public class OrderStatusDto {
    private Long id;
    private Long order_id;
    private Long status_id;

    public OrderStatusDto() {
    }

    public OrderStatusDto(Long id, Long order_id, Long status_id) {
        this.id = id;
        this.order_id = order_id;
        this.status_id = status_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }
}
