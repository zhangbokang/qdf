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
    @JsonView(SimpleUserView.class)
    private Long id;
    /**
     * 角色名称
     */
    @JsonView(SimpleUserView.class)
    private String role;
    /**
     * 角色说明
     */
    @JsonView(SimpleUserView.class)
    private String description;
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
     * 角色的用户
     * cascade参数,（可选）必须级联到关联目标的操作。
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "qdf_user_role",//关联表名
            joinColumns = @JoinColumn(name = "role_id"),//关联本类的ID
            inverseJoinColumns = @JoinColumn(name = "user_id")//关联对方类的ID
    )
    @Transient
    private Set<QdfUser> users = new HashSet<>();

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
    @JsonView(SimpleUserView.class)
    private Set<QdfPermission> permissions = new HashSet<>();
}
