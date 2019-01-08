/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var i = (layui.$, layui.admin), t = layui.view, l = layui.table, r = layui.form, layer = layui.layer;
    l.render({
        elem: "#LAY-user-manage",
        url: "/static/views/useradmin/webuser.js",
        cols: [[{type: "checkbox", fixed: "left"}, {field: "id", width: 100, title: "ID", sort: !0}, {
            field: "username",
            title: "用户名",
            minWidth: 100
        }, {field: "avatar", title: "头像", width: 100, templet: "#imgTpl"}, {
            field: "phone",
            title: "手机"
        }, {field: "email", title: "邮箱"}, {field: "sex", width: 80, title: "性别"}, {
            field: "ip",
            title: "IP"
        }, {field: "jointime", title: "加入时间", sort: !0}, {
            title: "操作",
            width: 150,
            align: "center",
            fixed: "right",
            toolbar: "#table-useradmin-webuser"
        }]],
        page: !0,
        limit: 30,
        height: "full-320",
        text: "对不起，加载出现异常！"
    }), l.on("tool(LAY-user-manage)", function (e) {
        var l = e.data;
        "del" === e.event ? layer.prompt({formType: 1, title: "敏感操作，请验证口令"}, function (i, t) {
            layer.close(t), layer.confirm("真的删除行么", function (i) {
                e.del(), layer.close(i)
            })
        }) : "edit" === e.event && i.popup({
            title: "编辑用户",
            area: ["500px", "450px"],
            id: "LAY-popup-user-add",
            success: function (e, i) {
                t(this.id).render("user/user/userform", l).done(function () {
                    r.render(null, "layuiadmin-form-useradmin"), r.on("submit(LAY-user-front-submit)", function (e) {
                        e.field;
                        layui.table.reload("LAY-user-manage"), layer.close(i)
                    })
                })
            }
        })
    }),
        // 获取所有用户信息，关键在于此处判断是否为具有， 此处会涉及到初始化按钮
        l.render({
            elem: "#LAY-user-back-manage",
            url: "/admin/alluser",
            cols: [[{type: "checkbox", fixed: "left"}, {
                field: "username",
                title: "登录名"
            }, {field: "nickname", title: "昵称"}, {field: "email", title: "邮箱"}, { field: "roleName",
                title: "角色"
            }, {field: "intime", title: "加入时间", sort: !0}, {
                field: "check",
                title: "审核状态",
                templet: "#buttonTpl",
                minWidth: 80,
                align: "center"
            }, {title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#table-useradmin-admin"}]],
            text: "对不起，加载出现异常！"
        }), l.on("tool(LAY-user-back-manage)", function (e) {
        var l = e.data;
        "del" === e.event ? layer.prompt({formType: 1, title: "敏感操作，请验证口令"}, function (i, t) {
            layer.close(t), layer.confirm("确定删除此管理员？", function (i) {
                console.log(e), e.del(), layer.close(i)
            })
        }) : "edit" === e.event && i.popup({
            title: "编辑管理员",
            area: ["420px", "450px"],
            id: "LAY-popup-user-add",
            success: function (e, i) {
                t(this.id).render("user/administrators/adminform", l).done(function () {
                    console.log("render finished");
                    // 更新添加用户表单数据
                    r.render(null, "layuiadmin-form-admin");
                    r.on("submit(LAY-user-back-submit)", function (data) {
                        layui.table.reload("LAY-user-back-manage");
                        layer.close(i)
                    })
                })
            }
        })
    }), l.render({
        elem: "#LAY-user-back-role",
        url: "/admin/allroles",
        cols: [[{type: "checkbox", fixed: "left"},
            {field: "name", title: "角色名"},
            {field: "remark", title: "备注"},
            {
                title: "操作",
                width: 150,
                align: "center",
                fixed: "right",
                toolbar: "#table-useradmin-admin"
            }]],
        text: "对不起，加载出现异常！",
        done: function () {
            $(".layuiadmin-btn-role-authenticate").on("click", function () {
                console.info("exe");
                // 如何跳出授权页面呢？
                $("#roles").hide();
                $("#authentication").show();
                // 传入角色id，获取角色已经拥有的权限
                t("authentication").render("admin/authentication", {});
            })
        }
    }), l.on("tool(LAY-user-back-role)", function (e) {
        var l = e.data;
        "del" === e.event ? layer.confirm("确定删除此角色？", function (i) {
            e.del(), layer.close(i)
        }) : "edit" === e.event && i.popup({
            title: "添加新角色",
            area: ["500px", "480px"],
            id: "LAY-popup-user-add",
            success: function (e, i) {
                t(this.id).render("user/administrators/roleform", l).done(function () {
                    r.render(null, "layuiadmin-form-role"), r.on("submit(LAY-user-role-submit)", function (e) {
                        e.field;
                        layui.table.reload("LAY-user-back-role"), layer.close(i)
                    })
                })
            }
        })
    });
    e("useradmin", {})
});