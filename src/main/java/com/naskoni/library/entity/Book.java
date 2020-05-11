package com.naskoni.library.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "book")
public class Book extends AbstractEntityId {

  @Column(nullable = false)
  private String name;

  private String author;

  private int year;

  private String isbn;
}
