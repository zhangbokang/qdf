package com.mycharx.qdf.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.mycharx.qdf.entity.jsonview.SimpleUserView;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表
 *
 * @author 张卜亢
 * @date 2019.03.14 17:41:45
 */
@Entity
@Data
public class QdfUser implements Serializable {

    private static final long serialVersionUID = 1L;

//    public interface SimpleView{}
//    public interface DetailView extends SimpleView{}
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.mycharx.qdf.common.QdfIdGenerator")
    @JsonView(SimpleUserView.class)
    private Long id;
    /**
     * 账号
     */
    @JsonView(SimpleUserView.class)
    private String username;
    /**
     * 名字
     */
    @JsonView(SimpleUserView.class)
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 联系电话
     */
    @JsonView(SimpleUserView.class)
    private String phone;
    /**
     * 备注
     */
    @JsonView(SimpleUserView.class)
    private String tips;
    /**
     * 状态 1:正常 2:禁用
     */
    @JsonView(SimpleUserView.class)
    private Integer state;
    /**
     * 创建时间
     */
    @JsonView(SimpleUserView.class)
    private Date createdTime;
    /**
     * 更新时间
     */
    @JsonView(SimpleUserView.class)
    private Date updatedTime;

    /**
     * 用户的角色
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "qdf_user_role",//关联表名
            joinColumns = @JoinColumn(name = "user_id"),//关联本类的ID
            inverseJoinColumns = @JoinColumn(name = "role_id")//关联对方类的ID
    )
    @JsonView(SimpleUserView.class)
    private Set<QdfRole> roles = new HashSet<>();

}
