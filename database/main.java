package database;

public class main {

	public static void main(String[] args) {
		CommandLine commandLine = new CommandLine();
		if (commandLine.startSession() && commandLine.execute()) {
			commandLine.endSession();
		}

	}

}
