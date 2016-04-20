package de.panda.rentapanda.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import de.panda.rentapanda.R;
import de.panda.rentapanda.helper.TranslateHelper;
import de.panda.rentapanda.model.ModelJob;

/**
 * @author Marcin
 */
public class JobRecyclerAdapter extends RecyclerView.Adapter<JobRecyclerAdapter.JobHolder> {

    private LayoutInflater inflater;
    private Context context;
    private SimpleDateFormat format;
    private List<ModelJob> jobs;
    private TranslateHelper translateHelper;
    private OnItemClickListener listener;

    public JobRecyclerAdapter(Context context, List<ModelJob> jobs, SimpleDateFormat format, TranslateHelper translateHelper) {
        this.context = context;
        this.jobs = jobs;
        this.format = format;
        this.translateHelper = translateHelper;
        inflater = LayoutInflater.from(context);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JobHolder(inflater.inflate(R.layout.view_job_item, parent, false), format, listener);
    }

    @Override
    public void onBindViewHolder(JobHolder holder, int position) {
        holder.bind(jobs.get(position), context.getString(translateHelper.getStatus(jobs.get(position).getStatus())));
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public static class JobHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView price;
        private TextView address;
        private TextView status;

        private SimpleDateFormat format;

        public JobHolder(View itemView, SimpleDateFormat format, OnItemClickListener listener) {
            super(itemView);
            this.format = format;
            date = (TextView) itemView.findViewById(R.id.job_date);
            price = (TextView) itemView.findViewById(R.id.job_price);
            address = (TextView) itemView.findViewById(R.id.job_address);
            status = (TextView) itemView.findViewById(R.id.job_status);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemClicked(getAdapterPosition());
                }
            });
        }

        public void bind(ModelJob job, String status) {
            date.setText(format.format(job.getJobDate()));
            price.setText(String.format("%s€", job.getPrice()));
            address.setText(String.format("%s, %s", job.getStreet(), job.getCity()));
            this.status.setText(status);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
