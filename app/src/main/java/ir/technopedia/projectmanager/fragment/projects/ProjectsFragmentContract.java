package ir.technopedia.projectmanager.fragment.projects;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

import ir.technopedia.projectmanager.model.Project;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class ProjectsFragmentContract {

    public interface Presenter {
        void setView(ProjectsFragmentContract.View view);

        void loadProjects(int state);

        void saveProject(EditText project_name);

        void handleClick(Context context, Project project);
    }

    public interface View {
        void setProjects(List<Project> projects);

        void showEmptyState();

        void showAddProjectDialog();
    }
}
