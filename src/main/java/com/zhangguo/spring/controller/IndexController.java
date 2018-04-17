package com.zhangguo.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangguo.spring.command.CreateTableParam;
import com.zhangguo.spring.entities.TableEntity;
import com.zhangguo.spring.manager.SysMysqlCreateTableManager;

import net.sf.json.JSONObject;

@Controller
public class IndexController {
	private static final Logger	log	= LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private SysMysqlCreateTableManager sysMysqlCreateTableManager;
	

	/**
	 * 创建表
	 */
	@RequestMapping(value="/createTable",method=RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String createTable(@RequestBody TableEntity table){
		Map<String,String> json = new HashMap<>();
		if(null!=table && StringUtils.isNotBlank(table.getTableName()) && table.getColumns().size()>0){
			try {
				log.info(table.toString());
				//检查表是否存在，若不存在，则创建，若存在，则给出提示
				if(!sysMysqlCreateTableManager.TableIsExist(table.getTableName())){
					List<CreateTableParam> list  = table.getColumns();
					Map<String,List<Object>> map = new HashMap<>();
					List<Object> columnList = new ArrayList<>();
					columnList.addAll(list);
					map.put(table.getTableName(), columnList);
					sysMysqlCreateTableManager.createTableByMap(map);
					json.put("msg", "创建表成功");
					return JSONObject.fromObject(json).toString();
				}else{
					json.put("msg", "创建表失败，表已存在！");
					return JSONObject.fromObject(json).toString();
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				json.put("msg", "创建表异常："+e.getMessage());
				return JSONObject.fromObject(json).toString();
			}
		}else{
			json.put("msg", "输入的表名或者字段为空 ");
			return JSONObject.fromObject(json).toString();
		}
		
		
	}
	
	/**
	 * 删除表
	 * @param table
	 * @return
	 */
	@RequestMapping(value="/dropTable",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String dropTable(@RequestBody TableEntity table){
		Map<String,String> json = new HashMap<>();
		if(null!=table && StringUtils.isNotBlank(table.getTableName())){
			try {
				if(sysMysqlCreateTableManager.TableIsExist(table.getTableName())){
					String tableName =table.getTableName();
					sysMysqlCreateTableManager.dorpTableByName(tableName);
					json.put("msg", "删除表成功");
					return JSONObject.fromObject(json).toString();
				}else{
					json.put("msg", "表不存在，无法删除");
					return JSONObject.fromObject(json).toString();
				}
				
			} catch (Exception e) {
				log.error(e.getMessage());
				json.put("msg", "删除表异常："+e.getMessage());
				return JSONObject.fromObject(json).toString();
			}
		}else{
			json.put("msg", "输入的表名为空");
			return JSONObject.fromObject(json).toString();
		}
		
		
	}
	
	/**
	 * 增加表字段
	 * @param table
	 * @return
	 */
	@RequestMapping(value="/addTableField",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String addTableField(@RequestBody TableEntity table){
		Map<String,String> json = new HashMap<>();
		if(null!=table && StringUtils.isNotBlank(table.getTableName()) &&table.getColumns().size()>0){
			try {
				List<CreateTableParam> list = table.getColumns();
				//判断要增加的字段表中是否存在，若不存在，才能增加！
				if(!sysMysqlCreateTableManager.ColumnIsExist(table.getTableName(), list)){
					List<Object> columnList = new ArrayList<>();
					columnList.addAll(list);
					Map<String,List<Object>> map = new HashMap<>();
					map.put(table.getTableName(), columnList);
					sysMysqlCreateTableManager.addFieldsByMap(map);
					json.put("msg", "增加字段成功");
					return JSONObject.fromObject(json).toString();
				}else{
					json.put("msg", "增加字段失败，字段已存在");
					return JSONObject.fromObject(json).toString();
				}
				
			} catch (Exception e) {
				log.error(e.getMessage());
				json.put("msg", "增加字段异常："+e.getMessage());
				return JSONObject.fromObject(json).toString();
			}
		}else{
			json.put("msg", "输入的表名或者字段为空");
			return JSONObject.fromObject(json).toString();
		}
		
	}
	
	/**
	 * 删除表字段
	 * @param table
	 * @return
	 */
	@RequestMapping(value="/removeTableField",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String removeTableField(@RequestBody TableEntity table){
		Map<String,String> json = new HashMap<>();
		if(null!=table && StringUtils.isNotBlank(table.getTableName()) &&table.getColumns().size()>0){
			try {
				List<CreateTableParam> list = table.getColumns();
				//判断要删除的字段表中是否存在，只要存在，才能删除！
				if(sysMysqlCreateTableManager.AllColumnIsExist(table.getTableName(), list)){
					List<Object> columnList = new ArrayList<>();
					columnList.addAll(list);
					Map<String,List<Object>> map = new HashMap<>();
					map.put(table.getTableName(), columnList);
					sysMysqlCreateTableManager.removeFieldsByMap(map);
					json.put("msg", "删除表字段成功！");
					return JSONObject.fromObject(json).toString();
				}else{
					json.put("msg", "删除表字段失败，字段不存在");
					return JSONObject.fromObject(json).toString();
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				json.put("msg", "删除表字段异常："+e.getMessage());
				return JSONObject.fromObject(json).toString();
			}
		}else{
			json.put("msg", "输入的表名或者字段为空");
			return JSONObject.fromObject(json).toString();
		}
		
		
	}
	
	/**
	 * 修改表字段
	 * @param table
	 * @return
	 */
	@RequestMapping(value="/modifyTableField",method=RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String modifyTableField(@RequestBody TableEntity table){
		Map<String,String> json = new HashMap<>();
		if(null!=table && StringUtils.isNotBlank(table.getTableName()) &&table.getColumns().size()>0){
			try {
				List<CreateTableParam> list = table.getColumns();
				sysMysqlCreateTableManager.SetNewFieldName(list);
				//判断要修改的字段，表中是否存在，存在才能修改
				if(sysMysqlCreateTableManager.AllColumnIsExist(table.getTableName(), list)){
					List<Object> columnList = new ArrayList<>();
					columnList.addAll(list);
					Map<String,List<Object>> map = new HashMap<>();
					map.put(table.getTableName(), columnList);
					sysMysqlCreateTableManager.modifyFieldsByMap(map);
					json.put("msg", "修改表字段成功！");
					return JSONObject.fromObject(json).toString();
				}else{
					json.put("msg", "修改表字段失败，字段不存在");
					return JSONObject.fromObject(json).toString();
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				json.put("msg", "修改表字段异常："+e.getMessage());
				return JSONObject.fromObject(json).toString();
			}
		}else{
			json.put("msg", "输入的表名或者字段为空");
			return JSONObject.fromObject(json).toString();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
