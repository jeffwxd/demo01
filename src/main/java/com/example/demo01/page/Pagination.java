package com.example.demo01.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Accessors(chain=true)
@ToString
public class Pagination<T> implements Serializable {

    /**
     * 当前页返回数据列表
     */
    private List<T> results;

    private long totalCount;

    public static <T> Pagination<T> build() {
        return new Pagination<T>();
    }

    public static <T> Pagination<T> build(List<T> results) {
        return new Pagination<T>().setResults(results);
    }

    public static <T> Pagination<T> build(List<T> results, long totalCount) {
        return new Pagination<T>().setResults(results).setTotalCount(totalCount);
    }

}
