
package com.gen.springbootserver.dto.filter;
import com.gen.springbootserver.dto.filter.bydefault.BaseFilter;
import java.time.LocalDateTime;

public class employeelistmenuEmployeemaintainFilter extends BaseFilter {

  private String filterByid;
      
  private String filterByname;
      
  private String filterByidnumber;
      
  private String filterBydepartment;
      
  private String filterByposition;
      
  private String filterByduty;
      
  private String filterBystarttime;
      
  private String filterBycontractend;
      
  private String filterBycurrent;
      
    public String getFilterByid() {
        return filterByid;
    }

    public void setFilterByid(String filterByid) {
        this.filterByid = filterByid;
    }
        
    public String getFilterByname() {
        return filterByname;
    }

    public void setFilterByname(String filterByname) {
        this.filterByname = filterByname;
    }
        
    public String getFilterByidnumber() {
        return filterByidnumber;
    }

    public void setFilterByidnumber(String filterByidnumber) {
        this.filterByidnumber = filterByidnumber;
    }
        
    public String getFilterBydepartment() {
        return filterBydepartment;
    }

    public void setFilterBydepartment(String filterBydepartment) {
        this.filterBydepartment = filterBydepartment;
    }
        
    public String getFilterByposition() {
        return filterByposition;
    }

    public void setFilterByposition(String filterByposition) {
        this.filterByposition = filterByposition;
    }
        
    public String getFilterByduty() {
        return filterByduty;
    }

    public void setFilterByduty(String filterByduty) {
        this.filterByduty = filterByduty;
    }
        
    public String getFilterBystarttime() {
        return filterBystarttime;
    }

    public void setFilterBystarttime(String filterBystarttime) {
        this.filterBystarttime = filterBystarttime;
    }
        
    public String getFilterBycontractend() {
        return filterBycontractend;
    }

    public void setFilterBycontractend(String filterBycontractend) {
        this.filterBycontractend = filterBycontractend;
    }
        
    public String getFilterBycurrent() {
        return filterBycurrent;
    }

    public void setFilterBycurrent(String filterBycurrent) {
        this.filterBycurrent = filterBycurrent;
    }
        
}
    