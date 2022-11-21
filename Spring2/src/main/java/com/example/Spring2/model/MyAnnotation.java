package com.example.Spring2.model;


import java.lang.annotation.*;

@Target(ElementType.FIELD) //能修飾成員變量
@Retention(value = RetentionPolicy.RUNTIME)
//編譯器把Annotation記錄在class文件中。當運行Java程序時，JVM會保留該Annotation，程序可以通過反射獲取該Annotation的信息。
@Documented
public @interface MyAnnotation {

    Mgni_Type value();

    public enum Mgni_Type {
        TYPE(1, "入金");
        private final int typeCode;
        private final String chinese;
        Mgni_Type(int typeCode, String chinese) {
            this.typeCode = typeCode;
            this.chinese = chinese;
        }

    }

    int length();
    String message();

}
