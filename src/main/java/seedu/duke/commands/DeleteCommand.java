package seedu.duke.commands;

import seedu.duke.DukeException;
import seedu.duke.common.Messages;
import seedu.duke.task.Task;
import seedu.duke.task.TaskList;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.ArrayList;

/**
 * Deletes a Task identified by its index in the task list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the task listing.\n"
            + "     Parameters: INDEX\n"
            + "     Example: " + COMMAND_WORD + " 1";

    private boolean hasPriorityValue = false;
    private boolean hasCategoryValue = false;
    private String categoryValue = "";
    private int index;
    private int priorityIndex;
    private static final Logger deleteCommandLogger = Logger.getLogger(DeleteCommand.class.getName());


    public DeleteCommand(int index) { // for single delete
        assert index > 0 : "Task number should be greater than 0";
        this.hasPriorityValue = false;
        this.hasCategoryValue = false;
        this.index = index;
    }

    public DeleteCommand(String inputValue) {  // for both priority + category
        if (inputValue.startsWith("p")) {  // for priority
            this.hasPriorityValue = true;
            this.priorityIndex = Integer.parseInt(inputValue.substring(2));
            //deleteCommandLogger.log(Level.WARNING, "Priority should be non-negative");
        } else { // for category
            this.hasCategoryValue = true;
            this.categoryValue = inputValue.substring(2);
            //deleteCommandLogger.log(Level.WARNING, "Priority should be non-negative");

        }
    }

    @Override
    public void execute(TaskList tasks) throws DukeException {
        ArrayList<Task> prioritytaskDeleted = new ArrayList<Task>();
        ArrayList<Task> categorytaskDeleted = new ArrayList<Task>();

        if (hasPriorityValue) {
            if (priorityIndex < 0) {
                throw new DukeException(Messages.EXCEPTION_INVALID_PRIORITY);
            }
            for (int i = tasks.size() - 1; i >= 0; i--) {
                if (tasks.get(i).getPriority() == priorityIndex) {
                    prioritytaskDeleted.add(tasks.get(i));
                    tasks.deletePriorityOrCategoryTask(i);
                }
            }
            if (prioritytaskDeleted.isEmpty()) {
                throw new DukeException(Messages.EXCEPTION_INVALID_PRIORITY);
            }
            tasks.displayDeletedPriorityOrCategoryTask(prioritytaskDeleted); //if priority exists
        } else if (hasCategoryValue) {
            for (int i = tasks.size() - 1; i >= 0; i--) {
                if (tasks.get(i).getCategory() == null) {
                    continue; //ignore if category is not set for the task
                }
                if (tasks.get(i).getCategory().equals(categoryValue)) {
                    prioritytaskDeleted.add(tasks.get(i));
                    tasks.deletePriorityOrCategoryTask(i);
                }
            }

            if (prioritytaskDeleted.isEmpty()) {
                throw new DukeException(Messages.EXCEPTION_CATEGORY_NOT_FOUND);
            }
            tasks.displayDeletedPriorityOrCategoryTask(prioritytaskDeleted);
        } else { // single deletion
            tasks.deleteTask(index);
        }
    }
}
