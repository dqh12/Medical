package com.henu.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.henu.cmn.mapper.DictMapper;
import com.henu.model.models.cmn.Dict;
import com.henu.model.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DictMapper dictMapper;
    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    //一行一行读取
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //调用方法添加数据库
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);//将dictEeVo的值复制到dict中
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
