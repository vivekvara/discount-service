package com.service.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.service.vo.DiscountVO;

@Service
public class DiscountServiceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscountServiceService.class);
	private static Map<String, List<DiscountVO>> discountGroup = null;

	@PostConstruct
	public void init() {
		File fileName = null;
		try {
			fileName = ResourceUtils.getFile("classpath:discount.csv");
			try (Stream<String> stream = Files.lines(fileName.toPath())) {
				//read CSV and group them by usertype and sorted by start range
				discountGroup = stream.skip(1).map(splitter).sorted(Comparator.comparingInt(DiscountVO::getStart))
						.collect(Collectors.groupingBy(DiscountVO::getUserType));
				LOGGER.debug("Discount Groups:", discountGroup.toString());
			} catch (IOException e) {
				LOGGER.error("could not parse file", e);
			}
		} catch (FileNotFoundException ex) {
			LOGGER.error("discount.csv is not found at classpath", ex);
		}
	}

	public int calculateDiscount(String userType, int amount) {
		LOGGER.info("method:{}, request[usertype:{},amount:{}]", "calculateDiscount", userType, amount);
		int discount = 0;
		int discountedAmount = 0;
		if (discountGroup.containsKey(userType)) {
			List<DiscountVO> discountVOs = discountGroup.get(userType);
			
			int descrease = amount;
			for (DiscountVO discountVO : discountVOs) {
				if(descrease > 0) {
					// read first discount of sorted group
					if(discountVO.getStart() == 0) {
						discount = (int) (discount + (discountVO.getStart() * ((double)discountVO.getDiscountPerc()/100.0)));
						descrease = descrease - discountVO.getEnd();
					}else if(descrease <= discountVO.getStart()) {
						// read last discount of sorted group
						discount = (int) (discount + ((descrease) * ((double)discountVO.getDiscountPerc()/100.0)));
						descrease = descrease - descrease;
					}else {
						// read other discounts
						discount = (int) (discount + ((discountVO.getEnd()-discountVO.getStart()) * ((double)discountVO.getDiscountPerc()/100.0)));
						descrease = descrease - ((discountVO.getEnd()-discountVO.getStart()));
					}
				} else {
					break;
				}
			}
			LOGGER.debug("final discount:", discount);
		}
		discountedAmount = amount - discount;
		LOGGER.info("method:{}, response[discountedamount:{}]", "calculateDiscount", discountedAmount);
		return discountedAmount;
	}

	private Function<String, DiscountVO> splitter = new Function<String, DiscountVO>() {
		public DiscountVO apply(String s) {
			String[] arg = s.split(",");
			return new DiscountVO(arg[0], Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
		}
	};

}
