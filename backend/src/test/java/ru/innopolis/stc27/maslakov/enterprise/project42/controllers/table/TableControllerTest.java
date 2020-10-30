package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.table;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.table.TableService;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TableController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
@ExtendWith(SpringExtension.class)
class TableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableService tableService;

    @Test
    @SneakyThrows(Exception.class)
    @WithMockUser
    void tables() {
        val id = UUID.randomUUID();
        val list = Collections.singletonList(new TableDTO(id, 1, TableStatus.RESERVED));
        given(tableService.getTables(null, null)).willReturn(list);

        mockMvc.perform(get("/tables")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }
}