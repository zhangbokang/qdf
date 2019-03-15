package com.mycharx.qdf.utils;

import com.mycharx.qdf.exception.QdfCustomException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Str arr util.
 *
 * @author 张卜亢
 * @date 2019.03.15 16:34:13
 */
public class StrUtil {
    /**
     * Str to long set set.
     * 逗号分割的字符串转换为Long类型的Set
     *
     * @param string the string
     * @return the set
     * @author 张卜亢
     * @date 2019.03.15 16:37:49
     */
    public static Set<Long> strToLongSet(String string) {
        if (StringUtils.isEmpty(string)) {
            throw new QdfCustomException("字符串转换异常");
        }
        String[] idsArr = string.split(",");
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < idsArr.length; i++) {
            set.add(Long.valueOf(idsArr[i]));
        }
        return set;
    }

    /**
     * Sets to long list.
     * 逗号分割的字符串转换为Long类型的List
     *
     * @param string the string
     * @return the to long list
     * @author 张卜亢
     * @date 2019.03.15 16:37:49
     */
    public static List<Long> strToLongList(String string) {
        if (StringUtils.isEmpty(string)) {
            throw new QdfCustomException("字符串转换异常");
        }
        String[] idsArr = string.split(",");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < idsArr.length; i++) {
            list.add(Long.valueOf(idsArr[i]));
        }
        return list;
    }
}
