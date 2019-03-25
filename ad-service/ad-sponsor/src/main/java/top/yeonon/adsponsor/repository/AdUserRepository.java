package top.yeonon.adsponsor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.yeonon.adsponsor.entity.AdUser;

/**
 * @Author yeonon
 * @date 2019/3/20 0020 21:18
 **/
@Repository
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    AdUser findByUsername(String username);
}
