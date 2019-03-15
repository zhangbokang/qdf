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
 * 权限表
 *
 * @author 熊能
 * @version 1.0
 * @since 2018/01/02
 */
@Entity
@Data
public class QdfPermission implements Serializable {

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
     * 权限名称
     */
    @JsonView(SimpleUserView.class)
    private String permission;
    /**
     * 权限说明
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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "qdf_role_permission",//关联表名
            joinColumns = @JoinColumn(name = "permission_id"),//关联本类的ID
            inverseJoinColumns = @JoinColumn(name = "role_id")//关联对方类的ID
    )
    @Transient
    private Set<QdfRole> roles = new HashSet<>();

}
