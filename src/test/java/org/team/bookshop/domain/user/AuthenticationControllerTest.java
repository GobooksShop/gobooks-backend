package org.team.bookshop.domain.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.team.bookshop.domain.user.controller.AuthenticationController;
import org.team.bookshop.domain.user.dto.UserLoginDto;
import org.team.bookshop.domain.user.service.AuthenticationService;
import org.team.bookshop.global.security.JwtTokenizer;

@WebMvcTest(AuthenticationController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class AuthenticationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtTokenizer jwtTokenizer;

  @MockBean
  private AuthenticationService authenticationService;

  @Autowired
  private ObjectMapper objectMapper;

  @InjectMocks
  private AuthenticationController authenticationController;

  @Test
  public void testLogin() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .email("test@test.com")
        .password("password")
        .build();
    mockMvc.perform(post("/api/auth/login").with(csrf())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(userLoginDto)))
        .andExpect(status().isOk());
  }
}
