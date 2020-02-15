package by.misterlucky.liquibase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class JDBCConnector {
	
	protected JDBCConnector(){}
	
	private String driver;
	private String url;
	private String login;
	private String password;
	private Connection connection;
	
	protected boolean init(){
		try {
			Logger.log("Driver: "+driver);
			Class.forName(driver);
			connection = (Connection) DriverManager.getConnection(url, login, password);
		} catch (Exception e) {
			Logger.log(e.getMessage());
			throw new LiquibaseException(e);
		}
		Logger.log("JDBCConnector have been initialized successfully");
		return true;
	}
	
	protected Set<ChangeSet> fetchExecutedChangeSets(){
		java.sql.Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			Logger.log("Exception during create statement: "+e.getMessage());
			throw new LiquibaseException(e);
		}
		ResultSet rs = null;
		boolean isTableExists = true;
		try{
			rs = statement.executeQuery(LiquibaseDefaultVariables.CHANGELOG_REQUEST);
		}catch(SQLException e){
			Logger.log("DATABASECHANGELOG does not exist");
			isTableExists = false;
			try {
				statement.close();
			} catch (SQLException e1) {
				Logger.log("Exception during closing statement");
				throw new LiquibaseException(e);
			}
		}
		if(isTableExists){
			Logger.log("DATABASECHANGELOG exists");
			Set<ChangeSet>set = new HashSet<>();
			try {
				while(rs.next()){
					String name = rs.getString("NAME");
					String author = rs.getString("AUTHOR");
					ChangeSet chSet = new ChangeSet(name,author,null,null);
					set.add(chSet);
				}
				return set;
			} catch (SQLException e) {
				Logger.log("Exception during reading changesets from database");
				throw new LiquibaseException(e);
			}
		}else{
			try {
				statement = connection.createStatement();
				Logger.log("create DATABASECHANGELOG");
				statement.execute(LiquibaseDefaultVariables.CHANGELOG_TABLE_SCRIPT);
				} catch (SQLException e) {
					Logger.log("Exception during CREATE DATABASECHANGELOG");
				throw new LiquibaseException();
			}
			return new HashSet<>();
		}
	}

	protected void execute(ChangeSet chSet) throws SQLException{
		Logger.log("EXECUTE SCRIPT: "+chSet.getScript());
		java.sql.Statement statement = null;
		statement = connection.createStatement();
		String script = insertScript(chSet);
		statement.execute(chSet.getScript());
		statement.execute(script);
		statement.close();
		Logger.log("script executed");
	}
	
	public String insertScript(ChangeSet chSet){
		return "INSERT INTO DATABASECHANGELOG (NAME,AUTHOR,DATEEXECUTED,SCRIPT) VALUES ('"+chSet.getName()+"','"+chSet.getAuthor()+"','"+new Date(System.currentTimeMillis()).toString()+"', '"+chSet.getScript()+"');";
	}

	/**
	 * @return the driver
	 */
	protected String getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	protected void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * @return the url
	 */
	protected String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	protected void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the login
	 */
	protected String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	protected void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	protected String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	protected void setPassword(String password) {
		this.password = password;
	}

}
