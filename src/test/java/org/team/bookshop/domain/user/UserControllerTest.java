package org.team.bookshop.domain.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.team.bookshop.domain.user.controller.UserController;
import org.team.bookshop.domain.user.dto.UserResponseDto;
import org.team.bookshop.domain.user.dto.UserUpdateDto;
import org.team.bookshop.domain.user.service.UserService;


@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser(username = "user", roles = {"USER"})
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testGetUser() throws Exception {
    Long userId = 1L;

    Mockito.when(userService.getUserById(1L)).thenReturn(new UserResponseDto());
    mockMvc.perform(get("/api/users/{id}", userId).with(csrf()))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateUser() throws Exception {
    Long userId = 1L;
    UserUpdateDto userUpdateDto = new UserUpdateDto();
    mockMvc.perform(put("/api/users/{id}", userId).with(csrf())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(userUpdateDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteUser() throws Exception {
    Long userId = 1L;
    mockMvc.perform(delete("/api/users/{id}", userId).with(csrf()))
        .andExpect(status().isNoContent());
  }
}