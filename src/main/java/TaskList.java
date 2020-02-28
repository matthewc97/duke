import Tasks.Task;
import Tasks.ToDo;
import Tasks.Deadline;
import Tasks.Event;

import java.util.ArrayList;

public class TaskList {
    public static final String EMPTY_LIST = "Your task list is empty! Try adding a task first!";
    public static final String INVALID_TASK_INDEX = "The task index you've entered is invalid.";
    public static final String KEYWORD_NOT_FOUND = "There are no tasks found in the list that match your keyword.";

    private ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> savedList) {
        this.taskList = savedList;
    }

    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }

    public int getTaskListSize() {
        return taskList.size();
    }

    public void listCommand() throws DukeException {
        if (taskList.isEmpty()) {
            throw new DukeException(EMPTY_LIST);
        }
    }

    public void markDone(int taskIndex) throws DukeException, NumberFormatException {
        if ((taskIndex < 0) || (taskIndex >= taskList.size())) {
            throw new DukeException(INVALID_TASK_INDEX);
        }
        taskList.get(taskIndex).setDone(true);
    }

    public void deleteTask(int taskIndex) throws DukeException, NumberFormatException {
        if ((taskIndex < 0) || (taskIndex >= taskList.size())) {
            throw new DukeException(INVALID_TASK_INDEX);
        }

        taskList.remove(taskList.get(taskIndex));
    }

    public void addToDo(String toDoTask) {
        taskList.add(new ToDo(toDoTask));
    }

    public void addEvent(String[] eventWords) {
        String eventTask = eventWords[0];
        String eventAt = eventWords[1];
        taskList.add(new Event(eventTask, eventAt));
    }

    public void addDeadline(String[] deadlineWords) {
        String deadlineTask = deadlineWords[0];
        String deadlineBy = deadlineWords[1];
        taskList.add(new Deadline(deadlineTask, deadlineBy));
    }

    public ArrayList<Task> findTask(String findKeywords) throws DukeException {
        ArrayList<Task> foundList = new ArrayList<>();
        for (Task t : taskList) {
            if(t.getTask().toLowerCase().contains(findKeywords.toLowerCase())) {
                foundList.add(t);
            }
        }
        if (foundList.isEmpty()) {
            throw new DukeException(KEYWORD_NOT_FOUND);
        }
        return foundList;
    }
}
