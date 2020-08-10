package com.booyaka.generator.entity;

public class GeneratorEntity {

	private String rootPath;

	private String extendsClassPath;

	private String modelPath;

	private String daoPath;

	private String mapperPath;

	private String servicePath;

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getExtendsClassPath() {
		return extendsClassPath;
	}

	public void setExtendsClassPath(String extendsClassPath) {
		this.extendsClassPath = extendsClassPath;
	}

	public String getModelPath() {
		return rootPath + modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getDaoPath() {
		return rootPath + daoPath;
	}

	public void setDaoPath(String daoPath) {
		this.daoPath = daoPath;
	}

	public String getMapperPath() {
		return rootPath + mapperPath;
	}

	public void setMapperPath(String mapperPath) {
		this.mapperPath = mapperPath;
	}

	public String getServicePath() {
		return rootPath + servicePath;
	}

	public String getServiceImplPath() {
		return rootPath + servicePath + "/impl";
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

}
