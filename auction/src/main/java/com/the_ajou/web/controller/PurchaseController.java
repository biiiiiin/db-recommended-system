package com.the_ajou.web.controller;

import com.the_ajou.service.PurchaseService;
import com.the_ajou.web.dao.purchase.PurchaseResponseDAO;
import com.the_ajou.web.dto.purchase.PurchaseCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/api/purchase/create")
    private int createPurchaseLog(@RequestBody PurchaseCreateDTO purchaseCreateDTO){
        return purchaseService.createPurchaseHistory(purchaseCreateDTO);
    }

    @GetMapping("/api/purchase/list/{userId}")
    private List<PurchaseResponseDAO> getPurchasesByUserId(@PathVariable("userId") int userId){
        return purchaseService.getPurchasesByBuyerId(userId);
    }

    @GetMapping("/api/purchase/{purchaseId}")
    private PurchaseResponseDAO getPurchaseById(@PathVariable("purchaseId") int purchaseId){
        return purchaseService.getPurchase(purchaseId);
    }

    @DeleteMapping("/api/purchase/delete/{purchaseId}")
    private void updatePurchase(@PathVariable("purchaseId")int purchaseId){
        purchaseService.deletePurchase(purchaseId);
    }

}
