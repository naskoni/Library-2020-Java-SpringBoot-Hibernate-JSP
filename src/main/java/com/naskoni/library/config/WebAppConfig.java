package com.naskoni.library.config;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.naskoni.library.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableAsync
@ComponentScan("com.naskoni.library")
public class WebAppConfig implements WebMvcConfigurer {

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setHibernateProperties(hibernateProperties());
    sessionFactory.setPackagesToScan("com.naskoni.library.entity");
    return sessionFactory;
  }

  @Bean
  public DataSource dataSource() {
    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.url("jdbc:mysql://localhost/library");
    dataSourceBuilder.username("root");
    dataSourceBuilder.password("password");

    return dataSourceBuilder.build();
  }

  private final Properties hibernateProperties() {
    Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
    hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

    return hibernateProperties;
  }

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public ApplicationSecurity applicationSecurity() {
    return new ApplicationSecurity();
  }

  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  public static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    PasswordEncoder sha256PasswordEncoder =
        new PasswordEncoder() {
          @Override
          public String encode(CharSequence rawPassword) {
            return Hashing.sha256().hashString(rawPassword, Charsets.UTF_8).toString();
          }

          @Override
          public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return encodedPassword.equals(
                Hashing.sha256().hashString(rawPassword, Charsets.UTF_8).toString());
          }
        };
    @Autowired private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf()
          .disable()
          .authorizeRequests()
          .antMatchers("/css/**")
          .permitAll()
          .anyRequest()
          .fullyAuthenticated()
          .and()
          .formLogin()
          .defaultSuccessUrl("/home")
          .loginPage("/login")
          .failureUrl("/login?error")
          .permitAll()
          .and()
          .logout()
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .logoutSuccessUrl("/login");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(sha256PasswordEncoder);
    }
  }
}
