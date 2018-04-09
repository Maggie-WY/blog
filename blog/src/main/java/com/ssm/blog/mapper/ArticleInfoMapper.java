package com.ssm.blog.mapper;

import com.ssm.blog.view.ArticleInfo;
import com.ssm.blog.view.ArticleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int countByExample(ArticleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int deleteByExample(ArticleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int insert(ArticleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int insertSelective(ArticleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    List<ArticleInfo> selectByExampleWithBLOBs(ArticleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    List<ArticleInfo> selectByExample(ArticleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    ArticleInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int updateByExampleSelective(@Param("record") ArticleInfo record, @Param("example") ArticleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") ArticleInfo record, @Param("example") ArticleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int updateByExample(@Param("record") ArticleInfo record, @Param("example") ArticleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int updateByPrimaryKeySelective(ArticleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(ArticleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_info
     *
     * @mbggenerated Wed Apr 04 16:00:39 CST 2018
     */
    int updateByPrimaryKey(ArticleInfo record);
}