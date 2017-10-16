package ir.technopedia.projectmanager.activity.project_detail;

import android.support.v7.widget.SwitchCompat;
import android.widget.EditText;

import java.util.Calendar;
import java.util.List;

import ir.technopedia.projectmanager.model.Project;
import ir.technopedia.projectmanager.model.Task;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class ProjectDetailActivityPresenter implements ProjectDetailActivityContract.Presenter {

    private ProjectDetailActivityContract.View view;
    Project project;

    public ProjectDetailActivityPresenter() {
    }

    @Override
    public void setView(ProjectDetailActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void loadProject(long project_id) {
        project = Project.findById(Project.class, project_id);

        List<Task> doneTasks = Task.find(Task.class, "projectid = ? and state = 2", project.getId() + "");
        List<Task> totalTasks = Task.find(Task.class, "projectid = ?", project.getId() + "");
        int done = 0, total = 0;
        for (int i = 0; i < doneTasks.size(); i++) {
            done += doneTasks.get(i).getValue();
        }
        for (int i = 0; i < totalTasks.size(); i++) {
            total += totalTasks.get(i).getValue();
        }
        project.setDone(done);
        project.setTotal(total);
        project.setPercent();

        view.handleProject(project);
    }

    @Override
    public void deleteProject() {
        project.delete();
    }

    @Override
    public void setEditProjectData(EditText project_name, SwitchCompat is_archived) {
        project_name.setText(project.getName());
        if (project.getState() == 0) {
            is_archived.setChecked(false);
        } else {
            is_archived.setChecked(true);
        }
    }

    @Override
    public void saveTask(String task_name, String task_value) {
        Task task = new Task();
        task.setName(task_name);
        task.setValue(Integer.valueOf(task_value));
        task.setState(0);
        task.setDate(Calendar.getInstance().getTime().toString());
        task.setProject_id(project.getId());
        task.save();
    }

    @Override
    public void saveProject(EditText project_name, SwitchCompat is_archived) {
        if (is_archived.isChecked()) {
            project.setState(1);
        } else {
            project.setState(0);
        }

        project.setName(project_name.getText().toString());

        project.save();
    }
}
