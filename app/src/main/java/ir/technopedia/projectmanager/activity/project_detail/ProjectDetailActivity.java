package ir.technopedia.projectmanager.activity.project_detail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.technopedia.projectmanager.R;
import ir.technopedia.projectmanager.fragment.tasks.TasksFragment;
import ir.technopedia.projectmanager.model.Project;

public class ProjectDetailActivity extends AppCompatActivity implements ProjectDetailActivityContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabs)
    TabLayout mTabs;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    TextView btn_save, btn_cancel;
    EditText task_name, task_value, project_name;
    SwitchCompat is_archived;
    AlertDialog dialog;
    TaskFragmentsViewPagerAdapter adapter;

    ProjectDetailActivityPresenter presenter = new ProjectDetailActivityPresenter();
    long project_id = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        ButterKnife.bind(this);

        project_id = getIntent().getLongExtra("project_id", 0l);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initUi() {
        setupViewPager();
        mTabs.setupWithViewPager(mViewPager);
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                ((TasksFragment) adapter.getFragment(mTabs.getSelectedTabPosition())).update();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void setupViewPager() {
        adapter = new TaskFragmentsViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(TasksFragment.newInstance(0, project_id), getResources().getString(R.string.tab_todo));
        adapter.addFrag(TasksFragment.newInstance(1, project_id), getResources().getString(R.string.tab_doing));
        adapter.addFrag(TasksFragment.newInstance(2, project_id), getResources().getString(R.string.tab_done));
        adapter.addFrag(TasksFragment.newInstance(3, project_id), getResources().getString(R.string.tab_canceled));
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void showAddTaskDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_add_task, null);

        task_name = mView.findViewById(R.id.task_name);
        task_value = mView.findViewById(R.id.task_value);
        btn_save = mView.findViewById(R.id.btn_save);
        btn_cancel = mView.findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveTask(task_name.getText().toString(), task_value.getText().toString());
                ((TasksFragment) adapter.getFragment(mTabs.getSelectedTabPosition())).update();
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(ProjectDetailActivity.this);
        alert.setView(mView);
        dialog = alert.show();
    }

    @Override
    public void showEditProjectDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_edit_project, null);

        project_name = mView.findViewById(R.id.project_name);
        is_archived = mView.findViewById(R.id.is_archived);
        btn_save = mView.findViewById(R.id.btn_save);
        btn_cancel = mView.findViewById(R.id.btn_cancel);

        presenter.setEditProjectData(project_name, is_archived);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveProject(project_name, is_archived);
                presenter.loadProject(project_id);
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(ProjectDetailActivity.this);
        alert.setView(mView);
        dialog = alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadProject(project_id);
        initUi();
    }

    @Override
    public void handleProject(Project project) {
        setTitle(project.getName());
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.project_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.edit_project:
                showEditProjectDialog();
                return true;
            case R.id.delete_project:
                presenter.deleteProject();
                finish();
                return true;
            case R.id.add_task:
                showAddTaskDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
