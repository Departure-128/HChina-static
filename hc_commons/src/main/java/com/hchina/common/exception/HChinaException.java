package com.hchina.common.exception;

import com.hchina.common.enums.ExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HChinaException extends RuntimeException {
    private ExceptionEnums exceptionEnums;
}
