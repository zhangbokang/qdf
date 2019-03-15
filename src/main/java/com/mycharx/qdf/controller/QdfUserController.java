package com.mycharx.qdf.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mycharx.qdf.entity.QdfRole;
import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.entity.jsonview.SimpleUserView;
import com.mycharx.qdf.service.QdfRoleService;
import com.mycharx.qdf.service.QdfUserService;
import com.mycharx.qdf.utils.StrUtil;
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
    @JsonView(SimpleUserView.class)
    public QdfUser save(@RequestBody QdfUser qdfUser) {
        return qdfUserService.save(qdfUser);
    }

    @RequestMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        qdfUserService.deleteById(id);
    }

    @RequestMapping("/find/{id}")
    @JsonView(SimpleUserView.class)
    public QdfUser findById(@PathVariable("id") Long id) {
        return qdfUserService.findById(id);
    }

    @RequestMapping("/find")
    @JsonView(SimpleUserView.class)
    public List<QdfUser> findAll() {
        return qdfUserService.findAll();
    }

    @RequestMapping("/findpage")
    @JsonView(SimpleUserView.class)
    public Page<QdfUser> findPage(Pageable pageable) {
        return qdfUserService.findByPage(pageable);
    }

    @RequestMapping("/addrole")
    @JsonView(SimpleUserView.class)
    public QdfUser addRoles(Long userId, String roleIds) {
        return qdfUserService.addRoles(userId, StrUtil.strToLongSet(roleIds));
    }

    @RequestMapping("/delrole")
    @JsonView(SimpleUserView.class)
    public QdfUser delRoles(Long userId, String roleIds) {
        return qdfUserService.delRoles(userId, StrUtil.strToLongSet(roleIds));
    }
}
