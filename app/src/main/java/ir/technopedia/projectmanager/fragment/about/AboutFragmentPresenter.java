package ir.technopedia.projectmanager.fragment.about;

import java.util.List;

import ir.technopedia.projectmanager.model.Task;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class AboutFragmentPresenter implements AboutFragmentContract.Presenter {

    AboutFragmentContract.View view;

    @Override
    public void setView(AboutFragmentContract.View view) {
        this.view = view;
    }
}
