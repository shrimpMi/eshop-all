package com.polystone.tools.excel;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BeanUtils {
	
	
	public static List<Map<String,Object>> beanLToMap(List<Object> list){
		List<Map<String,Object>> mlist = new ArrayList<Map<String,Object>>();
		if(list!=null && list.size() > 0 ) {
			for(Object obj:list) {
				mlist.add(beanToMap(obj));
			}
		}
		return mlist;
	}
	
	public static Map<String, Object> beanToMap(Object obj) {    
        if(obj == null)  
            return null;      
        try {
        	Map<String, Object> map = new HashMap<String, Object>();   
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
            for (PropertyDescriptor property : propertyDescriptors) {    
                String key = property.getName();    
                if (key.compareToIgnoreCase("class") == 0) {   
                    continue;  
                }
                Method getter = property.getReadMethod();  
                Object value = getter!=null ? getter.invoke(obj) : null;  
                if(value!=null) {
                	map.put(key, value);  
                }
            }
            return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    } 

	
	@SuppressWarnings("unchecked")
	public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) throws Exception {    
        if (map == null)  
            return null;    
  
        Object obj = beanClass.newInstance();  
  
        Field[] fields = obj.getClass().getDeclaredFields();   
        for (Field field : fields) {    
            int mod = field.getModifiers();    
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }
            field.setAccessible(true);    
            field.set(obj, map.get(field.getName()));   
        }   
        return (T) obj;    
    }

	public static String getAttrValue(Object obj, String key) {
		try {
			Object ret = null;
			if(obj instanceof Map) {
				Map<?,?> map = (Map<?, ?>) obj;
				ret = map.get(key);
			}else {
				Class<? extends Object> cls = obj.getClass();
				String mname = "get"+key.substring(0, 1).toUpperCase()+key.substring(1);
				Method get = cls.getDeclaredMethod(mname);
				if(get!=null) {
					ret = get.invoke(obj);
				}
			}
			return ret==null?"":ret.toString();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public static void setAttrValue(Object obj, String key, Object val) {
		if(obj instanceof Map) {
			Map<String,Object> map = (Map<String,Object>) obj;
			map.put(key,val);
		}else {
//			try {
				
				setFieldValue(obj, key, val);
				
//				Class<? extends Object> cls = obj.getClass();
//				String mname = "set"+key.substring(0, 1).toUpperCase()+key.substring(1);
//				Method set = cls.getDeclaredMethod(mname,val.getClass());
//				if(set!=null) {
//					set.setAccessible(true);
//					set.invoke(obj,val);
//				}
//			}catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	/** 
     * 循环向上转型, 获取对象的 DeclaredField 
     * @param object : 子类对象 
     * @param fieldName : 父类中的属性名 
     * @return 父类中的属性对象 
     */  
      
    public static Field getDeclaredField(Object object, String fieldName){  
        Field field = null ;  
          
        Class<?> clazz = object.getClass() ;  
          
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                field = clazz.getDeclaredField(fieldName) ;  
                return field ;  
            } catch (Exception e) {  
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
                  
            }   
        }  
      
        return null;  
    }     
      
    /** 
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter 
     * @param object : 子类对象 
     * @param fieldName : 父类中的属性名 
     * @param value : 将要设置的值 
     */  
      
    public static void setFieldValue(Object object, String fieldName, Object value){  
      
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象  
        Field field = getDeclaredField(object, fieldName) ;  
          
        //抑制Java对其的检查  
        field.setAccessible(true) ;  
          
        try {  
            //将 object 中 field 所代表的值 设置为 value  
             field.set(object, value) ;  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
          
    }
	
	
	
	
	
}
