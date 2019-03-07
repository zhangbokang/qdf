package com.mycharx.qdf.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The type Qdf user.
 *
 * @author 张卜亢
 * @date 2019.03.07 13:07:39
 */
@Entity
@Data
public class QdfUser implements Serializable {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.mycharx.qdf.common.QdfIdGenerator")
    private Long id;
    @Column(length = 20)
    private String username;
    @Column(length = 20)
    private String password;
}
