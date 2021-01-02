package sa.com.saib.web.dgi.config;

import io.github.jhipster.config.JHipsterConstants;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import sa.com.saib.web.dgi.aop.logging.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
