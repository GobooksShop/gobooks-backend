package org.team.bookshop.domain.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.team.bookshop.domain.user.controller.UserAdminController;
import org.team.bookshop.domain.user.service.UserService;

@WebMvcTest(UserAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UserAdminControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetAllUsers() throws Exception {
    Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());
    mockMvc.perform(get("/api/admin/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void testGetAllUsersWithNoAuthorization() throws Exception {
    Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());
    mockMvc.perform(get("/api/admin/users"))
        .andExpect(status().isForbidden())
        ;
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testGetAllUserStatus() throws Exception {
    mockMvc.perform(get("/api/admin/users/status"))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetAllUserStatusWithNotLogin() throws Exception {
    mockMvc.perform(get("/api/admin/users/status"))
        .andExpect(status().isUnauthorized());
  }
}