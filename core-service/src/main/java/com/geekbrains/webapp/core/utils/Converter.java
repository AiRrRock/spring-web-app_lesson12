package com.geekbrains.webapp.core.utils;

import com.geekbrains.webapp.api.dtos.*;
import com.geekbrains.webapp.core.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {
    public ProductDto productToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

    public CategoryDto categoryToDto(Category category) {
        List<ProductDto> products = category.getProducts().stream().map(p -> productToDto(p)).collect(Collectors.toList());
        return new CategoryDto(category.getId(), category.getTitle(), products);
    }

    public OrderItemDto orderItemToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getTitle(), orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }

    public OrderDto orderToDto(Order order) {
        return new OrderDto(order.getId(), order.getItems().stream().map(oi -> orderItemToDto(oi)).collect(Collectors.toList()), order.getAddress(), order.getPhone(), order.getPrice());
    }

    public OrderStatusDto orderStatusToDto(OrderStatus orderStatus) {
        return new OrderStatusDto(orderStatus.getId(), orderStatus.getOrder().getId(), orderStatus.getStatus().getId());
    }
}
