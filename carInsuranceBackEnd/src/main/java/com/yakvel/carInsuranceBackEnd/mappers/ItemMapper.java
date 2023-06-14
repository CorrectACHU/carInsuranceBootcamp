package com.yakvel.carInsuranceBackEnd.mappers;

public interface ItemMapper<S,T>{
    T toEntity(S s);
    S toDto(T t);

}
