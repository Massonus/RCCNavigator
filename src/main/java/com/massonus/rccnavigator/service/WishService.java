package com.massonus.rccnavigator.service;

import com.massonus.rccnavigator.dto.ItemDto;
import com.massonus.rccnavigator.entity.Product;
import com.massonus.rccnavigator.entity.User;
import com.massonus.rccnavigator.entity.Wish;
import com.massonus.rccnavigator.entity.WishObject;
import com.massonus.rccnavigator.repo.UserRepo;
import com.massonus.rccnavigator.repo.WishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WishService {

    private final ProductService productService;
    private final WishRepo wishRepo;
    private final UserRepo userRepo;
    private final WishObjectService objectService;

    @Autowired
    public WishService(ProductService productService, WishRepo wishRepo, UserRepo userRepo, WishObjectService objectService) {
        this.productService = productService;
        this.wishRepo = wishRepo;
        this.userRepo = userRepo;
        this.objectService = objectService;
    }

    public Long addProductToWishes(Long id, Long userId) {

        final Product productById = productService.getProductById(id);

        final Wish currentWish = getUserWish(userId);
        User userById = userRepo.findUserById(userId);
        List<WishObject> wishObjects = currentWish.getWishObjects();

        WishObject wishObject = new WishObject();
        wishObject.setProductId(productById.getId());
        wishObject.setTitle(productById.getTitle());
        wishObject.setImage(productById.getImage());
        wishObject.setImageLink(productById.getImageLink());
        wishObject.setPrice(productById.getPrice());
        wishObject.setUser(userById);
        wishObject.setCompany(productById.getCompany());

        objectService.saveWishObject(wishObject);

        wishObjects.add(wishObject);

        wishRepo.save(currentWish);
        userById.setWish(currentWish);

        return productById.getCompany().getId();
    }

    public Wish getUserWish(Long userId) {
        Wish wishByUserId = getWishByUserId(userId);
        if (Objects.isNull(wishByUserId)) {
            Wish wish = new Wish();
            wish.setUser(userRepo.findUserById(userId));
            return wishRepo.save(wish);
        }
        return wishByUserId;
    }

    private Wish getWishByUserId(Long id) {
        return wishRepo.findWishByUserId(id);
    }

    public Wish getWishById(Long id) {
        return wishRepo.findWishById(id);
    }

    public Boolean isInWishes(String productId, String userId) {

        return getUserWish(Long.valueOf(userId)).getWishObjects().stream().anyMatch(o -> o.getProductId().equals(Long.valueOf(productId)));
    }

    public ItemDto deleteWishItem(ItemDto itemDto, User user) {
        WishObject wishObjectById = objectService.getWishObjectByProductIdAndUserId(itemDto.getProductId(), user.getId());
        getWishByUserId(user.getId()).getWishObjects().remove(wishObjectById);
        objectService.deleteWishObject(wishObjectById);
        itemDto.setItemId(wishObjectById.getId());
        return itemDto;
    }

    public void clearWishes(final User user) {
        userRepo.findUserById(user.getId()).setWish(null);
        wishRepo.delete(getWishByUserId(user.getId()));
    }
}
