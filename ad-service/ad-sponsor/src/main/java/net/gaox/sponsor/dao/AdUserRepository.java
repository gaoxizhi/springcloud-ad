package net.gaox.sponsor.dao;

import net.gaox.sponsor.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * <h2>根据用户名查找用户记录</h2>
     */
    AdUser findByUsername(String username);
}