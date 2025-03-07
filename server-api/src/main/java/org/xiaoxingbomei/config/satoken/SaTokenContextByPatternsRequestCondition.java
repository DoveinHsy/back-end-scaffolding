package org.xiaoxingbomei.config.satoken;

import cn.dev33.satoken.reactor.spring.SaTokenContextForSpringReactor;
import cn.dev33.satoken.spring.pathmatch.SaPathMatcherHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 自定义 SaTokenContext 实现类，重写 matchPath 方法，将 PathPatternParser.defaultInstance 改为 SaPathMatcherHolder.getPathMatcher()
 */
@Primary
@Component
public class SaTokenContextByPatternsRequestCondition extends SaTokenContextForSpringReactor {
    @Override
    public boolean matchPath(String pattern, String path) {
        return SaPathMatcherHolder.getPathMatcher().match(pattern, path);
    }
}