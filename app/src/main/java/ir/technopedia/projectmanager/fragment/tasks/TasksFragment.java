package ir.technopedia.projectmanager.fragment.tasks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.technopedia.projectmanager.R;
import ir.technopedia.projectmanager.helpers.RecyclerItemClickListener;
import ir.technopedia.projectmanager.model.Task;

public class TasksFragment extends Fragment implements TasksFragmentContract.View {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty_state)
    LinearLayout empty_state;

    TasksAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    List<Task> tasks;
    int state = 0, edit_state = 0;
    long project_id = 0l;

    TextView btn_save, btn_cancel;
    EditText task_name, task_value;
    AppCompatSpinner stateSpinner;
    AlertDialog dialog;

    TasksFragmentPresenter tasksFragmentPresenter = new TasksFragmentPresenter();

    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {

            view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tasksFragmentPresenter.handleDeleteClick(tasks.get(position));
                }
            });
            view.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showEditTaskDialog(tasks.get(position));
                }
            });
        }
    });

    public TasksFragment() {
    }

    public static TasksFragment newInstance(int state, long project_id) {
        TasksFragment fragment = new TasksFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        bundle.putLong("project_id", project_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tasks, container, false);
        ButterKnife.bind(this, v);

        Bundle bundle = getArguments();
        state = bundle.getInt("state");
        project_id = bundle.getLong("project_id");

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        tasksFragmentPresenter.setView(this, state, project_id);
        tasksFragmentPresenter.loadTasks();
    }

    @Override
    public void update() {
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        empty_state.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.removeOnItemTouchListener(onItemTouchListener);
        recyclerView.addOnItemTouchListener(onItemTouchListener);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new TasksAdapter(TasksFragment.this.tasks);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showEmptyState() {
        empty_state.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showEditTaskDialog(final Task task) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_add_task, null);

        task_name = mView.findViewById(R.id.task_name);
        task_value = mView.findViewById(R.id.task_value);
        stateSpinner = mView.findViewById(R.id.task_state);
        btn_save = mView.findViewById(R.id.btn_save);
        btn_cancel = mView.findViewById(R.id.btn_cancel);

        task_name.setText(task.getName());
        task_value.setText(task.getValue() + "");
        edit_state = task.getState();

        stateSpinner.setSelection(edit_state);

        stateSpinner.setVisibility(View.VISIBLE);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                edit_state = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasksFragmentPresenter.handleEditClick(task, task_name.getText().toString(), task_value.getText().toString(), edit_state);
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(mView);
        dialog = alert.show();
    }
}
