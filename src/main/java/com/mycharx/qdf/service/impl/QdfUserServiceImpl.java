package com.mycharx.qdf.service.impl;

import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.repostory.QdfUserRepostory;
import com.mycharx.qdf.service.QdfUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public QdfUser save(QdfUser qdfUser) {
        return qdfUserRepostory.save(qdfUser);
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
}
