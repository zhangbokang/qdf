package com.mycharx.qdf.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The type Qdf user.
 *
 * @author 张卜亢
 * @date 2019.03.07 13:07:39
 */
@Entity
public class QdfUser implements Serializable {
    @Id
    private Long id;
    private String username;
    private String password;
}
