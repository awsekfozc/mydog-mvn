package io.mycat.mydog.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import freemarker.cache.TemplateLoader;
import io.mycat.mydog.xml.XmlConfigLoader;

public class MydogTemplateLoader implements TemplateLoader {

	private File outputDirectory;

	private final ResourceLoader resourceLoader;

	private final String templateLoaderPath;

	public MydogTemplateLoader(ResourceLoader resourceLoader, String templateLoaderPath ) {
		this.resourceLoader = resourceLoader;
		if (!templateLoaderPath.endsWith("/")) {
			templateLoaderPath += "/";
		}
		this.templateLoaderPath = templateLoaderPath;
		
		this.outputDirectory = new File(XmlConfigLoader.getConfigLoader().getOutputDirectory(), "target/classes/");
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		Resource resource = this.resourceLoader.getResource(this.templateLoaderPath + name);
		if (!resource.exists()) {
			try {
				resource = new FileSystemResource(String.format("%s/%s", outputDirectory.getAbsolutePath(), name));
				if (!resource.exists()) {
					return null;
				}
				return resource;
			} catch (Exception e) {
			}
			return null;
		}

		return resource;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		Resource resource = (Resource) templateSource;
		try {
			return new InputStreamReader(resource.getInputStream(), "UTF-8");
		} catch (IOException ex) {
			throw ex;
		}
	}

	@Override
	public long getLastModified(Object templateSource) {
		Resource resource = (Resource) templateSource;
		try {
			return resource.lastModified();
		} catch (IOException ex) {

			return -1;
		}
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
	}

}