package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/*
order
order -> member
order -> delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /*
    엔티티로 반환 -> 하지 말 것
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all){
            order.getMember().getName(); // lazy가 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    /*
    dto로 반환
     */
    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> ordersV2(){
        return orderRepository.findAll(new OrderSearch())
                .stream().map(o-> new OrderSimpleQueryDto(o))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(); // 쿼리로 한 번에 가져오기
        return orders.stream().map(o->new OrderSimpleQueryDto(o))
                .collect(Collectors.toList());

    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

}
