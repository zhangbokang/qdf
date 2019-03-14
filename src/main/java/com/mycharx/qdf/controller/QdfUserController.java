package com.mycharx.qdf.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.service.QdfUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户相关controller
 *
 * @author 张卜亢
 * @date 2019.03.07 13:08:15
 */
@RestController
@RequestMapping("/qdfuser")
public class QdfUserController {

    @Resource
    private QdfUserService qdfUserService;

    @RequestMapping("/save")
    @JsonView(QdfUser.SimpleView.class)
    public QdfUser save(@RequestBody QdfUser qdfUser) {
        return qdfUserService.save(qdfUser);
    }

    @RequestMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        qdfUserService.deleteById(id);
    }

    @RequestMapping("/find/{id}")
    @JsonView(QdfUser.SimpleView.class)
    public QdfUser findById(@PathVariable("id") Long id) {
        return qdfUserService.findById(id);
    }

    @RequestMapping("/find")
    @JsonView(QdfUser.SimpleView.class)
    public List<QdfUser> findAll() {
        return qdfUserService.findAll();
    }

    @RequestMapping("/findpage")
    @JsonView(QdfUser.SimpleView.class)
    public Page<QdfUser> findPage(Pageable pageable) {
        return qdfUserService.findByPage(pageable);
    }
}
