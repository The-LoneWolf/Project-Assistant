package ir.technopedia.projectmanager.activity.project_detail;

import android.support.v7.widget.SwitchCompat;
import android.widget.EditText;

import ir.technopedia.projectmanager.model.Project;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public interface ProjectDetailActivityContract {

    public interface Presenter {
        void setView(ProjectDetailActivityContract.View view);

        void loadProject(long project_id);

        void deleteProject();

        void setEditProjectData(EditText project_name, SwitchCompat is_archived);

        void saveTask(String task_name, String task_value);

        void saveProject(EditText project_name, SwitchCompat is_archived);
    }

    public interface View {
        void handleProject(Project project);

        void showToast(String str);

        void showAddTaskDialog();

        void showEditProjectDialog();
    }
}
