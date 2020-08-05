var $table = $('#table');
function select(){
	var selects = document.getElementById("dataBase");
    var indexs = selects.selectedIndex;
   	console.log(selects.options[indexs].text)
   	$table.bootstrapTable({
		url : '/MyBatis-Generator-Plus/tables?dataBaseName='+selects.options[indexs].text,
		showColumns : false,
		showButtonIcons : false,
		showExport: false,
		showButtonText : false,
		showRefresh : false,
		showToggle: false,
		cache: false,
		detailView : false,
		detailViewIcon: false,
		detailViewByClick: false,
		pagination: true,                   // 是否显示分页（*）
        paginationPreText:'上一页',
        paginationNextText:'下一页',
        columns : [ {
			field : 'TABLE',
			title : 'TableName',
			width : 200
		},{
			field : 'REMARK',
			title : 'Remark',
			width : 150
		},{
			title : 'Operate',
			align : 'center',
			width : 100,
			formatter : function(value, row, index) {
				return "<a class=\"text-info\" href=\"javascript:void(0)\" onclick=\"editModal('"+row.TABLE+"')\">详情</a> "
			}
		} ],
	})
    
}
