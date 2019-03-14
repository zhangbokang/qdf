package com.mycharx.qdf.service.impl;

import com.mycharx.qdf.entity.QdfRole;
import com.mycharx.qdf.repostory.QdfRoleRepostory;
import com.mycharx.qdf.service.QdfRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Qdf role service.
 *
 * @author 张卜亢
 * @date 2019.03.14 23:22:38
 */
@Service
public class QdfRoleServiceImpl implements QdfRoleService {

    @Resource
    private QdfRoleRepostory qdfRoleRepostory;

    @Override
    public QdfRole save(QdfRole qdfRole) {
        return qdfRoleRepostory.save(qdfRole);
    }

    @Override
    public void delete(QdfRole qdfRole) {
        qdfRoleRepostory.delete(qdfRole);
    }

    @Override
    public void deleteById(Long id) {
        qdfRoleRepostory.deleteById(id);
    }

    /**
     * @param id the id
     * @return
     * @TODO 研究下Optional后优化下
     */
    @Override
    public QdfRole findById(Long id) {
        return qdfRoleRepostory.findById(id).get();
    }

    @Override
    public List<QdfRole> findAll() {
        return qdfRoleRepostory.findAll();
    }

    @Override
    public Page<QdfRole> findByPage(Pageable pageable) {
        return qdfRoleRepostory.findAll(pageable);
    }
}
