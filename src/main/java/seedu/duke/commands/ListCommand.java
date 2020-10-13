package seedu.duke.commands;

import seedu.duke.DukeException;
import seedu.duke.common.Messages;
import seedu.duke.task.Task;
import seedu.duke.task.TaskList;

import java.util.ArrayList;

/**
 * Lists all tasks in the task list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all tasks in the task list as a list with index numbers.\n"
            + "     Example: " + COMMAND_WORD;
    private final boolean hasPriority;
    private int priority;
    public static int listSize;
    public static int newListSize;

    public ListCommand() {
        this.hasPriority = false;
    }

    public ListCommand(int priority) {
        this.hasPriority = true;
        this.priority = priority;
    }

    @Override
    public void execute(TaskList tasks) throws DukeException {
        ArrayList<Task> newTasks = new ArrayList<Task>();
        listSize = tasks.size();
        if (hasPriority) {
            if (priority < 0) {
                throw new DukeException(Messages.EXCEPTION_INVALID_PRIORITY);
            }
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getPriority() == priority) { //if the task matches the same priority input by user
                    newTasks.add(tasks.get(i)); // add the task into arraylist (newTasks)
                }
            }
            TaskList newTaskList = new TaskList(newTasks); //created a new object called newTaskList
            newTaskList.listTask(priority);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                newTasks.add(tasks.get(i));
            }
            TaskList newTaskList = new TaskList(newTasks);
            newTaskList.listTask();
        }
        newListSize = newTasks.size();
    }

    public int getSize(Boolean isNew) {
        if (isNew) {
            return newListSize;
        } else {
            return listSize;
        }
    }
}