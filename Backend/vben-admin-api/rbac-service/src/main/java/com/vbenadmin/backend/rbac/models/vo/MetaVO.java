package com.vbenadmin.backend.rbac.models.vo;

public record MetaVO(

        /** 菜单标题（i1 key 或直接文本） */
        String title,

        /** 菜单 / 页面图标 */
        String icon,

        /** 排序权重（越小越靠前） */
        Integer order,

        /** 是否固定在标签页 */
        Boolean affixTab,

        /** 徽标内容 */
        String badge,

        /** 徽标类型：normal | dot */
        String badgeType,

        /** 徽标颜色 / 变体 */
        String badgeVariants,

        /** iframe 内嵌地址 */
        String iframeSrc,

        /** 外链地址 */
        String link

) {
    // 空对象常量
    public static final MetaVO EMPTY = new MetaVO(null, null, 0, false, null, null, null, null, null);

    public MetaVO {
        if (order == null)
            order = 0;
        if (affixTab == null)
            affixTab = false;
    }
}
