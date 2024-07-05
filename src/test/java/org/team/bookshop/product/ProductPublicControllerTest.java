package org.team.bookshop.product;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.team.bookshop.domain.product.controller.ProductPublicController;
import org.team.bookshop.domain.product.dto.ProductDto;
import org.team.bookshop.domain.product.dto.ProductResponseDto;
import org.team.bookshop.domain.product.dto.ProductResponseMainDto;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.entity.Product.Status;
import org.team.bookshop.domain.product.service.ProductService;

@WebMvcTest(ProductPublicController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductPublicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

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
    @WithMockUser(username = "user", roles = {"USER"})
    public void getProductsByCategoryTest() throws Exception {
        Product productEntity = Product.builder()
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .content("Content")
                .fixedPrice(1000)
                .publicationYear(LocalDate.now())
                .status(Status.AVAILABLE)
                .discount(true)
                .stockQuantity(10)
                .pictureUrl("http://example.com/picture.jpg")
                .build();
        productEntity.setId(1L);

        List<ProductDto> products = List.of(new ProductDto(productEntity));
        when(productService.getProductsByCategoryId(1L)).thenReturn(products);

        mockMvc.perform(get("/api/products/category/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getProductsByCategoryPagedTest() throws Exception {
        Product productEntity = Product.builder()
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .content("Content")
                .fixedPrice(1000)
                .publicationYear(LocalDate.now())
                .status(Status.AVAILABLE)
                .discount(true)
                .stockQuantity(10)
                .pictureUrl("http://example.com/picture.jpg")
                .build();
        productEntity.setId(1L);

        List<ProductDto> products = List.of(new ProductDto(productEntity));
        Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.DESC, "createdAt"));
        when(productService.getProductsByCategoryId(1L, pageable))
                .thenReturn(new PageImpl<>(products));

        mockMvc.perform(get("/api/products/category/1/paged")
                        .param("page", "0")
                        .param("size", "12")
                        .param("sort", "createdAt,desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getProductTest() throws Exception {
        Product productEntity = Product.builder()
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .content("Content")
                .fixedPrice(1000)
                .publicationYear(LocalDate.now())
                .status(Status.AVAILABLE)
                .discount(true)
                .stockQuantity(10)
                .pictureUrl("http://example.com/picture.jpg")
                .build();
        productEntity.setId(1L);

        ProductResponseDto product = new ProductResponseDto(productEntity);
        when(productService.getProduct(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getAllProductsTest() throws Exception {
        Product productEntity = Product.builder()
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .content("Content")
                .fixedPrice(1000)
                .publicationYear(LocalDate.now())
                .status(Status.AVAILABLE)
                .discount(true)
                .stockQuantity(10)
                .pictureUrl("http://example.com/picture.jpg")
                .build();
        productEntity.setId(1L);

        List<ProductResponseDto> products = List.of(new ProductResponseDto(productEntity));
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getMainProductsTest() throws Exception {
        List<ProductResponseMainDto> products = List.of(new ProductResponseMainDto(1L, "title", "author", "pictureUrl"));
        when(productService.getMainProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products/main"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1").isArray());
    }
}