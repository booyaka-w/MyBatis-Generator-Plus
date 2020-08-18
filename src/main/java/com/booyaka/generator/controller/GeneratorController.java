package com.booyaka.generator.controller;

import java.io.File;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booyaka.generator.entity.GeneratorEntity;

@RestController
public class GeneratorController {

	@PostMapping("/generator")
	public String generator(GeneratorEntity entity) {
		mkdirs(entity);
		return "table-details.html";
	}

	private void mkdirs(GeneratorEntity entity) {
		File file = new File(entity.getModelPath());
		file.mkdirs();
		file = new File(entity.getDaoPath());
		file.mkdirs();
		file = new File(entity.getMapperPath());
		file.mkdirs();
		file = new File(entity.getServicePath());
		file.mkdirs();
		file = new File(entity.getServiceImplPath());
		file.mkdirs();
	}
}
