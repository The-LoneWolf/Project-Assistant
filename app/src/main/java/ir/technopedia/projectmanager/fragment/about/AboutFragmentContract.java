package ir.technopedia.projectmanager.fragment.about;

import java.util.List;

import ir.technopedia.projectmanager.model.Task;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class AboutFragmentContract {

    public interface Presenter {
        void setView(AboutFragmentContract.View view);
    }

    public interface View {
        void showLiscenceDialog();
    }
}
