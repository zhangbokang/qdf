package com.mycharx.qdf.service.impl;

import com.mycharx.qdf.entity.QdfRole;
import com.mycharx.qdf.exception.QdfCustomException;
import com.mycharx.qdf.repostory.QdfRoleRepostory;
import com.mycharx.qdf.service.QdfPermissionService;
import com.mycharx.qdf.service.QdfRoleService;
import com.mycharx.qdf.utils.BeanUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Resource
    private QdfPermissionService qdfPermissionService;

    @Override
    public QdfRole save(QdfRole qdfRole) {
        //如果id不为空，则执行更新操作
        if (qdfRole.getId() == null) {
            return qdfRoleRepostory.save(qdfRole);
        } else {
            Optional<QdfRole> o = qdfRoleRepostory.findById(qdfRole.getId());
            if (o.isPresent()) {
                BeanUtil.copyNonNullProperties(qdfRole, o.get());
                return qdfRoleRepostory.save(o.get());
            }
            throw new QdfCustomException("更新错误，未找到角色");
        }
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

    @Override
    public QdfRole addPermissions(Long roleId, Set<Long> permissionIds) {
        QdfRole qdfRole = this.findById(roleId);
        qdfRole.getPermissions().addAll(qdfPermissionService.findAllById(permissionIds));
        return this.save(qdfRole);
    }

    @Override
    public List<QdfRole> findAllById(Set<Long> ids) {
        return qdfRoleRepostory.findAllById(ids);
    }


}
