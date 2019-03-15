package com.mycharx.qdf.service.impl;

import com.mycharx.qdf.entity.QdfPermission;
import com.mycharx.qdf.exception.QdfCustomException;
import com.mycharx.qdf.repostory.QdfPermissionRepostory;
import com.mycharx.qdf.service.QdfPermissionService;
import com.mycharx.qdf.utils.BeanUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Qdf permission service.
 *
 * @author 张卜亢
 * @date 2019.03.14 23:22:43
 */
@Service
public class QdfPermissionServiceImpl implements QdfPermissionService {

    @Resource
    private QdfPermissionRepostory qdfPermissionRepostory;

    @Override
    public QdfPermission save(QdfPermission qdfPermission) {
        //如果id不为空，则执行更新操作
        if (qdfPermission.getId() == null) {
            return qdfPermissionRepostory.save(qdfPermission);
        } else {
            Optional<QdfPermission> o = qdfPermissionRepostory.findById(qdfPermission.getId());
            if (o.isPresent()) {
                BeanUtil.copyNonNullProperties(qdfPermission, o.get());
                return qdfPermissionRepostory.save(o.get());
            }
            throw new QdfCustomException("更新错误，未找到权限");
        }
    }

    @Override
    public void delete(QdfPermission qdfPermission) {
        qdfPermissionRepostory.delete(qdfPermission);
    }

    @Override
    public void deleteById(Long id) {
        qdfPermissionRepostory.deleteById(id);
    }

    @Override
    public QdfPermission findById(Long id) {
        return qdfPermissionRepostory.findById(id).get();
    }

    @Override
    public List<QdfPermission> findAll() {
        return qdfPermissionRepostory.findAll();
    }

    @Override
    public Page<QdfPermission> findByPage(Pageable pageable) {
        return qdfPermissionRepostory.findAll(pageable);
    }

    @Override
    public List<QdfPermission> findAllById(Set<Long> ids) {
        return qdfPermissionRepostory.findAllById(ids);
    }
}
