/**
 * 进行格式转换
 */
function timeFormatter(value) {
	return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
}

function actionFormatter(value, row, index) {
		return [
			'<a class="edit m-r-sm text-warning" href="javascript:void(0)" title="编辑产品列表">',
			'<i class="glyphicon glyphicon-edit"></i>',
			'</a>',
			'<a class="log m-r-sm text-primary" href="javascript:void(0)" title="商品图片列表">',
			'<i class="glyphicon glyphicon-picture"></i>',
			'</a>',
			'<a class="log m-r-sm text-primary" href="javascript:void(0)" title="查看商品分类">',
			'<i class="glyphicon glyphicon-list-alt"></i>',
			'</a>',
			'<a class="detailUpdate m-r-sm text-primary" href="javascript:void(0)" title="商品详情修改">',
			'<i class="glyphicon glyphicon-tree-deciduous"></i>',
			'</a>'
		].join('');

}

window.actionEvents = {
	'click .edit' : function(e, value, row, index) {
		layer_show("修改商品详情", baselocation + '/product/detail/' + row.productId + '/edit', 900, 650)
	},
	'click .log' : function(e, value, row, index) {
		debugger
		layer_show("商品图片列表", baselocation + '/product/detail/' + row.productId + '/list', 1300, 720)
	},
	'click .update' : function(e, value, row, index) {
		debugger
		layer_show("商品分类列表", baselocation + '/product/detail/' + row.productId + '/categoryList', 900, 720)
	},
	'click .detailUpdate' : function(e, value, row, index) {
		debugger
		layer_show("商品详情修改", baselocation + '/product/detail/' + row.productId + '/update', 900, 720)


	}
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
					message : '商品名称验证失败',
					validators : {
						notEmpty : {
							message : '商品名称不能为空'
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
							parent.layer.msg("更新商品详情成功!", {
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
							parent.layer.msg("创建商品信息成功!", {
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