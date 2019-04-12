package com.eng.framework.util;

import com.eng.framework.log.Log;

public class ObjectUtil {

  /**
   *  Don't let anyone instantiate this class
   */
  private ObjectUtil() {
  }

  /**
   * Class의 변수들의 초기화 용으로 사용한다.(단 필드는 String에 국한한다.)
   * @param   instance     셋팅되는 빈
   * @return  field.length 필드수
   * @param   willYouTrim  공백제거
   */
  public static int setParams(
      java.lang.Object instance,
      boolean willYouTrim
      ) {
    java.lang.Class cls = instance.getClass();
    java.lang.reflect.Field[] fields = cls.getFields();
    String value = "";
    for (int i = 0; i < fields.length; i++) {
      try {
        value = fields[i].getName();
        if (willYouTrim) {
          value = (value == null) ? "" : value.trim();
        }
        fields[i].set(instance, value);
      } catch (IllegalAccessException e) {
        Log.debug("PDA", "com.moneta.pda.util.ObjectUtil : ",
                  "setParams exception : " + e.toString());
      }
    }
    return fields.length;
  }

  /**
   * class의 public String Field의 값을 request의 parameter로 부터 찾아서 set.<p>
   * @param   req          javax.servlet.http.HttpServletRequest
   * @param   instance     셋팅되는 빈
   * @return  field.length 필드수
   */

  public static int setParams2(
      javax.servlet.http.HttpServletRequest req,
      java.lang.Object instance,
      boolean doTrim) {
    java.lang.Class cls = instance.getClass();
    java.lang.reflect.Field[] fields = cls.getFields();
    String val = "";

    for (int i = 0; i < fields.length; i++) {
      try {
        val = fields[i].getName();
        val = (String) req.getParameter(val) == null ? "" :
            (String) req.getParameter(val);
        if (doTrim) {
          val = val.trim();
        }
        if (!"".equals(val)) {
          fields[i].set(instance, val);
        }
      } catch (IllegalAccessException e) {
        Log.debug("PDA", "com.moneta.pda.util.ObjectUtil : ", e.toString());
      }
    }
    return fields.length;
  }

  /**
   * class의 public String Field의 값을 request의 parameter로 부터 찾아서 set.<p>
   * @param   req          javax.servlet.http.HttpServletRequest
   * @param   instance     셋팅되는 빈
   * @return  field.length 필드수
   */

  public static int getSessInfo(javax.servlet.http.HttpSession session,
                                java.lang.Object instance) {
    java.lang.Class cls = instance.getClass();
    java.lang.reflect.Field[] fields = cls.getFields();
    String val = "";
    for (int i = 0; i < fields.length; i++) {
      try {
        val = (String) (session.getAttribute(fields[i].getName()));
        fields[i].set(instance, val);
      } catch (Exception e) {
        Log.debug("PDA", "com.moneta.pda.util.ObjectUtil : ", e.toString());
      }
    }
    return fields.length;
  }

  /**
   * class의 public String field를 ""로 set.
   * @param instance
   */
  public static void clearFields(java.lang.Object instance) {
    java.lang.Class cls = instance.getClass();
    java.lang.reflect.Field[] fields = cls.getFields();
    for (int i = 0; i < fields.length; i++) {
      try {
        fields[i].set(instance, "");
      } catch (IllegalAccessException e) {
        Log.debug("PDA", "com.moneta.pda.util.ObjectUtil : ", e.toString());
      }
    }
  }

  /**
   * Class의 public String field 출력.
   * @param instance
   */
  public static void printFields(java.lang.Object instance) {
    java.lang.Class cls = instance.getClass();
    java.lang.reflect.Field[] fields = cls.getFields();
    String fieldsValue = "";
    for (int i = 0; i < fields.length; i++) {
      try {
        //Log.debug("PDA","com.moneta.pda.util.ObjectUtil : ","\n"+ fields[ i ].getName() + ":"
        //                    + ( String ) ( fields[ i ].get( instance ) ) + "." );
        fieldsValue += "\n" + fields[i].getName() + ":" +
            (String) (fields[i].get(instance));
      } catch (IllegalAccessException e) {
        Log.debug("", "", e.toString());
      }
    }
    Log.debug("", "", fieldsValue);
  }

  /**
   * class의 public String field를 다른 class의 같은 이름의 field로 copy.
   * @param fromInstance
   * @param toInstance
   */
  public static void copyFields(java.lang.Object fromInstance,
                                java.lang.Object toInstance) {
    java.lang.Class fromCls = fromInstance.getClass();
    java.lang.Class toCls = toInstance.getClass();
    java.lang.reflect.Field[] fromFields = fromCls.getFields();
    String fieldName = "";
    String fieldValue = "";
    for (int i = 0; i < fromFields.length; i++) {
      try {
        fieldName = fromFields[i].getName();
        fieldValue = (String) (fromFields[i].get(fromInstance));
        toCls.getField(fieldName).set(toInstance, fieldValue);
      } catch (NoSuchFieldException ex) {
      } catch (Exception e) {
        Log.debug("PDA", "com.moneta.pda.util.ObjectUtil : ", e.toString());
      }
    }
  }

  /**
   * set a field of class with value.
   * @param instance
   * @param fieldName
   * @param fieldValue
   */
  public static void setFieldByName(java.lang.Object instance, String fieldName,
                                    String fieldValue) {
    try {
      instance.getClass().getField(fieldName).set(instance, fieldValue);
    } catch (Exception e) {
      Log.debug("PDA", "com.moneta.pda.util.ObjectUtil : ", e.toString());
    }
  }

  /**
   * class의 public String Field의 값을 ResultSet의 field Name로 부터 찾아서 값을 set.
   * @param rs
   * @param instance
   * @param columnPrefix
   * @return
   */
  public static int setFieldsFromRS(java.sql.ResultSet rs,
                                    java.lang.Object instance,
                                    String columnPrefix) {
    java.lang.Class cls = instance.getClass();
    java.lang.reflect.Field[] fields = cls.getFields();
    for (int i = 0; i < fields.length; i++) {
      try {
        String value = rs.getString(columnPrefix
                                    + fields[i].getName().toUpperCase());
        fields[i].set(instance, new String(value));
      } catch (Exception e) {
        Log.debug("PDA", "com.moneta.pda.util.ObjectUtil : ",
                  "Note: " + fields[i].getName()
                  + " Not Found in the ResultSet.");
      }
    }
    return fields.length;
  }

  /**
   * class의  public   String Field의 값중 null인것을 "" 로 변환<p>
   * @param  instance  적용하고자 하는 빈
   * @return int       적용된 필드수
   */
  public static int setFieldsNVL(java.lang.Object instance) {
    java.lang.Class cls = instance.getClass();
    java.lang.reflect.Field[] fields = cls.getFields();
    for (int i = 0; i < fields.length; i++) {
      try {
        if ( (String) (fields[i].get(instance)) == null) {
          fields[i].set(instance, "");
        }
      } catch (Exception e) {
        Log.debug("PDA", "com.moneta.pda.util.ObjectUtil : ", e.toString());
      }
    }
    return fields.length;
  }

}
