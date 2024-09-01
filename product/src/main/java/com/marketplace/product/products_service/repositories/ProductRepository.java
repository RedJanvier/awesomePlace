package com.marketplace.product.products_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketplace.product.products_service.models.Product;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Modifying
//    @Query("update Product p set p.featured = :featured where p.id = :id")
//    int setIsFeaturedById(@Param("id") Integer id, @Param("featured") Boolean featured);

    // @Autowired
    // private EntityManager entityManager;

    // public List<Product> findProductsByTitle(String title) {
    //     JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    //     QProduct product = QProduct.product;
    //     return queryFactory.selectFrom(product)
    //             .where(product.name.containsIgnoreCase(title))
    //             .fetch();
    // }
}
