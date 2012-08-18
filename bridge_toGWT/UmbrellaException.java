package com.github.a2g.core.bridge;

import java.util.Collections;
import java.util.Set;

/**
 * A {@link RuntimeException} that collects a {@link Set} of child
 * {@link Throwable}s together. Typically thrown after a loop, with all of the
 * exceptions thrown during that loop, but delayed so that the loop finishes
 * executing.
 */
public class UmbrellaException extends RuntimeException {

  private static final String MSG =
      "One or more exceptions caught, see full set in UmbrellaException#getCauses";
  /**
   * The causes of the exception.
   */
  private Set<Throwable> causes;

  public UmbrellaException(Set<Throwable> causes) {
    super(MSG, causes.size() == 0 ? null : causes.toArray(new Throwable[0])[0]);
    this.causes = causes;
  }

  /**
   * Required for GWT RPC serialization.
   */
  protected UmbrellaException() {
    // Can't delegate to the other constructor or GWT RPC gets cranky
    super(MSG);
    this.causes = Collections.<Throwable> emptySet();
  }

  /**
   * Get the set of exceptions that caused the failure.
   * 
   * @return the set of causes
   */
  public Set<Throwable> getCauses() {
    return causes;
  }
}
