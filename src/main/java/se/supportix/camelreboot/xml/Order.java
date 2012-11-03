package se.supportix.camelreboot.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Order {

	private  String product;
	
	private  String number;

	public String getProduct() {
		return product;
	}

	@XmlElement
	public void setProduct(String product) {
		this.product = product;
	}

	public String getNumber() {
		return number;
	}

	@XmlElement
	public void setNumber(String number) {
		this.number = number;
	}

	
	
}
