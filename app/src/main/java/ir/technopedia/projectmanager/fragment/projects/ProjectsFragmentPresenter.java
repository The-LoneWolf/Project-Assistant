package ir.technopedia.projectmanager.fragment.projects;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import java.util.List;

import ir.technopedia.projectmanager.activity.project_detail.ProjectDetailActivity;
import ir.technopedia.projectmanager.model.Project;
import ir.technopedia.projectmanager.model.Task;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class ProjectsFragmentPresenter implements ProjectsFragmentContract.Presenter {

    ProjectsFragmentContract.View view;

    @Override
    public void setView(ProjectsFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void loadProjects(int state) {
//        List<Project> projectList = Project.listAll(Project.class);
        List<Project> projectList = Project.find(Project.class, "state = ?", state + "");
        if (projectList.size() <= 0) {
            view.showEmptyState();
        } else {
            view.setProjects(projectList);
        }
    }

    @Override
    public void saveProject(EditText project_name) {
        Project project = new Project();
        project.setName(project_name.getText().toString());
        project.setState(0);
        project.save();
    }

    @Override
    public void handleClick(Context context, Project project) {
        Intent intent = new Intent(context, ProjectDetailActivity.class);
        intent.putExtra("project_id", project.getId());
        context.startActivity(intent);
    }
}
