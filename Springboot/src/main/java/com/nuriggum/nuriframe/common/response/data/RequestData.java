package com.nuriggum.nuriframe.common.response.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Builder
@Data
public class RequestData<T> {

    private T data;
    private Pageable pageable;
}
