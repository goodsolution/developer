package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.auth.AuthenticatedUser;
import pl.com.goodsolution.adviser.config.ApplicationConfig;
import pl.com.goodsolution.adviser.logic.instagram.InstagramService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ScheduleService {
    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private CacheService cacheService;
    private OrdersService ordersService;
    private DeliveryService deliveryService;
    private ApplicationConfig applicationConfig;
    private AuthenticatedUser authenticatedUser;
    private InstagramService instagramService;

    public ScheduleService(CacheService cacheService, OrdersService ordersService, DeliveryService deliveryService, ApplicationConfig applicationConfig, UserDetailsService userDetailsService, AuthenticatedUser authenticatedUser, InstagramService instagramService) {
        this.cacheService = cacheService;
        this.ordersService = ordersService;
        this.deliveryService = deliveryService;
        this.applicationConfig = applicationConfig;
        this.authenticatedUser = authenticatedUser;
        this.instagramService = instagramService;
    }

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void scheduleFixedDelayTask() {
        cacheService.invalidateDictionaries();
        cacheService.invalidateMenu();
        cacheService.invalidateUserRole();
        cacheService.invalidateCustomers();
        log.debug("Caches invalidated");
    }


    //@Scheduled(fixedDelay = 5 * 60 * 1000)
    public void scheduleUpdateOrderStatus() {
        if (!applicationConfig.getSchedulerUpdateOrderStatusDisabled()) {
            log.debug("scheduleUpdateOrderStatus start with hour: " + applicationConfig.getSchedulerUpdateOrderStatusHour());
            LocalDateTime date = LocalDateTime.now();
            if (date.getHour() == applicationConfig.getSchedulerUpdateOrderStatusHour()) {
                loginIfNecessary();
                log.debug("scheduleUpdateOrderStatus processing start");
                ordersService.updateStatusForNewOrdersWithCashPayment(date.minusDays(1).truncatedTo(ChronoUnit.DAYS), date.truncatedTo(ChronoUnit.HOURS));
                log.debug("scheduleUpdateOrderStatus processing stop");
            }
            log.debug("scheduleUpdateOrderStatus stop");
        }
    }

   // @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void scheduleUpdateOrderStatusWithTraditionalTransfer() {
        if (!applicationConfig.getSchedulerUpdateOrderStatusTraditionalDisabled()) {
            log.debug("scheduleUpdateOrderStatusTraditional start with hour: " + applicationConfig.getSchedulerUpdateOrderStatusTraditionalHour());
            LocalDateTime date = LocalDateTime.now();
            if (date.getHour() == applicationConfig.getSchedulerUpdateOrderStatusTraditionalHour()) {
                loginIfNecessary();
                log.debug("scheduleUpdateOrderStatusTraditional processing start");
                ordersService.updateStatusForNewOrdersWithTraditionalTransferPayment(date.minusDays(1).truncatedTo(ChronoUnit.DAYS), date.truncatedTo(ChronoUnit.HOURS));
                log.debug("scheduleUpdateOrderStatusTraditional processing stop");
            }
            log.debug("scheduleUpdateOrderStatusTraditional stop");
        }
    }

   // @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void scheduleSetDeliveriesAsDelivered() {
        if (!applicationConfig.getSchedulerDeliveryDeliveredDisabled()) {
            log.debug("scheduleSetDeliveriesAsDelivered start with hour: " + applicationConfig.getSchedulerDeliveryDeliveredHour());
            LocalDateTime date = LocalDateTime.now();
            if (date.getHour() == applicationConfig.getSchedulerDeliveryDeliveredHour()) {
                loginIfNecessary();
                log.debug("scheduleSetDeliveriesAsDelivered processing start");
                ordersService.setDeliveriesAsDelivered();
                log.debug("scheduleSetDeliveriesAsDelivered processing stop");
            }
            log.debug("scheduleSetDeliveriesAsDelivered stop");
        }
    }

    //@Scheduled(fixedDelay = 10 * 60 * 1000)
    public void instagramSync() {
        if (!applicationConfig.getSchedulerInstagramSyncDisabled()) {
            log.debug("instagramSync start");
            instagramService.sync();
            log.debug("instagramSync stop");
        }
    }


    private void loginIfNecessary() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication ==null || authentication.getName() == null) {
            authenticatedUser.loginAs("Automat");
        }
    }


}
