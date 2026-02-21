package com.ded.BTS.config;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

@Configuration
public class JacksonConfig {

	@Bean
	Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(DateTimeFormatter formatter) {

		return builder -> {

			JavaTimeModule module = new JavaTimeModule();

			module.addSerializer(Instant.class, new JsonSerializer<Instant>() {
				@Override
				public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {

					gen.writeString(formatter.format(value));
				}
			});

			builder.modules(module);
		};
	}
}
