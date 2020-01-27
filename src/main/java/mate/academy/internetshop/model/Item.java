package mate.academy.internetshop.model;

import java.util.Objects;

public class Item {
    private Long itemId;
    private String name;
    private Double price;

    public Item() {
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(getItemId(), item.getItemId())
                && Objects.equals(getName(), item.getName())
                && Objects.equals(getPrice(), item.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getName(), getPrice());
    }

    @Override
    public String toString() {
        return "Item{" + "itemId=" + itemId + ", name='" + name + '\'' + ", price=" + price + '}';

    }
}
