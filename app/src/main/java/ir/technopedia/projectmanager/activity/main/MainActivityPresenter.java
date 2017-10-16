package ir.technopedia.projectmanager.activity.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ir.technopedia.projectmanager.R;
import ir.technopedia.projectmanager.fragment.about.AboutFragment;
import ir.technopedia.projectmanager.fragment.projects.ProjectsFragment;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;
    private FragmentManager manager;
    private boolean isFirstPage = false;

    ProjectsFragment projectsFragment, archiveFragment;
    AboutFragment abouFragment;

    public MainActivityPresenter() {

    }

    @Override
    public void setView(MainActivityContract.View view, FragmentManager manager) {
        this.view = view;
        this.manager = manager;

        projectsFragment = ProjectsFragment.newInstance(0);
        archiveFragment = ProjectsFragment.newInstance(1);
        abouFragment = AboutFragment.newInstance();
    }

    @Override
    public boolean hasView() {
        if (this.view == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean loadFragments(int id) {

        switch (id) {
            case R.id.navigation_projects:
                isFirstPage = true;
                this.commitFragment(projectsFragment);
                return true;
            case R.id.navigation_archive:
                isFirstPage = false;
                this.commitFragment(archiveFragment);
                return true;
            case R.id.navigation_about:
                isFirstPage = false;
                this.commitFragment(abouFragment);
                return true;
        }
        return false;
    }

    @Override
    public void commitFragment(Fragment fragment) {
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction().replace(R.id.content, fragment).commit();
    }
}
