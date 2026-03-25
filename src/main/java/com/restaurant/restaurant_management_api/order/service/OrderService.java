package com.restaurant.restaurant_management_api.order.service;

import com.restaurant.restaurant_management_api.Ingredient.domain.Ingredient;
import com.restaurant.restaurant_management_api.global.error.BusinessException;
import com.restaurant.restaurant_management_api.global.error.ErrorCode;
import com.restaurant.restaurant_management_api.menu.domain.Menu;
import com.restaurant.restaurant_management_api.menu.repository.MenuRepository;
import com.restaurant.restaurant_management_api.order.domain.OrderItem;
import com.restaurant.restaurant_management_api.order.domain.OrderStatus;
import com.restaurant.restaurant_management_api.order.domain.Orders;
import com.restaurant.restaurant_management_api.order.dto.OrderRequest;
import com.restaurant.restaurant_management_api.order.repository.OrderRepository;
import com.restaurant.restaurant_management_api.recipe.domain.Recipe;
import com.restaurant.restaurant_management_api.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public Long createOrder(OrderRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        int totalPrice = 0;
        Store store = null;

        for (OrderRequest.OrderItemRequest itemDto : request.orderItems()) {
            Menu menu = menuRepository.findById(itemDto.menuId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR));

            // 첫 번째 메뉴의 매장 정보를 기준으로 설정 (모든 메뉴는 같은 매장 소속이라고 가정)
            if (store == null) {
                store = menu.getStore();
            }

            // 1. 재고 차감 실행
            decreaseStock(menu, itemDto.quantity());

            // 2. 주문 항목 생성 (빌더에 price가 추가된 버전)
            OrderItem orderItem = OrderItem.builder()
                    .menu(menu)
                    .quantity(itemDto.quantity())
                    .price(menu.getPrice() * itemDto.quantity()) // 각 항목별 합계 금액
                    .build();

            orderItems.add(orderItem);
            totalPrice += (menu.getPrice() * itemDto.quantity());
        }

        // 3. 주문 마스터 생성
        Orders order = Orders.builder()
                .store(store) // 위에서 추출한 store 사용
                .status(OrderStatus.COOKING)
                .totalPrice(totalPrice)
                .build();

        // 4. 연관관계 편의 메서드 사용
        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }

        return orderRepository.save(order).getId();
    }

    private void decreaseStock(Menu menu, int orderQuantity) {
        // ★ menu.getRecipes()를 사용하여 메뉴에 연결된 레시피 목록을 가져옵니다.
        for (Recipe recipe : menu.getRecipes()) {
            Ingredient ingredient = recipe.getIngredient();
            int requiredAmount = recipe.getRequiredQuantity() * orderQuantity;

            // 식자재 엔티티 내부의 차감 로직 호출
            ingredient.decreaseStock(requiredAmount);
        }
    }
}