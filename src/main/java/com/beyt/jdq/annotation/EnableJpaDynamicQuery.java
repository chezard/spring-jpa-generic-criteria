package com.beyt.jdq.annotation;

import com.beyt.jdq.config.DeserializerConfig;
import com.beyt.jdq.config.EntityManagerProviderConfig;
import com.beyt.jdq.util.ApplicationContextUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({ApplicationContextUtil.class, EntityManagerProviderConfig.class, DeserializerConfig.class})
public @interface EnableJpaDynamicQuery {
}
