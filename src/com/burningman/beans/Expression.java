package com.burningman.beans;


public class Expression {

  public static final String EXPRESSION_TYPE_KEY = "EXPRESSION_TYPE";
  public static final String EXPRESSION_LIST_KEY = "EXPRESSION_LIST";
  
private String id;
  private String contact_email;
  private String description;
  private String name;
  private String slug;
  private String year;
  private String url;

  public Expression() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContact_email() {
    return contact_email;
  }

  public void setContact_email(String contact_email) {
    this.contact_email = contact_email;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  
  

}