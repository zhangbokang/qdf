package com.mycharx.qdf.service;

import com.mycharx.qdf.entity.QdfUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Qdf user service.
 *
 * @author 张卜亢
 * @date 2019.03.07 13:07:29
 */
public interface QdfUserService {
    /**
     * Save qdf user.
     *
     * @param qdfUser the qdf user
     * @return the qdf user
     * @author 张卜亢
     * @date 2019.03.07 13:46:11
     */
    QdfUser save(QdfUser qdfUser);

    /**
     * Delete.
     *
     * @param qdfUser the qdf user
     * @author 张卜亢
     * @date 2019.03.07 13:46:11
     */
    void delete(QdfUser qdfUser);

    /**
     * Delete by id.
     *
     * @param id the id
     * @author 张卜亢
     * @date 2019.03.07 13:46:11
     */
    void deleteById(Long id);

    /**
     * Find by id qdf user.
     *
     * @param id the id
     * @return the qdf user
     * @author 张卜亢
     * @date 2019.03.07 13:46:11
     */
    QdfUser findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     * @author 张卜亢
     * @date 2019.03.07 13:46:11
     */
    List<QdfUser> findAll();

    /**
     * Find by page page.
     *
     * @param pageable the pageable
     * @return the page
     * @author 张卜亢
     * @date 2019.03.07 13:46:11
     */
    Page<QdfUser> findByPage(Pageable pageable);
}
