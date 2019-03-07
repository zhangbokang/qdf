package com.mycharx.qdf.common;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * The type Qdf id generator.
 *
 * @author 张卜亢
 * @date 2019.03.07 13:16:22
 */
public class QdfIdGenerator implements IdentifierGenerator {
    /**
     * 初始化一个雪花算法id生成器
     */
    private final SnowFlake snowFlake = new SnowFlake(1, 1);
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return snowFlake.nextId();
    }
}
