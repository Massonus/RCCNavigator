package com.massonus.rccnavigator.repo;

import com.massonus.rccnavigator.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepo extends JpaRepository<AccessToken, Long> {

}
