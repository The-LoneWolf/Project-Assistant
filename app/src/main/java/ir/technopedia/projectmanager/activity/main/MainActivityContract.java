package ir.technopedia.projectmanager.activity.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public interface MainActivityContract {

    public interface Presenter {
        void setView(MainActivityContract.View view, FragmentManager manager);
        boolean loadFragments(int id);
        void commitFragment(Fragment fragment);
        boolean hasView();
    }

    public interface View {
        boolean handleNavMenu(int id);
    }
}
