var $table = $('#table');
function select(){
	var selects = document.getElementById("dataBase");
    var indexs = selects.selectedIndex;
   	console.log(selects.options[indexs].text)
   	$table.bootstrapTable({
		url : '/MyBatis-Generator-Plus/tables?dataBaseName='+selects.options[indexs].text,
		cache: false,
		pagination: true,                   // 是否显示分页（*）
		pageNumber: 1,						// 初始化加载第一页，默认第一页
        pageSize: 1,                     	// 每页的记录行数（*）
        sidePagination: "client",           // 分页方式：client客户端分页，server服务端分页（*）
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
				return "<a class=\"text-info\" href=\"javascript:void(0)\" onclick=\"toTableInfo('"+row.TABLE+"')\">详情</a> "
			}
		} ],
	})
}

function toTableInfo(tableName){

}
