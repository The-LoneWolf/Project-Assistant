package ir.technopedia.projectmanager.fragment.tasks;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

import ir.technopedia.projectmanager.model.Project;
import ir.technopedia.projectmanager.model.Task;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class TasksFragmentContract {

    public interface Presenter {
        void setView(TasksFragmentContract.View view, int state, long project_id);

        void loadTasks();

        void handleDeleteClick(Task task);

        void handleEditClick(Task task, String name, String value, int state);
    }

    public interface View {
        void setTasks(List<Task> tasks);

        void showEmptyState();

        void showEditTaskDialog(Task task);

        void update();
    }
}
