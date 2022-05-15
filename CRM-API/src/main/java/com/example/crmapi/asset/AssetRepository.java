package com.example.crmapi.asset;

import com.example.crmapi.genericCrud.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends GenericRepository<Asset> {
    @Query("SELECT a FROM Asset a left join fetch a.user left join fetch a.contact")
    List<Asset> findAllAssets();
    @Query("SELECT a FROM Asset a left join fetch a.user left join fetch a.contact WHERE a.id IN :ids")
    List<Asset> findAllByIds(@Param("ids") List<Long> ids);
    @Query("SELECT a FROM Asset a LEFT JOIN fetch a.user LEFT JOIN fetch a.contact WHERE a.id = ?1")
    Optional<Asset> findById(Long id);
}
