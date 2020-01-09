package mate.academy.internetshop.model;

public class IdGenerator {
    private static Long bucketId = 0L;
    private static Long ItemId = 0L;
    private static Long OrderId = 0L;
    private static Long UserId = 0L;

    public static Long bucketIdGenerator() {
        return ++bucketId;
    }

    public static Long itemIdGenerator() {
        return ++ItemId;
    }

    public static Long orderIdGenerator() {
        return ++OrderId;
    }

    public static Long userIdGenerator() {
        return ++UserId;
    }
}
