package org.team.bookshop.domain.user;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.team.bookshop.domain.user.controller.AddressController;
import org.team.bookshop.domain.user.dto.AddressPostDto;
import org.team.bookshop.domain.user.entity.Address;
import org.team.bookshop.domain.user.entity.User;
import org.team.bookshop.domain.user.entity.UserRole;
import org.team.bookshop.domain.user.service.AddressService;

@WebMvcTest(AddressController.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser(username = "user", roles = {"USER"})
public class AddressControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AddressService addressService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldReturnAddress() throws Exception {
    // given
    User user = User.builder()
        .id(1L)
        .email("tester@test.com")
        .name("Tester")
        .nickname("Tester")
        .role(UserRole.USER)
        .build();

    Address address = Address.builder()
        .id(1L)
        .isPrimary(true)
        .user(user)
        .label("직장")
        .zipcode("12345")
        .address1("서울특별시 성동구 아차산로17길 48")
        .address2("성수낙낙 2층 엘리스랩")
        .recipientName("엘리스")
        .recipientPhone("01012345678")
        .build();

    AddressPostDto addressPostDto = AddressPostDto.toDto(address);

    given(addressService.getUserAddress(1L,1L)).willReturn(addressPostDto);

    // when & then
    mockMvc.perform(get("/api/users/1/address/1").with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.isPrimary").value(true))
        .andExpect(jsonPath("$.label").value("직장"))
        .andExpect(jsonPath("$.zipcode").value("12345"))
        .andExpect(jsonPath("$.address1").value("서울특별시 성동구 아차산로17길 48"))
        .andExpect(jsonPath("$.address2").value("성수낙낙 2층 엘리스랩"))
        .andExpect(jsonPath("$.recipientName").value("엘리스"))
        .andExpect(jsonPath("$.recipientPhone").value("01012345678"))
        .andDo(print())
    ;
  }

  @Test
  void shouldReturnAddressList() throws Exception {
    // given

    Long userId = 1L;
    Long addressIdA = 1L;
    Long addressIdB = 2L;

    User user = User.builder()
        .id(userId)
        .email("tester@test.com")
        .name("Tester")
        .nickname("Tester")
        .role(UserRole.USER)
        .build();

    List<AddressPostDto> addressList = new ArrayList<>();

    Address addressA = Address.builder()
        .id(addressIdA)
        .isPrimary(false)
        .user(user)
        .label("직장")
        .zipcode("12345")
        .address1("서울특별시 성동구 아차산로17길 48")
        .address2("성수낙낙 2층 엘리스랩")
        .recipientName("엘리스")
        .recipientPhone("01012345678")
        .build();

    Address addressB = Address.builder()
        .id(addressIdB)
        .isPrimary(true)
        .user(user)
        .label("집")
        .zipcode("54321")
        .address1("서울특별시 성동구 왕십로 83-21")
        .address2("아크로서울포레스트")
        .recipientName("테스터")
        .recipientPhone("01087654321")
        .build();

    addressList.add(AddressPostDto.toDto(addressA));
    addressList.add(AddressPostDto.toDto(addressB));

    given(addressService.getUserAddress(userId)).willReturn(addressList);

    // when & then
    mockMvc.perform(get("/api/users/1/address").with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(addressIdA))
        .andExpect(jsonPath("$[0].isPrimary").value(false))
        .andExpect(jsonPath("$[0].label").value("직장"))
        .andExpect(jsonPath("$[0].zipcode").value("12345"))
        .andExpect(jsonPath("$[0].address1").value("서울특별시 성동구 아차산로17길 48"))
        .andExpect(jsonPath("$[0].address2").value("성수낙낙 2층 엘리스랩"))
        .andExpect(jsonPath("$[0].recipientName").value("엘리스"))
        .andExpect(jsonPath("$[0].recipientPhone").value("01012345678"))
        .andExpect(jsonPath("$[1].id").value(addressIdB))
        .andExpect(jsonPath("$[1].isPrimary").value(true))
        .andExpect(jsonPath("$[1].label").value("집"))
        .andExpect(jsonPath("$[1].zipcode").value("54321"))
        .andExpect(jsonPath("$[1].address1").value("서울특별시 성동구 왕십로 83-21"))
        .andExpect(jsonPath("$[1].address2").value("아크로서울포레스트"))
        .andExpect(jsonPath("$[1].recipientName").value("테스터"))
        .andExpect(jsonPath("$[1].recipientPhone").value("01087654321"))
        .andDo(print())
    ;
  }

  @Test
  public void testCreateAddress() throws Exception {
    // given
    User user = User.builder()
        .id(1L)
        .email("tester@test.com")
        .name("Tester")
        .nickname("Tester")
        .role(UserRole.USER)
        .build();

    Address address = Address.builder()
        .isPrimary(true)
        .user(user)
        .label("직장")
        .zipcode("12345")
        .address1("서울센터 서울 성동구 아차산로17길 48")
        .address2("성수낙낙 2층 엘리스랩")
        .recipientName("엘리스")
        .recipientPhone("01012345678")
        .build();

    AddressPostDto addressPostDto = AddressPostDto.toDto(address);

    given(addressService.saveUserAddress(1L, addressPostDto)).willReturn(addressPostDto);

    mockMvc.perform(post("/api/users/{userId}/address", 1L).with(csrf())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(addressPostDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        ;
  }

  @Test
  public void testUpdateAddress() throws Exception {

    // given
    User user = User.builder()
        .id(1L)
        .email("tester@test.com")
        .name("Tester")
        .nickname("Tester")
        .role(UserRole.USER)
        .build();

    Address address = Address.builder()
        .id(1L)
        .isPrimary(true)
        .user(user)
        .label("직장")
        .zipcode("12345")
        .address1("서울센터 서울 성동구 아차산로17길 48")
        .address2("성수낙낙 2층 엘리스랩")
        .recipientName("엘리스")
        .recipientPhone("01012345678")
        .build();

    AddressPostDto addressPostDto = AddressPostDto.toDto(address);
    given(addressService.updateUserAddress(1L, addressPostDto)).willReturn(addressPostDto);

    mockMvc.perform(put("/api/users/{userId}/address", 1L).with(csrf())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(addressPostDto)))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteAddress() throws Exception {

    Long userId = 1L;
    Long addressId = 1L;

    doNothing().when(addressService).deleteUserAddress(userId, addressId);

    mockMvc.perform(delete("/api/users/{userId}/address/{addressId}", userId, addressId).with(csrf()))
        .andExpect(status().isNoContent())
        .andDo(print())
    ;
  }
}
