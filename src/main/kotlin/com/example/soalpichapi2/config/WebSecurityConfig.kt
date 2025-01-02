package com.example.soalpichapi2.config

import com.example.soalpichapi2.filter.JwtAuthenticationFilter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
class WebSecurityConfig(private val jwtAuthenticationFilter: JwtAuthenticationFilter) {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/api/auth/**").permitAll()
                    // Category
                    .requestMatchers("/api/categories").authenticated()
                    .requestMatchers("/api/categories/**").authenticated()
                    // Question
                    .requestMatchers("/api/questions").authenticated()
                    .requestMatchers("/api/questions/**").authenticated()
                    // Other APIs
                    .anyRequest().authenticated()
            }
            .logout {
                it.logoutUrl("/api/auth/logout")
                it.logoutSuccessHandler { _, response, _ ->
                    response.status = HttpServletResponse.SC_NO_CONTENT
                }
                it.invalidateHttpSession(true)
                it.clearAuthentication(true)
                it.permitAll()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic(withDefaults())
            .csrf { it.disable() }
            .cors(withDefaults())
        return http.build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        http: HttpSecurity,
        bCryptPasswordEncoder: BCryptPasswordEncoder?,
        userDetailsService: UserDetailsService,
    ): AuthenticationManager {
        val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        builder
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
        return builder.build()
    }
}
