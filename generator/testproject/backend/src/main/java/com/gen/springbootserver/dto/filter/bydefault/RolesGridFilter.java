
package com.gen.springbootserver.dto.filter.bydefault;
import com.gen.springbootserver.dto.filter.bydefault.BaseFilter;
import java.time.LocalDateTime;

public class RolesGridFilter extends BaseFilter {

  private String filterByid;
      
  private String filterByisDefault;
      
  private String filterByname;
      
    public String getFilterByid() {
        return filterByid;
    }

    public void setFilterByid(String filterByid) {
        this.filterByid = filterByid;
    }
        
    public String getFilterByisDefault() {
        return filterByisDefault;
    }

    public void setFilterByisDefault(String filterByisDefault) {
        this.filterByisDefault = filterByisDefault;
    }
        
    public String getFilterByname() {
        return filterByname;
    }

    public void setFilterByname(String filterByname) {
        this.filterByname = filterByname;
    }
        
}
    