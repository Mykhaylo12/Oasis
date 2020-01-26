package mate.academy.internetshop.model;

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
    public String toString() {
        return "Item{" + "itemId=" + itemId + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}
