package com.mycharx.qdf.repostory;

import com.mycharx.qdf.entity.QdfUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Qdf user repostory.
 *
 * @author 张卜亢
 * @date 2019.03.07 13:07:46
 */
public interface QdfUserRepostory extends JpaRepository<QdfUser, Long> {
    /**
     * Find qdf user by username qdf user.
     *
     * @param username the username
     * @return the qdf user
     * @author 张卜亢
     * @date 2019.03.14 16:49:45
     */
    QdfUser findQdfUserByUsername(String username);
}
