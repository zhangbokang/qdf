package com.mycharx.qdf.repostory;

import com.mycharx.qdf.entity.QdfPermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Qdf permission repostory.
 *
 * @author 张卜亢
 * @date 2019.03.14 23:10:23
 */
public interface QdfPermissionRepostory extends JpaRepository<QdfPermission, Long> {
}
