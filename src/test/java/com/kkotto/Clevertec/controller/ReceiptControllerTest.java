package com.kkotto.Clevertec.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkotto.Clevertec.service.ReceiptService;
import com.kkotto.Clevertec.service.model.request.PaymentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ReceiptController.class)
@ContextConfiguration(classes = ReceiptController.class)
class ReceiptControllerTest {
    @Autowired
    ReceiptController receiptController;
    @MockBean
    ReceiptService receiptService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void isReceiptDtoReturned() throws Exception {
        mockMvc.perform(post("/api/receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new PaymentDto()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}