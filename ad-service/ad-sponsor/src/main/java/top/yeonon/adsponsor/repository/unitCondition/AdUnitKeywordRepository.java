package top.yeonon.adsponsor.repository.unitCondition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.yeonon.adsponsor.entity.unitCondition.AdUnitKeyword;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 18:12
 **/
@Repository
public interface AdUnitKeywordRepository extends JpaRepository<AdUnitKeyword, Long> {

}
