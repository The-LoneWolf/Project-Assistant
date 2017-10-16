package ir.technopedia.projectmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by TheLoneWolf on 10/6/2017.
 */

public class Project extends SugarRecord {

    public Project() {
    }

    private String name;
    private int state;

    @Ignore
    private int percent = 0;
    @Ignore
    private int done = 0;
    @Ignore
    private int total = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent() {
        if (this.total == 0) {
            this.percent = 0;
        } else {
            this.percent = this.done  * 100 / this.total;
        }
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
