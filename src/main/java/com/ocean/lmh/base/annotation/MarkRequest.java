package com.ocean.lmh.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

import com.ocean.lmh.base.constant.AppConstant;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface MarkRequest {

	String value() default "returnURI";

	String scope() default AppConstant.SESSION_SCOPE;

	String[] excludes() default {};
}
