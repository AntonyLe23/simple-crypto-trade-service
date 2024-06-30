package org.anthonyle.simplecryptotradeservice.repositories;

import org.anthonyle.simplecryptotradeservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
