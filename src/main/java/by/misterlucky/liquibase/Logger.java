package by.misterlucky.liquibase;

public class Logger {
	protected static ILogger LOGGER = new ILogger() {
		@Override
		public void log(String message) {
			System.out.println(message);
		}
	};
	protected static void log(String message){
		LOGGER.log(message);
	}
}
