package nsu.sber.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.restaurant_table.CreateRestaurantTableRequest;
import nsu.sber.domain.model.restaurant_table.RestaurantTableResponse;
import nsu.sber.domain.service.RestaurantTableService;
import nsu.sber.util.ApiConstants;
import nsu.sber.web.dto.CreateRestaurantTableRequestDto;
import nsu.sber.web.dto.CreateRestaurantTableResponseDto;
import nsu.sber.web.dto.RestaurantTableDto;
import nsu.sber.web.dto.RestaurantTableResponseDto;
import nsu.sber.web.dto.UpdateRestaurantTableRequestDto;
import nsu.sber.web.mapper.RestaurantTableDtoMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant-tables")
@Tag(name = "Restaurant Table Controller")
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;
    private final RestaurantTableDtoMapper restaurantTableDtoMapper;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new restaurant table",
            description = "Registers a new table in the Digital Waiter system. " + ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public CreateRestaurantTableResponseDto createRestaurantTable(@RequestBody @Valid CreateRestaurantTableRequestDto dto) {
        CreateRestaurantTableRequest request = restaurantTableDtoMapper.dtoToCreateRestaurantTableRequest(dto);
        return restaurantTableDtoMapper.createRestaurantTableResponseToDto(
                restaurantTableService.createRestaurantTable(request)
        );
    }

    @GetMapping("/")
    @Operation(
            summary = "Get tables by terminal group ID",
            description = "Returns a list of tables filtered by terminal group ID. " +
                    "If the terminalGroupId parameter is not provided, returns all restaurant tables. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public List<RestaurantTableDto> getRestaurantTables(
            @RequestParam(name = "terminalGroupId", required = false) @Min(1) Integer terminalGroupId
    ) {
        return restaurantTableDtoMapper.restaurantTablesToDtoList(
                restaurantTableService.getRestaurantTables(terminalGroupId)
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get table by ID",
            description = "Returns detailed information about a specific table. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public RestaurantTableResponseDto getRestaurantTableById(@PathVariable @Min(1) Integer id) {
        return restaurantTableDtoMapper.restaurantTableResponseToDto(
                restaurantTableService.getDetailedInformationAboutRestaurantTable(id)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a table",
            description = "Deletes a table. " + ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public void deleteRestaurantTableById(@PathVariable @Min(1) Integer id) {
        restaurantTableService.deleteRestaurantTable(id);
    }

    @DeleteMapping("/by-terminal-group/{id}")
    @Operation(
            summary = "Delete all tables of a terminal group",
            description = "Deletes all tables associated with the specified terminal group. " +
                    ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public void deleteRestaurantTablesByTerminalGroupId(@PathVariable @Min(1) Integer id) {
        restaurantTableService.deleteRestaurantTablesByTerminalGroupId(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a table",
            description = "Updates the table information. " + ApiConstants.ADMIN_ACCESS_RESTRICTION
    )
    public RestaurantTableResponseDto updateRestaurantTableById(
            @PathVariable @Min(1) Integer id,
            @RequestBody @Valid UpdateRestaurantTableRequestDto updateRestaurantTableRequestDto
    ) {
        RestaurantTableResponse response = restaurantTableService.updateRestaurantTable(
                id,
                restaurantTableDtoMapper.dtoToUpdateRestaurantTableRequest(updateRestaurantTableRequestDto)
        );

        return restaurantTableDtoMapper.restaurantTableResponseToDto(response);
    }
}
