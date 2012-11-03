package se.supportix.camelreboot;

import javax.xml.bind.JAXBContext;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import se.supportix.camelreboot.xml.Order;

public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args) throws Exception {
        
    	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    	
    	CamelContext camelContext = new SpringCamelContext(context);
        
        
        ProducerTemplate template = camelContext.createProducerTemplate();
        
        final DataFormat jaxb = new JaxbDataFormat(JAXBContext.newInstance(Order.class));
        
        
        RouteBuilder route1 = new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("direct:hello").wireTap("log:se.supportix?level=INFO&showAll=true").filter(simple("${body} != 'NOK'")).
				choice().
				when(simple("${header.prop1} == 'R1'")).to("direct:showmulti").	
				when(simple("${body} == 'R2'")).to("direct:something");
				
				//Bean endpointen nedan är ett sätt att referera till POJOs i registry med i en
				// to()-metod
				from("direct:showmulti").split(body().tokenize("-")).transform(simple("Transformed ${body}")).multicast().to("bean:fixerBean","bean:fixerBean");
				
			}
		};
        
		camelContext.addRoutes(route1);
		
		camelContext.start();
		
		template.sendBodyAndHeader("direct:hello", "OK-TJOHEJ", "prop1", "R1");
		
		
		
		camelContext.stop();
    }
    
    
}
