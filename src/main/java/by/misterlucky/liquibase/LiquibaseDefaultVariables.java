package by.misterlucky.liquibase;

public class LiquibaseDefaultVariables {
	
	private LiquibaseDefaultVariables(){}
	
	protected static final String POSTGRES_DRIVER = "org.postgresql.Driver";
	protected static final String CHANGELOG_TABLE = "DATABASECHANGELOG";
	protected static final String CHANGELOG_TABLE_SCRIPT = "CREATE TABLE DATABASECHANGELOG (ID SERIAL, NAME CHARACTER VARYING NOT NULL, AUTHOR CHARACTER VARYING NOT NULL, DATEEXECUTED DATE NOT NULL, SCRIPT TEXT, PRIMARY KEY(ID));";
	protected static final String CHANGELOG_REQUEST = "SELECT NAME,AUTHOR FROM DATABASECHANGELOG;";
	public static final String PRPODUCTION_REQUIRENMENT_LEVEL = "production";
	public static final String DEVELOPMENT_REQUIRENMENT_LEVEL = "development";
	public static final String TEST_REQUIRENMENT_LEVEL = "test";
	

}
