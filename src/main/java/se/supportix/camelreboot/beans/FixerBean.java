package se.supportix.camelreboot.beans;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FixerBean {

	private static final Logger logger = LoggerFactory.getLogger(FixerBean.class);
	
	public void processTheExchange(Exchange exchange) {
		logger.info("The message body: " + exchange.getIn().getBody());
		
	}
	
}
