package com.the_ajou.auction;

import com.the_ajou.model.invoice.Invoice;
import com.the_ajou.model.invoice.InvoiceRepository;
import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@WebAppConfiguration
class InvoiceServiceTest {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Rollback
    @Test
    void createInvoice(){
        Product product = productRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setStatus('Y');

        Invoice invoice = Invoice.builder()
                .product(product)
                .shippingCompany("Test Address")
                .invoice(123456789)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        invoiceRepository.save(invoice);

        Assertions.assertThat(invoice).isNotNull();
    }
}
