package top.klw8.alita.examples.dubbodoc.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import top.klw8.alita.dubbodoc.annotations.EnableDubboApiDoc;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocConfig
 * @Description: dubbo doc 配制
 * @date 2020/2/27 12:51
 */
@Configuration
@EnableDubboApiDoc
@Profile("dev")
public class DubboDocConfig {
}
