package com.booyaka.generator.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.booyaka.generator.entity.GeneratorEntity;

@Controller
public class GeneratorController {

	@PostMapping("/generator")
	public void generator(GeneratorEntity entity) {
		boolean flag = mkdirs(entity);
		if (flag) {

		}

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
