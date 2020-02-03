import java.util.Scanner;

public class Duke {

    public static final int MAX_TASK = 100;

    public static void main(String[] args) {
        printWelcomeMessage();
        boolean isExit = false;
        Task[] list = new Task[MAX_TASK];
        int itemCount = 0;
        do {
            Scanner in = new Scanner(System.in);
            String input = in.nextLine(); // Get string input
            if(input.equals("bye")) { // Exit
                printExitMessage();
                isExit = true;
            } else if(input.equals("list")) { // List tasks
                printBorder();
                System.out.println("    Here are the tasks in your list:");
                for(int i = 0; i < itemCount; ++i) { // Print list of tasks
                    System.out.println("    " + (i + 1) + ". " + list[i].toString() );
                }
                printBorder();
            } else if(input.startsWith("done") ) { // Mark task as done
                printBorder();
                System.out.println("    Nice! I've marked this task as done: ");
                String doneTask = input.substring(5, input.length() );
                int taskIndex = Integer.parseInt(doneTask) - 1;
                list[taskIndex].setDone(true);
                System.out.println("    " + list[taskIndex].toString() ); // Print task marked as done
                printBorder();
            } else if(input.startsWith("deadline")) { // Deadline
                int dividerIndex = input.indexOf("/by");
                String deadlineTask = input.substring(9, (dividerIndex - 1));
                String deadlineBy = input.substring((dividerIndex + 4), input.length() );
                list[itemCount] = new Deadline(deadlineTask, deadlineBy);
                printBorder();
                printTaskAdded(list, itemCount);
                itemCount++;
                printListCount(itemCount);
                printBorder();
            } else if(input.startsWith("event")) { // Event
                int dividerIndex = input.indexOf("/at");
                String eventTask = input.substring(6, (dividerIndex - 1));
                String eventAt = input.substring((dividerIndex + 4), input.length());
                list[itemCount] = new Event(eventTask, eventAt);
                printBorder();
                printTaskAdded(list, itemCount);
                itemCount++;
                printListCount(itemCount);
                printBorder();
            } else if(input.startsWith("todo")) { // ToDo
                String toDoTask = input.substring(5, input.length() );
                list[itemCount] = new ToDo(toDoTask);
                printBorder();
                printTaskAdded(list, itemCount);
                itemCount++;
                printListCount(itemCount);
                printBorder();
            }
            else { // Add task
                printBorder();
                list[itemCount] = new Task(input);
                itemCount++;
                System.out.println("    added: " + input); // Print task added
                printBorder();
            }
        } while (isExit == false);
    }

    private static void printListCount(int itemCount) {
        System.out.println("    Now you have " + itemCount + " tasks in the list."); // Print list count
    }

    private static void printTaskAdded(Task[] list, int itemCount) {
        System.out.println("    Got it. I've added this task: " );
        System.out.println("      " + list[itemCount].toString() ); // Print task info
    }

    private static void printBorder() {
        System.out.println("    ____________________________________________________________");
    }

    private static void printExitMessage() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(" _                \n"
                + "| |               \n"
                + "| |__  _   _  ___ \n"
                + "| '_ \\| | | |/ _ \\\n"
                + "| |_) | |_| |  __/\n"
                + "|_.__/ \\__, |\\___|\n"
                + "        __/ |     \n"
                + "       |___/      \n");
    }

    private static void printWelcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What can I do for you?");
    }
}