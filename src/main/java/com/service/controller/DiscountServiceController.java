package com.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.service.DiscountServiceService;

@RestController
@RequestMapping("/")
public class DiscountServiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscountServiceController.class);

	@Autowired
	private DiscountServiceService discountService;

	@GetMapping("/discount/{userType}/{amount}")
	public @ResponseBody int discount(@PathVariable String userType, @PathVariable int amount) {
		LOGGER.info("method:{}, request[usertype:{},amount:{}]", "discount", userType, amount);

		if (amount <= 0) {
			return amount;
		}

		int discountedAmount = discountService.calculateDiscount(userType, amount);

		LOGGER.info("method:{}, response[discountedamount:{}]", "discount", discountedAmount);
		return discountedAmount;
	}

}
