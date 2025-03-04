<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
//////////////////////////////////////////////////////////////////////////////////
//
//
// ${classData.simpleNamePre} 的 BaseMapping.xml，自动生成，请勿修改。
// 如有需要扩展，请自行创建新的 Mapping.xml。
//
// @author http://usr.ink
// @date ${projectConfig.dateTime}
//
//
//////////////////////////////////////////////////////////////////////////////////
-->
<mapper namespace="${classData.className}">

    <!-- 插入 -->
    <insert id="insert" parameterType="${projectConfig.packageName}.common.entity.model.mysql.${classData.simpleNamePre}Model" keyProperty="${tableModel.primaryFieldModel.name}" useGeneratedKeys="true">
        <![CDATA[ INSERT INTO `${tableModel.tableName}` ]]>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list tableModel.fieldModelList as fieldModel>
            <if test="${fieldModel.name}!=null and ${fieldModel.name}.toString()!=''">
                <![CDATA[ `${fieldModel.name}`, ]]>
            </if>
            </#list>
        </trim>
        <![CDATA[ VALUES ]]>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list tableModel.fieldModelList as fieldModel>
            <if test="${fieldModel.name}!=null and ${fieldModel.name}.toString()!=''">
                <![CDATA[ ${'#'}{${fieldModel.name}}, ]]>
            </if>
            </#list>
        </trim>
    </insert>

    <!-- 批量插入 -->
    <insert id="insertBatch" parameterType="java.util.List">
        <![CDATA[ INSERT INTO `${tableModel.tableName}` ]]>
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list tableModel.fieldModelList as fieldModel>
            <#if !fieldModel.primary>
            `${fieldModel.name}`<#if (tableModel.fieldModelList?size > fieldModel_index + 1)>,</#if>
            </#if>
        </#list>
        </trim>
        <![CDATA[ VALUES ]]>
        <foreach collection="list" item="item" separator=",">
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list tableModel.fieldModelList as fieldModel>
            <#if !fieldModel.isPrimary()>
            ${'#'}{item.${fieldModel.name}}<#if (tableModel.fieldModelList?size > fieldModel_index + 1)>,</#if>
            </#if>
        </#list>
        </trim>
        </foreach>
    </insert>

    <!-- 更新 -->
    <update id="update" parameterType="${projectConfig.packageName}.common.entity.model.mysql.${classData.simpleNamePre}Model">
        <![CDATA[ UPDATE `${tableModel.tableName}` ]]>
        <set>
            <#list tableModel.fieldModelList as fieldModel>
            <#if !fieldModel.isPrimary()>
            <if test="${fieldModel.name}!=null and ${fieldModel.name}.toString()!=''">
                <![CDATA[ `${fieldModel.name}` = ${'#'}{${fieldModel.name}}, ]]>
            </if>
            </#if>
            </#list>
        </set>
        <where>
            <![CDATA[ AND `${tableModel.primaryFieldModel.name}` = ${'#'}{${tableModel.primaryFieldModel.name}} ]]>
        </where>
    </update>

    <!-- 更新 -->
    <update id="updateByMap" parameterType="java.util.Map">
        <![CDATA[ UPDATE `${tableModel.tableName}` ]]>
        <set>
            <#list tableModel.fieldModelList as fieldModel>
            <#if !fieldModel.isPrimary()>
            <if test="${fieldModel.name}!=null and ${fieldModel.name}.toString()!=''">
                <![CDATA[ `${fieldModel.name}` = ${'#'}{${fieldModel.name}}, ]]>
            </if>
            </#if>
            </#list>
        </set>
        <where>
            <![CDATA[ AND `${tableModel.primaryFieldModel.name}` = ${'#'}{${tableModel.primaryFieldModel.name}} ]]>
        </where>
    </update>

    <!-- 删除 -->
    <delete id="delete" parameterType="java.util.Map">
        <![CDATA[ DELETE FROM `${tableModel.tableName}` ]]>
        <where>
        <#list tableModel.fieldModelList as fieldModel>
            <if test="${fieldModel.name}!=null and ${fieldModel.name}.toString()!=''">
            <![CDATA[ AND `${fieldModel.name}` = ${'#'}{${fieldModel.name}} ]]>
            </if>
        </#list>
        </where>
    </delete>

    <!-- 查询记录 -->
    <select id="getModelList" parameterType="java.util.Map" resultType="${projectConfig.packageName}.common.entity.model.mysql.${classData.simpleNamePre}Model">
        <![CDATA[
        SELECT
            <#list tableModel.fieldModelList as fieldModel>
            `${fieldModel.name}`<#if (tableModel.fieldModelList?size>fieldModel_index+1)>,</#if>
            </#list>
        FROM
            `${tableModel.tableName}`
        ]]>
        <where>
        <#list tableModel.fieldModelList as fieldModel>
            <if test="${fieldModel.name}!=null and ${fieldModel.name}.toString()!=''">
                <![CDATA[ AND `${fieldModel.name}` = ${'#'}{${fieldModel.name}} ]]>
            </if>
            <#if fieldModel.primary>
            <if test="${fieldModel.name}s!=null and ${fieldModel.name}s.size() >= 1">
                <![CDATA[ AND `${fieldModel.name}` IN ]]>
                <foreach collection="list" item="item" open="(" separator="," close=")">
                 <![CDATA[ ${"#"}{item} ]]>
                </foreach>
            </if>
            </#if>
        </#list>
        </where>
    </select>

</mapper>