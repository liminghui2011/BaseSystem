package com.ocean.lmh.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;


@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
@Mapping
public @interface Log {
	String value() default "";
}
