package com.nagopy.android.bashfulclock.app

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import com.nagopy.android.bashfulclock.BuildConfig
import com.nagopy.android.bashfulclock.infra.remoteconfig.RemoteConfig
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject


class RemoteConfigService : JobService() {

    @Inject
    lateinit var remoteConfig: RemoteConfig

    override fun onCreate() {
        Timber.d("onCreate")
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Timber.d("onStartJob")
        remoteConfig.fetchRequest { isSuccessful ->
            jobFinished(params, isSuccessful)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Timber.d("onStopJob")
        return true
    }

    companion object {

        const val JOB_ID = 1

        fun requestFetch(context: Context) {
            Timber.d("requestFetch")
            var builder = JobInfo.Builder(JOB_ID, ComponentName(context, RemoteConfigService::class.java))
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            //.setBackoffCriteria(10000, JobInfo.BACKOFF_POLICY_LINEAR)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                // -23
                builder = builder.setPeriodic(BuildConfig.REMOTE_CONFIG_FETCH_INTERVAL)
            } else {
                // 24-
                builder = builder.setPeriodic(BuildConfig.REMOTE_CONFIG_FETCH_INTERVAL, JobInfo.getMinFlexMillis())
            }
            val scheduler = ContextCompat.getSystemService(context, JobScheduler::class.java)
            scheduler?.apply {
                cancel(JOB_ID)
                schedule(builder.build())
            }
        }

    }
}
