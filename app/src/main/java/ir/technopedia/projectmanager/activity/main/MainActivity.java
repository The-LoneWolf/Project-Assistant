package ir.technopedia.projectmanager.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.technopedia.projectmanager.R;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    MainActivityPresenter presenter = new MainActivityPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!presenter.hasView()){
            presenter.setView(this, getSupportFragmentManager());
            presenter.loadFragments(R.id.navigation_projects);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return handleNavMenu(item.getItemId());
        }
    };

    @Override
    public boolean handleNavMenu(int id) {
        return presenter.loadFragments(id);
    }
}
