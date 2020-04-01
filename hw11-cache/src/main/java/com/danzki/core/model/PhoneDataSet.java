package com.danzki.core.model;

import javax.persistence.*;

@Entity
@Table(name = "Phone")
public class PhoneDataSet {
  @Id
  @Column(name = "id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  @Column(name = "number")
  private String number;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public PhoneDataSet() {
  }

  public PhoneDataSet(String number, User user) {
    this.number = number;
    this.user = user;
  }

  public static class PhoneDataSetBuilder {
    private long id;
    private String number;
    private User user;

    public PhoneDataSetBuilder() {
    }

    public PhoneDataSetBuilder withNumber(String number) {
      this.number = number;
      return this;
    }

    public PhoneDataSetBuilder withUser(User user) {
      this.user = user;
      return this;
    }

    public PhoneDataSet build() {
      return new PhoneDataSet(number, user);
    }
  }
}
