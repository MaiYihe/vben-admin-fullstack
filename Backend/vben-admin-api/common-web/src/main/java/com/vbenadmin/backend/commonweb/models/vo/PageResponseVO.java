package com.vbenadmin.backend.commonweb.models.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponseVO<T> {
    private List<T> items; // 当前页的数据
    private long total; // 总记录数
}
