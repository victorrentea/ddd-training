package victor.training.ddd.spring.thread;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class ThreadScopeListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		ThreadScopeContextHolder.currentThreadScopeAttributes().clear();
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
	}

}
