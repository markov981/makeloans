package com.libertymutual.goforcode.makeloans.services;

import org.springframework.stereotype.Service;

@Service
public class RandomNumberGenerator {
	
	public long nextRandomNumber() {
		return Math.round(Math.random() * 1000000000);
	}

}
