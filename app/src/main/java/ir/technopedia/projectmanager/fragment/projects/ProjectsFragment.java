package ir.technopedia.projectmanager.fragment.projects;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.technopedia.projectmanager.R;
import ir.technopedia.projectmanager.helpers.RecyclerItemClickListener;
import ir.technopedia.projectmanager.model.Project;

public class ProjectsFragment extends Fragment implements ProjectsFragmentContract.View {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty_state)
    LinearLayout empty_state;

    @BindView(R.id.add_project)
    FloatingActionButton add_project;

    TextView btn_save, btn_cancel;
    EditText project_name;
    AlertDialog dialog;

    ProjectsAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    List<Project> projects;
    int state = 0;

    ProjectsFragmentPresenter projectsFragmentPresenter = new ProjectsFragmentPresenter();

    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            projectsFragmentPresenter.handleClick(getActivity(), ProjectsFragment.this.projects.get(position));
        }
    });

    public ProjectsFragment() {
    }

    public static ProjectsFragment newInstance(int state) {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
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
        View v = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.bind(this, v);

        Bundle bundle = getArguments();
        state = bundle.getInt("state");

        add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddProjectDialog();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        projectsFragmentPresenter.setView(this);
        projectsFragmentPresenter.loadProjects(state);
    }

    @Override
    public void setProjects(List<Project> projects) {
        this.projects = projects;
        empty_state.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.removeOnItemTouchListener(onItemTouchListener);
        recyclerView.addOnItemTouchListener(onItemTouchListener);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new ProjectsAdapter(ProjectsFragment.this.projects);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showEmptyState() {
        empty_state.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showAddProjectDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_add_project, null);

        project_name = mView.findViewById(R.id.project_name);
        btn_save = mView.findViewById(R.id.btn_save);
        btn_cancel = mView.findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectsFragmentPresenter.saveProject(project_name);
                projectsFragmentPresenter.loadProjects(state);
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
