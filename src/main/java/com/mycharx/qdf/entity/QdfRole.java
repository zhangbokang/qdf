package com.mycharx.qdf.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 角色表
 *
 * @author 张卜亢
 * @date 2019.03.14 17:42:37
 */
@Entity
@Data
public class QdfRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.mycharx.qdf.common.QdfIdGenerator")
    private Long id;
    /**
     * 角色名称
     */
    private String role;
    /**
     * 角色说明
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 角色的用户
     * cascade参数,（可选）必须级联到关联目标的操作。
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "qdf_user_role",//关联表名
            joinColumns = @JoinColumn(name = "role_id"),//关联本类的ID
            inverseJoinColumns = @JoinColumn(name = "user_id")//关联对方类的ID
    )
    private Set<QdfUser> users;

    /**
     * 角色的权限
     * cascade参数,（可选）必须级联到关联目标的操作。
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "qdf_role_permission",//关联表名
            joinColumns = @JoinColumn(name = "role_id"),//关联本类的ID
            inverseJoinColumns = @JoinColumn(name = "permission_id")//关联对方类的ID
    )
    private Set<QdfPermission> permissions;
}
