package com.nuriggum.code.service;

import com.nuriggum.code.mapper.CodeMapper;
import com.nuriggum.code.model.Code;
import com.nuriggum.nuriframe.common.message.model.MessageKey;
import com.nuriggum.nuriframe.common.message.util.MessageUtil;
import com.nuriggum.nuriframe.common.response.data.RequestData;
import com.nuriggum.nuriframe.common.util.NuriSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CodeService {

    private final CodeMapper codeMapper;
    private final MessageUtil messageUtil;

    public int codeRegister(Code code) {
        //중복 코드 체크
        if(codeDuplicate(code)) throw new DuplicateKeyException(messageUtil.getMessage(MessageKey.DUPLICATE_ID,null));
        code.setRegistUserId(NuriSecurityUtil.getLoginUserId());
        code.setChangeUserId(NuriSecurityUtil.getLoginUserId());
        return codeMapper.insertCode(code);
    }

    public boolean codeDuplicate(Code code) {
        return codeMapper.selectCodeDuplicate(code);
    }

    public Page<Code> getCodeList(Code code, Pageable pageable) {
        if(code == null) code = new Code();
        //검색 객체 생성
        RequestData<?> requestData = RequestData.builder().data(code).pageable(pageable).build();
        //코드 그룹 목록 조회
        List<Code> codeList = codeMapper.selectCodeList(requestData);
        //코드 그룹 카운트 조회
        int count = codeMapper.selectCodeListCount(code);

        return new PageImpl<>(codeList, pageable, count);
    }
}
