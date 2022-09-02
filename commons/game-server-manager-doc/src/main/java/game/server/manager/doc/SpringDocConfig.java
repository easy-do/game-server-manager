package game.server.manager.doc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuzhanfeng
 * @Date 2022/9/2 15:40
 * @Description doc配置
 */
@Configuration
@Data
public class SpringDocConfig {

    @Value("${springdoc.customize.title:'SpringDoc API'}")
    private String title;

    @Value("${springdoc.customize.description:'SpringDoc API'}")
    private String description;

    @Value("${springdoc.customize.version:1.0.0}")
    private String version;

    @Value("${springdoc.customize.license.name:noSetting}")
    private String licenseName;

    @Value("${springdoc.customize.license.url:localhost}")
    private String licenseUrl;

    @Value("${springdoc.customize.externalDocs.description:外部文档}")
    private String externalDocsDescription;

    @Value("${springdoc.customize.externalDocs.url:localhost}")
    private String externalDocsUrl;

    @Bean
    public OpenAPI mallTinyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(title)
                        .description(description)
                        .version(version)
                        .license(new License().name(licenseName).url(licenseUrl)))
                .externalDocs(new ExternalDocumentation()
                        .description(externalDocsDescription)
                        .url(externalDocsUrl));
    }
}
