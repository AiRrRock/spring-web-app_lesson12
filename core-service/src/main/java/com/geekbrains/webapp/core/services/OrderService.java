package com.geekbrains.webapp.core.services;

import com.geekbrains.webapp.api.dtos.*;
import com.geekbrains.webapp.api.exceptions.MarketError;
import com.geekbrains.webapp.api.exceptions.ResourceNotFoundException;
import com.geekbrains.webapp.core.integration.CartServiceIntegration;
import com.geekbrains.webapp.core.model.*;
import com.geekbrains.webapp.core.repositories.OrderRepository;
import com.geekbrains.webapp.core.repositories.OrderStatusRepository;
import com.geekbrains.webapp.core.repositories.StatusRepository;
import com.geekbrains.webapp.core.utils.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final StatusRepository statusRepository;

    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productService;
    private final Converter converter;

    private static final String CONFIRMED = "Confirmed";
    private static final String PAID = "Paid";
    private static final String DELIVERED = "Delivered";

    @Transactional
    public Order createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto cart = cartServiceIntegration.getUserCartDto(username);
        Order order = new Order();
        order.setUsername(username);
        order.setPrice(cart.getTotalPrice());
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto i : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(i.getPrice());
            orderItem.setPricePerProduct(i.getPricePerProduct());
            orderItem.setQuantity(i.getQuantity());
            orderItem.setProduct(productService.findById(i.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт при оформлении заказа. ID продукта: " + i.getProductId())));
            items.add(orderItem);
        }
        order.setItems(items);
        orderRepository.save(order);
        cartServiceIntegration.clearUserCart(username);

        OrderStatus status = new OrderStatus();
        status.setOrder(order);
        status.setStatus(statusRepository.findByStatus(CONFIRMED).get());
        orderStatusRepository.save(status);

        return order;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Optional<OrderDto> findDtoByIdAndUsername(Long id, String username) {
        return orderRepository.findOneByIdAndUsername(id, username).map(o -> converter.orderToDto(o));
    }

    public List<Order> findAllByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    @Transactional
    public void confirmPayment(Long id, String username){
        Order order = orderRepository.findOneByIdAndUsername(id, username).orElseThrow(()-> new ResourceNotFoundException("Не удалось найти заказ"));
        OrderStatus status = orderStatusRepository.findByOrder(order).orElseThrow(()-> new ResourceNotFoundException("Не удалось найти статус для заказа"));
        status.setStatus(statusRepository.findByStatus(PAID).get());
        orderStatusRepository.save(status);
    }

    public OrderStatusDto getOrderStatus(Long id, String username) {
        Order order = orderRepository.findOneByIdAndUsername(id, username).orElseThrow(()-> new ResourceNotFoundException("Не удалось найти заказ"));
        return converter.orderStatusToDto(orderStatusRepository.findByOrder(order).orElseThrow(()-> new ResourceNotFoundException("Не удалось найти статус для заказа")));
    }
}
