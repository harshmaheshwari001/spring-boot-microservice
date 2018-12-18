package com.forex;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForexController {

	@Autowired
	private Environment environment;
	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(method = RequestMethod.GET, value = "/currency-exchange/from/{from}/to/{to}", produces = {
			"application/json" })
	public ExchangeValue retrieveExchangeValue(@PathVariable(value = "from", required = true) String from,
			@PathVariable(value = "to", required = true) String to) {
//		//try {
//			try {
//				Thread.sleep(20000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		// }
		ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
		System.out.println(exchangeValue);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		return exchangeValue;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/currency-exchange-2/to/{to}", produces = {
			"application/json" })
	public List<ExchangeValue> retrieveExchangeValue(
			@PathVariable(value = "to", required = true) String to) {
////try {
//	try {
//		Thread.sleep(20000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
		List<ExchangeValue> exchangeValue = exchangeValueRepository.findByTo(to);
		System.out.println(exchangeValue);
		//exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		return exchangeValue;
	}

	@RequestMapping("/")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
