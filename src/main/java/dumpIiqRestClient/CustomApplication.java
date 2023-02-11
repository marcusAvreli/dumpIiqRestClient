package dumpIiqRestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)

public class CustomApplication {
	private static final Logger logger = LoggerFactory.getLogger(CustomApplication.class);
	
	

	
	private String id;
	private String name;
	private String displayName;
	private String description;
	
	
	
	public CustomApplication() {
		
	}
	
	
	public CustomApplication(String id,String name,String displyName,String description) {
		this.id = id;
		this.name = name;
		this.displayName = displyName;
		//this.id=id;
		this.description=description;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
