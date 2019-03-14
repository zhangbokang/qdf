package com.mycharx.qdf.service.impl;

import com.mycharx.qdf.entity.QdfPermission;
import com.mycharx.qdf.repostory.QdfPermissionRepostory;
import com.mycharx.qdf.service.QdfPermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        return qdfPermissionRepostory.save(qdfPermission);
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
}
