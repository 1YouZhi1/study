package com.example.study.service.impl;

import com.example.study.core.common.constant.ErrorCodeEnum;
import com.example.study.core.common.resp.RestResp;
import com.example.study.dao.entity.Comments;
import com.example.study.dao.mapper.CommentsMapper;
import com.example.study.dto.req.CommentsReqDto;
import com.example.study.dto.resp.CommentsRespDto;
import com.example.study.manager.cache.CommentsCacheManager;
import com.example.study.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论 实现类
 *
 * @Author YouZhi
 * @Date 2023 - 09 - 17 - 21:06
 */
@Component
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsCacheManager commentsCacheManager;

    private final CommentsMapper commentsMapper;

    @Override
    public RestResp<List<CommentsRespDto>> getComments(Long post_id) {
        return RestResp.ok(commentsCacheManager.getComments(post_id));
    }

    @Override
    public RestResp insertComments(CommentsReqDto commentsReqDto) {

        try{
            Comments comments = new Comments();
            comments.setPostId(commentsReqDto.getPost_id());
            comments.setContent(commentsReqDto.getTitle());
            comments.setCreateTime(LocalDateTime.now());
            comments.setUpdataTime(LocalDateTime.now());
            comments.setIsDeleted(false);
            comments.setLikes(0L);
            commentsMapper.insert(comments);
        }catch (Exception e) {
            e.printStackTrace();
            return RestResp.fail(ErrorCodeEnum.USER_COMMENT);
        }

        return RestResp.ok();
    }
}
