package org.team.bookshop.product;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.team.bookshop.domain.product.controller.ProductAdminController;
import org.team.bookshop.domain.product.dto.ProductSaveRequestDto;
import org.team.bookshop.domain.product.dto.SimpleProductResponseDto;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.service.ProductService;

@WebMvcTest(ProductAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createProductTest() throws Exception {
        ProductSaveRequestDto requestDto = ProductSaveRequestDto.builder()
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .content("Content")
                .fixedPrice(1000)
                .publicationYear(LocalDate.now())
                .status(Product.Status.AVAILABLE)
                .discount(true)
                .stockQuantity(10)
                .categoryIds(List.of(1L, 2L))
                .build();

        SimpleProductResponseDto responseDto = SimpleProductResponseDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .content("Content")
                .fixedPrice(1000)
                .publicationYear(LocalDate.now())
                .status(Product.Status.AVAILABLE)
                .discount(true)
                .stockQuantity(10)
                .categoryIds(List.of(1L, 2L))
                .build();

        MockMultipartFile file = new MockMultipartFile(
                "pictureFile",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "image data".getBytes()
        );

        when(productService.saveOrUpdateProduct(requestDto, file)).thenReturn(responseDto);

        mockMvc.perform(multipart("/api/products")
                        .file(file)
                        .param("product", objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDto.getId()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateProductTest() throws Exception {
        ProductSaveRequestDto requestDto = ProductSaveRequestDto.builder()
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .content("Content")
                .fixedPrice(1000)
                .publicationYear(LocalDate.now())
                .status(Product.Status.AVAILABLE)
                .discount(true)
                .stockQuantity(10)
                .categoryIds(List.of(1L, 2L))
                .build();

        SimpleProductResponseDto responseDto = SimpleProductResponseDto.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .content("Content")
                .fixedPrice(1000)
                .publicationYear(LocalDate.now())
                .status(Product.Status.AVAILABLE)
                .discount(true)
                .stockQuantity(10)
                .categoryIds(List.of(1L, 2L))
                .build();

        MockMultipartFile file = new MockMultipartFile(
                "pictureFile",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "image data".getBytes()
        );

        when(productService.saveOrUpdateProduct(requestDto, file)).thenReturn(responseDto);

        mockMvc.perform(multipart("/api/products/1")
                        .file(file)
                        .param("product", objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.getId()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteProductTest() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}