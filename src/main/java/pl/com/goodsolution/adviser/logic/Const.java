package pl.com.goodsolution.adviser.logic;

public class Const {
    final static long PAYMENT_STATUS_NOT_PAID = 0;
    final static long PAYMENT_STATUS_PAID = 1;
    final static long PAYMENT_STATUS_WHO_KNOWS = 2;

    final static long PAYMENT_METHOD_TRADITIONAL_TRANSFER = 1;
    final static long PAYMENT_METHOD_CASH = 2;
    final static long PAYMENT_METHOD_EXPRESS_TRANSFER = 3;
    final static long PAYMENT_METHOD_BITCOIN = 4;

    final static long DELIVERY_METHOD_COURIER = 1;
    final static long DELIVERY_METHOD_PERSONAL = 2;

    final static long ORDER_STATUS_NEW = 1; // nowe
    final static long ORDER_STATUS_ACCEPTED = 2; //Przyjęte
    final static long ORDER_STATUS_READY = 3; //Gotowe
    final static long ORDER_STATUS_COMPLETED = 4; //Zrealizowane
    final static long ORDER_STATUS_APPROVED = 5; // zaakceptowane
    final static long ORDER_STATUS_CANCELLED = 6; // anulowane
    final static long ORDER_STATUS_SUSPENDED = 7; // wstrzymane

    final static long DELIVERY_ORDER_STATUS_RELEASED = -2L;
    final static long DELIVERY_ORDER_STATUS_CANCELLED = -1L;
    final static long DELIVERY_ORDER_STATUS_WAITING = 0L;
    final static long DELIVERY_ORDER_STATUS_DELIVERED = 1L;

    final static String ORDER_CHANGE_STATUS_SOURCE_SCHEDULER_FOR_PAYMENT_BY_CASH = "S";
    final static String ORDER_CHANGE_STATUS_SOURCE_SCHEDULER_FOR_TRADITIONAL_TRANSFER = "T";

    final static String ORDER_DAY_STATUS_NEW = "N";
    final static String ORDER_DAY_STATUS_REMOVED = "R";
    final static String ORDER_DAY_STATUS_SUSPENDED = "S";
    final static String ORDER_DAY_STATUS_REACTIVATED = "E";
    final static String ORDER_DAY_STATUS_FILLED = "F";
    final static String ORDER_DAY_STATUS_RELEASED = "L";
    final static String ORDER_DAY_STATUS_DELIVERED = "D";
    final static String ORDER_DAY_STATUS_ADDED = "A";

    final static String ORDER_DAY_DELIVERY_INFO_DELIVERED = "D";
    final static String ORDER_DAY_DELIVERY_INFO_NOT_DELIVERED = "N";

    final static long WEEKEND_OPTION_ID_WITH_WEEKENDS = 1L;
    final static long WEEKEND_OPTION_ID_WITHOUT_WEEKENDS = 0L;
    final static long WEEKEND_OPTION_ID_WITHOUT_SUNDAYS = 2L;

}

