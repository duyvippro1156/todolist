package com.example.demo.config;

// import io.jsonwebtoken.JwtException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.simp.config.ChannelRegistration;
// import org.springframework.messaging.simp.stomp.StompCommand;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
// import org.springframework.messaging.support.ChannelInterceptor;
// import org.springframework.messaging.support.MessageHeaderAccessor;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;



@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                // .setHandshakeHandler(new HttpHandshakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue");
    }

    // @Override
    // public void configureClientInboundChannel(ChannelRegistration registration) {
    //     registration.interceptors(new ChannelInterceptor() {
    //         @Override
    //         public Message<?> preSend(Message<?> message, MessageChannel channel) {
    //             StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    //             // Authenticate user on CONNECT
    //             if (StompCommand.CONNECT.equals(accessor.getCommand())) {
    //                 // Extract JWT token from header, validate it and extract user authorities
    //                 String authHeader = accessor.getFirstNativeHeader("Authorization");
    //                 if (authHeader == null || !authHeader.startsWith("Bearer" + " ")) {
    //                     // If there is no token present then we should interrupt handshake process and
    //                     // throw an AccessDeniedException
    //                     throw new AccessDeniedException(WebSocketSecurityConfig.WS_UNAUTHORIZED_MESSAGE);
    //                 }
    //                 String token = authHeader.substring("Bearer".length() + 1);
    //                 String username;
    //                 try { 
    //                     //Validate JWT token with any resource server          
    //                     username = jwtTokenProvider.getUsername(token);
    //                 } catch (JwtException ex) {
    //                     // In case the JWT token is expired or cannot be decoded, an
    //                     // AccessDeniedException should bethrown log.warn(ex.getMessage());
    //                     throw new AccessDeniedException(WebSocketSecurityConfig.WS_UNAUTHORIZED_MESSAGE);
    //                 }
    //                 UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
    //                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
    //                     userDetails, null, userDetails.getAuthorities()
    //                 );
    //                 // JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, getUserAuthorities(jwt));
    //                 accessor.setUser(authentication);
    //             }
    //             return message;
    //         }
    //     });

    // }

}
