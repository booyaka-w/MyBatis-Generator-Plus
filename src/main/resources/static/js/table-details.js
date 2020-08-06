var $table = $('#tableDetails');
$(function select(){
   	console.log()
   	$table.bootstrapTable({
		url : '/MyBatis-Generator-Plus/tableDetailsData?tableName='+$("#table").val(),
		cache: false,
		pagination: true,                   
		pageNumber: 1,						
        pageSize: 5,                     	
        sidePagination: "client",           
        paginationPreText:'上一页',
        paginationNextText:'下一页',
        columns : [ {
			field : 'columnName',
			title : 'Column Name',
			width : 200
		},{
			field : 'columnTypeName',
			title : 'Column Type Name',
			width : 150
		},{
			field : 'columnClassName',
			title : 'Column Class Name',
			width : 100
		} ],
	})
})
