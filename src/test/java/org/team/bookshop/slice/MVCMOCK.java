package org.team.bookshop.slice;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.controller.OrderController;
import org.team.bookshop.domain.payment.controller.PaymentController;
import org.team.bookshop.domain.payment.service.PaymentService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class MVCMOCK {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private OrderService orderService;


    @Test
    public void createPayment() throws Exception{

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}