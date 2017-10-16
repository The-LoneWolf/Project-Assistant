package ir.technopedia.projectmanager.fragment.projects;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.technopedia.projectmanager.R;
import ir.technopedia.projectmanager.model.Project;
import ir.technopedia.projectmanager.model.Task;

/**
 * Created by TheLoneWolf on 10/8/2017.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {
    private final int NOTIFY_DELAY = 500;

    private List<Project> mProjects;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;

        @BindView(R.id.progress)
        ProgressBar mProgress;

        @BindView(R.id.percent)
        TextView mPercent;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ProjectsAdapter(List<Project> projects) {
        mProjects = projects;
    }

    public void updateAdapter(List<Project> projects) {
        mProjects = projects;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Project project = mProjects.get(position);

        List<Task> doneTasks = Task.find(Task.class, "projectid = ? and state = ?", project.getId() + "", "2");
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

        viewHolder.mName.setText(project.getName());
        viewHolder.mPercent.setText(project.getPercent() + "%");
        viewHolder.mProgress.setProgress(project.getPercent());
    }

    @Override
    public long getItemId(int position) {
        return mProjects.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }


    public void addProject(final Project project, final int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProjects.add(position, project);
                notifyItemInserted(position);
            }
        }, NOTIFY_DELAY);
    }

    public void removeProject(final int position) {
        mProjects.remove(position);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemRemoved(position);
            }
        }, NOTIFY_DELAY);
    }
}