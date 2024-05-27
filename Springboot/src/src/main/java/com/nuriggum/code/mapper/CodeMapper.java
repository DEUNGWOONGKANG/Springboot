package com.nuriggum.code.mapper;

import com.nuriggum.code.model.CodeGroup;
import com.nuriggum.code.model.Code;
import com.nuriggum.nuriframe.common.response.data.RequestData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
    List<CodeGroup> selectCodeGroupList(RequestData<?> codeGroupVo);

    int selectCodeGroupListCount(CodeGroup codeGroup);

    int insertCodeGroup(CodeGroup codeGroup);

    int insertCode(Code code);

    int selectCodeListCount(Code code);

    List<Code> selectCodeList(RequestData<?> requestData);

    boolean selectCodeGroupDuplicate(CodeGroup codeGroup);

    boolean selectCodeDuplicate(Code code);

    int updateCodeGroup(CodeGroup codeGroup);
}
