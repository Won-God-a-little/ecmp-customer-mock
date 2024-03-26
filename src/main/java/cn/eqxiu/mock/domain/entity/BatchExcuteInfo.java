package cn.eqxiu.mock.domain.entity;

import java.util.ArrayList;

public class BatchExcuteInfo {
    private String env;

    private Boolean isPreview;

    private ArrayList<String> caseNums;
    private String testType;


    public BatchExcuteInfo(String env, Boolean isPreview, ArrayList<String> caseNums, String testType) {
        this.env = env;
        this.isPreview = isPreview;
        this.caseNums = caseNums;
        this.testType = testType;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public ArrayList<String> getCaseNums() {
        return caseNums;
    }

    public void setCaseNums(ArrayList<String> caseNums) {
        this.caseNums = caseNums;
    }

    public Boolean getPreview() {
        return isPreview;
    }

    public void setPreview(Boolean preview) {
        isPreview = preview;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
}
