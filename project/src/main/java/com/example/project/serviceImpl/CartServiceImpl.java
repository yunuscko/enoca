package com.example.project.serviceImpl;

import com.example.project.Exception.ProductIsNotFound;
import com.example.project.Exception.WrongParameter;
import com.example.project.entity.Cart;
import com.example.project.entity.Customer;
import com.example.project.entity.Entry;
import com.example.project.entity.Product;
import com.example.project.repository.CartRepository;
import com.example.project.repository.EntryRepository;
import com.example.project.service.CartService;
import com.example.project.service.ProductService;
import com.example.project.service.SessionService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    @Resource
    private CartRepository cartRepository;

    @Resource
    private SessionService sessionService;

    @Resource
    private ProductService productService;

    @Resource
    private EntryRepository entryRepository;


    @Override
    public Cart getCartForCustomer(Customer customer) {
        Optional<Cart> optionalCart=cartRepository.findCartByCustomer(customer);
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            calculateCart(cart);
            return cart;
        }else {
            Cart cart=new Cart();
            cart.setCustomer(customer);
            return cart;
        }
    }
    @Override
    public void updateCart(String productCode, int quantity) throws Exception {
        Product product=productService.getProductForCode(productCode);
        Cart cart=sessionService.getCurrentCart();

        if(quantity<product.getStockValue()){
            if(quantity<=0){
                throw new WrongParameter("Wrong Parameter"+productCode);
            }
            if(Objects.nonNull(cart)){
                Entry entry=cart.getEntries().stream().filter(entry1 -> entry1.getProduct().equals(product)).findFirst()
                        .orElseThrow();
                entry.setQuantity(quantity);
                calculateCart(cart);
                cartRepository.save(cart);
            }else {
                throw new WrongParameter("");
            }
        }
    }

    @Override
    public void emptyCart() {
        Cart cart=sessionService.getCurrentCart();
        if(Objects.nonNull(cart)){
            cartRepository.delete(cart);
            cartRepository.save(cart);
        }

    }

    @Override
    public void calculateCart(Cart cart) {
        double total=0;
        if(CollectionUtils.isEmpty(cart.getEntries())){
            throw new ProductIsNotFound("no entry");
        }else {
            for(Entry entry:cart.getEntries()){
                Double price=entry.getProduct().getPrice();
                Integer quantity=entry.getQuantity();
                total=total+(quantity*price);
            }
        }
        cart.setTotalPriceOfProduct(total);
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void addProductToCart(String productCode, int quantity) throws Exception {
        final Customer currentCustomer=sessionService.getCurrentCustomer();
        if (Objects.nonNull(currentCustomer)) {
            final Cart cart = sessionService.getCurrentCart();
            final Set<Entry> entries = cart.getEntries() != null ? new HashSet<>(cart.getEntries()) : new HashSet<>();

            final Product product = productService.getProductForCode(productCode);
            if(quantity > product.getStockValue()){
                throw new Exception("QUANTITY MUST BE LESS THAN OR EQUAL STOCKVALUE!");
            }
            final Entry entry = new Entry();
            entry.setProduct(product);
            entry.setQuantity(quantity);
            entry.setEntryPrice(product.getPrice() * quantity);
            entryRepository.save(entry);

            entries.add(entry);
            cart.setEntries(entries);
            calculateCart(cart);
            cartRepository.save(cart);

        } else {
            throw new Exception("User not found.");
        }
}
    @Override
    public void removeProductToCart(String ProductCode) {
        final Customer currentCustomer=sessionService.getCurrentCustomer();
        if (Objects.nonNull(currentCustomer)) {
            final Cart cart=sessionService.getCurrentCart();
            final Set<Entry> entries=new HashSet<>(cart.getEntries());

            Entry targetEntry=entries.stream()
                    .filter(entry -> entry.getProduct().getCode().equals(ProductCode))
                    .findFirst()
                    .orElseThrow(()->new ProductIsNotFound("Entry is not found"+ProductCode));
            entries.remove(targetEntry);

            cart.setEntries(entries);
            calculateCart(cart);
            cartRepository.save(cart);

        }else {
            throw new ProductIsNotFound("user not found");
        }
    }

}
