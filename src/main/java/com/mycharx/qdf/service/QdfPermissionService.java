package com.mycharx.qdf.service;

import com.mycharx.qdf.entity.QdfPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Qdf permission service.
 *
 * @author 张卜亢
 * @date 2019.03.14 23:12:29
 */
public interface QdfPermissionService {
    /**
     * Save qdf permission.
     *
     * @param qdfPermission the qdf permission
     * @return the qdf permission
     * @author 张卜亢
     * @date 2019.03.14 23:21:42
     */
    QdfPermission save(QdfPermission qdfPermission);

    /**
     * Delete.
     *
     * @param qdfPermission the qdf permission
     * @author 张卜亢
     * @date 2019.03.14 23:21:42
     */
    void delete(QdfPermission qdfPermission);

    /**
     * Delete by id.
     *
     * @param id the id
     * @author 张卜亢
     * @date 2019.03.14 23:21:42
     */
    void deleteById(Long id);

    /**
     * Find by id qdf permission.
     *
     * @param id the id
     * @return the qdf permission
     * @author 张卜亢
     * @date 2019.03.14 23:21:42
     */
    QdfPermission findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     * @author 张卜亢
     * @date 2019.03.14 23:21:42
     */
    List<QdfPermission> findAll();

    /**
     * Find by page page.
     *
     * @param pageable the pageable
     * @return the page
     * @author 张卜亢
     * @date 2019.03.14 23:21:42
     */
    Page<QdfPermission> findByPage(Pageable pageable);
}
