package com.vbenadmin.backend.commonweb.autoconfig;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({
  com.vbenadmin.backend.commonweb.security.AccessCheckAspect.class,
  com.vbenadmin.backend.commonweb.exception.GlobalExceptionHandler.class,
  com.vbenadmin.backend.commonweb.filter.JwtContextFilter.class
})
public class CommonWebAutoConfiguration {}

