package org.team.bookshop.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.team.bookshop.domain.category.controller.CategoryAdminController;
import org.team.bookshop.domain.category.dto.CategoryCreateRequestDto;
import org.team.bookshop.domain.category.dto.CategoryResponseDto;
import org.team.bookshop.domain.category.dto.CategoryUpdateRequestDto;
import org.team.bookshop.domain.category.service.CategoryService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryService categoryService;

  private CategoryCreateRequestDto createRequestDto;
  private CategoryUpdateRequestDto updateRequestDto;
  private CategoryResponseDto responseDto;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .defaultRequest(post("/**").with(csrf()))
        .defaultRequest(put("/**").with(csrf()))
        .defaultRequest(delete("/**").with(csrf()))
        .build();

    createRequestDto = new CategoryCreateRequestDto("새로운 카테고리", 1L);
    updateRequestDto = new CategoryUpdateRequestDto("수정된 카테고리", 2L);
    responseDto = new CategoryResponseDto(1L, "테스트 카테고리");
  }

  @Test
  @DisplayName("카테고리 생성 테스트")
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  void createCategory() throws Exception {
    given(categoryService.createCategory(any(CategoryCreateRequestDto.class)))
        .willReturn(responseDto);

    String requestJson = String.format(
        """
            {
              "name": "%s",
              "parentId": %d
            }
            """,
        createRequestDto.getName(), createRequestDto.getParentId()
    );

    mockMvc.perform(
            post("/api/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(responseDto.getId()))
        .andExpect(jsonPath("$.name").value(responseDto.getName()))
        .andDo(print())
    ;
  }

  @Test
  @DisplayName("카테고리 수정 테스트")
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  void updateCategory() throws Exception {
    given(categoryService.updateCategory(any(Long.class), any(CategoryUpdateRequestDto.class)))
        .willReturn(responseDto);

    String requestJson = String.format(
        """
            {
              "name": "%s",
              "parentId": %d
            }
            """,
        updateRequestDto.getName(), updateRequestDto.getParentId()
    );

    mockMvc.perform(
            put("/api/admin/categories/{categoryId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(responseDto.getId()))
        .andExpect(jsonPath("$.name").value(responseDto.getName()))
        .andDo(print())
    ;
  }

  @Test
  @DisplayName("카테고리 삭제 테스트")
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  void deleteCategory() throws Exception {
    mockMvc.perform(
            delete("/api/admin/categories/{categoryId}", 1L))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""))
        .andDo(print())
    ;
  }
}
