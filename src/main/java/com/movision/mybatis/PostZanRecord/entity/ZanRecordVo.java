package com.movision.mybatis.PostZanRecord.entity;

import com.movision.mybatis.comment.entity.CommentVo;
import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.user.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author zhanglei
 * @Date 2017/4/12 11:42
 */
public class ZanRecordVo implements Serializable {


    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }

    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Integer commentid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String title;
    private String postcontent;
    private Integer activetype;
    private Integer type;
    private String coverimg;


    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public Integer getActivetype() {
        return activetype;
    }

    public void setActivetype(Integer activetype) {
        this.activetype = activetype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }

    public Integer getIsactive() {
        return isactive;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CommentVo> comment;

    public List<CommentVo> getComment() {
        return comment;
    }

    public void setComment(List<CommentVo> comment) {
        this.comment = comment;
    }

    private Integer id;
    private Integer ctype;


    private Integer userid;

    private Integer postid;
    private String photo;


    private Date intime;


    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }


    private String nickname;


    private Integer isactive;

    public List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}