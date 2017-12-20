package com.actitime.domain;

import java.util.Objects;

/**
 * Created by zaskanov on 20.12.2017.
 */
public class AbstractEntity {

  protected Long id;

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other.getClass() != this.getClass()) {
      return false;
    }

    return Objects.equals(this.id, ((AbstractEntity) other).id);
  }

  public int hashCode() {
    return Objects.hashCode(id);
  }
}
