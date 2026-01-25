package com.cryfirock.account.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cryfirock.account.dto.AccountRequestDto;
import com.cryfirock.account.dto.AccountResponseDto;
import com.cryfirock.account.type.AccountAssets;
import com.cryfirock.account.type.AccountNature;
import com.cryfirock.account.type.AccountOperational;
import com.cryfirock.account.type.AccountStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 1. Pruebas de integración para los endpoints CRUD de cuentas.
 * 2. Valida el flujo completo con persistencia en H2.
 * 3. Comprueba la búsqueda por usuario y eliminación de cuentas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-25
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Flujo CRUD de cuentas")
    class CrudFlowTests {
        @Test
        @DisplayName("Debe ejecutar el flujo CRUD completo con relaciones")
        void shouldExecuteCrudFlow() throws Exception {
            AccountRequestDto createRequest = buildRequest(
                    100L,
                    "USD",
                    new BigDecimal("1000.00"),
                    List.of(10L, 20L),
                    List.of(200L, 201L));

            MvcResult createResult = mockMvc.perform(post("/api/accounts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

            AccountResponseDto created = objectMapper.readValue(
                    createResult.getResponse().getContentAsString(),
                    AccountResponseDto.class);

            assertNotNull(created.id());
            assertEquals(100L, created.ownerId());

            mockMvc.perform(get("/api/accounts/{id}", created.id()))
                    .andExpect(status().isOk());

            AccountRequestDto updateRequest = buildRequest(
                    200L,
                    "EUR",
                    new BigDecimal("2500.50"),
                    List.of(30L),
                    List.of(300L));

            MvcResult updateResult = mockMvc.perform(put("/api/accounts/{id}", created.id())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

            AccountResponseDto updated = objectMapper.readValue(
                    updateResult.getResponse().getContentAsString(),
                    AccountResponseDto.class);

            assertEquals(200L, updated.ownerId());
            assertTrue(updated.userIds().contains(30L));

            MvcResult byUserResult = mockMvc.perform(get("/api/accounts/users/{userId}", 30L))
                    .andExpect(status().isOk())
                    .andReturn();

            AccountResponseDto[] byUserResponses = objectMapper.readValue(
                    byUserResult.getResponse().getContentAsString(),
                    AccountResponseDto[].class);

            assertTrue(byUserResponses.length > 0);

            mockMvc.perform(delete("/api/accounts/{id}", created.id()))
                    .andExpect(status().isNoContent());

            mockMvc.perform(get("/api/accounts/{id}", created.id()))
                    .andExpect(status().isNotFound());
        }
    }

    private AccountRequestDto buildRequest(
            Long ownerId,
            String currency,
            BigDecimal balance,
            List<Long> userIds,
            List<Long> productIds) {
        return new AccountRequestDto(
                ownerId,
                AccountAssets.CRYPTO,
                currency,
                "ES7620770024003102575766",
                balance,
                AccountNature.CUSTOMER,
                AccountOperational.AVAILABLE,
                AccountStatus.ACTIVE,
                userIds,
                productIds);
    }
}
