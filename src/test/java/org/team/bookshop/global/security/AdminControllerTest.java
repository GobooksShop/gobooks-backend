package org.team.bookshop.global.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.team.bookshop.domain.category.controller.CategoryAdminController;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.service.CategoryService;
import org.team.bookshop.domain.user.entity.UserRole;


@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(CategoryAdminController.class)
@EnableAspectJAutoProxy
public class AdminControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryService categoryService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .defaultRequest(post("/**").with(csrf()))
        .defaultRequest(put("/**").with(csrf()))
        .defaultRequest(delete("/**").with(csrf()))
        .build();
    UserDetails userDetails = User.withUsername("User")
        .password("admin")
        .authorities(AuthorityUtils.createAuthorityList(UserRole.USER.getRoleName()))
        .build();
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(userDetails, "admin", userDetails.getAuthorities()));
  }

  @Test
  @WithMockUser(username = "admin" , roles = {"ADMIN"})
  public void shouldCreateCategory()  throws Exception {

    String categoryName = "세계사";
    Long parentId = 1L;

    CategoryCreateRequestDto requestDto = new CategoryCreateRequestDto(categoryName, parentId);

    mockMvc.perform(post("/api/admin/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto))
                    .with(csrf())
            )
            .andExpect(status().isCreated())
            .andDo(print())
    ;

  }

  @Test
  @WithMockUser(username = "user" , roles = {"USER"})
  public void shouldNotCreateCategory()  throws Exception {

    String categoryName = "세계사";
    Long parentId = 1L;

    CategoryCreateRequestDto requestDto = new CategoryCreateRequestDto(categoryName, parentId);

    mockMvc.perform(post("/api/admin/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))
            .with(csrf())
        )
        .andExpect(status().isCreated())
        .andDo(print())
    ;

  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  void shouldDenyAccessForNonAdmin() throws Exception {
    Long categoryId = 1L;

    mockMvc.perform(delete("/api/admin/categories/{categoryId}", categoryId)
                    .with(csrf())
            )
            .andExpect(status().isForbidden())
            .andDo(print())
    ;
  }
}
