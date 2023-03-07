package com.the_ajou.service;

import com.the_ajou.model.category.Category;
import com.the_ajou.model.category.CategoryRepository;
import com.the_ajou.model.interests.Interest;
import com.the_ajou.model.interests.InterestRepository;
import com.the_ajou.model.point.Point;
import com.the_ajou.model.point.PointRepository;
import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.web.dao.product.ProductResponseDAO;
import com.the_ajou.web.dao.product.ProductSearchResponseDAO;
import com.the_ajou.web.dto.product.*;
import com.the_ajou.web.dto.purchase.PurchaseCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final InterestRepository interestRepository;
    private final PurchaseService purchaseService;
    private final PointRepository pointRepository;

    @Transactional
    public ProductResponseDAO getProduct(ProductInfoDTO productInfoDTO){
        Product product = productRepository.findById(productInfoDTO.getProductId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        List<String> images = new LinkedList<>();

        if(!product.getProductImage1().isBlank())
            images.add(product.getProductImage1());
        if(!product.getProductImage2().isBlank())
            images.add(product.getProductImage2());
        if(!product.getProductImage3().isBlank())
            images.add(product.getProductImage3());
        if(!product.getProductImage4().isBlank())
            images.add(product.getProductImage4());
        if(!product.getProductImage5().isBlank())
            images.add(product.getProductImage5());

        Date startTime;
        Date endTime;
        Date nowTime;

        boolean before = false;
        boolean after = false;
        boolean now = false;

        String endTimeStr = null;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
        try {
            endTime = simpleDateFormat.parse(product.getStartTime());
            calendar.setTime(endTime);
            calendar.add(Calendar.MINUTE, product.getDuration());

            endTimeStr = simpleDateFormat.format(calendar.getTime());

            startTime = simpleDateFormat.parse(product.getStartTime());
            endTime = simpleDateFormat.parse(endTimeStr);
            nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            now = nowTime.equals(startTime) || nowTime.equals(endTime);
            before = nowTime.after(startTime);
            after = nowTime.before(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        boolean isLive = (before && after || now) && product.getUserIn() == 1;

        return ProductResponseDAO.builder()
                .productId(product.getId())
                .seller(product.getUser().getNickName())
                .title(product.getTitle())
                .description(product.getDescription())
                .startTime(product.getStartTime())
                .endTime(endTimeStr)
                .startPrice(product.getStartPrice())
                .instant(product.getInstant())
                .buyNowPrice(product.getBuyNowPrice())
                .duration(product.getDuration())
                .bidPrice(product.getBidPrice())
                .like(interestRepository.findByProductIdAndUserId(product.getId(), productInfoDTO.getUserId()) != null)
                .live(product.getInstant() != 1 && isLive)
                .productImages(images)
                .build();
    }

    @Transactional
    public List<ProductSearchResponseDAO> getProductList(){
        List<Product> products = productRepository.findAll();
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Product product : products){
            if(product.getStatus() == 'Y'){
                Date startTime;
                Date endTime;
                Date nowTime;


                boolean before = false;
                boolean after = false;
                boolean now = false;
                String endTimeStr;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
                try {
                    endTime = simpleDateFormat.parse(product.getStartTime());
                    calendar.setTime(endTime);
                    calendar.add(Calendar.MINUTE, product.getDuration());

                    endTimeStr = simpleDateFormat.format(calendar.getTime());

                    startTime = simpleDateFormat.parse(product.getStartTime());
                    endTime = simpleDateFormat.parse(endTimeStr);
                    nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                    now = nowTime.equals(startTime) || nowTime.equals(endTime);
                    before = nowTime.after(startTime);
                    after = nowTime.before(endTime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean isLive = (before && after || now) && product.getUserIn() == 1;

                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                        .productId(product.getId())
                        .title(product.getTitle())
                        .buyNowPrice(product.getBuyNowPrice())
                        .instance(product.getInstant() == 1)
                        .live(product.getInstant() != 1 && isLive)
                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                        .image(product.getProductImage1())
                        .build();
                productSearchResponseDAOS.add(productSearchResponseDAO);
            }
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public List<ProductSearchResponseDAO> getAuctionProductList(){
        List<Product> products = productRepository.findAll();
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Product product : products){
            if(product.getStatus() == 'Y' && product.getInstant() == 0){
                Date startTime;
                Date endTime;
                Date nowTime;


                boolean before = false;
                boolean after = false;
                boolean now = false;
                String endTimeStr;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
                try {
                    endTime = simpleDateFormat.parse(product.getStartTime());
                    calendar.setTime(endTime);
                    calendar.add(Calendar.MINUTE, product.getDuration());

                    endTimeStr = simpleDateFormat.format(calendar.getTime());

                    startTime = simpleDateFormat.parse(product.getStartTime());
                    endTime = simpleDateFormat.parse(endTimeStr);
                    nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                    now = nowTime.equals(startTime) || nowTime.equals(endTime);
                    before = nowTime.after(startTime);
                    after = nowTime.before(endTime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean isLive = (before && after || now) && product.getUserIn() == 1;

                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                        .productId(product.getId())
                        .title(product.getTitle())
                        .buyNowPrice(product.getBuyNowPrice())
                        .instance(product.getInstant() == 1)
                        .live(product.getInstant() != 1 && isLive)
                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                        .image(product.getProductImage1())
                        .build();
                productSearchResponseDAOS.add(productSearchResponseDAO);
            }
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public List<ProductSearchResponseDAO> getAuctioningProductList(){
        List<Product> products = productRepository.findAll();
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Product product : products){
            if(product.getStatus() == 'Y'){
                Date startTime;
                Date endTime;
                Date nowTime;


                boolean before = false;
                boolean after = false;
                boolean now = false;
                String endTimeStr;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
                try {
                    endTime = simpleDateFormat.parse(product.getStartTime());
                    calendar.setTime(endTime);
                    calendar.add(Calendar.MINUTE, product.getDuration());

                    endTimeStr = simpleDateFormat.format(calendar.getTime());

                    startTime = simpleDateFormat.parse(product.getStartTime());
                    endTime = simpleDateFormat.parse(endTimeStr);
                    nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

                    now = nowTime.equals(startTime) || nowTime.equals(endTime);
                    before = nowTime.after(startTime);
                    after = nowTime.before(endTime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean isLive = (before && after || now) && product.getUserIn() == 1;

                if(isLive){
                    ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                            .productId(product.getId())
                            .title(product.getTitle())
                            .buyNowPrice(product.getBuyNowPrice())
                            .live(product.getInstant() != 1 && isLive)
                            .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                            .image(product.getProductImage1())
                            .build();
                    productSearchResponseDAOS.add(productSearchResponseDAO);
                }
            }
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public List<ProductSearchResponseDAO> getInstanceProductList(){
        List<Product> products = productRepository.findAll();
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Product product : products){
            if(product.getStatus() == 'Y' && product.getInstant() == 1){
                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                        .productId(product.getId())
                        .title(product.getTitle())
                        .buyNowPrice(product.getBuyNowPrice())
                        .instance(product.getInstant() == 1)
                        .live(false)
                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                        .image(product.getProductImage1())
                        .build();
                productSearchResponseDAOS.add(productSearchResponseDAO);
            }
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public List<ProductSearchResponseDAO> getProductListByCategoryId(int categoryId){
        List<Product> products;
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        if(categoryId == 1)
            products = productRepository.findAll();
        else
            products = productRepository.findAllByCategoryId(categoryId);

        for(Product product : products){
            if(product.getStatus() == 'Y'){
                Date startTime;
                Date endTime;
                Date nowTime;


                boolean before = false;
                boolean after = false;
                boolean now = false;
                String endTimeStr;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
                try {
                    endTime = simpleDateFormat.parse(product.getStartTime());
                    calendar.setTime(endTime);
                    calendar.add(Calendar.MINUTE, product.getDuration());

                    endTimeStr = simpleDateFormat.format(calendar.getTime());

                    startTime = simpleDateFormat.parse(product.getStartTime());
                    endTime = simpleDateFormat.parse(endTimeStr);
                    nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                    now = nowTime.equals(startTime) || nowTime.equals(endTime);
                    before = nowTime.after(startTime);
                    after = nowTime.before(endTime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean isLive = (before && after || now) && product.getUserIn() == 1;

                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                        .productId(product.getId())
                        .title(product.getTitle())
                        .buyNowPrice(product.getBuyNowPrice())
                        .instance(product.getInstant() == 1)
                        .live(isLive)
                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                        .image(product.getProductImage1())
                        .build();
                productSearchResponseDAOS.add(productSearchResponseDAO);
            }
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public List<ProductSearchResponseDAO> getProductListByLike(int userId){
        List<Interest> interests = interestRepository.findAllByUserId(userId);
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Interest interest : interests){
            Product product = productRepository.findById(interest.getProduct().getId())
                    .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입이다."));

            if(product.getStatus() == 'Y'){
                Date startTime;
                Date endTime;
                Date nowTime;


                boolean before = false;
                boolean after = false;
                boolean now = false;
                String endTimeStr;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
                try {
                    endTime = simpleDateFormat.parse(product.getStartTime());
                    calendar.setTime(endTime);
                    calendar.add(Calendar.MINUTE, product.getDuration());

                    endTimeStr = simpleDateFormat.format(calendar.getTime());

                    startTime = simpleDateFormat.parse(product.getStartTime());
                    endTime = simpleDateFormat.parse(endTimeStr);
                    nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                    now = nowTime.equals(startTime) || nowTime.equals(endTime);
                    before = nowTime.after(startTime);
                    after = nowTime.before(endTime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean isLive = (before && after || now) && product.getUserIn() == 1;

                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                        .productId(product.getId())
                        .title(product.getTitle())
                        .buyNowPrice(product.getBuyNowPrice())
                        .instance(product.getInstant() == 1)
                        .live(product.getInstant() != 1 && isLive)
                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                        .image(product.getProductImage1())
                        .build();
                productSearchResponseDAOS.add(productSearchResponseDAO);
            }
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public int createProduct(ProductCreateDTO productCreateDTO){
        User user = userRepository.findById(productCreateDTO.getUserId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Category category = categoryRepository.findById(productCreateDTO.getCategoryId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        int imagesLength = productCreateDTO.getProductImages().size();

        Product product = Product.builder()
                .user(user)
                .category(category)
                .title(productCreateDTO.getTitle())
                .description(productCreateDTO.getDescription())
                .startTime(productCreateDTO.getStartTime())
                .duration(productCreateDTO.getDuration())
                .bidPrice(productCreateDTO.getBidPrice())
                .startPrice(productCreateDTO.getStartPrice())
                .instant(productCreateDTO.getInstant())
                .buyNowPrice(productCreateDTO.getBuyNowPrice())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .endPrice(productCreateDTO.getInstant() == 1 ? productCreateDTO.getStartPrice() : 0)
                .status('Y')
                .userIn(0)
                .buyerId(0)
                .productImage1(0 < imagesLength ?  productCreateDTO.getProductImages().get(0) : "")
                .productImage2(1 < imagesLength ? productCreateDTO.getProductImages().get(1) : "")
                .productImage3(2 < imagesLength ? productCreateDTO.getProductImages().get(2) : "")
                .productImage4(3 < imagesLength ? productCreateDTO.getProductImages().get(3) : "")
                .productImage5(4 < imagesLength ? productCreateDTO.getProductImages().get(4) : "")
                .build();

        productRepository.save(product);


        return product.getId();
    }

    @Transactional
    public boolean deleteProduct(int productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setStatus('N');
        product.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        return product.getStatus() == 'N';
    }

    @Transactional
    public boolean updateProduct(ProductUpdateDTO productUpdateDTO){
        Product product = productRepository.findById(productUpdateDTO.getProductId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));


        int imagesLength = productUpdateDTO.getImages().size();

        if(productUpdateDTO.getCategoryId() != 0) {
            Category category = categoryRepository.findById(productUpdateDTO.getCategoryId())
                    .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));
            product.setCategory(category);
        }

        if(!Objects.equals(productUpdateDTO.getTitle(), ""))
            product.setTitle(productUpdateDTO.getTitle());

        if(!Objects.equals(productUpdateDTO.getDescription(), ""))
            product.setDescription(productUpdateDTO.getDescription());

        if(!Objects.equals(productUpdateDTO.getStartTime(), ""))
            product.setStartTime(productUpdateDTO.getStartTime());

        if(productUpdateDTO.getStartPrice() != 0)
            product.setStartPrice(productUpdateDTO.getStartPrice());

        product.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        product.setInstant(productUpdateDTO.getInstant());

        if(productUpdateDTO.getDuration() != 0)
            product.setDuration(productUpdateDTO.getDuration());

        if(productUpdateDTO.getBidPrice() != 0)
            product.setBidPrice(productUpdateDTO.getBidPrice());

        if(productUpdateDTO.getBuyNowPrice() != 0)
            product.setBuyNowPrice(productUpdateDTO.getBuyNowPrice());

        product.setProductImage1(0 < imagesLength ?  productUpdateDTO.getImages().get(0) : "");
        product.setProductImage2(1 < imagesLength ?  productUpdateDTO.getImages().get(1) : "");
        product.setProductImage3(2 < imagesLength ?  productUpdateDTO.getImages().get(2) : "");
        product.setProductImage4(3 < imagesLength ?  productUpdateDTO.getImages().get(3) : "");
        product.setProductImage5(4 < imagesLength ?  productUpdateDTO.getImages().get(4) : "");

        return product.getId() == productUpdateDTO.getProductId();
    }

    @Transactional
    public List<ProductSearchResponseDAO> getProductBySearch(String keyword){
        List<Product> products = productRepository.findAll();
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Product product : products){
            if(product.getStatus() == 'Y'){
                if(product.getCategory().getName().contains(keyword) || product.getTitle().contains(keyword)){

                    Date startTime;
                    Date endTime;
                    Date nowTime;


                    boolean before = false;
                    boolean after = false;
                    boolean now = false;
                    String endTimeStr;
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
                    try {
                        endTime = simpleDateFormat.parse(product.getStartTime());
                        calendar.setTime(endTime);
                        calendar.add(Calendar.MINUTE, product.getDuration());

                        endTimeStr = simpleDateFormat.format(calendar.getTime());

                        startTime = simpleDateFormat.parse(product.getStartTime());
                        endTime = simpleDateFormat.parse(endTimeStr);
                        nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                        now = nowTime.equals(startTime) || nowTime.equals(endTime);
                        before = nowTime.after(startTime);
                        after = nowTime.before(endTime);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    boolean isLive = (before && after || now) && product.getUserIn() == 1;

                    ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                            .productId(product.getId())
                            .title(product.getTitle())
                            .buyNowPrice(product.getBuyNowPrice())
                            .instance(product.getInstant() == 1)
                            .live(isLive)
                            .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                            .image(product.getProductImage1())
                            .build();
                    productSearchResponseDAOS.add(productSearchResponseDAO);
                }
            }
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public List<ProductSearchResponseDAO> getMySellList(int userId){
        List<Product> products = productRepository.findAllByUserId(userId);
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Product product : products){
            Date startTime;
            Date endTime;
            Date nowTime;


            boolean before = false;
            boolean after = false;
            boolean now = false;
            String endTimeStr;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
            try {
                endTime = simpleDateFormat.parse(product.getStartTime());
                calendar.setTime(endTime);
                calendar.add(Calendar.MINUTE, product.getDuration());

                endTimeStr = simpleDateFormat.format(calendar.getTime());

                startTime = simpleDateFormat.parse(product.getStartTime());
                endTime = simpleDateFormat.parse(endTimeStr);
                nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                now = nowTime.equals(startTime) || nowTime.equals(endTime);
                before = nowTime.after(startTime);
                after = nowTime.before(endTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            boolean isLive = (before && after || now) && product.getUserIn() == 1;

            ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                    .productId(product.getId())
                    .title(product.getTitle())
                    .buyNowPrice(product.getBuyNowPrice())
                    .live(isLive)
                    .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                    .image(product.getProductImage1())
                    .build();
            productSearchResponseDAOS.add(productSearchResponseDAO);
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public List<ProductSearchResponseDAO> getMyBuyList(int userId){
        List<Product> products = productRepository.findAll();
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Product product : products){
            if(product.getStatus() == 'S' && product.getBuyerId() == userId){
                Date startTime;
                Date endTime;
                Date nowTime;


                boolean before = false;
                boolean after = false;
                boolean now = false;
                String endTimeStr;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
                try {
                    endTime = simpleDateFormat.parse(product.getStartTime());
                    calendar.setTime(endTime);
                    calendar.add(Calendar.MINUTE, product.getDuration());

                    endTimeStr = simpleDateFormat.format(calendar.getTime());

                    startTime = simpleDateFormat.parse(product.getStartTime());
                    endTime = simpleDateFormat.parse(endTimeStr);
                    nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                    now = nowTime.equals(startTime) || nowTime.equals(endTime);
                    before = nowTime.after(startTime);
                    after = nowTime.before(endTime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean isLive = (before && after || now) && product.getUserIn() == 1;

                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                        .productId(product.getId())
                        .title(product.getTitle())
                        .buyNowPrice(product.getBuyNowPrice())
                        .live(isLive)
                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                        .image(product.getProductImage1())
                        .build();
                productSearchResponseDAOS.add(productSearchResponseDAO);
            }
        }

        Collections.reverse(productSearchResponseDAOS);

        return productSearchResponseDAOS;
    }

    @Transactional
    public boolean instantPurchase(ProductInstantPurchaseDTO productInstantPurchaseDTO){
        Product product = productRepository.findById(productInstantPurchaseDTO.getProductId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));
        User buyer = userRepository.findById(productInstantPurchaseDTO.getBuyerId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        product.setStatus('S');
        product.setBuyerId(buyer.getId());
        buyer.setPoint(buyer.getPoint() - product.getBuyNowPrice());

        PurchaseCreateDTO purchaseCreateDTO = PurchaseCreateDTO.builder()
                .buyerId(productInstantPurchaseDTO.getBuyerId())
                .productId(productInstantPurchaseDTO.getProductId())
                .price(product.getBuyNowPrice())
                .build();

        Point point = Point.builder()
                .user(buyer)
                .point(-1 * product.getBuyNowPrice())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        pointRepository.save(point);

        purchaseService.createPurchaseHistory(purchaseCreateDTO);

        return true;
    }

    @Transactional
    public boolean auctionPurchase(ProductAuctionPurchaseDTO productAuctionPurchaseDTO){
        Product product = productRepository.findById(productAuctionPurchaseDTO.getProductId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));
        User buyer = userRepository.findById(productAuctionPurchaseDTO.getBuyerId())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        product.setStatus('S');
        product.setBuyerId(buyer.getId());
        product.setUserIn(0);
        product.setEndPrice(productAuctionPurchaseDTO.getEndPrice());
        buyer.setPoint(buyer.getPoint() - productAuctionPurchaseDTO.getEndPrice());

        Point point = Point.builder()
                .user(buyer)
                .point(-1 * product.getEndPrice())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        pointRepository.save(point);

        PurchaseCreateDTO purchaseCreateDTO = PurchaseCreateDTO.builder()
                .buyerId(productAuctionPurchaseDTO.getBuyerId())
                .productId(productAuctionPurchaseDTO.getProductId())
                .price(productAuctionPurchaseDTO.getEndPrice())
                .build();

        purchaseService.createPurchaseHistory(purchaseCreateDTO);

        return true;
    }

    @Transactional
    public boolean auctionFail(int productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setStatus('F');
        product.setUserIn(0);

        return product.getStatus() == 'F';
    }

    @Transactional
    public boolean sellerIn(int productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setUserIn(1);

        return product.getUserIn() == 1;
    }
}