package com.massonus.rccnavigator.repo;

import com.massonus.rccnavigator.entity.WishObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishObjectRepo extends JpaRepository<WishObject, Long> {

    WishObject findWishObjectByProductIdAndUserId(Long productId, Long userId);

    List<WishObject> findWishObjectsByUserId(Long userId);
}
