package victor.training.ddd.spring.thread;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Scope(value = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public @interface ThreadScoped {

}
