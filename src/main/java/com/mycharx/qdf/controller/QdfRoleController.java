package com.mycharx.qdf.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mycharx.qdf.entity.QdfRole;
import com.mycharx.qdf.entity.jsonview.SimpleUserView;
import com.mycharx.qdf.service.QdfRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Qdf role controller.
 *
 * @author 张卜亢
 * @date 2019.03.14 23:32:43
 */
@RestController
@RequestMapping("/qdfrole")
public class QdfRoleController {

    @Resource
    private QdfRoleService qdfRoleService;

    @RequestMapping("/save")
    @JsonView(SimpleUserView.class)
    public QdfRole save(@RequestBody QdfRole qdfRole) {
        return qdfRoleService.save(qdfRole);
    }

    @RequestMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        qdfRoleService.deleteById(id);
    }

    @RequestMapping("/find/{id}")
    @JsonView(SimpleUserView.class)
    public QdfRole findById(@PathVariable("id") Long id) {
        return qdfRoleService.findById(id);
    }

    @RequestMapping("/find")
    @JsonView(SimpleUserView.class)
    public List<QdfRole> findAll() {
        return qdfRoleService.findAll();
    }

    @RequestMapping("/findpage")
    @JsonView(SimpleUserView.class)
    public Page<QdfRole> findPage(Pageable pageable) {
        return qdfRoleService.findByPage(pageable);
    }
}
