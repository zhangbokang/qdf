package com.mycharx.qdf.service.impl;

import com.mycharx.qdf.entity.QdfRole;
import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.exception.QdfCustomException;
import com.mycharx.qdf.repostory.QdfUserRepostory;
import com.mycharx.qdf.service.QdfRoleService;
import com.mycharx.qdf.service.QdfUserService;
import com.mycharx.qdf.utils.BeanUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Qdf user service.
 *
 * @author 张卜亢
 * @date 2019.03.07 13:07:20
 */
@Service
public class QdfUserServiceImpl implements QdfUserService {

    @Resource
    private QdfUserRepostory qdfUserRepostory;

    @Resource
    private QdfRoleService qdfRoleService;

    @Override
    public QdfUser save(QdfUser qdfUser) {
        //如果id不为空，则执行更新操作
        if (qdfUser.getId() == null) {
            return qdfUserRepostory.save(qdfUser);
        } else {
            Optional<QdfUser> o = qdfUserRepostory.findById(qdfUser.getId());
            if (o.isPresent()) {
                BeanUtil.copyNonNullProperties(qdfUser, o.get());
                return qdfUserRepostory.save(o.get());
            }
            throw new QdfCustomException("更新错误，未找到用户");
        }
    }

    @Override
    public void delete(QdfUser qdfUser) {
        qdfUserRepostory.delete(qdfUser);
    }

    @Override
    public void deleteById(Long id) {
        qdfUserRepostory.deleteById(id);
    }

    @Override
    public QdfUser findById(Long id) {
        return qdfUserRepostory.findById(id).get();
    }

    @Override
    public List<QdfUser> findAll() {
        return qdfUserRepostory.findAll();
    }

    @Override
    public Page<QdfUser> findByPage(Pageable pageable) {
        return qdfUserRepostory.findAll(pageable);
    }

    @Override
    public QdfUser findByUsername(String username) {
        try {
            return qdfUserRepostory.findQdfUserByUsername(username);
        } catch (Exception e) {
            throw new AuthenticationException("User didn't existed!");
        }
    }

    @Override
    public List<QdfUser> findAllById(Set<Long> ids) {
        return qdfUserRepostory.findAllById(ids);
    }

    @Override
    public QdfUser addRoles(Long userId, Set<Long> roleIds) {
        QdfUser qdfUser = this.findById(userId);
        qdfUser.getRoles().addAll(qdfRoleService.findAllById(roleIds));
        return this.save(qdfUser);
    }

    @Override
    public QdfUser delRoles(Long userId, Set<Long> roleIds) {
        QdfUser qdfUser = this.findById(userId);
        qdfUser.getRoles().removeAll(qdfRoleService.findAllById(roleIds));
        return this.save(qdfUser);
    }
}
