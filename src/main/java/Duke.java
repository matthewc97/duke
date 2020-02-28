import Tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static final String INVALID_COMMAND = "I'm sorry, but I don't know what that means :-( \n\t Type 'help' for the list of commands available.";
    public static final String OOPS = "\t ☹ OOPS!!! ";
    public static final String FILE_NOT_FOUND = "File is not found!";
    public static final String INVALID_NUMBER = "You entered an invalid number!";

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private Parser parser;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();

        try {
            tasks = new TaskList(storage.loadFromFile());
        } catch (IOException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    public void run() throws IOException {
        ui.printWelcomeMessage();
        storage.loadFromFile();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine(); // Get string input

        while (!input.equals("bye")) {
            String[] words = input.split(" ", 2); // Split command from rest of sentence
            try {
                manageCommand(words);
            } catch (DukeException e) {
                ui.printErrorMessage(OOPS + e.getMessage());
            } catch (NumberFormatException e) {
                ui.printErrorMessage(OOPS + INVALID_NUMBER);
            }

            try {
                storage.writeToFile(tasks.getTaskList());
            } catch (IOException e) {
                ui.printErrorMessage(OOPS + FILE_NOT_FOUND);
            }
            input = in.nextLine(); // Get string input
        }
        ui.printExitMessage(); // Exit
        in.close();
    }

    public static void main(String[] args) throws IOException {
        new Duke("data/duke.txt").run();
    }

    private void manageCommand(String[] words) throws DukeException {
        ArrayList<Task> taskList = tasks.getTaskList();
        int taskListSize = 0;
        int taskIndex = 0;
        switch(words[0]) {
            case "help": // Shows help guide
                ui.printHelpMessage();
                break;
            case "list":  // List tasks
                tasks.listCommand();
                ui.printListMessage(taskList);
                break;
            case "done": // Mark task as done
                parser.parseDone(words);
                taskListSize = tasks.getTaskListSize();
                taskIndex = parser.parseTaskIndex(words, taskListSize);
                tasks.markDone(taskIndex);
                ui.printDoneMessage(taskList, taskIndex);
                break;
            case "deadline": // Add deadline
                String[] deadlineWords = parser.parseDeadline(words);
                tasks.addDeadline(deadlineWords);
                ui.printBorder();
                ui.printTaskAdded(taskList);
                ui.printListCount(taskList);
                ui.printBorder();
                break;
            case "event": // Add event
                String[] eventWords = parser.parseEvent(words);
                tasks.addEvent(eventWords);
                ui.printBorder();
                ui.printTaskAdded(taskList);
                ui.printListCount(taskList);
                ui.printBorder();
                break;
            case "todo": // Add to-do
                String toDoTask = parser.parseToDo(words);
                tasks.addToDo(toDoTask);
                ui.printBorder();
                ui.printTaskAdded(taskList);
                ui.printListCount(taskList);
                ui.printBorder();
                break;
            case "delete": // Delete task
                parser.parseDelete(words);
                taskListSize = tasks.getTaskListSize();
                taskIndex = parser.parseTaskIndex(words, taskListSize);
                ui.printBorder();
                ui.printDeleteMessage(taskList, taskIndex);
                tasks.deleteTask(taskIndex);
                ui.printListCount(taskList);
                ui.printBorder();
                break;
            case "find":
                String findKeywords = parser.parseFind(words);
                ArrayList<Task> foundList = tasks.findTask(findKeywords);
                ui.printFindResults(foundList);
                break;
            default:
                throw new DukeException(INVALID_COMMAND);
        }
    }

}