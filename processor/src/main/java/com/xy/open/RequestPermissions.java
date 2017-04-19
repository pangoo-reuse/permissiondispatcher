package com.xy.open;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yonehsiung@gmail.com on 2017/4/14.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface RequestPermissions {
    String[] permissions() default "";
    int grantCode() default -1;
    String denyMessage() default "This application does not work properly if denied !";
}
