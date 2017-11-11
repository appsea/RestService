package com.exuberant.shared;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
 
   @Override
   public void commence(
     HttpServletRequest request,
     HttpServletResponse response,
     AuthenticationException authException) throws IOException {
       System.err.println("Inside commence");
      response.sendError( HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
   }
}