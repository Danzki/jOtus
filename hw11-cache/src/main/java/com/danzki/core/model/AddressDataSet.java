package com.danzki.core.model;

import javax.persistence.*;

@Entity
@Table(name = "Address")
public class AddressDataSet {
  @Id
  @Column(name = "id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  @Column(name = "street")
  private String street;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public AddressDataSet() {
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public AddressDataSet(String street, User user) {
    this.street = street;
    this.user = user;
  }
}
