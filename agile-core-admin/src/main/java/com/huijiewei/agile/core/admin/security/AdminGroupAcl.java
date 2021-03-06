package com.huijiewei.agile.core.admin.security;

import java.util.ArrayList;
import java.util.List;

public class AdminGroupAcl {
    public static List<AdminGroupAclItem> getAll() {
        List<AdminGroupAclItem> all = new ArrayList<>();

        all.add(new AdminGroupAclItem().name("管理首页")
                .addChild(new AdminGroupAclItem().name("测试短信发送").actionId("site/sms-send"))
                .addChild(new AdminGroupAclItem().name("更新系统缓存").actionId("site/clean-cache"))
        );

        all.add(new AdminGroupAclItem().name("用户管理")
                .addChild(new AdminGroupAclItem().name("用户列表").actionId("user/index"))
                .addChild(new AdminGroupAclItem().name("用户导出").actionId("user/export"))
                .addChild(new AdminGroupAclItem().name("用户新建").actionId("user/create"))
                .addChild(new AdminGroupAclItem().name("用户查看").actionId("user/view"))
                .addChild(new AdminGroupAclItem().name("用户编辑").actionId("user/edit").addCombine("user/view"))
                .addChild(new AdminGroupAclItem().name("用户删除").actionId("user/delete"))
                .addChild(new AdminGroupAclItem().name("用户导入").actionId("user/import"))
        );

        all.add(new AdminGroupAclItem().name("商品管理")
                .addChild(new AdminGroupAclItem().name("商品管理")
                        .addChild(new AdminGroupAclItem().name("商品列表").actionId("shop-product/index"))
                        .addChild(new AdminGroupAclItem().name("商品新建").actionId("shop-product/create"))
                        .addChild(new AdminGroupAclItem().name("商品查看").actionId("shop-product/view"))
                        .addChild(new AdminGroupAclItem().name("商品编辑").actionId("shop-product/edit").addCombine("shop-product/view"))
                        .addChild(new AdminGroupAclItem().name("商品删除").actionId("shop-product/delete"))
                )
                .addChild(new AdminGroupAclItem().name("商品分类管理")
                        .addChild(new AdminGroupAclItem().name("商品分类").actionId("shop-category/index"))
                        .addChild(new AdminGroupAclItem().name("分类新建").actionId("shop-category/create"))
                        .addChild(new AdminGroupAclItem().name("分类查看").actionId("shop-category/view"))
                        .addChild(new AdminGroupAclItem().name("分类编辑").actionId("shop-category/edit").addCombine("shop-category/view"))
                        .addChild(new AdminGroupAclItem().name("分类删除").actionId("shop-category/delete").addCombine("shop-category/view"))
                )
                .addChild(new AdminGroupAclItem().name("商品品牌管理")
                        .addChild(new AdminGroupAclItem().name("商品品牌").actionId("shop-brand/index"))
                        .addChild(new AdminGroupAclItem().name("品牌新建").actionId("shop-brand/create"))
                        .addChild(new AdminGroupAclItem().name("品牌查看").actionId("shop-brand/view"))
                        .addChild(new AdminGroupAclItem().name("品牌编辑").actionId("shop-brand/edit").addCombine("shop-brand/view"))
                        .addChild(new AdminGroupAclItem().name("品牌删除").actionId("shop-brand/delete").addCombine("shop-brand/view"))
                )
        );

        all.add(new AdminGroupAclItem().name("系统管理")
                .addChild(new AdminGroupAclItem().name("管理员管理")
                        .addChild(new AdminGroupAclItem().name("管理员列表").actionId("admin/index"))
                        .addChild(new AdminGroupAclItem().name("管理员新建").actionId("admin/create"))
                        .addChild(new AdminGroupAclItem().name("管理员查看").actionId("admin/view"))
                        .addChild(new AdminGroupAclItem().name("管理员编辑").actionId("admin/edit").addCombine("admin/view"))
                        .addChild(new AdminGroupAclItem().name("管理员删除").actionId("admin/delete"))
                )
                .addChild(new AdminGroupAclItem().name("管理组管理")
                        .addChild(new AdminGroupAclItem().name("管理组列表").actionId("admin-group/index"))
                        .addChild(new AdminGroupAclItem().name("管理组新建").actionId("admin-group/create"))
                        .addChild(new AdminGroupAclItem().name("管理组查看").actionId("admin-group/view"))
                        .addChild(new AdminGroupAclItem().name("管理组编辑").actionId("admin-group/edit").addCombine("admin-group/view"))
                        .addChild(new AdminGroupAclItem().name("管理组删除").actionId("admin-group/delete"))
                )
                .addChild(new AdminGroupAclItem().name("操作日志")
                        .addChild(new AdminGroupAclItem().name("操作日志查看").actionId("admin-log/index"))
                )
        );

        return all;
    }
}
