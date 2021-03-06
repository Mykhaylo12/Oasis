package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private Long userId;
    private Long bucketId;
    private List<Item> items;

    public Bucket(User user) {
        items = new ArrayList<>();
        userId = user.getUserId();
    }

    public Bucket() {
    }

    public Bucket(Long userId, Long bucketId, List<Item> items) {
        this.userId = userId;
        this.bucketId = bucketId;
        this.items = items;
    }

    public Bucket(Long userId, List<Item> items) {
        this.userId = userId;
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBucketId() {
        return bucketId;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Bucket{" + "userId=" + userId + ", bucketId="
                + bucketId + ", items=" + items + '}';
    }
}
