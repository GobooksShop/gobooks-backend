package org.team.bookshop.product;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.team.bookshop.domain.product.controller.ProductImgDetailController;
import org.team.bookshop.domain.product.entity.ProductImgDetail;
import org.team.bookshop.domain.product.service.ProductImgDetailService;

@WebMvcTest(ProductImgDetailController.class)
@MockBean(JpaMetamodelMappingContext.class)

public class ProductImgDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductImgDetailService productImgDetailService;

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
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // 관리자 role로 바꾸기
    public void getProductImgDetailByProductIdTest() throws Exception {
        ProductImgDetail productImgDetail = new ProductImgDetail(1L, null, "http://example.com");
        when(productImgDetailService.findByProductId(1L)).thenReturn(Optional.of(productImgDetail));

        mockMvc.perform(get("/api/products/detail-img/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(productImgDetail.getDetailPageUrl()));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // 관리자 role로 바꾸기
    public void deleteProductImgDetailTest() throws Exception {
        mockMvc.perform(delete("/api/products/detail-img/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}