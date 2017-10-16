package ir.technopedia.projectmanager.model;

import com.orm.SugarRecord;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class Task extends SugarRecord {

    public Task() {
    }

    private String name, date;
    private int value, state;
    private long project_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }
}
