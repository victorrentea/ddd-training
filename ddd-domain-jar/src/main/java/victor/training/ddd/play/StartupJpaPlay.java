package victor.training.ddd.play;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupJpaPlay {

	@Autowired
	private Playground playground;
	@Autowired
	private DDDAggregate testOptimistic;
	
	


	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("Props"  + clasaProprietati);
		System.out.println("Props"  + clasaProprietati.getClass());
//		ByteArrayOutputStream baos;
//		new ObjectOutputStream(baos);
		
		System.out.println("Application started. Running playground code...");
//		dummyDataCreator.persistDummyData();
		System.out.println(" ========= FIRST TRANSACTION ========== ");
		playground.firstTransaction();
		System.out.println(" ========= SECOND TRANSACTION ========== ");
		
		playground.secondTransaction(new Customer("Jane"));
		System.out.println(" ========= END ========== ");
		
		testOptimistic.insertInitial();
		OrderX o1 = testOptimistic.tranzactiaCareAduceDinBackend();
		OrderX o2 = testOptimistic.tranzactiaCareAduceDinBackend();
		System.out.println("o1.ver = " + o1.version + "o2.ver = " + o2.version);
		o1.setLineComment(0, "Axds");
//		o1.setLinePrice(0, 15);
		o2.setLineComment(1, "Comentariu");
		testOptimistic.tranzactiaCareSalveazaInBackend(o2);
		try {
			testOptimistic.tranzactiaCareSalveazaInBackend(o1);
		} catch (Exception e ) {
			e.printStackTrace(System.out);
		}
		
		
		
		
	}
	
	
	@Autowired
	ClasaProprietati clasaProprietati;
	
	@Configuration
	@ConfigurationProperties(prefix="base")
	static class ClasaProprietati {
		String a,b;

		public String toString() {
			return "ClasaProprietati [a=" + a + ", b=" + b + "]";
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}
		
		
	}
	
	
	

//	@Bean
//	public AuditorAware<String> auditorProvider() {
//		return MyUtil::getUserOnCurrentThread;
//	}

	public static void main(String[] args) {
		SpringApplication.run(StartupJpaPlay.class, args);
	}
}
