package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.com.goodsolution.adviser.domain.InvoiceData;
import pl.com.goodsolution.adviser.domain.courseplatform.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoicesService {

    private InvoicesJdbcRepository invoicesJdbcRepository;
    private CourseOrdersService courseOrdersService;
    private FilesService filesService;

    public InvoicesService(InvoicesJdbcRepository invoicesJdbcRepository, CourseOrdersService courseOrdersService, FilesService filesService) {
        this.invoicesJdbcRepository = invoicesJdbcRepository;
        this.courseOrdersService = courseOrdersService;
        this.filesService = filesService;
    }

    public void create(InvoiceData data) {
        validate(data);
        invoicesJdbcRepository.create(data);
    }

    public List<InvoiceData> find(InvoicesFilter filter) {
        return invoicesJdbcRepository.find(filter);
    }

    public void update(InvoiceData data) {
        validate(data);
        invoicesJdbcRepository.update(data);
    }

    public void delete(Long id) {
        InvoiceData invoice = find(new InvoicesFilter(id, null, null)).get(0);
        update(new InvoiceData(invoice, LocalDateTime.now()));
    }

    public void addInvoiceToOrder(Long orderId, MultipartFile invoiceFile, InvoiceData invoiceData) {
        if(!invoiceFile.isEmpty()) {
            String fileName = filesService.saveInvoiceOnServer(invoiceFile);
            CourseOrderData courseOrderData = courseOrdersService.find(new CourseOrdersFilter(orderId, null)).get(0);
            create(new InvoiceData(courseOrderData, invoiceData.getType(), fileName, invoiceData.getNumber()));
        }
    }

    private void validate(InvoiceData data) {
        if(!data.getType().equals(InvoiceType.CORRECTION.getCode()) && !data.getType().equals(InvoiceType.STANDARD.getCode())) {
            throw new IllegalArgumentException("Wrong invoice type");
        } else if (data.getNumber() == null || data.getNumber().equals("")) {
            throw new IllegalArgumentException("Invoice number can't be empty");
        } else if (data.getNumber().length() > 100) {
            throw new IllegalArgumentException("Invoice number too long, max 100 characters");
        }
    }
}
