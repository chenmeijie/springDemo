package com.great.springdemo.bean;


import java.math.BigDecimal;

public class User {

  private BigDecimal userId;
  private String uName;
  private String uPwd;
  private String uSex;
  private String education;
  private String job;
  private String phoneNum;
  private String email;
  private BigDecimal pid;
  private String pName;




  public BigDecimal getUserId()
  {
    return userId;
  }

  public void setUserId(BigDecimal userId)
  {
    this.userId = userId;
  }

  public String getuName()
  {
    return uName;
  }

  public void setuName(String uName)
  {
    this.uName = uName;
  }

  public String getuPwd()
  {
    return uPwd;
  }

  public void setuPwd(String uPwd)
  {
    this.uPwd = uPwd;
  }

  public String getuSex()
  {
    return uSex;
  }

  public void setuSex(String uSex)
  {
    this.uSex = uSex;
  }

  public String getEducation()
  {
    return education;
  }

  public void setEducation(String education)
  {
    this.education = education;
  }

  public String getJob()
  {
    return job;
  }

  public void setJob(String job)
  {
    this.job = job;
  }

  public String getPhoneNum()
  {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum)
  {
    this.phoneNum = phoneNum;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public BigDecimal getPid()
  {
    return pid;
  }

  public void setPid(BigDecimal pid)
  {
    this.pid = pid;
  }

  public String getpName()
  {
    return pName;
  }

  public void setpName(String pName)
  {
    this.pName = pName;
  }
}
