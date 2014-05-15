package edu.tcu.gaduo.springmvc.layouts;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Layout {
    String value() default "";
}