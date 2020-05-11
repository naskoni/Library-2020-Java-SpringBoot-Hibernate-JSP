package com.naskoni.library.util;

import com.naskoni.library.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import static com.naskoni.library.constant.UserConstants.USER;

public final class UserUtils {

  private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

  private UserUtils() {}

  public static UserDetailsImpl getUser() {
    Object principal;

    try {
      principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    } catch (NullPointerException e) {
      logger.error("No logged user", e);
      return null;
    }

    if (!(principal instanceof UserDetailsImpl)) {
      return null;
    }

    return (UserDetailsImpl) principal;
  }

  public static void addUserToModel(Model model) {
    UserDetails loggedUser = getUser();
    model.addAttribute(USER, loggedUser);
  }
}
