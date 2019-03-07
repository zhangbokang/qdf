package com.mycharx.qdf.controller;

import com.mycharx.qdf.entity.QdfUser;
import com.mycharx.qdf.service.QdfUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The type Qdf user controller.
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
    public QdfUser save(QdfUser qdfUser) {
        return qdfUserService.save(qdfUser);
    }

    @RequestMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        qdfUserService.deleteById(id);
    }

    @RequestMapping("/findbyid/{id}")
    public QdfUser findById(@PathVariable("id") Long id) {
        return qdfUserService.findById(id);
    }
}
