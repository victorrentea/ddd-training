package victor.training.ddd.spring.thread;

public class ThreadScopeContextHolder {

	private static final ThreadLocal<ThreadScopeAttributes> threadScopeAttributesHolder = new ThreadLocal<ThreadScopeAttributes>() {
		protected ThreadScopeAttributes initialValue() {
			return new ThreadScopeAttributes();
		}
	};

	public static ThreadScopeAttributes currentThreadScopeAttributes() throws IllegalStateException {
		ThreadScopeAttributes accessor = threadScopeAttributesHolder.get();

		if (accessor == null) {
			throw new IllegalStateException("No thread scoped attributes.");
		}

		return accessor;
	}

	public static void clearThread() {
		threadScopeAttributesHolder.get().clear();
	}

}
