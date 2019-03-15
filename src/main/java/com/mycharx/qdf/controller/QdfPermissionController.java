package com.mycharx.qdf.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mycharx.qdf.entity.QdfPermission;
import com.mycharx.qdf.entity.jsonview.SimpleUserView;
import com.mycharx.qdf.service.QdfPermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Qdf permission controller.
 *
 * @author 张卜亢
 * @date 2019.03.14 23:32:50
 */
@RestController
@RequestMapping("/qdfpermission")
public class QdfPermissionController {

    @Resource
    private QdfPermissionService qdfPermissionService;

    @RequestMapping("/save")
    @JsonView(SimpleUserView.class)
    public QdfPermission save(@RequestBody QdfPermission qdfPermission) {
        return qdfPermissionService.save(qdfPermission);
    }

    @RequestMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        qdfPermissionService.deleteById(id);
    }

    @RequestMapping("/find/{id}")
    @JsonView(SimpleUserView.class)
    public QdfPermission findById(@PathVariable("id") Long id) {
        return qdfPermissionService.findById(id);
    }

    @RequestMapping("/find")
    @JsonView(SimpleUserView.class)
    public List<QdfPermission> findAll() {
        return qdfPermissionService.findAll();
    }

    @RequestMapping("/findpage")
    @JsonView(SimpleUserView.class)
    public Page<QdfPermission> findPage(Pageable pageable) {
        return qdfPermissionService.findByPage(pageable);
    }
}
