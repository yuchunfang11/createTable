<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">

input{
	width:100px;
	height:20px;
	margin-butom:2px;
	margin-bottom: 6px;
}
</style>
<script type="text/javascript" src="jquery-1.8.3.min.js"></script>
<!-- <script type="text/javascript" src="jquery.min.js"></script> -->
<script type="text/javascript">
/* 创建表 */
function createTable(){   
	/* 获取表字段 */
    var customerArray = new Array();  
  	customerArray.push({fieldName: "id",fieldType: "int",fieldLength:"5",fileTypeLength:"1",fieldIsKey:"true",fieldIsAutoIncrement:"true"});   
  	customerArray.push({fieldName: "name",fieldType: "varchar",fieldLength:"10",fileTypeLength:"1",fieldIsNull:"true"});   
  	var table = {};  
  	/* 获取表结构，表名 */
  	table.tableName="test_mybatis";
  	table.columns = customerArray;
  	var str = JSON.stringify(table);
      
    $.ajax({  
        type: 'POST',  
        url: 'createTable.do' ,           
        dataType: 'json',  
        contentType:'application/json;charset=UTF-8',  
        data:str,  //提交json字符串数组，如果提交json字符串去掉[]   
        success:function(data){  
            alert(data.msg);  
        },  
        error:function(data){  
        	  alert(data.msg);  
        }  
    });  
}  
/* 增加表字段 */
function addColumn(){   
	/* 获取表字段 */
    var customerArray = new Array();  
    customerArray.push({fieldName: "password",fieldType: "varchar",fieldLength:"10",fileTypeLength:"1",fieldIsNull:"true"});   
    customerArray.push({fieldName: "dept",fieldType: "varchar",fieldLength:"10",fileTypeLength:"1",fieldIsNull:"true"});   
  	var table = {};  
  	/* 获取表结构，表名 */
  	table.tableName="test_mybatis";
  	table.columns = customerArray;
  	var str = JSON.stringify(table);
      
    $.ajax({  
        type: 'POST',  
        url: 'addTableField.do' ,           
        dataType: 'json',  
        contentType:'application/json;charset=UTF-8',  
        data:str,  //提交json字符串数组，如果提交json字符串去掉[]   
        success:function(data){  
        	  alert(data.msg);  
        },  
        error:function(data){  
        	  alert(data.msg);  
        }  
    });  
}  

/* 修改表字段的名称或者属性 */
function modifyColumn(){   
	/* 获取表字段 */
    var customerArray = new Array();  
    customerArray.push({fieldName: "password",fieldType: "varchar",fieldLength:"15",fileTypeLength:"1",fieldIsNull:"false",fieldDefaultValue:"123456",newFieldName:"pwd"});   
    customerArray.push({fieldName: "name",fieldType: "varchar",fieldLength:"15",fileTypeLength:"1",fieldIsNull:"true"});   
  	var table = {};  
  	/* 获取表结构，表名 */
  	table.tableName="test_mybatis";
  	table.columns = customerArray;
  	var str = JSON.stringify(table);
      
    $.ajax({  
        type: 'POST',  
        url: 'modifyTableField.do' ,           
        dataType: 'json',  
        contentType:'application/json;charset=UTF-8',  
        data:str,  //提交json字符串数组，如果提交json字符串去掉[]   
        success:function(data){  
        	  alert(data.msg);  
        },  
        error:function(data){  
        	  alert(data.msg);  
        }  
    });  
}  

/* 删除表字段 */
function dropColumn(){   
	/* 获取表字段 */
    var customerArray = new Array();  
  	customerArray.push({fieldName: "dept",fieldType: "varchar"});   
  	var table = {};  
  	/* 获取表结构，表名 */
  	table.tableName="test_mybatis";
  	table.columns = customerArray;
  	var str = JSON.stringify(table);
      
    $.ajax({  
        type: 'POST',  
        url: 'removeTableField.do' ,           
        dataType: 'json',  
        contentType:'application/json;charset=UTF-8',  
        data:str,  //提交json字符串数组，如果提交json字符串去掉[]   
        success:function(data){  
        	  alert(data.msg);  
        },  
        error:function(data){  
        	  alert(data.msg);  
        }  
    });  
} 
/* 删除表 */
function dropTable(){   
	
  	var table = {};  
  	table.tableName="test_mybatis";
  	var str = JSON.stringify(table);
      
    $.ajax({  
        type: 'POST',  
        url: 'dropTable.do' ,           
        dataType: 'json',  
        contentType:'application/json;charset=UTF-8',  
        data:str,  //提交json字符串数组，如果提交json字符串去掉[]   
        success:function(data){  
        	  alert(data.msg);  
        },  
        error:function(data){  
        	  alert(data.msg);  
        }  
    });  
} 

</script>
</head>
<body style="text-align:center;">
	<div style="height:200px;"></div>
		<h3>基于Spring+SpringMVC+Mybatis+maven+Mysql环境，实现对数据库中表结构的增删改查</h3>
	<div>
		<input type="button" onclick="createTable();" value="创建表"><br>
		<input type="button" onclick="addColumn();" value="增加表字段"><br>
		<input type="button" onclick="modifyColumn();" value="修改表字段"><br>
		<input type="button" onclick="dropColumn();" value="删除表字段"><br>
		<input type="button" onclick="dropTable();" value="删除表">
	</div>
	
</body>
</html>