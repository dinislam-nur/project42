package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.table;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.table.TableService;

@RestController
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping(path = "/tables/{table_id}")
    public TableDTO table(@PathVariable(name = "table_id") Long tableId) {
        return tableService.getTable(tableId);
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String tableNotFoundHandler(IllegalStateException exception) {
        return exception.getMessage();
    }
}
