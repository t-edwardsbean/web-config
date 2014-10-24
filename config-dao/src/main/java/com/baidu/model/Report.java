package com.baidu.model;

/**
 * Created by edwardsbean on 2014/8/24 0024.
 */
public class Report {
    private int id;
    private String reportName;
    private int jobId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
