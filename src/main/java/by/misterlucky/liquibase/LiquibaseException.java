package by.misterlucky.liquibase;

public class LiquibaseException extends RuntimeException{

	/**
	 * Provides exception that can be thrown during running liquibase runner
	 */
	private static final long serialVersionUID = 1L;

	public LiquibaseException() {
	}

	public LiquibaseException(Exception e) {
		super(e);
	}

	public LiquibaseException(String message) {
		super(message);
	}

}
