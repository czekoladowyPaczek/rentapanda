package de.panda.rentapanda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.panda.rentapanda.helper.TranslateHelper;
import de.panda.rentapanda.model.ModelJob;
import de.panda.rentapanda.ui.JobRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements JobRecyclerAdapter.OnItemClickListener {

    @Inject
    MainPresenter presenter;
    @Inject
    TranslateHelper translateHelper;
    private List<ModelJob> jobs;
    private JobRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private View loadingView;
    private View errorView;
    private View emptyView;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((PandaApplication) getApplication()).getActivityComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobs = new ArrayList<>();
        adapter = new JobRecyclerAdapter(this, jobs, new SimpleDateFormat("dd.MM.yyyy"), translateHelper);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        loadingView = findViewById(R.id.main_loading);
        errorView = findViewById(R.id.main_error);
        emptyView = findViewById(R.id.main_empty);
        retryButton = (Button) findViewById(R.id.main_retry);
        retryButton.setOnClickListener(view -> presenter.retry());

        presenter.setParent(this, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public void setData(List<ModelJob> jobs) {
        int oldCount = this.jobs.size();
        this.jobs.clear();
        adapter.notifyItemRangeRemoved(0, oldCount);
        this.jobs.addAll(jobs);
        adapter.notifyItemRangeInserted(0, jobs.size());

        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        if (jobs.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    public void showError(Throwable error) {
        error.printStackTrace();
        errorView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(int position) {
        presenter.onItemClicked(jobs.get(position));
    }
}
