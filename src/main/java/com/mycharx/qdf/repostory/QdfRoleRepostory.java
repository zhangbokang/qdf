package com.mycharx.qdf.repostory;

import com.mycharx.qdf.entity.QdfRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Qdf role repostory.
 *
 * @author 张卜亢
 * @date 2019.03.14 23:11:15
 */
public interface QdfRoleRepostory extends JpaRepository<QdfRole, Long> {
}
