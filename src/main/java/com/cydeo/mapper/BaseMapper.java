package com.cydeo.mapper;

import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BaseMapper {

    private ModelMapper mapper;

    public BaseMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T convert(Object object, T convert){

        return mapper.map(object,(Type) convert.getClass());

    }
}
