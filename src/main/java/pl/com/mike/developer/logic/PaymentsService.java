package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.auth.AuthenticatedUser;
import pl.com.mike.developer.domain.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class PaymentsService {
    private static final Logger log = LoggerFactory.getLogger(PaymentsService.class);
    private PaymentsJdbcRepository paymentsJdbcRepository;
    private DictionariesService dictionariesService;
    private AuthenticatedUser authenticatedUser;
    private EmailsService emailsService;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
    private DateTimeFormatter timeFormatterLong = DateTimeFormatter.ofPattern("H:mm:ss");
    private DateTimeFormatter timeFormatterShort = DateTimeFormatter.ofPattern("H:mm");

    public PaymentsService(PaymentsJdbcRepository paymentsJdbcRepository, DictionariesService dictionariesService, AuthenticatedUser authenticatedUser, EmailsService emailsService) {
        this.paymentsJdbcRepository = paymentsJdbcRepository;
        this.dictionariesService = dictionariesService;
        this.authenticatedUser = authenticatedUser;
        this.emailsService = emailsService;
    }

    public PaymentMethodData getPaymentMethod(Long paymentMethodId) {
        return paymentsJdbcRepository.getPaymentMethod(paymentMethodId);
    }

    void changePaymentStatus(OrderDetailsData order, List<OrderProductCreateData> items, Long paymentStatusId) {
        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }
        if (paymentStatusId == null) {
            throw new IllegalArgumentException("paymentStatusId is null");
        }
        paymentsJdbcRepository.updatePaymentStatusOnOrder(order.getId(), paymentStatusId);
        createPaymentStatusChange(new OrderPaymentChangeData(order.getId(), paymentStatusId, authenticatedUser.getUserId()));
        if (paymentStatusId.longValue() == Const.PAYMENT_STATUS_PAID) {
            emailsService.sendOrderPaid(order, items);
        }
    }

    void changePaymentMethod(Long orderId, Long newPaymentMethodId, Long oldPaymentMethodId) {
        if (orderId == null) {
            throw new IllegalArgumentException("orderId is null");
        }
        if (newPaymentMethodId == null) {
            throw new IllegalArgumentException("newPaymentMethodId is null");
        }
        if (oldPaymentMethodId == null) {
            throw new IllegalArgumentException("oldPaymentMethodId is null");
        }
        paymentsJdbcRepository.createPaymentMethodChange(
                new OrderPaymentMethodChangeData(orderId, oldPaymentMethodId, newPaymentMethodId, authenticatedUser.getUserId()));
        String paymentCompanyName = dictionariesService.getDictionaryValueById(newPaymentMethodId, DictionaryType.ORDER_PAYMENT_METHODS, Language.PL);
        paymentsJdbcRepository.updatePaymentMethodOnOrder(orderId, newPaymentMethodId, paymentCompanyName);
    }

    void createPaymentStatusChange(OrderPaymentChangeData data) {
        paymentsJdbcRepository.createPaymentStatusChange(data);
    }

    void updatePaymentUpdateDateOnOrder(Long orderId) {
        paymentsJdbcRepository.updatePaymentUpdateDateOnOrder(orderId);
    }
}
