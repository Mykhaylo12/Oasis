package mate.academy.internetshop.model;

import java.util.List;

public class Order {
    private Long orderId;
    private List<Item> items;
    private Double totalPrice;
    private Long userId;

    public Order(User user, List<Item> items) {
        this.userId = user.getUserId();
        this.totalPrice = countTotalPrice(items);
        this.items = items;
    }

    public Order(Long orderId, Long userId, List<Item> items) {
        this.orderId = orderId;
        this.items = items;
        this.userId = userId;
    }

    public Order() {
    }

    private Double countTotalPrice(List<Item> items) {
        return items.stream().map(Item::getPrice).reduce(0.0, Double::sum);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", items=" + items + ", totalPrice=" + totalPrice
                + ", userId=" + userId + '}';
    }
}
