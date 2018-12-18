package com.forex;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository  extends JpaRepository<ExchangeValue, Long>{

	ExchangeValue findByFromAndTo(String from,String to);
	List<ExchangeValue> findByTo(String to);
}
