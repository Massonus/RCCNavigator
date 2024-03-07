package com.massonus.rccnavigator.service;

import com.massonus.rccnavigator.dto.OrderDto;
import com.massonus.rccnavigator.entity.*;
import com.massonus.rccnavigator.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderObjectService orderObjectService;
    private final BasketService basketService;
    private final BasketObjectService basketObjectService;

    @Autowired
    public OrderService(OrderRepo orderRepo, OrderObjectService orderObjectService, BasketService basketService, BasketObjectService basketObjectService) {
        this.orderRepo = orderRepo;
        this.orderObjectService = orderObjectService;
        this.basketService = basketService;
        this.basketObjectService = basketObjectService;
    }

    public OrderDto checkout(final OrderDto orderDto, final User user) {

        List<BasketObject> basketObjects = basketObjectService.getBasketObjectsByUserId(user.getId());

        List<String> companyTitles = basketObjects.stream()
                .map(o -> o.getCompany().getTitle())
                .distinct()
                .toList();

        if (companyTitles.size() > 1) {
            orderDto.setIsSuccess(false);
            return orderDto;
        }

        for (BasketObject basketObject : basketObjects) {

            OrderObject orderObject = new OrderObject();
            orderObject.setTitle(basketObject.getTitle());
            orderObject.setImage(basketObject.getImage());
            orderObject.setCompany(basketObject.getCompany());
            orderObject.setUser(user);
            orderObject.setAmount(basketObject.getAmount());
            orderObject.setSum(basketObject.getSum());
            orderObject.setProductId(basketObject.getProductId());

            orderObjectService.saveOrderObject(orderObject);
        }
        basketService.clearBasket(user);

        orderDto.setIsSuccess(true);
        return orderDto;
    }

}
