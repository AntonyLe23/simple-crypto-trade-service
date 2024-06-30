package org.anthonyle.simplecryptotradeservice.controllers;

import org.anthonyle.simplecryptotradeservice.dto.ApiResponse;
import org.anthonyle.simplecryptotradeservice.models.User;
import org.anthonyle.simplecryptotradeservice.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/test")
  public ApiResponse<User> registerTestUser() {
    ApiResponse<User> apiResponse = new ApiResponse<>();
    User user = userService.registerFakeUser();
    apiResponse.setData(user);
    return apiResponse;
  }
}
