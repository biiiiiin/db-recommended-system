package com.the_ajou.web.dto.invoice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvoiceCreateDTO {
    private int productId;
    private String shippingCompany;
    private int invoice;

    @Builder
    InvoiceCreateDTO(int productId, String shippingCompany, int invoice){
        this.productId = productId;
        this.shippingCompany = shippingCompany;
        this.invoice = invoice;
    }
}
