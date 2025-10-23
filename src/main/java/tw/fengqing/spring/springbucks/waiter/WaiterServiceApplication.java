package tw.fengqing.spring.springbucks.waiter;	

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import tw.fengqing.spring.springbucks.waiter.controller.PerformanceInteceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Compression;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class WaiterServiceApplication implements WebMvcConfigurer,
		WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	public static void main(String[] args) {
		SpringApplication.run(WaiterServiceApplication.class, args);
	}

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		// 壓縮，搭配 application.properties 的 server.compression.enabled=true 和 server.compression.min-response-size=512B 使用
		Compression compression = new Compression();
		compression.setEnabled(true);
		compression.setMinResponseSize(DataSize.ofBytes(512));
		factory.setCompression(compression);
	}

	@Override
	public void addInterceptors(@NonNull InterceptorRegistry registry) {
		registry.addInterceptor(new PerformanceInteceptor())
				.addPathPatterns("/coffee/**").addPathPatterns("/order/**");
	}

	@Bean
	public Hibernate6Module hibernate6Module() {
		return new Hibernate6Module();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
		return builder ->
				builder.indentOutput(true)
						.timeZone(TimeZone.getTimeZone("Asia/Taipei"));
	}
}
