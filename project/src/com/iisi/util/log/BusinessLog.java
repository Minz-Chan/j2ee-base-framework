package com.iisi.util.log;

import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  
/**
 * 
 * @author 陈明珍
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD})  
public @interface BusinessLog {  
    // The name of module  
    String moduleName();  
    // The description of module 
    String desc();  
    // The error message of module
    String exMsg();
}  