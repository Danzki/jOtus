package com.danzki.jsonwriter.examples;

import java.util.Arrays;
import java.util.List;

public final class Employee {
  private final String firstName;
  private final String lastName;
  private final String secondName;
  private final int age;
  private final int castaId;
  private final Boolean isWork;
  private final String[] params;
  private final List<String> phones;

  Employee(String firstName, String lastName, String secondName, int age, int castaId, Boolean isWork, String[] params, List<String> phones) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.secondName = secondName;
    this.age = age;
    this.castaId = castaId;
    this.isWork = isWork;
    this.params = params;
    this.phones = phones;
  }

  public static EmployeeBuilder builder() {
    return new EmployeeBuilder();
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getSecondName() {
    return this.secondName;
  }

  public int getAge() {
    return this.age;
  }

  public int getCastaId() {
    return this.castaId;
  }

  public Boolean getIsWork() {
    return this.isWork;
  }

  public String[] getParams() {
    return this.params;
  }

  public List<String> getPhones() {
    return this.phones;
  }

  public String toString() {
    return "Employee(firstName=" + this.getFirstName() +
        ", lastName=" + this.getLastName() +
        ", secondName=" + this.getSecondName() +
        ", age=" + this.getAge() +
        ", castaId=" + this.getCastaId() +
        ", isWork=" + this.getIsWork() +
        ", params=" + java.util.Arrays.deepToString(this.getParams()) +
        ", phones=" + this.getPhones() + ")";
  }

  public EmployeeBuilder toBuilder() {
    return new EmployeeBuilder()
        .firstName(this.firstName)
        .lastName(this.lastName)
        .secondName(this.secondName)
        .age(this.age)
        .castaId(this.castaId)
        .isWork(this.isWork)
        .params(this.params)
        .phones(this.phones);
  }

  public static class EmployeeBuilder {
    private String firstName;
    private String lastName;
    private String secondName;
    private int age;
    private int castaId;
    private Boolean isWork;
    private String[] params;
    private List<String> phones;

    EmployeeBuilder() {
    }

    public EmployeeBuilder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public EmployeeBuilder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public EmployeeBuilder secondName(String secondName) {
      this.secondName = secondName;
      return this;
    }

    public EmployeeBuilder age(int age) {
      this.age = age;
      return this;
    }

    public EmployeeBuilder castaId(int castaId) {
      this.castaId = castaId;
      return this;
    }

    public EmployeeBuilder isWork(Boolean isWork) {
      this.isWork = isWork;
      return this;
    }

    public EmployeeBuilder params(String[] params) {
      this.params = params;
      return this;
    }

    public EmployeeBuilder phones(List<String> phones) {
      this.phones = phones;
      return this;
    }

    public Employee build() {
      return new Employee(firstName, lastName, secondName, age, castaId, isWork, params, phones);
    }

    public String toString() {
      return "Employee.EmployeeBuilder(firstName=" + this.firstName +
          ", lastName=" + this.lastName +
          ", secondName=" + this.secondName +
          ", age=" + this.age +
          ", castaId=" + this.castaId +
          ", isWork=" + this.isWork +
          ", params=" + java.util.Arrays.deepToString(this.params) +
          ", phones=" + this.phones + ")";
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof EmployeeBuilder)) return false;

      EmployeeBuilder that = (EmployeeBuilder) o;

      if (age != that.age) return false;
      if (castaId != that.castaId) return false;
      if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
      if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
      if (secondName != null ? !secondName.equals(that.secondName) : that.secondName != null) return false;
      if (isWork != null ? !isWork.equals(that.isWork) : that.isWork != null) return false;
      // Probably incorrect - comparing Object[] arrays with Arrays.equals
      if (!Arrays.equals(params, that.params)) return false;
      return phones != null ? phones.equals(that.phones) : that.phones == null;

    }

    @Override
    public int hashCode() {
      int result = firstName != null ? firstName.hashCode() : 0;
      result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
      result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
      result = 31 * result + age;
      result = 31 * result + castaId;
      result = 31 * result + (isWork != null ? isWork.hashCode() : 0);
      result = 31 * result + Arrays.hashCode(params);
      result = 31 * result + (phones != null ? phones.hashCode() : 0);
      return result;
    }
  }
}
