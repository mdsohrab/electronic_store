package Electronic.Store.Electronic.Store.Repositories;


import Electronic.Store.Electronic.Store.Entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

   Optional<UserEntity> findByEmail(String email);
   

   List<UserEntity> findByNameContaining(String keywords);


}
