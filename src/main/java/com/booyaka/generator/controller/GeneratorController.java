package com.booyaka.generator.controller;

import java.io.File;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booyaka.generator.entity.GeneratorEntity;

@RestController
public class GeneratorController {

	@PostMapping("/generator")
	public String generator(GeneratorEntity entity) {
		boolean flag = mkdirs(entity);
		if (flag) {
			System.err.println(flag);
		}
		return "table-details.html";
	}

	private boolean mkdirs(GeneratorEntity entity) {
		boolean flag = false;
		File file = new File(entity.getModelPath());
		flag = file.mkdirs();
		if (flag) {
			file = new File(entity.getDaoPath());
			flag = file.mkdirs();
		}
		if (flag) {
			file = new File(entity.getMapperPath());
			flag = file.mkdirs();
		}
		if (flag) {
			file = new File(entity.getServicePath());
			flag = file.mkdirs();
		}
		if (flag) {
			file = new File(entity.getServiceImplPath());
			flag = file.mkdirs();
		}
		return flag;
	}
}
