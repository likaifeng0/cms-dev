package com.wangyang.model.pojo.vo;

import com.wangyang.model.pojo.dto.CategoryDto;
import com.wangyang.model.pojo.entity.Template;

import java.util.List;

public class IndexVo {

    private List<CategoryDto> recommend;
    private List<Template> templates;

    public List<CategoryDto> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<CategoryDto> recommend) {
        this.recommend = recommend;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
}
