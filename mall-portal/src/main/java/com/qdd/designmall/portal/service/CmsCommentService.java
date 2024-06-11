package com.qdd.designmall.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.CmsProductComment;
import com.qdd.designmall.portal.po.CmsPortalProductCommentPageParam;
import com.qdd.designmall.portal.po.CmsProductCommentCreateParam;

public interface CmsCommentService {
    void create(CmsProductCommentCreateParam param);

    void delete(Long commentId);

    IPage<CmsProductComment> page(CmsPortalProductCommentPageParam param);
}
