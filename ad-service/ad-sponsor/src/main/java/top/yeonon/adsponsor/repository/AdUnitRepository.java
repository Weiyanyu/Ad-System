package top.yeonon.adsponsor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.yeonon.adsponsor.entity.AdUnit;

import java.util.List;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 17:31
 **/
@Repository
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
