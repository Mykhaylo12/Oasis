package mate.academy.internetshop;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {
    @Inject
    private static ItemService itemService;

    @Inject
    private static OrderService orderService;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static UserService userService;

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        User user1 = new User("John", "1111");
//        User user2 = new User("Michael", "2222");
//        userService.create(user1);
//        userService.create(user2);
//        System.out.println(Storage.users);
//
//        Bucket bucket = new Bucket(user1);
//        bucketService.create(bucket);
//        Item item1 = new Item("Iphone 10", 1000.0);
//        Item item2 = new Item("Samsung Fold", 2000.0);
//        itemService.create(item1);
//        itemService.create(item2);
//        System.out.println(Storage.items);

//        bucketService.addItem(bucket, item1);
//        bucketService.addItem(bucket, item2);
//        System.out.println(Storage.buckets);
//
//        orderService.completeOrder(bucketService.getAllItems(bucket), user1);
//        System.out.println(Storage.orders);
    }
}
