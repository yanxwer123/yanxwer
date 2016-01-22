package com.kld.gsm.center.web.security;


import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthSecurity {
    boolean validate() default true;

    String value() default "";
}