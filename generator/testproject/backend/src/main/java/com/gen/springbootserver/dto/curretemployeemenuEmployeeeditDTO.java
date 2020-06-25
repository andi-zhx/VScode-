
package com.gen.springbootserver.dto;

import java.time.LocalDateTime;

public class curretemployeemenuEmployeeeditDTO {

  private Long id;
      
  private String name;
      
  private String idnumber;
      
  private String department;
      
  private String position;
      
  private String duty;
      
  private LocalDateTime starttime;
      
  private LocalDateTime contractend;
      
  private Boolean current;
      
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
        
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
        
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
        
    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }
        
    public LocalDateTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalDateTime starttime) {
        this.starttime = starttime;
    }
        
    public LocalDateTime getContractend() {
        return contractend;
    }

    public void setContractend(LocalDateTime contractend) {
        this.contractend = contractend;
    }
        
    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
        
  @Override
  public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(getClass().getSimpleName());
      sb.append(" [");
      sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", name=").append(name);
    sb.append(", idnumber=").append(idnumber);
    sb.append(", department=").append(department);
    sb.append(", position=").append(position);
    sb.append(", duty=").append(duty);
    sb.append(", starttime=").append(starttime);
    sb.append(", contractend=").append(contractend);
    sb.append(", current=").append(current);
    sb.append("]");
    return sb.toString();
  }
}
    