package com.example.soalpichapi2

import com.example.soalpichapi2.config.MessageSourceConfig
import com.example.soalpichapi2.config.WebMvcConfig
import com.example.soalpichapi2.config.WebSecurityConfig
import com.example.soalpichapi2.model.BaseModel
import com.example.soalpichapi2.repository.BaseRepository
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport

@Configuration
@EnableJpaAuditing
@EntityScan(basePackageClasses = [BaseModel::class])
@EnableJpaRepositories(basePackageClasses = [BaseRepository::class])
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@Import(
    WebSecurityConfig::class,
    MessageSourceConfig::class,
    WebMvcConfig::class,
)
class SoalPichApiConfiguration
