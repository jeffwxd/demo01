package com.example.demo01.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 产品层和业务层字段翻译工具类
 *
 * @author Zhang Yanhui
 * @since 2018/11/26 17:02
 */

@Slf4j
public final class TranslateUtil {

    private TranslateUtil() {
    }

    /**
     * 业务值转产品值，不存在时返回null
     *
     * @param businessValue 业务值
     * @param enumType      枚举类型
     * @param <E>           枚举类型
     * @return 产品值
     */
    public static <E extends Enum<E> & Translatable> String b2p(String businessValue, Class<E> enumType) {
        for (E anEnum : enumType.getEnumConstants()) {
            if (anEnum.getBusinessValue().equals(businessValue)) {
                return anEnum.getProductValue();
            }
        }
        return null;
    }

    /**
     *  业务值转Enum，不存在时返回null
     *
     * @param businessValue 业务值
     * @param enumType     枚举类型
     * @param <E>          枚举类型
     * @return 业务值
     */
    public static <E extends Enum<E> & Translatable> E b2Enum(String businessValue, Class<E> enumType) {
        for (E anEnum : enumType.getEnumConstants()) {
            if (anEnum.getBusinessValue().equals(businessValue)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 产品值转业务值，不存在时返回null
     *
     * @param productValue 产品值
     * @param enumType     枚举类型
     * @param <E>          枚举类型
     * @return 业务值
     */
    public static <E extends Enum<E> & Translatable> String p2b(String productValue, Class<E> enumType) {
        for (E anEnum : enumType.getEnumConstants()) {
            if (anEnum.getProductValue().equals(productValue)) {
                return anEnum.getBusinessValue();
            }
        }
        return null;
    }

    /**
     * 可翻译接口，枚举类实现后可用工具类的翻译功能双向翻译
     */
    public interface Translatable {

        /**
         * 获取字段业务层的值
         *
         * @return 字段业务层的值
         */
        String getBusinessValue();

        /**
         * 获取字段产品层的值
         *
         * @return 字段产品层的值
         */
        String getProductValue();

    }
}