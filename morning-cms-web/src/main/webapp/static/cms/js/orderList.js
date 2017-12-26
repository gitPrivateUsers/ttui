/**
 * 进行格式转换
 */
function timeFormatter(value) {
	return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
}

function payFormatter(value){
	if(value == 0){
	return '<span class="">线下支付</span>'
	}else if (value == 1){
	 return '<span class="">在线支付</span>'
	}
}

function shipTimeFormatter(value) {
 	if (value == 1) {
 	return '<span class="">不限送货时间</span>'
 	} else if (value == 2) {
 	return '<span class="">工作日送货</span>'
 	}else if (value == 3) {
		return '<span class="">双休日、假日送货</span>'
	}
}

function statusFormatter(value) {
	if (value == 1) {
		return '<span  class="label label-primary">已提交</span>'
	} 
	if (value == 2) {
		return '<span  class="label label-warning">待付款</span>'
	} 
	if (value == 3) {
		return '<span  class="label label-danger">已取消</span>'
	} 
	if (value == 4) {
		return '<span  class="label label-info">已付款</span>'
	} 
	if (value == 5) {
		return '<span  class="label label-default">配送中</span>'
	} 
	if (value == 6) {
		return '<span  class="label label-success">已完成</span>'
	} 
}
function typeFormatter(value) {
	if (value == 1) {
		return '<span class="label label-primary">图片</span>'
	} else if (value == 0) {
		return '<span class="label label-danger">文本</span>'
	}
}

function actionFormatter(value, row, index) {
	 return [ 
			'<a class="edit m-r-sm text-warning" href="javascript:void(0)" title="编辑">',
			'<i class="glyphicon glyphicon-edit"></i>',
			'</a>',
		].join(''); 
}

window.actionEvents = { 
	'click .edit' : function(e, value, row, index) {
		 layer_show(row.name, baselocation + '/system/order/' + row.orderId + '/edit', 900, 650)
 	},
};



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
					message : '广告名称验证失败',
					validators : {
						notEmpty : {
							message : '广告名称不能为空'
						}
					}
				},
				'code' : {
					message : '广告标志验证失败',
					validators : {
						notEmpty : {
							message : '广告标志不能为空'
						}
					}
				},
				'showNumber' : {
					message : '显示数量验证失败',
					validators : {
						notEmpty : {
							message : '广告栏显示数量不能为空'
						},
						regexp: {
							regexp: /^[0-9]*$/,
							message: '广告栏显示数量只能为数字'
						}
					}
				},
				'width' : {
					message : '宽度验证失败',
					validators : {
						notEmpty : {
							message : '宽度不能为空'
						},
						regexp: {
							regexp: /^[0-9]*$/,
							message: '宽度只能为数字'
						}
					}
				},
				'height' : {
					message : '高度验证失败',
					validators : {
						notEmpty : {
							message : '高度不能为空'
						},
						regexp: {
							regexp: /^[0-9]*$/,
							message: '高度只能为数字'
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
							parent.layer.msg("更新订单成功!", {
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
							parent.layer.msg("创建订单成功!", {
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