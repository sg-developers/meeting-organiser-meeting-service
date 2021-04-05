package pl.sg.meetingorganiser.meetingorganiserservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sg.meetingorganiser.meetingorganiserservice.config.properties.MeetingOrganiserSecurityConfigProperties;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class SecurityConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider(MeetingOrganiserSecurityConfigProperties securityProperties) {
        return new JwtTokenProvider(securityProperties);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenAuthenticationFilter tokenAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider,
            MeetingOrganiserUserDetailsService meetingOrganiserUserDetailsService) {
        return new JwtTokenAuthenticationFilter(jwtTokenProvider, meetingOrganiserUserDetailsService);
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

}
