package Electronic.Store.Electronic.Store.Repositories;

import Electronic.Store.Electronic.Store.Dtos.PageableResponse;
import Electronic.Store.Electronic.Store.Entities.CategoryEntity;
import Electronic.Store.Electronic.Store.Entities.ProductEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    Page<ProductEntity> findByTitleContaining(String subTitle, Pageable pageable);

    Page<ProductEntity> findByLiveTrue(Pageable pageable);

    Page<ProductEntity> findByCategory(CategoryEntity category, Pageable pageable);
    //OtherMethods

}
