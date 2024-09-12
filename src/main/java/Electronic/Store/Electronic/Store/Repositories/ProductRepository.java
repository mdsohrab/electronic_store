package Electronic.Store.Electronic.Store.Repositories;

import Electronic.Store.Electronic.Store.Entities.ProductEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    Page<ProductEntity> findByTitleContaining(String subTitle, Pageable pageable);

    Page<ProductEntity> findByLiveTrue(Pageable pageable);

    //OtherMethods

}
