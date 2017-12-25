/**
 * 进行格式转换
 */
function timeFormatter(value) {
	return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
}
function navFormatter(value) {
	if (value == 1) {
		return '<span class="label label-primary">显示</span>'
	} else if (value == 0) {
		return '<span class="label label-danger">隐藏</span>'
	}
}
function topFormatter(value) {
	if (value == 1) {
		return '<span class="label label-danger">置顶</span>'
	} else if (value == 0) {
		return '<span class="label label-primary">默认</span>'
	}
}
function hotFormatter(value) {
	if (value == 1) {
		return '<span class="label label-danger">热门</span>'
	} else if (value == 0) {
		return '<span class="label label-primary">默认</span>'
	}
}
function shelveFormatter(value) {
	if (value == 1) {
		return '<span class="label label-danger">上架</span>'
	} else if (value == 0) {
		return '<span class="label label-primary">下架</span>'
	}
}

function actionFormatter(value, row, index) {
		return [
			'<a class="edit m-r-sm text-warning" href="javascript:void(0)" title="编辑产品列表">',
			'<i class="glyphicon glyphicon-edit"></i>',
			'</a>',
			'<a class="log m-r-sm text-primary" href="javascript:void(0)" title="商品图片列表">',
			'<i class="glyphicon glyphicon-picture"></i>',
			'</a>',
			'<a class="categoryUpdate m-r-sm text-primary" href="javascript:void(0)" title="更新商品分类">',
			'<i class="glyphicon glyphicon-th-list"></i>',
			'</a>',
			'<a class="detailUpdate m-r-sm text-primary" href="javascript:void(0)" title="商品详情修改">',
			'<i class="glyphicon glyphicon-tree-deciduous"></i>',
			'<a class="parameterView m-r-sm text-primary" href="javascript:void(0)" title="商品参数列表">',
			'<i class="glyphicon glyphicon-object-align-horizontal"></i>',
		].join('');

}

window.actionEvents = {
	'click .edit' : function(e, value, row, index) {
		layer_show("修改商品详情", baselocation + '/product/detail/' + row.productId + '/edit', 900, 650)
	},
	'click .log' : function(e, value, row, index) {
		layer_show("商品图片列表", baselocation + '/product/detail/' + row.productId + '/list', 1300, 720)
	},
	'click .categoryUpdate' : function(e, value, row, index) {
		debugger
		layer_show("更新商品分类", baselocation + '/product/detail/' + row.productId + '/categoryUpdate', 900, 720)
	},
	'click .detailUpdate' : function(e, value, row, index) {
		debugger
		layer_show("商品详情修改", baselocation + '/product/detail/' + row.productId + '/update', 900, 720)

	},
	'click .parameterView' : function(e, value, row, index) {
		debugger
		layer_show("商品参数列表", baselocation + '/product/detail/' + row.productId + '/parameterView', 900, 720)

	}
};

//function detailFormatter(index, row, $detail) {
//	InitSubTable(index, row, $detail);
//}
//
////初始化子表格(无线循环)
//InitSubTable = function(index, row, $detail) {
//	var parentid = row.categoryId;
//	var cur_table = $detail.html('<table></table>').find('table');
//	$(cur_table).bootstrapTable({
//		url : baselocation + '/product/category/gid/' + parentid,
//		method : 'get',
//		sidePagination : 'server',
//		clickToSelect : true,
//		uniqueId : "categoryId",
//		pagination : true,
//		pageSize : 10,
//		pageList : [ 10, 25 ],
//		pageNumber : 1,
//		columns : [ {
//			field : 'categoryId',
//			title : '分类编号',
//			halign : 'center',
//			align : 'center',
//			sortable : 'true'
//		}, {
//			field : 'name',
//			title : '分类名称',
//			halign : 'center',
//			align : 'center',
//			sortable : 'true'
//		}, {
//			field : 'sort',
//			title : '排序',
//			halign : 'center',
//			align : 'center',
//			sortable : 'true'
//		}, {
//			field : 'status',
//			title : '状态',
//			halign : 'center',
//			align : 'center',
//			sortable : 'true',
//			formatter : 'statusFormatter'
//		}, {
//			field : 'showInNav',
//			title : '是否导航',
//			halign : 'center',
//			align : 'center',
//			sortable : 'true',
//			formatter : 'navFormatter'
//		}, {
//			field : 'showInTop',
//			title : '是否置顶',
//			halign : 'center',
//			align : 'center',
//			sortable : 'true',
//			formatter : 'topFormatter'
//		}, {
//			field : 'showInHot',
//			title : '是否热门',
//			halign : 'center',
//			align : 'center',
//			sortable : 'true',
//			formatter : 'hotFormatter'
//		}, {
//			title : '操作',
//			halign : 'center',
//			align : 'center',
//			sortable : 'true',
//			formatter : 'actionFormatter',
//			events : 'actionEvents'
//		}, ],
//		//无线循环取子表，直到子表里面没有记录
//		onExpandRow : function(index, row, $Subdetail) {
//			oInit.InitSubTable(index, row, $Subdetail);
//		}
//	});
//};

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