package mate.academy.internetshop.model;

public class IdGenerator {
    private static Long bucketId = 0L;
    private static Long ItemId = Long.parseLong("0");
    private static Long OrderId = Long.parseLong("0");
    private static Long UserId = Long.parseLong("0");

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
