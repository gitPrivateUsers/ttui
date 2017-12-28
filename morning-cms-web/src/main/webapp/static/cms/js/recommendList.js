/**
 * 进行格式转换
 */
function timeFormatter(value) {
    return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
}



function statusFormatter(value){
	if(value == 1){
		return '<span  class="label label-success">显示</span>'
	} 
	if(value == 0){
		return '<span  class="label label-default">隐藏</span>'
    }
	
}
function actionFormatter(value, row, index) {
    return [
        '<a class="edit m-r-sm text-warning" href="javascript:void(0)" title="编辑">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>',
        '<a class="remove m-r-sm text-danger" href="javascript:void(0)" title="删除">',
        '<i class="glyphicon glyphicon-remove"></i>',
        '</a>',
    ].join('');

}

window.actionEvents = {
    'click .edit' : function(e, value, row, index) {
        layer_show("修改推荐位信息", baselocation + '/product/detail/' + row.recommendProductId + '/productRecommendEdit', 900, 650)
    },
    'click .remove' :function(e,value,row,index){
        var url =  baselocation + '/product/detail/recommendProductDelete/';
        admin_delete(index, row.recommendProductId,url);
    }
};

/**
 * 删除
 */
function admin_delete(index, value, url) {
    layer.confirm('确认要删除该条商品的图片吗？', {
        btn : [ '确定', '取消' ] //按钮
    }, function() {
        debugger
        $.ajax({
            type : 'delete',
            dataType : 'json',
            url : url + value,
            success : function(result) {
                if (result.code == 1) {
                    $('#table').bootstrapTable('hideRow', {
                        index : index
                    });
                    layer.msg('该条商品的图片删除成功!', {
                        icon : 1,
                        time : 1000
                    });
                } else {
                    layer.alert(result.message, {
                        icon : 2
                    });
                }
            }
        })
    });
}

/**
 * 多选框插件
 */
$(document).ready(function() {
    $('input').iCheck({
        checkboxClass : 'icheckbox_flat-green',
        radioClass : 'iradio_flat-green'
    });
});

/**
 * 系统提示
 */
$(function() {
    $('.status-tip').on("click", function() {
        layer.tips('"显示" 代表此数据可用<br>"隐藏" 代表此数据不可用', '.status-tip');
    })
})


/**
 * 表单验证
 */
$(function() {
    $('#form').bootstrapValidator({
            container : 'tooltip',
            message : 'This value is not valid',
            feedbackIcons : {
                valid : 'glyphicon glyphicon-ok',
                invalid : 'glyphicon glyphicon-remove',
                validating : 'glyphicon glyphicon-refresh'
            },
            fields : {
                'name' : {
                    message : '用户名验证失败',
                    validators : {
                        notEmpty : {
                            message : '用户名不能为空'
                        }
                    }
                },

            }
        })
        .on('success.form.bv', function(e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            var method = $('#form').attr('data-method');
            // Use Ajax to submit form data
            if (method == 'put') {
                $.ajax({
                    data : $form.serialize(),
                    dataType : 'json',
                    type : 'put',
                    url : $form.attr('action'),
                    success : function(result) {
                        if (result.code == 1) {
                            parent.layer.msg("更新推荐成功!", {
                                shade : 0.3,
                                time : 1500
                            }, function() {
                                window.parent.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon : 2,
                                time : 1000
                            });
                        }
                    }
                })
            } else if (method == 'post') {
                $.ajax({
                    data : $form.serialize(),
                    dataType : 'json',
                    type : 'post',
                    url : $form.attr('action'),
                    success : function(result) {
                        if (result.code == 1) {
                            parent.layer.msg("创建推荐成功!", {
                                shade : 0.3,
                                time : 1500
                            }, function() {
                                window.parent.location.reload(); // 刷新父页面
                            });
                        } else {
                            layer.msg(result.message, {
                                icon : 2,
                                time : 1000
                            });
                        }
                    }
                })
            }
        });
})