/**
 * 进行格式转换
 */
function timeFormatter(value) {
	return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
}

function starFormatter(value){
	if(value == 1){
	return '<span style="color: #f7ca77"><i class="glyphicon glyphicon-star"></i></span>'
	}else if (value == 2){
	 return '<span style="color: #f7ca77"><i class="glyphicon glyphicon-star"></i>' +
		     '<i class="glyphicon glyphicon-star"></i></span>'
	} else if (value == 3){
	 return '<span style="color: #f7ca77"><i class="glyphicon glyphicon-star"></i>' +
		    '<i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i></span>'
	} else if (value == 4){
	 return '<span style="color: #f7ca77"><i class="glyphicon glyphicon-star"></i>' +
		     '<i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i>' +
			 '<i class="glyphicon glyphicon-star"></i></span>'
	} else if (value == 5){
	 return '<span style="color: #f7ca77"><i class="glyphicon glyphicon-star"></i>' +
		   '<i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i>' +
		   '<i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i></span>'
	}
}

function statusFormatter(value) {
	if (value == 1) {
		return '<span  class="label label-primary">显示</span>'
	}else if (value == 0) {
		return '<span  class="label label-default">隐藏</span>'
	}
}
function styleFormatter(value) {
	if (value == 1) {
		return '<span  class="label label-success">优质</span>'
	}else if (value == 0) {
		return '<span  class="label label-default">普通</span>'
	}
}



function actionFormatter(value, row, index) {
	 return [ 
            '<a class="edit m-r-sm text-warning" href="javascript:void(0)" title="修改状态">',
            '<i class="glyphicon glyphicon-edit"></i>',
            '</a>',
		    //'<a class="updateShipment m-r-sm text-warning" href="javascript:void(0)" title="修改订单配送信息">',
		    //'<i class="glyphicon glyphicon-map-marker"></i>',
		    //'</a>',
		    '<a class="delComment m-r-sm text-warning" href="javascript:void(0)" title="强制删除">',
		    '<i class="glyphicon glyphicon-remove-sign"></i>',
		    '</a>',
		].join('');
}

window.actionEvents = { 
	'click .edit' : function(e, value, row, index) {
		 layer_show("修改评论状态", baselocation + '/system/order/comment/' + row.commentId + '/edit', 900, 650)
 	},
	//'click .updateShipment' : function(e, value, row, index) {
	//	 layer_show("修改订单配送信息", baselocation + '/system/order/' + row.orderId + '/updateShipment', 900, 650)
 	//},
	'click .delComment' : function(e, value, row, index) {
		//debugger
		 comment_delete(index,row.commentId,row.status);
 	},
};

/**
 * 取消订单
 */
function comment_delete(index,value,stu) {
	if(stu == 0) {
		layer.confirm('确定要强制删除本条评论吗？', {
			btn: ['确定', '取消'] //按钮
		}, function () {
			$.ajax({
				type: 'put',
				dataType: 'json',
				url: baselocation + '/system/order/comment/delete/' + value,
				success: function (result) {
					if (result.code == 1) {
						layer.msg('订单取消成功!', {
							icon: 1,
							time: 1000
						});
						window.location.reload();
					} else {
						layer.alert(result.message, {
							icon: 2
						});
					}
				}
			})
		});
	} else {
		layer.alert('操作不合法，请联系管理员！');
	}
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