package com.the_ajou.model.invoice;

import com.the_ajou.model.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name = "shippingCompany")
    private String shippingCompany;

    @Column(name = "invoice")
    private int invoice;

    @Column(name = "createdAt")
    private String createdAt;

    @Builder
    Invoice(Product product, String shippingCompany, int invoice, String createdAt){
        this.product = product;
        this.shippingCompany = shippingCompany;
        this.invoice = invoice;
        this.createdAt = createdAt;
    }
}
