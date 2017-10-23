package services;

import android.widget.Toast;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;
/**
 * Created by Admin on 10/23/2017.
 */
public class MyServices extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        jobFinished(params,false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
