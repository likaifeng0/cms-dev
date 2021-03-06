package com.wangyang.model.pojo.vo;

import com.wangyang.model.pojo.dto.SheetDto;

public class SheetDetailVo extends SheetDto {
    private String originalContent;
    private String formatContent;
    private String toc;

    public String getToc() {
        return toc;
    }

    public void setToc(String toc) {
        this.toc = toc;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent;
    }

}
