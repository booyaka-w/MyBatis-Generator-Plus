package com.booyaka.generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyBatisGeneratorPlusController {

	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
