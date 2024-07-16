package org.example;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;

public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Job executed at: " + new Date());
        // Yahan aap Vertex AI se interaction ka code likh sakte hain
    }
}
