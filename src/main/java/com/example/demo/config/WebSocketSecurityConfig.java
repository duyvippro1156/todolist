// package com.example.demo.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.simp.SimpMessageType;
// import org.springframework.security.authorization.AuthorizationManager;
// import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
// import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;


// @Configuration
// @EnableWebSocketSecurity
// public class WebSocketSecurityConfig {

// 	final static String WS_UNAUTHORIZED_MESSAGE = "Unauthorized";

// 	@Bean
// 	AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//         messages
//                 .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.DISCONNECT, SimpMessageType.OTHER).permitAll()
//                 // .simpDestMatchers("/app/**").hasRole("ADMIN") 
//                 .simpDestMatchers("/app/**").permitAll()
//                 // .simpSubscribeDestMatchers("/topic/**", "/queue/**").hasRole("ADMIN") 
//                 .simpSubscribeDestMatchers("/topic/**", "/queue/**").permitAll()
//                 .anyMessage().denyAll(); 

//         return messages.build();
// 	}

// }
