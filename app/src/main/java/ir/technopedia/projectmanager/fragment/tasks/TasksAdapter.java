package ir.technopedia.projectmanager.fragment.tasks;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.technopedia.projectmanager.R;
import ir.technopedia.projectmanager.model.Task;

/**
 * Created by TheLoneWolf on 10/8/2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private final int NOTIFY_DELAY = 500;

    private List<Task> tasks;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView mName;

        @BindView(R.id.value)
        TextView mValue;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void updateAdapter(List<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Task task = tasks.get(position);
        viewHolder.mName.setText(task.getName());
        viewHolder.mValue.setText(task.getValue() + " points");
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public void addTask(final Task task, final int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tasks.add(position, task);
                notifyItemInserted(position);
            }
        }, NOTIFY_DELAY);
    }

    public void removeTask(final int position) {
        tasks.remove(position);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemRemoved(position);
            }
        }, NOTIFY_DELAY);
    }
}