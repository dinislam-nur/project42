package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.table;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.TableDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.table.TableService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping(path = "/tables/{table_id}")
    public TableDTO table(@PathVariable(name = "table_id") UUID tableId) {
        return tableService.getTable(tableId);
    }

    @PatchMapping(path = "/tables/{table_id}/close")
    public TableDTO close(@PathVariable("table_id") UUID tableId) {
        return tableService.closeTable(tableId);
    }

    @DeleteMapping(path = "/tables/{table_id}")
    public void delete(@PathVariable("table_id") UUID tableId) {
        tableService.deleteTable(tableId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalStateException.class)
    public String tableNotFoundHandler(IllegalStateException exception) {
        return exception.getMessage();
    }
}
