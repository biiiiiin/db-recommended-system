package com.the_ajou.service;

import com.the_ajou.model.purchase.Purchase;
import com.the_ajou.model.purchase.PurchaseRepository;
import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.purchase.PurchaseResponseDAO;
import com.the_ajou.web.dto.purchase.PurchaseCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public int createPurchaseHistory(PurchaseCreateDTO purchaseCreateDTO){
        Product product = productRepository.findById(purchaseCreateDTO.getProductId())
                .orElseThrow(()->new IllegalArgumentException("상품이 존재하지 않습니다."));
        User buyer = userRepository.findById(purchaseCreateDTO.getBuyerId())
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Purchase purchase = Purchase.builder()
                .product(product)
                .buyer(buyer)
                .price(purchaseCreateDTO.getPrice())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        purchaseRepository.save(purchase);

        return purchase.getId();
    }


    @Transactional
    public List<PurchaseResponseDAO> getPurchasesByBuyerId(int buyerId){
        List<Purchase> purchases = purchaseRepository.getPurchasesByBuyerId(buyerId);
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

        return purchaseResponseDAOs;
    }

    @Transactional
    public PurchaseResponseDAO getPurchase(int purchaseId){
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 구매내역입니다."));

        return PurchaseResponseDAO.builder()
                .productId(purchase.getProduct().getId())
                .buyerId(purchase.getBuyer().getId())
                .price(purchase.getPrice())
                .createAt(purchase.getCreatedAt())
                .updateAt(purchase.getUpdatedAt())
                .build();
    }

    @Transactional
    public void deletePurchase(int purchaseId){
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 구매내역입니다."));

        purchaseRepository.delete(purchase);
    }
}
