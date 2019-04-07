package com.dhitech.framework.util;

import java.io.Serializable;

public class Value implements Serializable {

      public Value() {
      }

      public static boolean isBlank(String _strVal) {
            return _strVal != null && _strVal.trim().length() == 0;
      }

      public static boolean isFalse(String _strVal) {
            return isNull(_strVal) || "false".equalsIgnoreCase(_strVal.trim()) ||
                    "no".equalsIgnoreCase(_strVal.trim());
      }


      public static boolean isNull(String _strVal) {
            return _strVal == null || "null".equalsIgnoreCase(_strVal.trim());
      }

      public static boolean isTrue(String _strVal) {
            return!isNull(_strVal) &&
                    ("true".equalsIgnoreCase(_strVal.trim()) ||
                     "yes".equalsIgnoreCase(_strVal.trim()));
      }

      public static final String BLANK = "";
      public static final String TRUE = "true";
      public static final String FALSE = "false";
      public static final String NO = "no";
      public static final String YES = "yes";
      public static final String NULL = "null";
      public static final int KBYTE = 1024;
      public static final int MBYTE = 0x100000;
}
