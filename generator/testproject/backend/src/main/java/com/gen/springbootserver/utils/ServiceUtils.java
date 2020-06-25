package com.gen.springbootserver.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.dom4j.Element;
import org.springframework.stereotype.Service;

@Service
public class ServiceUtils {
    private static Integer pagenumber = 0;
    private static Integer pagesize = 10;

    public static Integer getlimitStart(Integer number, Integer size) {
        if (number > 0) {
            number = number - 1;
        }
        Integer start = pagenumber;
        if (number != null && number != 0) {
            start = number * size;
        }
        return start;
    }

    public static Integer getlimitEnd(Integer size) {
        Integer end = (pagenumber + 1) * pagesize;
        if (size != null && size != 0) {
            end = size;
        }
        return end;
    }

    public static String dealFilterfileName(String mappername, String str) {
        Element e = XmlUtils.getResultMapElement(mappername + ".xml", "BaseResultMap");
        return XmlUtils.getMapperColumnByElement(e, str.replace("filterBy", ""));
    }

    public static String dealsortString(String mappername, String sort, String order) {
        Element e = XmlUtils.getResultMapElement(mappername + ".xml", "BaseResultMap");
        sort = XmlUtils.getMapperColumnByElement(e, sort);

        sort = (sort == null || sort == "") ? "id" : sort;
        order = (order == null || order == "") ? "asc" : charToLowerCase(order);

        String result = sort + " " + order;
        return result;
    }

    /*** Turn to lowercase **/
    private static String charToLowerCase(String str) {
        char[] ch = str.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] <= 90 && ch[i] >= 65) {
                ch[i] += 32;
            }
            sbf.append(ch[i]);
        }
        return sbf.toString();
    }

    /**
     * 根据属性，获取get方法
     * 
     * @param ob   对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    public static Object getGetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i].invoke(ob);
            }
        }
        return null;
    }

    /**
     * 根据属性，拿到set方法，并把值set到对象中
     * 
     * @param obj       对象
     * @param clazz     对象的class
     * @param fileName  需要设置值得属性
     * @param typeClass
     * @param value
     */
    public static void setValue(Object obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) {
        filedName = removeLine(filedName);
        String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        try {
            Method method = clazz.getDeclaredMethod(methodName, new Class[] { typeClass });
            method.invoke(obj, new Object[] { getClassTypeValue(typeClass, value) });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 通过class类型获取获取对应类型的值
     * 
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }

    /**
     * 处理字符串 如： abc_dex ---> abcDex
     * 
     * @param str
     * @return
     */
    public static String removeLine(String str) {
        if (null != str && str.contains("_")) {
            int i = str.indexOf("_");
            char ch = str.charAt(i + 1);
            char newCh = (ch + "").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i + 1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }

}