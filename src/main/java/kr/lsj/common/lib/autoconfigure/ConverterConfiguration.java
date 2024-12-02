package kr.lsj.common.lib.autoconfigure;


import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * <pre>
 * Service name : VAS_v2
 * Package name : kr.co.wkit.hdsvas.cfg
 * Class   name : WebMvcConfig
 * Description  :
 *
 * ==============================================================================
 *
 * </pre>
 * @author JINY
 * @version 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class ConverterConfiguration {

    private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@SuppressWarnings("Convert2Lambda")
    @Bean
	public Converter<String, LocalDate> localDateConverter() {
		return new Converter<String, LocalDate>() {
			@Override
			public LocalDate convert(@NonNull String source) {
				return LocalDate.parse(source, DATE_FORMAT);
			}
		};
	}

	@SuppressWarnings("Convert2Lambda")
    @Bean
	public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
			@Override
			public LocalDateTime convert(@NonNull String source) {
				return LocalDateTime.parse(source, DATE_TIME_FORMAT);
			}
		};
	}

	@Bean
	public Jackson2ObjectMapperBuilder objectMapper() {
		return new Jackson2ObjectMapperBuilder()
				.timeZone(TimeZone.getTimeZone("Asia/Seoul"))
				.serializers(new LocalDateSerializer(DATE_FORMAT))
				.deserializers(new LocalDateDeserializer(DATE_FORMAT))
				.serializers(new LocalDateTimeSerializer(DATE_TIME_FORMAT))
				.deserializers(new LocalDateTimeDeserializer(DATE_TIME_FORMAT));
    }


}

