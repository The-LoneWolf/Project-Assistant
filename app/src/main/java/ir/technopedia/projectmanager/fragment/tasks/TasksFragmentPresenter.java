package ir.technopedia.projectmanager.fragment.tasks;

import android.content.Context;

import java.util.List;

import ir.technopedia.projectmanager.model.Task;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class TasksFragmentPresenter implements TasksFragmentContract.Presenter {

    TasksFragmentContract.View view;
    int state = 0;
    long project_id = 0l;

    @Override
    public void setView(TasksFragmentContract.View view, int state, long project_id) {
        this.view = view;
        this.state = state;
        this.project_id = project_id;
    }

    @Override
    public void loadTasks() {
        List<Task> tasks = Task.find(Task.class, "state = ? and projectid = ?", this.state + "", this.project_id + "");
        if (tasks.size() <= 0) {
            view.showEmptyState();
        } else {
            view.setTasks(tasks);
        }
    }

    @Override
    public void handleDeleteClick(Task task) {
        task.delete();
        this.loadTasks();
    }

    @Override
    public void handleEditClick(Task task, String name, String value, int state) {
        task.setName(name);
        task.setValue(Integer.valueOf(value));
        task.setState(state);
        task.save();
        this.loadTasks();
    }
}
