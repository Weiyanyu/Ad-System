package top.yeonon.adsponsor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.yeonon.adsponsor.entity.AdCreative;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 18:10
 **/
@Repository
public interface AdCreativeRepository extends JpaRepository<AdCreative, Long> {

    AdCreative findByNameAndUserId(String name, Long userId);

    List<AdCreative> findAllByAuditStatus(Integer auditStatus);
}
