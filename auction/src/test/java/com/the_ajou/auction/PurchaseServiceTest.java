package com.the_ajou.auction;

import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import com.the_ajou.model.purchase.Purchase;
import com.the_ajou.model.purchase.PurchaseRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.purchase.PurchaseResponseDAO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@WebAppConfiguration
class PurchaseServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional
    @Rollback
    @Test
    void createPurchaseHistory(){
        Product product = productRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("상품이 존재하지 않습니다."));
        User buyer = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Purchase purchase = Purchase.builder()
                .product(product)
                .buyer(buyer)
                .price(10000)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        purchaseRepository.save(purchase);

        Assertions.assertThat(purchase).isNotNull();
    }


    @Transactional
    @Rollback
    @Test
    void getPurchasesByBuyerId(){
        List<Purchase> purchases = purchaseRepository.getPurchasesByBuyerId(1);
        List<PurchaseResponseDAO> purchaseResponseDAOs = new LinkedList<>();

        for(Purchase purchase : purchases){
            PurchaseResponseDAO purchaseResponseDAO = PurchaseResponseDAO.builder()
                    .productId(purchase.getProduct().getId())
                    .buyerId(purchase.getBuyer().getId())
                    .price(purchase.getPrice())
                    .createAt(purchase.getCreatedAt())
                    .updateAt(purchase.getUpdatedAt())
                    .build();
            purchaseResponseDAOs.add(purchaseResponseDAO);
        }

        //Assertions.assertThat(purchaseResponseDAOs).isNotEmpty();
    }

    @Transactional
    @Rollback
    @Test
    void getPurchase(){
        Purchase purchase = purchaseRepository.findById(2)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 구매내역입니다."));

        PurchaseResponseDAO purchaseResponseDAO = PurchaseResponseDAO.builder()
                .productId(purchase.getProduct().getId())
                .buyerId(purchase.getBuyer().getId())
                .price(purchase.getPrice())
                .createAt(purchase.getCreatedAt())
                .updateAt(purchase.getUpdatedAt())
                .build();

        Assertions.assertThat(purchaseResponseDAO).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void deletePurchase(){
        Purchase purchase = purchaseRepository.findById(2)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 구매내역입니다."));

        purchaseRepository.delete(purchase);

        Assertions.assertThat(purchase).isNotNull();
    }
}
