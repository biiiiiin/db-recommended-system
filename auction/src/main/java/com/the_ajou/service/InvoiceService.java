package com.the_ajou.service;

import com.the_ajou.model.invoice.Invoice;
import com.the_ajou.model.invoice.InvoiceRepository;
import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import com.the_ajou.web.dto.invoice.InvoiceCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    @Transactional
    public boolean createInvoice(InvoiceCreateDTO invoiceCreateDTO){
        Product product = productRepository.findById(invoiceCreateDTO.getProductId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setStatus('Y');

        Invoice invoice = Invoice.builder()
                .product(product)
                .shippingCompany(invoiceCreateDTO.getShippingCompany())
                .invoice(invoiceCreateDTO.getInvoice())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        invoiceRepository.save(invoice);

        return invoice.getInvoice() != -1;
    }
}
