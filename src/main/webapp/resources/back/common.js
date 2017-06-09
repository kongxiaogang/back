var back = {
    index : {
        initHomePage : function() {
            $(".page-content").load(sys.rootPath + "/welcome.jsp");
            $(".breadcrumb").html('<li><i class="ace-icon fa fa-home home-icon"></i><a href="javascript:webside.index.initHomePage();">首页</a></li><li class="active">控制台</li>');
        },
        menu : {
            initMenuEvent : function() {
                $("[nav-menu]").each(function() {
                    $(this).bind("click", function() {
                        var nav = $(this).attr("nav-menu");
                        var sn = nav.split(",");
                        //清除用户信息菜单样式
                        $(".sidebar-menu").find('li').each(function() {
                            $(this).removeClass('active');
                        });
                        //处理监控-新窗口打开
                        if (sn[sn.length - 1] == '/sirona' || sn[sn.length - 1] == '/druid') {
                            window.open(sys.rootPath + sn[sn.length - 1]);
                        } else {
                            var breadcrumb = '<li><a href="#"><i class="fa fa-dashboard"></i> 首页</a></li>';
                            for (var i = 0; i < sn.length - 1; i++) {
                                breadcrumb += '<li class="active">' + sn[i] + '</li>';
                            }
                            //设置面包屑内容
                            $(".breadcrumb").html(breadcrumb);
                            //加载页面
                            $(".content").load(sys.rootPath + sn[sn.length - 1]);
                        }
                        var level = $(this).parent("li").attr("level");
                        if (level == 'level1' || level == 'level2') {
                            $(this).parent().addClass("active").parent().parent("li").addClass('active');
                        } else if (level == 'level3') {
                            //处理目录类型的点击事件-三级菜单
                            $(this).parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            //处理菜单类型的点击事件
                            $(this).parent().parent().parent().parent().parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            $("ul.nav-list").find("li.active").removeClass("active").removeClass('open');
                            $(this).parent().addClass("active").parent().parent().parent().parent("li").addClass('active open');
                        } else {
                            //处理目录类型的点击事件-四级菜单
                            $(this).parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            //处理菜单类型的点击事件
                            $(this).parent().parent().parent().parent().parent().parent().parent("li").siblings().find("ul.nav-show").removeClass('nav-show').addClass('nav-hide').attr('style', 'display:none');
                            $("ul.nav-list").find("li.active").removeClass("active").removeClass('open');
                            $(this).parent().addClass("active").parent().parent().parent().parent().parent().parent("li").addClass('active open');
                        }
                    });
                });
            },
        },
    },
    common : {
        /**
         *加载非菜单展示页面
         * @param nav 待加载的资源URL
         */
        loadPage : function(nav) {
            //加载页面
            $(".content").load(sys.rootPath + nav);
        },
        /**
         *加载非菜单展示页面
         * @param nav 待加载的资源URL
         */
        loadPageByParams : function(nav,data) {
            //加载页面
            $(".content").load(sys.rootPath + nav,data);
        },
        /**
         * 新增
         * @param {Object} nav  提交url
         */
        addModel : function(nav) {
            //加载新增页面
            back.common.loadPage(nav);
        },
        /**
         * 编辑
         * @param {Object} nav  提交url
         */
        editModel : function(nav) {
            //当前页码
            var nowPage = grid.pager.nowPage;
            //获取每页显示的记录数(即: select框中的10,20,30)
            var pageSize = grid.pager.pageSize;
            //获取排序字段
            var columnId = grid.sortParameter.columnId;
            //获取排序方式 [0-不排序，1-正序，2-倒序]
            var sortType = grid.sortParameter.sortType;
            //获取选择的行
            var rows = grid.getCheckedRecords();
            if (rows.length == 1) {
                webside.common.loadPage(nav + '?id=' + rows[0].id + "&page=" + nowPage + "&rows=" + pageSize + "&sidx=" + columnId + "&sord=" + sortType);
            } else {
                layer.msg("你没有选择行或选择了多行数据", {
                    icon : 0
                });
            }
        },
        /**
         * 删除
         * @param {Object} nav  提交url
         * @param callback 主函数执行完毕后调用的回调函数名称
         */
        delModel : function(nav, callback) {
            var rows = grid.getCheckedRecords();
            if (rows.length == 1) {
                if (nav == '/user/deleteBatch.html') {
                    if (rows[0].role.name == '超级管理员') {
                        layer.msg('该用户为超级管理员,不能删除!', {
                            icon : 0
                        });
                        return false;
                    }
                }
                if (nav == '/role/deleteBatch.html') {
                    if (rows[0].name == '超级管理员') {
                        layer.msg('该角色为基础角色,不能删除!', {
                            icon : 0
                        });
                        return false;
                    }
                }
                layer.confirm('确认删除吗？', {
                    icon : 3,
                    title : '删除提示'
                }, function(index, layero) {
                    var delete_ids = [];
                    /*
                     $.each(rows, function(index, value) {
                     ids.push(this.id);
                     });
                     */
                    delete_ids.push(rows[0].id);
                    $.ajax({
                        type : "POST",
                        url : sys.rootPath + nav,
                        data : {
                            "ids" : delete_ids.join(',')
                        },
                        dataType : "json",
                        success : function(resultdata) {
                            if (resultdata.success) {
                                layer.msg(resultdata.message, {
                                    icon : 1
                                });
                                if (callback) {
                                    callback();
                                }
                            } else {
                                layer.msg(resultdata.message, {
                                    icon : 5
                                });
                            }
                        },
                        error : function(errorMsg) {
                            layer.msg('服务器未响应,请稍后再试', {
                                icon : 3
                            });
                        }
                    });
                    layer.close(index);
                });
            } else {
                layer.msg("你没有选择行或选择了多行数据", {
                    icon : 0
                });
            }
        },
        /**
         * 提交表单
         * 适用场景：form表单的提交，主要用在用户、角色、资源等表单的添加、修改等
         * @param {Object} commitUrl 表单提交地址
         * @param {Object} listUrl 表单提交成功后转向的列表页地址
         */
        commit : function(formId, commitUrl, jumpUrl) {
            //组装表单数据
            var index;
            var data = $("#" + formId).serialize();
            if (undefined != $("#pageNum").val()) {
                jumpUrl = jumpUrl + '?page=' + $("#pageNum").val() + '&rows=' + $("#pageSize").val() + '&sidx=' + $("#orderByColumn").val() + '&sord=' + $("#orderByType").val();
            }
            $.ajax({
                type : "POST",
                url : sys.rootPath + commitUrl,
                data : data,
                dataType : "json",
                /*beforeSend : function() {
                    index = layer.load();
                },*/
                success : function(resultdata) {
                    if(resultdata.code==0) {
                    	bootbox.alert(resultdata.msg);
                    	back.common.loadPage(jumpUrl);
                    } else {
                    	bootbox.alert(resultdata.msg);
                    }
                },
                error : function(data, errorMsg) {
                	bootbox.alert(resultdata.msg);
                }
            });
        },
        search:function(formId, url){
        	var data = $("#" + formId).serialize();
        	back.common.loadPageByParams(url,data);
        },
    },
};