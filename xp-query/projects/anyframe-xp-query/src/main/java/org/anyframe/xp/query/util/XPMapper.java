/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.anyframe.xp.query.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.DataTypes;
import com.tobesoft.xplatform.data.Variable;
import com.tobesoft.xplatform.data.VariableList;


/**
 * The Util class needed at UI development using the Tobesoft's MiPlatform
 * <p>The MiPlatform's data trasmitting object are DataSet and VariableList.
 * So, it is different from the VO object used in Java.  When transmitting the
 * user inputted value by VO in Server Side, there needs a conversion of Data
 * for transmitting the value to the presentation layer again.</p>
 * <br>
 * <p>Consists of the method helping converting the data such as DataSet, 
 * VariableList and VO for MiPMapper.
 * @author Byunghun Woo
 */
public class XPMapper {
	
	/**
	 * <p>
	 * This method converts Value Object(VO) List into Dataset(MiPlatform).
	 * <p>
	 * 
	 * @param dataSetName
	 *            a name of Dataset
	 * @param voList
	 *            VO List to be converted into Dataset
	 * @param isCheck
	 *            if isCheck is 'true', when create Dataset, add check column to
	 *            Dataset.
	 * @return Dataset
	 * 
	 * @throws ServletException
	 * @throws Exception
	 */
	public static DataSet convertVoListToDataset(String dataSetName, List voList,
			boolean isCheck) throws ServletException, Exception {
		DataSet dataSet = new DataSet(dataSetName);
		populate(dataSet, voList, isCheck);
		return dataSet;
	}

	/**
	 * <p>
	 * This method converts Value Object(VO) into DataSet(MiPlatform).
	 * </p>
	 * 
	 * @param dataSetName
	 *            a name of DataSet
	 * @param obj
	 *            Value Object(VO) to be converted into DataSet
	 * @param isCheck
	 *            if isCheck is 'true', when create DataSet, add check column to
	 *            DataSet.
	 * @return DataSet
	 * @throws ServletException
	 * @throws Exception
	 */
	public static DataSet convertVoToDataset(String dataSetName, Object obj,
			boolean isCheck) throws ServletException, Exception {
		DataSet dataSet = new DataSet(dataSetName);
		populate(dataSet, obj, isCheck);
		return dataSet;
	}

	/**
	 * <p>
	 * This method converts DataSet into ListMap(VO LIST). if DataSet's status
	 * is 'insert', a key of Map is 'insert' if DataSet's status is 'update', a
	 * key of Map is 'update' if DataSet's status is 'delete', a key of Map is
	 * 'delete'
	 * </p>
	 * 
	 * @param cls
	 *            VO Class.
	 * @param ds
	 *            DataSet to be converted into Value Object(VO) List.
	 * @return HashMap
	 * @throws ServletException
	 * @throws Exception
	 */
	public static HashMap convertDatasetToListMap(Class cls, DataSet ds)
			throws ServletException, Exception {
		return populateCudList(cls, ds);
	}

	/**
	 * <p>
	 * This method converts DataSet into ListMap(VO LIST). if DataSet's status
	 * is 'insert', a key of Map is 'insert' if DataSet's status is 'update', a
	 * key of Map is 'update' if DataSet's status is 'delete', a key of Map is
	 * 'delete'
	 * </p>
	 * 
	 * @param cls
	 *            VO Class.
	 * @param ds
	 *            DataSet to be converted into Value Object(VO) List.
	 * @param convertToCamenCase
	 *            if DataSet's column name include '_'(underscore) and attribute
	 *            names of VO are Camelcase, this is 'true' ex) DataSet's column
	 *            name is 'test_sample', a attribute name of VO is 'testSample'
	 *            this value must be true.
	 * @return HashMap
	 * @throws ServletException
	 * @throws Exception
	 */
	public static HashMap convertDatasetToListMap(Class cls, DataSet ds,
			boolean convertToCamenCase) throws ServletException, Exception {
		return populateCudList(cls, ds, convertToCamenCase);
	}	
	
	/**
	 * Execute the data bind of VariableList and VO class.
	 * @param vo
	 * 			Sever Side VO
	 * @param variableList
	 * 			MiPlatform VariableList
	 */
	private static void populate(Object vo, VariableList variableList){
		populate(vo, variableList, false);
	}
	/**
	 * Execute the data bind of VariableList and VO. The VO's attribute name is
	 * of CameCase. And, the VariableList's Attribute Name includes
	 * underscore('_'). Then, we can set the converToCamelCase option as true
	 * and the data bind occurs.
	 * 
	 * @param vo
	 *            Server Side VO
	 * @param variableList
	 *            MiPlatform VariableList
	 * @param converToCamelCase
	 *            When changing to CamelCase the VariableList' Attribute Name,
	 *            then true.
	 */
	private static void populate(Object vo, VariableList variableList, boolean converToCamelCase){
		new XPDataBinder(vo,converToCamelCase).bind(variableList);
	}
	
	/**
	 * Map the VO the value of DataSet The populate method is different from
	 * populateCudList in that all Data record without relation to stauts maps
	 * to VO and returns Collection.
	 * 
	 * @param voClazz
	 *            Server Side VO
	 * @param dataList
	 *            MiPlatform DataSet
	 * @return Collection consisting of VO
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static Collection populate(Class voClazz, DataSet dataList) throws InstantiationException, IllegalAccessException{
		return populate(voClazz, dataList, false);
	}
	/**
	 * Map VO the DataSet's value The populate method is different from
	 * populateCudList in that all Data record without relation to stauts maps
	 * to VO and returns Collection. If the DataSet's Column Name includes the
	 * Underscore('_'), and if the convertToCamelCase value is true, then maps
	 * to VO as changed to CamelCase
	 * 
	 * @param voClazz
	 *            Sever Side VO
	 * @param dataList
	 *            MiPlatform DataSet
	 * @param converToCamelCase
	 *            If mapping by changing to CamcelCase the DataSet's column
	 *            name, then true
	 * @return The collection consisting of VO
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static Collection populate(Class voClazz, DataSet dataList, boolean converToCamelCase) throws InstantiationException, IllegalAccessException{
		int rowCount = dataList.getRowCount();
		ArrayList list = new ArrayList();
		Object vo = null;
		for(int i=0;i<rowCount;i++){		
			vo = voClazz.newInstance();
			list.add(vo);			
			new XPDataBinder(vo, converToCamelCase).bind(dataList, i);
		}
		return list;		
	}
	/**
	 * The DataSet consists of many records. Each record has the status such as
	 * insert, update and delete. populatedCudList method maps by dividing in
	 * ArryList consisting of VO with respect to the status of the Record.
	 * Afterwards, with key values such as insert, update, and delete, we return
	 * by saving in the HashMap.
	 * 
	 * @param voClazz
	 *            Server Side VO
	 * @param dataList
	 *            MiPlatform DataSet
	 * @return java.util.HashMap, HashMap�� key��: insert, update, delete
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static HashMap populateCudList(Class voClazz, DataSet dataList) throws InstantiationException, IllegalAccessException{
		return populateCudList(voClazz, dataList, false);
	}
	
	/**
	 * The DataSet consists of many records and each record has status such as
	 * insert, update and delete. populateCuList method returns by saving in the
	 * HashMap with the key values such as insert, update and delete after
	 * mapping in the ArryList consiting of VO according to the record's status.
	 * When the dataset's column name includes Underscore('_'), and VO's
	 * attribute name is CamelCase, then we set convertToCamelCase to true and
	 * the mapping occurs.
	 * 
	 * @param voClazz
	 *            Server Side VO
	 * @param dataList
	 *            MiPlatform DataSet
	 * @param converToCamelCase
	 *            If mapping by changing to CamelCast the DataSet's Column Name,
	 *            then true.
	 * @return java.util.HashMap, HashMap's key value are insert, update and
	 *         delete
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static HashMap populateCudList(Class voClazz, DataSet dataList, boolean converToCamelCase) throws InstantiationException, IllegalAccessException{
		ArrayList insertList = new ArrayList();
		ArrayList updateList = new ArrayList();
		ArrayList deleteList = new ArrayList();
		
		HashMap resultMap = new HashMap();
		resultMap.put("insert",insertList);
		resultMap.put("update",updateList);
		resultMap.put("delete",deleteList);

		Object vo = null;
		int rowCount = dataList.getRowCount();
		for(int i=0;i<rowCount;i++){		
			vo = voClazz.newInstance();
			if (DataSet.ROW_TYPE_INSERTED == dataList.getRowType(i))
				insertList.add(vo);
			else if ( DataSet.ROW_TYPE_UPDATED ==dataList.getRowType(i) )
				updateList.add(vo);

			new XPDataBinder(vo, converToCamelCase).bind(dataList, i);
		}

		int deleteRowCount = dataList.getRemovedRowCount();
		for(int i=0;i<deleteRowCount;i++){
			vo = voClazz.newInstance();
			deleteList.add(vo);
			new XPDataBinder(vo, converToCamelCase).bind(dataList, i, true);
		}
		return resultMap;		
	}	
	/**
	 * Maps the VO to VariaableList
	 * @param variableList
	 * 			MiPlatform variableList
	 * @param vo
	 * 			Sever Side VO
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static void populate(VariableList variableList, Object vo) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map propertyMap = setParameterMap(vo);
		Iterator variableIterator =  propertyMap.entrySet().iterator();
		Object key = null;
		Object value = null;
		while(variableIterator.hasNext()){
			Map.Entry entry = (Map.Entry)variableIterator.next();
			key = entry.getKey();
			value = entry.getValue();
			variableList.add((String)key, value);
		}		
	}
	/**
	 * Maps to DataSet the VO
	 * @param dataList
	 * 			MiPlatform DataSet
	 * @param vo
	 * 			Server Side VO
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static void populate(DataSet dataList, Object vo) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		populate(dataList, vo, false);
	}
	/**
	 * Maps DataSet the VO
	 * In the case the DataSet's Check Column is needed, then we set isCheck to true
	 * @param dataList
	 * 			MiPlatform DataSet
	 * @param vo
	 * 			Server Side VO
	 * @param isCheck
	 * 			when check column is needed, then true
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static void populate(DataSet dataList, Object vo, boolean isCheck) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map propertyMap = setParameterMap(vo);
		setupColumnInfo(propertyMap, dataList, true, 0, isCheck);
	}
	
	/**
	 * Maps to DataSet the List consisting of the Value Object
	 * @param dataList
	 * 			MiPlatform DataSet
	 * @param voList
	 * 			List consisting of Value Object
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static void populate(DataSet dataList, List voList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		populate(dataList, voList, false);
		
	}
	
	/**
	 * <p>
     * This method converts Value Object(VO) List into DataSet(MiPlatform).
     * <p>
	 * @param dataList
	 * 			MiPlatform DataSet
	 * @param voList
	 * 			List consisting of Value Object
	 * @param
	 * 			
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static void populate(DataSet dataList, List voList, boolean isCheck) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(voList.size() == 0) return;
		Map propertyMap = setParameterMap(voList.get(0));
		setupColumnInfo(propertyMap, dataList, true, 0, isCheck);
		for(int i=1; i< voList.size();i++){
			propertyMap = setParameterMap(voList.get(i));
			setupColumnInfo(propertyMap, dataList, false, i, isCheck);		
		}
	}
	/**
	 * Setting the value with respect to column after setting the column name of DataSet the 
         * Value Object(VO)'s attribute name
	 * if isCheck is true, the '_chk' column name is addtionally created.
	 * @param propertyMap
	 * 			The map consisting of the value object's attribute name and value
	 * @param dataList
	 * 			The dataset mappping the Value Object
	 * @param addColumnInfo
	 * 			We set the column info when called the first time because we can
         * create the column name when the dataset is created the first time.
	 * 			Set the column info Column
	 * @param rowNumber
	 * 			the row number of record for settingthe column name
	 * @param isCheck
	 * 			If the check field is need, then true
	 */
	private static void setupColumnInfo(Map propertyMap, DataSet dataList, boolean addColumnInfo, int rowNumber, boolean isCheck){
	        int row = rowNumber;
		Iterator variableIterator =  propertyMap.entrySet().iterator();
		String key = null;
		Object value = null;
		Variable variable = null;
		int columnIndex = 0;
		if (addColumnInfo && isCheck) dataList.addColumn("_chk", DataTypes.STRING, 255);
		while(variableIterator.hasNext()){
			try{
				Map.Entry entry = (Map.Entry)variableIterator.next();
				key = (String)entry.getKey();
				value = entry.getValue();
				if(addColumnInfo) {
					dataList.setChangeStructureWithData(true);
					dataList.addColumn(key, getDsType(value));
				}
				
				if( row == rowNumber ){
				    dataList.newRow();
				}
				dataList.set(rowNumber,key, value);
				row++;
			}catch(Exception e){
				continue;
			}
			
		}
		
	}
	
	/**
	 * Called when obtaining the column type of dataset mapping in the data type of the Value Object
	 * @param value
	 * 			data type
	 * @return
	 * 			The column type of dataset mapping in data type of value object.
	 */
	private static int getDsType(Object value) {
		int type = DataTypes.STRING;
		if(value == null) return type;
		String t = value.getClass().getName();
		
		if (t.equals(Long.class.getName())) {
			type = DataTypes.LONG;
		} else if (t.equals(Integer.class.getName())) {
			type = DataTypes.INT;
		} else if (t.equals(Boolean.class.getName())) {
			type = DataTypes.BOOLEAN;
		} else if (t.equals(Float.class.getName())) {
			type = DataTypes.FLOAT;
		} else if (t.equals(Double.class.getName())) {
			type = DataTypes.DOUBLE;
		} else if (t.equals(BigDecimal.class.getName())) {
			type = DataTypes.BIG_DECIMAL;
		} else if (t.equals(Date.class.getName())) {
			type = DataTypes.DATE_TIME;
		} else if (t.equals(byte[].class.getName())) {
			type = DataTypes.BLOB;
		}

		return type;
	}
	
	/**
	 * Returns by saving in the map the appropriate value and the Value Object(VO)'s Attribute Name
	 * @param vo
	 * 			Server Side Value Object
	 * @return
	 * 			java.util.Map
	 */
	private static Map setParameterMap(Object vo){
		Class cls = vo.getClass();
		Field[] field = ReflectionHelp.getAllDeclaredFields(cls);
		
		AccessibleObject.setAccessible(field, true);
		
		HashMap propertyMap = new HashMap(); 
		for ( int i = 0 ; i < field.length ; i ++ ){
			try{
				propertyMap.put( field[i].getName(), ReflectionHelp.getFieldValue(field[i], vo ));
			}catch(Exception e){
				continue;
			}
		}
		return propertyMap;
	}
}
