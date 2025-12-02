//package nsu.sber.govno.application.commands.dish;
//
//import an.awesome.pipelinr.Command;
//import lombok.RequiredArgsConstructor;
//import nsu.sber.domain.service.DishService;
//import nsu.sber.web.dto.DishInfoResponseDto;
//import nsu.sber.web.mapper.DishDtoMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DishHandler implements Command.Handler<DishCommand, DishInfoResponseDto> {
//
//    private final DishService dishService;
//    private final DishDtoMapper dishDtoMapper;
//
//    @Override
//    public DishInfoResponseDto handle(DishCommand command) {
//        var dish = dishService.getDishInfo(command.getDishId());
//        return dishDtoMapper.dishInfoResponseToDto(dish);
//    }
//}
