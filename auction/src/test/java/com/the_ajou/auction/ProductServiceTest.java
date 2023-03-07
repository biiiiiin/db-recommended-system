package com.the_ajou.auction;

import com.the_ajou.model.category.Category;
import com.the_ajou.model.category.CategoryRepository;
import com.the_ajou.model.interests.InterestRepository;
import com.the_ajou.model.product.Product;
import com.the_ajou.model.product.ProductRepository;
import com.the_ajou.model.user.User;
import com.the_ajou.model.user.UserRepository;
import com.the_ajou.service.PurchaseService;
import com.the_ajou.web.dao.product.ProductResponseDAO;
import com.the_ajou.web.dao.product.ProductSearchResponseDAO;
import com.the_ajou.web.dto.purchase.PurchaseCreateDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
class ProductServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private PurchaseService purchaseService;

    @Transactional
    @Rollback
    @Test
    void getProduct(){
        Product product = productRepository.findById(1)
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

        String endTimeStr = "";
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

        ProductResponseDAO productResponseDAO = ProductResponseDAO.builder()
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
                .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                .live(before && after || now)
                .productImages(images)
                .build();

        assertThat(productResponseDAO).isNotNull();
    }

//    @Transactional
//    @Rollback
//    @Test
//    void getProductList(){
//        List<Product> products = productRepository.findAll();
//        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();
//
//        for(Product product : products){
//            if(product.getStatus() == 'N'){
//                Date startTime;
//                Date endTime;
//                Date nowTime;
//
//
//                boolean before = false;
//                boolean after = false;
//                boolean now = false;
//                String endTimeStr = "";
//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
//                try {
//                    endTime = simpleDateFormat.parse(product.getStartTime());
//                    calendar.setTime(endTime);
//                    calendar.add(Calendar.MINUTE, product.getDuration());
//
//                    endTimeStr = simpleDateFormat.format(calendar.getTime());
//
//                    startTime = simpleDateFormat.parse(product.getStartTime());
//                    endTime = simpleDateFormat.parse(endTimeStr);
//                    nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
//                    now = nowTime.equals(startTime) || nowTime.equals(endTime);
//                    before = nowTime.after(startTime);
//                    after = nowTime.before(endTime);
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
//                        .title(product.getTitle())
//                        .buyNowPrice(product.getBuyNowPrice())
//                        .live(before && after || now)
//                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
//                        .image(product.getProductImage1())
//                        .build();
//                productSearchResponseDAOS.add(productSearchResponseDAO);
//            }
//        }
//
//        //assertThat(productSearchResponseDAOS).isNotEmpty();
//    }


    @Transactional
    @Rollback
    @Test
    void getProductListByCategoryId(){
        List<Product> products = productRepository.findAllByCategoryId(3);
        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();

        for(Product product : products){
            if(product.getStatus() == 'N'){
                Date startTime;
                Date endTime;
                Date nowTime;


                boolean before = false;
                boolean after = false;
                boolean now = false;
                String endTimeStr = "";
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
                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
                        .title(product.getTitle())
                        .buyNowPrice(product.getBuyNowPrice())
                        .live(before && after || now)
                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
                        .image(product.getProductImage1())
                        .build();
                productSearchResponseDAOS.add(productSearchResponseDAO);
            }
        }

        //assertThat(productSearchResponseDAOS).isNotEmpty();
    }

    @Transactional
    @Rollback
    @Test
    void createProduct(){
        User user = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Category category = categoryRepository.findById(3)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Product product = Product.builder()
                .user(user)
                .category(category)
                .title("Test Title")
                .description("Test Description")
                .startTime("2022-11-11 11:11")
                .duration(11)
                .bidPrice(100)
                .startPrice(2000)
                .instant(0)
                .buyNowPrice(0)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .endPrice(0)
                .status('N')
                .buyerId(0)
                .productImage1("TEST IMAGE 1")
                .productImage2("TEST IMAGE 2")
                .productImage3("TEST IMAGE 3")
                .productImage4("TEST IMAGE 4")
                .productImage5("TEST IMAGE 5")
                .build();

        productRepository.save(product);

        assertThat(product).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void deleteProduct(){
        Product product = productRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.setStatus('Y');
        product.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        assertThat(product.getStatus()).isEqualTo('Y');
    }

    @Transactional
    @Rollback
    @Test
    void updateProduct(){
        Product product = productRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));

        Category category = categoryRepository.findById(3)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));
        product.setCategory(category);

        product.setTitle("Test Title");

        product.setDescription("Test Description");

        product.setStartTime("2022-11-11 11:11");

        product.setStartPrice(10000);

        product.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        product.setInstant(1);

        product.setDuration(30);

        product.setBidPrice(500);

        product.setBuyNowPrice(50000);

        product.setProductImage1("Test Image1");
        product.setProductImage2("Test Image2");
        product.setProductImage3("Test Image3");
        product.setProductImage4("Test Image4");
        product.setProductImage5("Test Image5");

        assertThat(product.getTitle()).isEqualTo("Test Title");
    }

//    @Transactional
//    @Rollback
//    @Test
//    void getProductBySearch(){
//        List<Product> products = productRepository.findAll();
//        List<ProductSearchResponseDAO> productSearchResponseDAOS = new LinkedList<>();
//
//        for(Product product : products){
//            if(product.getCategory().getName().contains("자동차") || product.getTitle().contains("자동차")){
//
//                Date startTime;
//                Date endTime;
//                Date nowTime;
//
//
//                boolean before = false;
//                boolean after = false;
//                boolean now = false;
//                String endTimeStr = "";
//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat simpleDateFormat  = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
//                try {
//                    endTime = simpleDateFormat.parse(product.getStartTime());
//                    calendar.setTime(endTime);
//                    calendar.add(Calendar.MINUTE, product.getDuration());
//
//                    endTimeStr = simpleDateFormat.format(calendar.getTime());
//
//                    startTime = simpleDateFormat.parse(product.getStartTime());
//                    endTime = simpleDateFormat.parse(endTimeStr);
//                    nowTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
//                    now = nowTime.equals(startTime) || nowTime.equals(endTime);
//                    before = nowTime.after(startTime);
//                    after = nowTime.before(endTime);
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//                ProductSearchResponseDAO productSearchResponseDAO = ProductSearchResponseDAO.builder()
//                        .title(product.getTitle())
//                        .buyNowPrice(product.getBuyNowPrice())
//                        .live(before && after || now)
//                        .like(interestRepository.findByProductIdAndUserId(product.getId(), product.getUser().getId()) != null)
//                        .image(product.getProductImage1())
//                        .build();
//                productSearchResponseDAOS.add(productSearchResponseDAO);
//            }
//        }
//
//        Assertions.assertThat(productSearchResponseDAOS).isNotEmpty();
//    }

    @Transactional
    @Rollback
    @Test
    void instantPurchase(){
        Product product = productRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));
        User buyer = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        product.setStatus('Y');
        buyer.setPoint(buyer.getPoint() - product.getBuyNowPrice());

        PurchaseCreateDTO purchaseCreateDTO = PurchaseCreateDTO.builder()
                .buyerId(buyer.getId())
                .productId(product.getId())
                .price(product.getBuyNowPrice())
                .build();

        purchaseService.createPurchaseHistory(purchaseCreateDTO);

        assertThat(purchaseCreateDTO).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void auctionPurchase(){
        Product product = productRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 상품입니다."));
        User buyer = userRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));

        product.setStatus('Y');
        product.setEndPrice(1000000);
        buyer.setPoint(buyer.getPoint() - 1000000);

        PurchaseCreateDTO purchaseCreateDTO = PurchaseCreateDTO.builder()
                .buyerId(buyer.getId())
                .productId(product.getId())
                .price(1000000)
                .build();

        purchaseService.createPurchaseHistory(purchaseCreateDTO);

        assertThat(purchaseCreateDTO).isNotNull();
    }
}
