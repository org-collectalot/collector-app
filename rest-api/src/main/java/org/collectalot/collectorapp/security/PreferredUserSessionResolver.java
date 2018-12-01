package org.collectalot.collectorapp.security;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, METHOD})
public @interface PreferredUserSessionResolver {
}
