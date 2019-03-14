package com.mycharx.qdf.service;

import com.mycharx.qdf.entity.QdfRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Qdf role service.
 *
 * @author 张卜亢
 * @date 2019.03.14 23:12:24
 */
public interface QdfRoleService {
    /**
     * Save qdf role.
     *
     * @param qdfRole the qdf role
     * @return the qdf role
     * @author 张卜亢
     * @date 2019.03.14 23:20:11
     */
    QdfRole save(QdfRole qdfRole);

    /**
     * Delete.
     *
     * @param qdfRole the qdf role
     * @author 张卜亢
     * @date 2019.03.14 23:20:11
     */
    void delete(QdfRole qdfRole);

    /**
     * Delete by id.
     *
     * @param id the id
     * @author 张卜亢
     * @date 2019.03.14 23:20:11
     */
    void deleteById(Long id);

    /**
     * Find by id qdf role.
     *
     * @param id the id
     * @return the qdf role
     * @author 张卜亢
     * @date 2019.03.14 23:20:11
     */
    QdfRole findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     * @author 张卜亢
     * @date 2019.03.14 23:20:11
     */
    List<QdfRole> findAll();

    /**
     * Find by page page.
     *
     * @param pageable the pageable
     * @return the page
     * @author 张卜亢
     * @date 2019.03.14 23:20:11
     */
    Page<QdfRole> findByPage(Pageable pageable);
}
