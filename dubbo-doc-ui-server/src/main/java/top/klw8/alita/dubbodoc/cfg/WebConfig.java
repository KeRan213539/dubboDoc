package top.klw8.alita.dubbodoc.cfg;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: WebConfig
 * @Description: web 配制
 * @date 2020/3/24 11:52
 */
@Component
public class WebConfig implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().equals("/")) {
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()).build());
        }

        return chain.filter(exchange);
    }

}
