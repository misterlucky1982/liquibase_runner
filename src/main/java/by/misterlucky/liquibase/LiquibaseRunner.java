package by.misterlucky.liquibase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class LiquibaseRunner {
	
	
	private File file;
	private JDBCConnector connector = new JDBCConnector();
	private Set<ChangeSet>executedScripts;
	private Set<String>executionRequirenments = new HashSet<>();
	private List<ChangeSet> changeSetList;
	
	public void applyExequtionLevel(String requirenmentLevel){
		this.executionRequirenments.add(requirenmentLevel);
	}
	
	public void applyUrl(String url){
		this.connector.setUrl(url);
	}
	
	public void applyDriver(String driver){
		this.connector.setDriver(driver);
	}
	
	public void applyLogin(String login){
		this.connector.setLogin(login);
	}
	
	public void applyPassword(String password){
		this.connector.setPassword(password);
	}
	
	public void applyFile(String path){
		this.file = new File(path);
	}
	
	public void init(){
		if(connector.getDriver()==null)connector.setDriver(LiquibaseDefaultVariables.POSTGRES_DRIVER);
		this.connector.init();
		this.executedScripts = this.connector.fetchExecutedChangeSets();
		try {
			this.changeSetList = XMLUtils.changeSets(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new LiquibaseException(e);
		}
	}

	public void runScripts(){
		int count = 0;
		Logger.log("starting run scripts...");
		for(ChangeSet ch:changeSetList){
			if(!this.executedScripts.contains(ch)&&this.executionRequirenments.contains(ch.getRequirenmentLevel())){
				try {
					this.connector.execute(ch);
					count++;
				} catch (SQLException e) {
					Logger.log("Exception during execution: "+ch.toString());
					throw new LiquibaseException(e);
				}
			}
		}
		Logger.log("Script running has been finished successfulle. Number of script: "+count);
	}
}
