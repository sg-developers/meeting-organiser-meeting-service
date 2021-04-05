package pl.sg.meetingorganiser.meetingorganiserservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "meeting-organiser.security")
public class MeetingOrganiserSecurityConfigProperties {

    @Valid
    @NotNull
    private final Jwt jwt;

    @Valid
    @NotNull
    private final OAuth2 oAuth2;

    public MeetingOrganiserSecurityConfigProperties(@Valid @NotNull Jwt jwt, @Valid @NotNull OAuth2 oAuth2) {
        this.jwt = jwt;
        this.oAuth2 = oAuth2;
    }

    public Jwt getJwt() {
        return jwt;
    }

    public OAuth2 getoAuth2() {
        return oAuth2;
    }

    public static class Jwt {

        @NotNull
        private final String tokenSecret;

        @NotNull
        @DurationUnit(ChronoUnit.MILLIS)
        private final Duration tokenValidityInMs;

        public Jwt(@NotNull String tokenSecret, @NotNull Duration tokenValidityInMs) {
            this.tokenSecret = tokenSecret;
            this.tokenValidityInMs = tokenValidityInMs;
        }

        public String getTokenSecret() {
            return tokenSecret;
        }

        public Duration getTokenValidityInMs() {
            return tokenValidityInMs;
        }
    }

    public static class OAuth2 {

        @NotNull
        private final String authorizedRedirectUris;

        public OAuth2(@NotNull String authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
        }

        public String getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }
    }
}
