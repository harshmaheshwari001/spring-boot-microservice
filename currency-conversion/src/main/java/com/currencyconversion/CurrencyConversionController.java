package com.currencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	@Autowired
	private CurrencyConversionServiceProxy proxy;
	@Autowired
	private Environment enviroment;
	/**
	 * Calling Rest API using Spring Rest Template
	 * @param from
	 * @param to
	 * @param quantity
	 * @return
	 */
	@GetMapping("/currency-exchange/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean converCurrency(@PathVariable(value = "from", required = true) String from,
			@PathVariable(value = "to", required = true) String to,
			@PathVariable(value = "quantity", required = true) BigDecimal quantity) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);
		CurrencyConversionBean body = responseEntity.getBody();
		//return body;
		return new CurrencyConversionBean(body.getId(), body.getFrom(), body.getTo(), body.getConversionMultiple(),
				quantity, body.getConversionMultiple().multiply(quantity), body.getPort());
	}
	
	/**
	 * Calling rest Api using feign proxy implemenation
	 * @param from
	 * @param to
	 * @param quantity
	 * @return
	 */
	@GetMapping("/currency-exchange-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean converCurrencyUsingFeigh(@PathVariable(value = "from", required = true) String from,
			@PathVariable(value = "to", required = true) String to,
			@PathVariable(value = "quantity", required = true) BigDecimal quantity) {

		
		CurrencyConversionBean body = proxy.retrieveExchangeValue(from, to);
		//body.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
		//return body;
		return new CurrencyConversionBean(body.getId(), body.getFrom(), body.getTo(), body.getConversionMultiple(),
				quantity, body.getConversionMultiple().multiply(quantity), body.getPort());
	}
}
