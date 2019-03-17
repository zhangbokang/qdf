package com.mycharx.qdf.service.impl;

import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.exception.QdfCustomException;
import com.mycharx.qdf.exception.UnauthorizedException;
import com.mycharx.qdf.repostory.QdfUserRepostory;
import com.mycharx.qdf.service.QdfRoleService;
import com.mycharx.qdf.service.QdfUserService;
import com.mycharx.qdf.utils.BeanUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        //如果id为空，则执行保存操作，否则执行更新操作
        if (qdfUser.getId() == null) {
            qdfUser.setPassword(BCrypt.hashpw(qdfUser.getPassword(), BCrypt.gensalt()));
            //如果没有设置状态默认为 0:正常
            if (qdfUser.getState() == null) {
                qdfUser.setState(0);
            }
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

    @Override
    public QdfUser checkLogin(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new QdfCustomException("用户名和密码不能为空");
        }
        QdfUser qdfUser = new QdfUser();
        qdfUser.setUsername(username);
        qdfUser.setPassword(password);
        QdfUser q = this.findByUsername(qdfUser.getUsername());
        if (!BCrypt.checkpw(qdfUser.getPassword(), q.getPassword())) {
            throw new UnauthorizedException("用户名或密码错误");
        }
        return q;
    }
}
