package Electronic.Store.Electronic.Store.Repositories;

import Electronic.Store.Electronic.Store.Entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {

}
