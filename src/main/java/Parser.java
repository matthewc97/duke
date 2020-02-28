public class Parser {

    public static final String INVALID_TASK_INDEX = "The task index you've entered is invalid.";
    public static final String EMPTY_TASK_INDEX = "The task index cannot be empty.";
    public static final String EMPTY_TODO = "The description of the todo cannot be empty.";
    public static final String EMPTY_EVENT = "The description and/or date/time of the event cannot be empty.";
    public static final String EMPTY_DEADLINE = "The description and/or date/time of the deadline cannot be empty.";
    public static final String INVALID_EVENT = "The description of an event must be in this format: \n\t event <task name> /at <date/time>";
    public static final String INVALID_DEADLINE = "The description of a deadline must be in this format: \n\t deadline <task name> /by <date/time>";
    public static final String EMPTY_FIND = "The keyword for the search cannot be empty.";

    public int parseTaskIndex(String[] words, int taskListSize) throws DukeException {
        String taskString = words[1];
        int taskIndex = Integer.parseInt(taskString) - 1;
        if ((taskIndex < 0) || (taskIndex >= taskListSize)) {
            throw new DukeException(INVALID_TASK_INDEX);
        }
        return taskIndex;
    }

    public String parseToDo(String[] words) throws DukeException {
        if (words.length < 2 || words[1].replaceAll("\\s+","").length() == 0) {
            throw new DukeException(EMPTY_TODO);
        }
        return words[1];
    }

    public String[] parseEvent(String[] words) throws DukeException {
        if ((words.length < 2) || !words[1].contains(" /at ")) {
            throw new DukeException(INVALID_EVENT);
        }
        String[] eventWords = words[1].split(" /at ", 2); // Split task and date/time
        if (eventWords[0].replaceAll("\\s+","").length() == 0 || eventWords[1].replaceAll("\\s+","").length() == 0) {
            throw new DukeException(EMPTY_EVENT);
        }
        return eventWords;
    }

    public String[] parseDeadline(String[] words) throws DukeException{
        if ((words.length < 2) || !words[1].contains(" /by ")) {
            throw new DukeException(INVALID_DEADLINE);
        }
        String[] deadlineWords = words[1].split(" /by ", 2); // Split task and date/time
        if (deadlineWords[0].replaceAll("\\s+","").length() == 0 || deadlineWords[1].replaceAll("\\s+","").length() == 0) {
            throw new DukeException(EMPTY_DEADLINE);
        }

        return deadlineWords;
    }

    public void parseDone(String[] words) throws DukeException {
        if (words.length < 2) {
            throw new DukeException(EMPTY_TASK_INDEX);
        }
    }

    public void parseDelete(String[] words) throws DukeException {
        if (words.length < 2) {
            throw new DukeException(EMPTY_TASK_INDEX);
        }
    }

    public String parseFind(String[] words) throws DukeException {
        if (words.length < 2) {
            throw new DukeException(EMPTY_FIND);
        }
        return words[1];
    }
}
