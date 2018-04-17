package com.zhangguo.spring.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhangguo.spring.command.CreateTableParam;
import com.zhangguo.spring.command.SysMysqlColumns;
import com.zhangguo.spring.controller.IndexController;
import com.zhangguo.spring.mapping.BookTypeDAO;

@Component
public class SysMysqlCreateTableManager {
	private static final Logger	log	= LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private BookTypeDAO bookTypeDAO;
	
	/**
	 * 根据map结构创建表
	 * 
	 * @param newTableMap
	 */
	public void createTableByMap(Map<String, List<Object>> newTableMap){
		// 做创建表操作
		if (newTableMap.size() > 0) {
			for (Entry<String, List<Object>> entry : newTableMap.entrySet()){
				Map<String, List<Object>> map = new HashMap<String, List<Object>>();
				map.put(entry.getKey(), entry.getValue());
				log.info("开始创建表：" + entry.getKey());
				bookTypeDAO.createTable(map);
				log.info("完成创建表：" + entry.getKey());
			}
		}
	}
	
	/**
	 * 查看表是否存在
	 * 
	 * @param newTableMap
	 */
	public boolean TableIsExist(String tableName){
		boolean flag =false;
		if(StringUtils.isNotBlank(tableName)){
			int v = bookTypeDAO.findTableCountByTableName(tableName);
			if(1==v){
				flag =  true;
			}
		} 
		return flag;
		
	}
	
	/**
	 * 查看表中字段是否存在(增加字段时判断，只要有一个字段存在，即为true)
	 * 
	 * @param newTableMap
	 */
	public boolean ColumnIsExist(String tableName,List<CreateTableParam> list){
		boolean flag = false;
		if(StringUtils.isNotBlank(tableName) && list.size()>0){
			List<SysMysqlColumns> oldList = bookTypeDAO.findTableEnsembleByTableName(tableName);
			for(int i=0;i<list.size();i++){
				for(int j=0;j<oldList.size();j++){
					if(list.get(i).getFieldName().equals(oldList.get(j).getColumn_name())){
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 查看表中字段是否存在(删除字段时判断，所有字段都存在时，才为true)
	 * 
	 * @param newTableMap
	 */
	public boolean AllColumnIsExist(String tableName,List<CreateTableParam> list){
		boolean flag = false;
		if(StringUtils.isNotBlank(tableName) && list.size()>0){
			List<SysMysqlColumns> oldList = bookTypeDAO.findTableEnsembleByTableName(tableName);
			for(int i=0;i<list.size();i++){
				boolean v = false;
				for(int j=0;j<oldList.size();j++){
					if(list.get(i).getFieldName().equals(oldList.get(j).getColumn_name())){
						v = true;
					}
				}
				flag = v;
				if(!flag){
					break;
				}
			}
		}
		return flag;
	}
	
	public void dorpTableByName(String tableName){
		log.info("开始删除表："+tableName);
		bookTypeDAO.dorpTableByName(tableName);
		log.info("完成删除表："+tableName);
	}
	
	/**
	 * 根据map结构对表中添加新的字段
	 * 
	 * @param addTableMap
	 */
	public void addFieldsByMap(Map<String, List<Object>> addTableMap){
		// 做增加字段操作
		if (addTableMap.size() > 0) {
			for (Entry<String, List<Object>> entry : addTableMap.entrySet()){
				for (Object obj : entry.getValue()){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(entry.getKey(), obj);
					CreateTableParam fieldProperties = (CreateTableParam) obj;
					log.info("开始为表" + entry.getKey() + "增加字段" + fieldProperties.getFieldName());
					bookTypeDAO.addTableField(map);
					log.info("完成为表" + entry.getKey() + "增加字段" + fieldProperties.getFieldName());
				}
			}
		}
	}
	
	/**
	 * 根据map结构删除表中的字段
	 * 
	 * @param removeTableMap
	 */
	public void removeFieldsByMap(Map<String, List<Object>> removeTableMap){
		// 做删除字段操作
		if (removeTableMap.size() > 0) {
			for (Entry<String, List<Object>> entry : removeTableMap.entrySet()){
				for (Object obj : entry.getValue()){
					Map<String, Object> map = new HashMap<String, Object>();
					CreateTableParam fieldProperties = (CreateTableParam) obj;
					String fieldName = (String) fieldProperties.getFieldName();
					map.put(entry.getKey(),fieldName);
					log.info("开始删除表" + entry.getKey() + "中的字段" + fieldName);
					bookTypeDAO.removeTableField(map);
					log.info("完成删除表" + entry.getKey() + "中的字段" + fieldName);
				}
			}
		}
	}
	
	
	/**
	 * 根据map结构修改表中的字段类型等
	 * 
	 * @param modifyTableMap
	 */
	public void modifyFieldsByMap(Map<String, List<Object>> modifyTableMap){
		// 做修改字段操作
		if (modifyTableMap.size() > 0) {
			for (Entry<String, List<Object>> entry : modifyTableMap.entrySet()){
				for (Object obj : entry.getValue()){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(entry.getKey(), obj);
					CreateTableParam fieldProperties = (CreateTableParam) obj;
					log.info("开始修改表" + entry.getKey() + "中的字段" + fieldProperties.getFieldName());
					bookTypeDAO.modifyTableField(map);
					log.info("完成修改表" + entry.getKey() + "中的字段" + fieldProperties.getFieldName());
				}
			}
		}
	}
	
	/**
	 * 不修改字段名称时，默认为之前的字段名
	 * @param list
	 */
	public void SetNewFieldName(List<CreateTableParam>list){
		if(list.size()>0){
			for(CreateTableParam param:list){
				if(StringUtils.isBlank(param.getNewFieldName())){
					param.setNewFieldName(param.getFieldName());
				}
			}
		}
		
	}
	

}
