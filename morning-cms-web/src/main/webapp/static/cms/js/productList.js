/**
 * 进行格式转换
 */
function timeFormatter(value) {
	return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
}
function statusFormatter(value) {
	if (value == 1) {
		return '<span class="label label-primary">正常</span>'
	} else if (value == 0) {
		return '<span class="label label-danger">冻结</span>'
	}
}
function systemFormatter(value) {
	if (value == 1) {
		return '<span class="label label-danger">是</span>'
	} else if (value == 0) {
		return '<span class="label label-primary">否</span>'
	}
}
