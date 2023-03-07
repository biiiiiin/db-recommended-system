package com.the_ajou.web.controller;

import com.the_ajou.service.InvoiceService;
import com.the_ajou.web.dto.invoice.InvoiceCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/api/auction/invoice")
    private boolean createInvoice(@RequestBody InvoiceCreateDTO invoiceCreateDTO){
        return invoiceService.createInvoice(invoiceCreateDTO);
    }
}
