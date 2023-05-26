INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(1, '控制层', 'package ${packageName}.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import base.plus.easydo.web.BaseController;
import ${packageName}.qo.${ClassName}Qo;
import ${packageName}.service.${ClassName}Service;
import ${packageName}.vo.${ClassName}Vo;
import ${packageName}.dto.${ClassName}Dto;
import  ${packageName}.entity.${ClassName};
import plus.easydo.log.SaveLog;
import result.plus.easydo.dao.MpDataResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import result.plus.easydo.common.R;
import vaild.plus.easydo.common.Insert;
import vaild.plus.easydo.common.Update;

import java.util.List;


/**
 * ${functionName}Controller
 *
 * @author ${author}
 * @date ${datetime}
 */
@RestController
@RequestMapping("/${businessName}")
public class ${ClassName}Controller extends BaseController<${ClassName}Service,${ClassName},Long, ${ClassName}Qo,${ClassName}Vo,${ClassName}Dto> {

#if(${isQuery} == 1)
    /**
     * 获取所有${functionName}列表
     */
    @SaCheckPermission("${permissionPrefix}:list")
    @RequestMapping("/list")
    @Override
    public R<List<${ClassName}Vo>> list() {
        return super.list();
    }

    /**
     * 分页条件查询${functionName}列表
     */
    @SaCheckPermission("${permissionPrefix}:page")
    @PostMapping("/page")
    @Override
    public MpDataResult page(@RequestBody ${ClassName}Qo ${className}Qo) {
        return super.page(${className}Qo);
    }


    /**
     * 获取${functionName}详细信息
     */
    @SaCheckPermission("${permissionPrefix}:info")
    @GetMapping("/info/{id}")
    @Override
    public R<${ClassName}Vo> info(@PathVariable("id")Long id) {
        return super.info(id);
    }
#end

#if(${isInsert} == 1)
    /**
     * 新增${functionName}
     */
    @SaCheckPermission("${permissionPrefix}:add")
    @PostMapping("/add")
    @SaveLog(logType = "操作日志", moduleName = "${functionName}", description = "添加${functionName}", actionType = "添加")
    @Override
    public R<Object> add(@RequestBody @Validated({Insert.class}) ${ClassName}Dto ${className}Dto) {
        return super.add(${className}Dto);
    }
#end

#if(${isUpdate} == 1)
    /**
     * 修改${functionName}
     */
    @SaCheckPermission("${permissionPrefix}:update")
    @PostMapping("/update")
    @SaveLog(logType = "操作日志", moduleName = "${functionName}", description = "编辑${functionName}: ?1", expressions = {"#p1.id"},actionType = "编辑")
    @Override
    public R<Object> update(@RequestBody @Validated({Update.class}) ${ClassName}Dto ${className}Dto) {
        return super.update(${className}Dto);
    }
#end

#if(${isRemove} == 1)
    /**
     * 删除${functionName}
     */
    @SaCheckPermission("${permissionPrefix}:remove")
	@GetMapping("/remove/{id}")
    @SaveLog(logType = "操作日志", moduleName = "${functionName}", description = "删除${functionName}: ?1", expressions = {"#p1"}, actionType = "删除")
    @Override
    public R<Object> remove(@PathVariable("id")Long id) {
        return super.remove(id);
    }
#end
}
', 'java', 'private', '1.0.0', 'controller.java.vm', 'main/java/#{packageName}/controller/#{className}Controller.java', NULL, '2022-09-04 15:18:47.000', '2022-10-03 17:35:22.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(2, 'dto.java', 'package ${packageName}.dto;

#foreach ($import in $importList)
import ${import};
#end
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vaild.plus.easydo.common.Insert;
import vaild.plus.easydo.common.Update;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * ${functionName}数据传输对象
 *
 * @author ${author}
 * @date ${datetime}
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ${ClassName}Dto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

#foreach ($column in $columns)
#if(!$table.isSuperColumn($column.javaField))
    /** $column.columnComment */
#if($column.list)
#set($parentheseIndex=$column.columnComment.indexOf("（"))
#if($parentheseIndex != -1)
#set($comment=$column.columnComment.substring(0, $parentheseIndex))
#else
#set($comment=$column.columnComment)
#end

#if($column.isRequired)
#if($column.isInsert)
#if($column.isEdit)
    @NotNull(message = "${comment}必填",groups = {Insert.class, Update.class})
#else
    @NotNull(message = "${comment}必填",groups = {Insert.class})
#end
#elseif($column.isEdit)
    @NotNull(message = "${comment}必填",groups = {Update.class})
#end
#end
#end
    private $column.javaType $column.javaField;

#end
#end
#if($table.sub)
    /** $table.subTable.functionName信息 */
    private List<${subClassName}> ${subclassName}List;

#end
}
', 'java', 'private', '1.0.0', 'dto.java.vm', 'main/java/#{packageName}/dto/#{className}Dto.java', NULL, '2022-09-04 15:18:47.000', '2022-09-19 22:53:42.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(3, 'entity.java', 'package ${packageName}.entity;

#foreach ($import in $importList)
import ${import};
#end
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;


/**
 * ${functionName}数据库映射对象
 *
 * @author ${author}
 * @date ${datetime}
 */
#if($table.crud || $table.sub)
#set($Entity="MPBaseEntity")
#elseif($table.tree)
#set($Entity="TreeEntity")
#end
@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("${tableName}")
public class ${ClassName} implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

#foreach ($column in $columns)
#if(!$table.isSuperColumn($column.javaField))
    /** $column.columnComment */
#if($column.list)
#set($parentheseIndex=$column.columnComment.indexOf("（"))
#if($parentheseIndex != -1)
#set($comment=$column.columnComment.substring(0, $parentheseIndex))
#else
#set($comment=$column.columnComment)
#end
#if($column.isPk == 1)
    @TableId(value = "${column.columnName}" , type = IdType.AUTO)
#end
#if($column.javaType == ''LocalDateTime'')
    @JsonFormat(pattern = "yyyy-MM-dd")
#end
#end
    private $column.javaType $column.javaField;

#end
#end
}
', 'java', 'private', '1.0.0', 'entity.java.vm', 'main/java/#{packageName}/entity/#{className}.java', NULL, '2022-09-04 15:18:47.000', '2022-09-26 16:24:06.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(5, 'mapper.java', 'package ${packageName}.mapper;


import ${packageName}.entity.${ClassName};
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yuzhanfeng
 * @description ${functionName}的数据库操作Mapper
 * @Entity ${packageName}.entity.${ClassName}
 */
@Mapper
public interface ${ClassName}Mapper extends BaseMapper<${ClassName}> {

}
', 'java', 'private', '1.0.0', 'mapper.java.vm', 'main/java/#{packageName}/mapper/#{className}Mapper.java', NULL, '2022-09-04 15:18:47.000', '2022-09-19 22:26:26.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(6, 'qo.java', 'package ${packageName}.qo;

import qo.plus.easydo.dao.MpBaseQo;
import ${packageName}.entity.${ClassName};
#foreach ($import in $importList)
import ${import};
#end

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;


/**
 * ${functionName}查询对象
 *
 * @author ${author}
 * @date ${datetime}
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ${ClassName}Qo extends MpBaseQo<${ClassName}> {

}
', 'java', 'private', '1.0.0', 'qo.java.vm', 'main/java/#{packageName}/qo/#{className}Qo.java', NULL, '2022-09-04 15:18:47.000', '2022-10-03 17:37:53.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(7, 'service.java', 'package ${packageName}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import base.plus.easydo.web.BaseService;
import ${packageName}.dto.${ClassName}Dto;
import ${packageName}.qo.${ClassName}Qo;
import ${packageName}.vo.${ClassName}Vo;
import ${packageName}.entity.${ClassName};


/**
 * ${functionName}Service接口
 *
 * @author ${author}
 * @date ${datetime}
 */
public interface ${ClassName}Service extends BaseService<${ClassName}, ${ClassName}Qo, ${ClassName}Vo, ${ClassName}Dto>{


}
', 'java', 'private', '1.0.0', 'service.java.vm', 'main/java/#{packageName}/service/#{className}Service.java', NULL, '2022-09-04 15:18:47.000', '2022-09-19 22:50:00.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(8, 'serviceImpl.java', 'package ${packageName}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import base.plus.easydo.web.BaseServiceImpl;
import ${packageName}.dto.${ClassName}Dto;
import ${packageName}.entity.${ClassName};
import ${packageName}.qo.${ClassName}Qo;
import ${packageName}.vo.${ClassName}Vo;
import ${packageName}.mapstruct.${ClassName}Mapstruct;
import ${packageName}.mapper.${ClassName}Mapper;
import ${packageName}.service.${ClassName}Service;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;


/**
 * ${functionName}Service层
 *
 * @author ${author}
 * @date ${datetime}
 */
@Service
public class ${ClassName}ServiceImpl extends BaseServiceImpl<${ClassName},${ClassName}Qo, ${ClassName}Vo, ${ClassName}Dto, ${ClassName}Mapper> implements ${ClassName}Service {


    @Override
    public void listSelect(LambdaQueryWrapper<${ClassName}> wrapper) {

    }

    @Override
    public void pageSelect(LambdaQueryWrapper<${ClassName}> wrapper) {

    }

#if(${isQuery} == 1)

    /**
     * 获取所有${functionName}列表
     *
     * @return ${functionName}
     */
    @Override
    public List<${ClassName}Vo> voList() {
        LambdaQueryWrapper<${ClassName}> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(${ClassName}::getCreateTime);
        listSelect(wrapper);
        return ${ClassName}Mapstruct.INSTANCE.entityToVo(list(wrapper));
    }


     /**
     * 分页条件查询${functionName}列表
     *
     * @param mpBaseQo 查询条件封装
     * @return ${functionName}
     */
    @Override
    public IPage<${ClassName}Vo> page(${ClassName}Qo mpBaseQo) {
        mpBaseQo.initInstance(${ClassName}.class);
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(${ClassName}Mapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询${functionName}
     *
     * @param id id
     * @return ${functionName}
     */
    @Override
    public ${ClassName}Vo info(Serializable id) {
        return ${ClassName}Mapstruct.INSTANCE.entityToVo(getById(id));
    }



#end

#if(${isInsert} == 1)
    /**
     * 新增${functionName}
     *
     * @param ${className}Dto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(${ClassName}Dto ${className}Dto) {
        ${ClassName} entity = ${ClassName}Mapstruct.INSTANCE.dtoToEntity(${className}Dto);
        entity.setCreateBy(getUserId());
        return save(entity);
    }
#end

#if(${isUpdate} == 1)
    /**
     * 修改${functionName}
     *
     * @param ${className}Dto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(${ClassName}Dto ${className}Dto) {
        ${ClassName} entity = ${ClassName}Mapstruct.INSTANCE.dtoToEntity(${className}Dto);
        return updateById(entity);
    }
#end

#if(${isRemove} == 1)
    /**
     * 批量删除${functionName}
     *
     * @param id 需要删除的${functionName}ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        return removeById(id);
    }
#end

}
', 'java', 'private', '1.0.0', 'serviceImpl.java.vm', 'main/java/#{packageName}/service/impl/#{className}ServiceImpl.java', NULL, '2022-09-04 15:18:47.000', '2022-09-26 16:41:43.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(9, 'vo.java', 'package ${packageName}.vo;

#foreach ($import in $importList)
import ${import};
#end

import cn.hutool.core.date.DatePattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;

/**
 * ${functionName}数据展示对象
 *
 * @author ${author}
 * @date ${datetime}
 */
#if($table.crud || $table.sub)
#set($Entity="MPBaseEntity")
#elseif($table.tree)
#set($Entity="TreeEntity")
#end
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ${ClassName}Vo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

#foreach ($column in $columns)
#if(!$table.isSuperColumn($column.javaField))
    /** $column.columnComment */
#if($column.list)
#set($parentheseIndex=$column.columnComment.indexOf("（"))
#if($parentheseIndex != -1)
#set($comment=$column.columnComment.substring(0, $parentheseIndex))
#else
#set($comment=$column.columnComment)
#end
#if($parentheseIndex != -1)
#elseif($column.javaType == ''LocalDateTime'')
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
#end
#end
    private $column.javaType $column.javaField;
#end
#end

}
', 'java', 'private', '1.0.0', 'vo.java.vm', 'main/java/#{packageName}/vo/#{className}Vo.java', NULL, '2022-09-04 15:18:47.000', '2022-09-19 22:55:13.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(11, '资源菜单sql', '-- 菜单 SQL

INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}'', ''${moduleName}:${businessName}'', ''${parentMenuId}'', ''M'', 0, ''${moduleName}/${businessName}'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);

-- 父资源ID
SELECT @parentId := LAST_INSERT_ID();


-- 接口 SQL
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-所有列表接口'', ''list'', @parentId, ''I'', 0, ''get:/${moduleName}/${businessName}/list'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-分页查询接口'', ''page'', @parentId, ''I'', 0, ''post:/${moduleName}/${businessName}/page'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-详情接口'', ''info'', @parentId, ''I'', 0, ''get:/${moduleName}/${businessName}/info/{id}'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-新增接口'', ''add'', @parentId, ''I'', 0, ''post:/${moduleName}/${businessName}/add'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-修改接口'', ''update'', @parentId, ''I'', 0, ''post:/${moduleName}/${businessName}/update'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-删除接口'', ''remove'', @parentId, ''I'', 0, ''get:/${moduleName}/${businessName}/remove/{id}'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);



-- 按钮 SQL
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-详情按钮'', ''info'', @parentId, ''A'', 0, ''${moduleName}/${businessName}/info'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-新增按钮'', ''add'', @parentId, ''A'', 0, ''${moduleName}/${businessName}/add'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-编辑按钮'', ''update'', @parentId, ''A'', 0, ''${moduleName}/${businessName}/update'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
INSERT INTO sys_resource (````resource_name````, ````resource_code````, ````parent_id````, ````resource_type````, ````order_number````, ````path````, ````param````, ````is_cache````, ````status````, ````icon````, ````create_by````, ````create_time````, ````update_by````, ````update_time````, ````remark````, ````del_flag````)
VALUES (''${functionName}-删除按钮'', ''remove'', @parentId, ''A'', 0, ''${moduleName}/${businessName}/remove'', '''', 0, 0, '''', 0, sysdate(), 0, sysdate(), '''', 0);
', 'java', 'private', '1.0.0', 'sql.vm', '#{businessName}Resource.sql', NULL, '2022-09-04 15:18:47.000', '2022-09-23 10:29:44.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(14, '模板属性说明', '## 模板变量信息说明 --
## ${tplCategory} 生成的类型
## ${tableName} 数据表名称
## ${functionName} 程序名称
## ${ClassName} 类名-首字母大写
## ${className} 类名-首字母小写
## ${moduleName} 模块名称
## ${BusinessName} 业务名称 -首字母大写
## ${businessName} 业务名称 -首字母小写
## ${basePackage} 包前缀 如 xxx.xx.x 的 xxx
## ${packageName} 包路径
## ${author} 作者
## ${datetime} 当前时间-模板运行时的时间
## ${pkColumn} 主键字段
## ${importList} 需要导入包的列表
## 如果字段类型包含 LOCAL_DATE_TIME 则导入 java.time.LocalDateTime com.fasterxml.jackson.annotation.JsonFormat
## 如果所有列包含BigDecimal 则导入java.math.BigDecimal
## 如果生成的类型是是数据结构则导入 java.util.List
## ${permissionPrefix} 更具模块名及业务名自动生成的权限字符   moduleName：businessName：
## ${columns} 表的所有字段集合
## ${isQuery} 是否生成更新代码
## ${isInsert} 是否生成插入代码
## ${isUpdate} 是否生成更新代码
## ${isRemove} 是否生成删除代码
## ${isWrapper} 是否生成Wrapper代码
## ${isManager} 是否生成manager层级

## ${table} GenTable 表对象属性说明

##  编号  tableId
##  表名称  tableName
##  表描述  tableComment
##  关联父表的表名  subTableName
##  本表关联父表的外键名  subTableFkName
##  实体类名称(首字母大写) className
##  生成的类型（crud单表操作 tree树表操作 sub主子表操作） tplCategory
##  生成包路径  packageName
##  生成模块名 moduleName
##  生成业务名  businessName
##  生成功能名  functionName
##  作者  functionAuthor
##  生成代码方式（0zip压缩包 1自定义路径）  genType
##  生成路径（不填默认项目路径） genPath
##  主键信息  类型 GenTableColumn  pkColumn
##  子表信息  类型 GenTable subTable
##  表列信息 List<GenTableColumn> columns
##  其它生成选项  options
##  树编码字段  treeCode
##  树父编码字段  treeParentCode
##  树名称字段  treeName
##  上级菜单ID字段 parentMenuId
##  级菜单名称字段 parentMenuName
##  是否生成查询 isQuery
##  是否生成插入  isInsert
##  是否生成更新  isUpdate
##  是否生成删除  isRemove
##  是否生成Lambda条件语句 isWrapper
##  是否生成Manager层 isManager
##  使用的模板id  templateIds

## ${table.pkColumn} GenTableColumn 主键信息属性说明

 ##     编号 columnId
 ##    归属表编号 tableId
 ##    列名称  columnName
 ##    列描述  columnComment
 ##    列类型  columnType
 ##    JAVA类型   javaType
 ##    JAVA字段名  javaField
 ##    是否主键（1是） isPk
 ##    是否必填（1是） isRequired
 ##    是否为插入字段（1是）  isInsert
 ##    是否编辑字段（1是）   isEdit
 ##    是否列表字段（1是）   isList
 ##    是否查询字段（1是）   isQuery
 ##    查询方式 queryType（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
 ##    显示类型 htmlType（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）
 ##    字典类型 dictType
 ##    排序   sort', 'java', 'private', '1.0.0', 'demo.vm', '/', NULL, '2022-09-04 15:18:47.000', '2022-09-04 16:24:24.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(20, '国际化语言', 'const i18n = {
  ''en-US'': {
    ''list'': ''List'',
    ''list.searchTable'': ''Search Table'',
    ''searchTable.info.title'': ''Details'',
    ''searchTable.update.title'': ''Update'',
    ''searchTable.form.search'': ''Search'',
    ''searchTable.form.reset'': ''Reset'',
#foreach ($column in $columns)
    ''searchTable.columns.${column.javaField}'': ''${column.javaField}'',
#end
    ''searchTable.columns.operations'': ''Operation'',
    ''searchTable.columns.operations.view'': ''View'',
    ''searchTable.columns.operations.update'': ''Edit'',
    ''searchTable.columns.operations.remove'': ''Remove'',
    ''searchTable.columns.operations.remove.confirm'': ''Are you sure you want to delete?'',
    ''searchTable.operations.add'': ''New'',
    ''searchForm.all.placeholder'': ''all'',
#foreach ($column in $columns)
    ''searchForm.${column.javaField}.placeholder'': ''Please enter the ${column.javaField}'',
#end
#foreach ($column in $columns)
    ''searchTable.rules.${column.javaField}.required'': ''${column.javaField} Can not be empty'',
#end
  },
  ''zh-CN'': {
    ''list'': ''列表页'',
    ''list.searchTable'': ''条件搜索'',
    ''searchTable.info.title'': ''详情'',
    ''searchTable.update.title'': ''编辑'',
    ''searchTable.form.search'': ''查询'',
    ''searchTable.form.reset'': ''重置'',
#foreach ($column in $columns)
    ''searchTable.columns.${column.javaField}'': ''${column.columnComment}'',
#end
    ''searchTable.columns.operations'': ''操作'',
    ''searchTable.columns.operations.view'': ''查看'',
    ''searchTable.columns.operations.update'': ''修改'',
    ''searchTable.columns.operations.remove'': ''删除'',
    ''searchTable.columns.operations.remove.confirm'': ''确定要删除吗?'',
    ''searchTable.operations.add'': ''新建'',
    ''searchForm.all.placeholder'': ''全部'',
#foreach ($column in $columns)
    ''searchForm.${column.javaField}.placeholder'': ''请输入${column.columnComment}'',
#end
#foreach ($column in $columns)
    ''searchTable.rules.${column.javaField}.required'': ''${column.columnComment}不能为空'',
#end
  },
};

export default i18n;

', '通用', 'private', '1.0.0', 'index.tsx.vm', 'locale/index.tsx', NULL, '2022-09-12 01:05:24.000', '2022-09-19 18:21:06.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(21, '表格查询常量', 'import React from ''react'';
import { Button, Typography, Badge, Popconfirm } from ''@arco-design/web-react'';
import { SearchTypeEnum } from ''@/utils/systemConstant'';
import PermissionWrapper from ''@/components/PermissionWrapper'';
import { dictLabelEnum, getDictList } from ''@/utils/dictDataUtils'';

export const statusEnum = dictLabelEnum(''status_select'',''string'')

export interface DataInfoVo{
#foreach ($column in $columns)
    ${column.javaField}: string,
#end
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
#foreach ($column in $columns)
#if($column.isList)
    ''${column.javaField}'',
#end
#end
      ];
}

// 搜索配置
export function searchConfig() {
  return {
#foreach ($column in $columns)
#if($column.isQuery == "1")
        ''${column.javaField}'': SearchTypeEnum.${column.queryType},
#end
#end
  }
}

//默认排序字段
export function getDefaultOrders(){
  return [{column: ''createTime'', asc: false}];
}


//表单展示字段
export function getColumns(
  t: any,
  callback: (record: Record<string, any>, type: string) => Promise<void>
) {
  return [
#foreach ($column in $columns)
#if($column.isList == "1")

    {
      title: t[''searchTable.columns.${column.javaField}''],
      dataIndex: ''${column.javaField}'',
      ellipsis:true,
    },
#end
#end
    {
      title: t[''searchTable.columns.operations''],
      dataIndex: ''operations'',
      headerCellStyle: { paddingLeft: ''15px'' },
      render: (_, record) => (
        <div>
#if($isQuery == "1")
          <PermissionWrapper
            requiredPermissions={[
              { resource: ''${moduleName}:${businessName}'', actions: [''info''] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, ''view'')}
            >
                {t[''searchTable.columns.operations.view'']}
            </Button>
          </PermissionWrapper>
#end
#if($isUpdate == "1")
          <PermissionWrapper
            requiredPermissions={[
              { resource: ''${moduleName}:${businessName}'', actions: [''update''] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, ''update'')}
            >
                {t[''searchTable.columns.operations.update'']}
            </Button>
          </PermissionWrapper>
#end
#if($isRemove == "1")
          <PermissionWrapper
            requiredPermissions={[
              { resource: ''${moduleName}:${businessName}'', actions: [''remove''] },
            ]}
          >
            <Popconfirm
                title={t[''searchTable.columns.operations.remove.confirm'']}
                onOk={() => callback(record, ''remove'')}
            >
                <Button type="text" status="warning" size="small">
                {t[''searchTable.columns.operations.remove'']}
                </Button>
            </Popconfirm>
          </PermissionWrapper>
#end
        </div>
      ),
    },
  ];
}

', '通用', 'private', '1.0.0', 'constants.tsx.vm', 'constants.tsx', NULL, '2022-09-12 01:05:54.000', '2022-10-08 15:33:54.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(22, '搜索表单', 'import React, { useContext } from ''react'';
import {
  Form,
  Input,
  Select,
  DatePicker,
  Button,
  Grid,
} from ''@arco-design/web-react'';
import { GlobalContext } from ''@/context'';
import locale from ''./locale'';
import useLocale from ''@/utils/useLocale'';
import { IconRefresh, IconSearch } from ''@arco-design/web-react/icon'';
import styles from ''./style/index.module.less'';
import { statusEnum } from ''./constants'';
import DictDataSelect from ''@/components/DictCompenent/dictDataSelect'';

const { Row, Col } = Grid;
const { useForm } = Form;
const TextArea = Input.TextArea;

function SearchForm(props: {
  onSearch: (values: Record<string, any>) => void;
}) {


  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const [form] = useForm();

  const handleSubmit = () => {
    const values = form.getFieldsValue();
    props.onSearch(values);
  };

  const handleReset = () => {
    form.resetFields();
    props.onSearch({});
  };

  const colSpan = lang === ''zh-CN'' ? 8 : 12;

  return (
    <div className={styles[''search-form-wrapper'']}>
      <Form
        form={form}
        className={styles[''search-form'']}
        labelAlign="left"
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 19 }}
      >
        <Row gutter={24}>
#foreach ($column in $columns)
#if($column.isQuery == "1")
#if($column.dictType != "")
          <Col span={colSpan}>
            <Form.Item
              label={t[''searchTable.columns.${column.javaField}'']}
              field="${column.javaField}"
            >
              <DictDataSelect dictCode={''${column.dictType}''} placeholder={t[''searchForm.${column.javaField}.placeholder'']} />
            </Form.Item>
          </Col>
#elseif($column.htmlType == "input")
          <Col span={colSpan}>
            <Form.Item
              label={t[''searchTable.columns.${column.javaField}'']}
              field="${column.javaField}"
            >
              <Input placeholder={t[''searchForm.${column.javaField}.placeholder'']} allowClear />
            </Form.Item>
          </Col>
#elseif($column.htmlType == "textarea")
          <Col span={colSpan}>
            <Form.Item
              label={t[''searchTable.columns.${column.javaField}'']}
              field="${column.javaField}"
            >
              <TextArea placeholder={t[''searchForm.${column.javaField}.placeholder'']} />
            </Form.Item>
          </Col>
#elseif(($column.htmlType == "select" || $column.htmlType == "radio"))
          <Col span={colSpan}>
            <Form.Item
              label={t[''searchTable.columns.${column.javaField}'']}
              field="${column.javaField}"
            >
              <Select
                placeholder={t[''searchForm.${column.javaField}.placeholder'']}
                options={statusEnum.map((item, index) => ({
                  label: item,
                  value: index,
                }))}
                // mode="multiple"
                allowClear
              />
            </Form.Item>
          </Col>
#elseif($column.htmlType == "datetime" && $column.queryType != "BETWEEN")
          <Col span={colSpan}>
            <Form.Item
              label={t[''searchTable.columns.${column.javaField}'']}
              field="${column.javaField}"
            >
              <DatePicker.RangePicker
                allowClear
                style={{ width: ''100%'' }}
                disabledDate={(date) => dayjs(date).isAfter(dayjs())}
              />
            </Form.Item>
          </Col>
#end
#end
#end
        </Row>
      </Form>
      <div className={styles[''right-button'']}>
        <Button type="primary" icon={<IconSearch />} onClick={handleSubmit}>
          {t[''searchTable.form.search'']}
        </Button>
        <Button icon={<IconRefresh />} onClick={handleReset}>
          {t[''searchTable.form.reset'']}
        </Button>
      </div>
    </div>
  );
}

export default SearchForm;
', '通用', 'private', '1.0.0', 'form.tesx.vm', 'form.tsx', NULL, '2022-09-12 01:06:27.000', '2022-10-08 15:37:47.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(23, '表格index', 'import React, { useState, useEffect, useMemo } from ''react'';
import {
  Table,
  Card,
  PaginationProps,
  Button,
  Space,
  Typography,
  Notification,
} from ''@arco-design/web-react'';
import PermissionWrapper from ''@/components/PermissionWrapper'';
import { IconDownload, IconPlus } from ''@arco-design/web-react/icon'';
import useLocale from ''@/utils/useLocale'';
import SearchForm from ''./form'';
import locale from ''./locale'';
import styles from ''./style/index.module.less'';
import { getColumns, getDefaultOrders, getSearChColumns, searchConfig } from ''./constants'';
import { managerPage, removeRequest } from ''@/api/${businessName}'';
import { SearchTypeEnum } from ''@/utils/systemConstant'';
import { SorterResult } from ''@arco-design/web-react/es/Table/interface'';
#if($isQuery == "1")
import InfoPage from ''./info'';
#end
#if($isUpdate == "1")
import UpdatePage from ''./update'';
#end
#if($isInsert == "1")
import AddPage from ''./add'';
#end

const { Title } = Typography;

function SearchTable() {
  const t = useLocale(locale);

  //表格操作按钮回调
  const tableCallback = async (record, type) => {
#if($isQuery == "1")
    //查看
    if (type === ''view'') {
      viewInfo(record.${pkColumn.javaField});
    }
#end

#if($isUpdate == "1")
    //编辑
    if (type === ''update'') {
      updateInfo(record.${pkColumn.javaField});
    }
#end

#if($isRemove == "1")
    //删除
    if (type === ''remove'') {
      removeData(record.${pkColumn.javaField});
    }
#end
  };


#if($isQuery == "1")
  //查看
  const [viewInfoId, setViewInfoId] = useState();
  const [isViewInfo, setisViewInfo] = useState(false);

  function viewInfo(id) {
    setViewInfoId(id);
    setisViewInfo(true);
  }
#end

#if($isInsert == "1")
  //新增
  const [isAddData, setIsAddData] = useState(false);

  function addData(){
    setIsAddData(true);
  }

  function addDataSuccess() {
    setIsAddData(false);
    fetchData();
  }
#end

#if($isUpdate == "1")
  const [updateId, setUpdateId] = useState();
  const [isUpdateInfo, setisUpdateInfo] = useState(false);

  //编辑
  function updateInfo(id) {
    setUpdateId(id);
    setisUpdateInfo(true);
  }

  function updateSuccess() {
    setisUpdateInfo(false);
    fetchData();
  }
#end

#if($isRemove == "1")
  //删除
  function removeData(id){
    removeRequest(id).then((res)=>{
      const { success, msg} = res.data
      if(success){
        Notification.success({ content: msg, duration: 300 })
        fetchData();
      }
    })
  }
#end

  //获取表格展示列表、绑定操作列回调
  const columns = useMemo(() => getColumns(t, tableCallback), [t]);

  const [data, setData] = useState([]);
  const [pagination, setPatination] = useState<PaginationProps>({
    sizeCanChange: true,
    showTotal: true,
    pageSize: 10,
    current: 1,
    pageSizeChangeResetCurrent: true,
  });
  const [loading, setLoading] = useState(true);
  const [formParams, setFormParams] = useState({});
  const [orders, setOrders] = useState(getDefaultOrders());

  useEffect(() => {
    fetchData();
  }, [
    pagination.current,
    pagination.pageSize,
    JSON.stringify(formParams),
    orders,
  ]);

  // 获取数据
  function fetchData() {
    const { current, pageSize } = pagination;
    setLoading(true);
    managerPage({
      currentPage: current,
      pageSize,
      searchParam: formParams,
      orders: orders,
      columns: getSearChColumns(),
      searchConfig: searchConfig(),
    }).then((res) => {
      setData(res.data.data);
      setPatination({
        ...pagination,
        current,
        pageSize,
        total: res.data.total,
      });
      setLoading(false);
    });
  }

  //表格搜索排序回调函数
  function onChangeTable(
    pagination: PaginationProps,
    sorter: SorterResult,
    _filters: Partial<any>,
    _extra: {
      currentData: any[];
      action: ''paginate'' | ''sort'' | ''filter'';
    }
  ) {
    setPatination({
      ...pagination,
      current: pagination.current,
      pageSize: pagination.pageSize,
    });
    if (sorter) {
      if (sorter.direction === ''ascend'') {
        setOrders([{ column: sorter.field, asc: true }]);
      } else if (sorter.direction === ''descend'') {
        setOrders([{ column: sorter.field, asc: false }]);
      } else if (sorter.direction === undefined) {
        setOrders(getDefaultOrders());
      }
    }
  }

  //点击搜索按钮
  function handleSearch(params) {
    setPatination({ ...pagination, current: 1 });
    setFormParams(params);
  }

  return (
    <Card>
      <Title heading={6}>{t[''list.searchTable'']}</Title>
      <SearchForm onSearch={handleSearch} />
      <PermissionWrapper
        requiredPermissions={[
          { resource: ''${moduleName}:${businessName}'', actions: [''add''] },
        ]}
      >
        <div className={styles[''button-group'']}>
          <Space>
            <Button type="primary" icon={<IconPlus />} onClick={()=>addData()}>
              {t[''searchTable.operations.add'']}
            </Button>
          </Space>
        </div>
      </PermissionWrapper>
      <Table
        rowKey="id"
        loading={loading}
        onChange={onChangeTable}
        pagination={pagination}
        columns={columns}
        data={data}
      />
#if($isInsert == "1")
      <AddPage
        visible={isAddData}
        setVisible={setIsAddData}
        successCallBack={addDataSuccess}
      />
#end
#if($isQuery == "1")
      <InfoPage
        id={viewInfoId}
        visible={isViewInfo}
        setVisible={setisViewInfo}
      />
#end
#if($isUpdate == "1")
      <UpdatePage
        id={updateId}
        visible={isUpdateInfo}
        setVisible={setisUpdateInfo}
        successCallBack={updateSuccess}
      />
#end
    </Card>
  );
}

export default SearchTable;
', '通用', 'private', '1.0.0', 'index.tsx.vm', 'index.tsx', NULL, '2022-09-12 01:07:01.000', '2022-09-21 21:16:35.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(24, '详情页', 'import React, { useEffect, useState } from ''react'';
import { Descriptions, Modal, Skeleton } from ''@arco-design/web-react'';
import locale from ''./locale'';
import useLocale from ''@/utils/useLocale'';
import { infoRequest } from ''@/api/${businessName}'';
import { DataInfoVo } from ''./constants'';


function InfoPage(props: {id:number,visible,setVisible}) {

    const [loading,setLoading] = useState(false)

    const [infoData, setInfoData] = useState<DataInfoVo>();

    function fetchData() {
      setLoading(true)
      if (props.id !== undefined && props.visible) {
        infoRequest(props.id).then((res) => {
          const { success, data } = res.data;
          if (success) {
            setInfoData(data);
          }
          setLoading(false)
        });
      }
    }

    useEffect(() => {
        fetchData();
      }, [props.id,props.visible]);

  const t = useLocale(locale);

  const data = [
#foreach ($column in $columns)
#if($column.isInfo == "1")
    {
      label: t[''searchTable.columns.${column.javaField}''],
      value: loading? <Skeleton text={{ rows: 1, style: { width: ''200px'' } }} animation /> : infoData? infoData.${column.javaField}:'''',
    },
#end
#end
  ];

  return (
    <Modal
      title={t[''searchTable.info.title'']}
      visible={props.visible}
      onOk={() => {
        props.setVisible(false);
      }}
      onCancel={() => {
        props.setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <Descriptions
        column={1}
        data={data}
        style={{ marginBottom: 20 }}
        labelStyle={{ paddingRight: 36 }}
      />
    </Modal>

  );
}

export default InfoPage;
', '通用', 'private', '1.0.0', 'info.tsx.vm', 'info.tsx', NULL, '2022-09-12 01:07:38.000', '2022-09-27 17:15:25.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(25, '样式', '.toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
}

.operations {
  display: flex;
}

.content-type {
  display: flex;

  > svg {
    margin-right: 8px;
    margin-top: 3px;
  }
}

.search-form-wrapper {
  display: flex;
  border-bottom: 1px solid var(--color-border-1);
  margin-bottom: 20px;

  .right-button {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding-left: 20px;
    margin-bottom: 20px;
    border-left: 1px solid var(--color-border-2);
    box-sizing: border-box;
  }
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.search-form {
  padding-right: 20px;

  :global(.arco-form-label-item-left) {
    > label {
      white-space: nowrap;
    }
  }
}
', '通用', 'private', '1.0.0', 'index.module.less.vm', 'style/index.module.less', NULL, '2022-09-12 23:08:49.000', '2022-09-12 23:12:25.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(26, '编辑页面', 'import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification } from ''@arco-design/web-react'';
import locale from ''./locale'';
import useLocale from ''@/utils/useLocale'';
import { updateRequest, infoRequest } from ''@/api/${businessName}'';
import { GlobalContext } from ''@/context'';
import { Status } from ''./constants'';
import { useContext, useEffect, useRef } from ''react'';
import React from ''react'';
import DictDataSelect from ''@/components/DictCompenent/dictDataSelect'';

function UpdatePage({ id, visible, setVisible, successCallBack }) {

  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);

  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true)
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          formRef.current.setFieldsValue(data);
        }
        setLoading(false)
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id,visible]);

  const t = useLocale(locale);

  //提交修改
  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      updateRequest(values).then((res) => {
        const { success, msg} = res.data
        if(success){
          Notification.success({ content: msg, duration: 300 })
          successCallBack();
        }
      });
    });
  };

  return (
    <Modal
      title={t[''searchTable.update.title'']}
      visible={visible}
      onOk={() => {
        handleSubmit();
      }}
      onCancel={() => {
        setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <Form
        style={{ width: ''95%'', marginTop: ''6px'' }}
        labelCol={{ span: lang === ''en-US'' ? 7 : 6 }}
        wrapperCol={{ span: lang === ''en-US'' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
      >
      <Spin tip=''loading Data...'' loading={loading}>
#foreach ($column in $columns)
#if($column.isEdit == "1")
        <Form.Item
          label={t[''searchTable.columns.${column.javaField}'']}
          field="${column.javaField}"
#if($column.isPk == "1")
          hidden
#end
#if($column.isRequired == "1")
          rules={[
            { required: true, message: t[''searchTable.rules.${column.javaField}.required''] },
          ]}
#end
        >
#if($column.dictType != "")
           <DictDataSelect dictCode={''${column.dictType}''} placeholder={t[''searchForm.${column.javaField}.placeholder'']} />
#elseif($column.htmlType == "input")
          <Input placeholder={t[''searchForm.${column.javaField}.placeholder'']} allowClear />
#elseif($column.htmlType == "textarea")
          <TextArea placeholder={t[''searchForm.${column.javaField}.placeholder'']} />
#elseif(($column.htmlType == "select" || $column.htmlType == "radio"))
          <Select
            placeholder={t[''searchForm.${column.javaField}.placeholder'']}
            options={Status.map((item, index) => ({
              label: item,
              value: index,
            }))}
            // mode="multiple"
            allowClear
          />
#elseif($column.htmlType == "datetime" && $column.queryType != "BETWEEN")
          <DatePicker
            style={{ width: ''100%'' }}
            showTime={{
              defaultValue: ''04:05:06'',
            }}
            format=''YYYY-MM-DD HH:mm:ss''
          />
#end
        </Form.Item>
#end
#end
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;', '通用', 'private', '1.0.0', 'update.tsx.vm', 'update.tsx', NULL, '2022-09-12 23:09:24.000', '2022-09-27 17:14:05.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(27, '新增页面', 'import React, { useContext, useRef } from ''react'';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from ''@arco-design/web-react'';
import locale from ''./locale'';
import useLocale from ''@/utils/useLocale'';
import { addRequest } from ''@/api/${businessName}'';
import { GlobalContext } from ''@/context'';
import DictDataSelect from ''@/components/DictCompenent/dictDataSelect'';

function AddPage({ visible, setVisible, successCallBack }) {

  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      addRequest(values).then((res) => {
        const { success, msg} = res.data
        if(success){
          Notification.success({ content: msg, duration: 300 })
          successCallBack();
        }
      });
    });
  };

  return (
    <Modal
      title={t[''searchTable.operations.add'']}
      visible={visible}
      onOk={() => {
        handleSubmit();
      }}
      onCancel={() => {
        setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <Form
        ref={formRef}
        style={{ width: ''95%'', marginTop: ''6px'' }}
        labelCol={{ span: lang === ''en-US'' ? 7 : 6 }}
        wrapperCol={{ span: lang === ''en-US'' ? 17 : 18 }}
        labelAlign="left"
      >
#foreach ($column in $columns)
#if($column.isInsert == "1")
        <Form.Item
          label={t[''searchTable.columns.${column.javaField}'']}
          field="${column.javaField}"
#if($column.isRequired == "1")
          rules={[
            { required: true, message: t[''searchTable.rules.${column.javaField}.required''] },
          ]}
#end
        >
#if($column.dictType != "")
           <DictDataSelect dictCode={''${column.dictType}''} placeholder={t[''searchForm.${column.javaField}.placeholder'']} />
#elseif($column.htmlType == "input")
          <Input placeholder={t[''searchForm.${column.javaField}.placeholder'']} allowClear />
#elseif($column.htmlType == "textarea")
          <TextArea placeholder={t[''searchForm.${column.javaField}.placeholder'']} />
#elseif(($column.htmlType == "select" || $column.htmlType == "radio"))
          <DictDataSelect
            placeholder={t[''searchForm.${column.javaField}.placeholder'']}
            dictCode={''status_select''}
          />
#elseif($column.htmlType == "datetime" && $column.queryType != "BETWEEN")
          <DatePicker
            style={{ width: ''100%'' }}
            showTime={{
              defaultValue: ''04:05:06'',
            }}
            format=''YYYY-MM-DD HH:mm:ss''
          />
#end
        </Form.Item>
#end
#end
      </Form>
    </Modal>
  );
}

export default AddPage;', '通用', 'private', '1.0.0', 'add.tsx.vm', 'add.tsx', NULL, '2022-09-12 23:10:02.000', '2022-10-08 15:37:18.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(28, 'api.js', 'import { get,postRequestBody} from "../utils/request"

export const list = () => get("/${moduleName}/${businessName}/list");
export const infoRequest = (id) => get("/${moduleName}/${businessName}/info/" + id);
export const removeRequest = (id) => get("/${moduleName}/${businessName}/remove/" + id);
export const managerPage = (param) => postRequestBody("/${moduleName}/${businessName}/page",param);
export const addRequest = (param) => postRequestBody("/${moduleName}/${businessName}/add",param);
export const updateRequest = (param) => postRequestBody("/${moduleName}/${businessName}/update",param);', '通用', 'private', '1.0.0', 'api.js', 'api/#{businessName}.js', NULL, '2022-09-12 23:11:15.000', '2022-09-20 00:08:31.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(30, 'mapstruct.java', 'package ${packageName}.mapstruct;

import ${packageName}.entity.${ClassName};
import ${packageName}.vo.${ClassName}Vo;
import ${packageName}.dto.${ClassName}Dto;
import plus.easydo.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.io.Serializable;
import java.util.List;



/**
 * ${functionName}转换类
 * @author ${author}
 * @date ${datetime}
 */
@Mapper
public interface ${ClassName}Mapstruct extends BaseMapstruct<${ClassName}, ${ClassName}Vo, ${ClassName}Dto> {

    ${ClassName}Mapstruct INSTANCE = Mappers.getMapper(${ClassName}Mapstruct.class);

}', 'java', 'private', '1.0.0', 'Mapstruct.java.vm', 'main/java/#{packageName}/mapstruct/#{className}Mapstruct.java', NULL, '2022-09-19 22:05:46.000', '2022-09-19 22:09:10.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(42, '数据库文档1', '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<?mso-application progid="Word.Document"?>
<pkg:package xmlns:pkg="http://schemas.microsoft.com/office/2006/xmlPackage">
    <pkg:part pkg:name="/_rels/.rels" pkg:contentType="application/vnd.openxmlformats-package.relationships+xml"
              pkg:padding="512">
        <pkg:xmlData>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId3"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties"
                              Target="docProps/app.xml"/>
                <Relationship Id="rId2"
                              Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties"
                              Target="docProps/core.xml"/>
                <Relationship Id="rId1"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument"
                              Target="word/document.xml"/>
                <Relationship Id="rId4"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/custom-properties"
                              Target="docProps/custom.xml"/>
            </Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/document.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml">
        <pkg:xmlData>
            <w:document xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas"
                        xmlns:cx="http://schemas.microsoft.com/office/drawing/2014/chartex"
                        xmlns:cx1="http://schemas.microsoft.com/office/drawing/2015/9/8/chartex"
                        xmlns:cx2="http://schemas.microsoft.com/office/drawing/2015/10/21/chartex"
                        xmlns:cx3="http://schemas.microsoft.com/office/drawing/2016/5/9/chartex"
                        xmlns:cx4="http://schemas.microsoft.com/office/drawing/2016/5/10/chartex"
                        xmlns:cx5="http://schemas.microsoft.com/office/drawing/2016/5/11/chartex"
                        xmlns:cx6="http://schemas.microsoft.com/office/drawing/2016/5/12/chartex"
                        xmlns:cx7="http://schemas.microsoft.com/office/drawing/2016/5/13/chartex"
                        xmlns:cx8="http://schemas.microsoft.com/office/drawing/2016/5/14/chartex"
                        xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                        xmlns:aink="http://schemas.microsoft.com/office/drawing/2016/ink"
                        xmlns:am3d="http://schemas.microsoft.com/office/drawing/2017/model3d"
                        xmlns:o="urn:schemas-microsoft-com:office:office"
                        xmlns:oel="http://schemas.microsoft.com/office/2019/extlst"
                        xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                        xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                        xmlns:v="urn:schemas-microsoft-com:vml"
                        xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing"
                        xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
                        xmlns:w10="urn:schemas-microsoft-com:office:word"
                        xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                        xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                        xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml"
                        xmlns:w16cex="http://schemas.microsoft.com/office/word/2018/wordml/cex"
                        xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid"
                        xmlns:w16="http://schemas.microsoft.com/office/word/2018/wordml"
                        xmlns:w16sdtdh="http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash"
                        xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex"
                        xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup"
                        xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk"
                        xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
                        xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"
                        mc:Ignorable="w14 w15 w16se w16cid w16 w16cex w16sdtdh wp14">
                <w:body>
                    #foreach ($t in $tables)
                    <w:p w14:paraId="640BB1A3" w14:textId="77777777" w:rsidR="00000000" w:rsidRDefault="00000000">
                        <w:pPr>
                            <w:ind w:firstLine="480"/>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                        </w:pPr>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                            <w:t>${t.tableComment}</w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                            <w:t xml:space="preserve"> </w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                            <w:t>${t.tableName}</w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                            <w:t>：</w:t>
                        </w:r>
                    </w:p>
                    <w:p w14:paraId="2FAA9A93" w14:textId="77777777" w:rsidR="00000000" w:rsidRDefault="00000000">
                        <w:pPr>
                            <w:ind w:firstLine="420"/>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="21"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                        </w:pPr>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="21"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                            <w:t>表</w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="21"/>
                            </w:rPr>
                            <w:t>5</w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="21"/>
                            </w:rPr>
                            <w:t xml:space="preserve">.1.5.1-1 </w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="21"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                            <w:t>${t.tableComment}</w:t>
                        </w:r>
                    </w:p>
                    <w:tbl>
                        <w:tblPr>
                            <w:tblW w:w="5000" w:type="pct"/>
                            <w:jc w:val="center"/>
                            <w:tblInd w:w="0" w:type="dxa"/>
                            <w:tblBorders>
                                <w:top w:val="single" w:sz="4" w:space="0" w:color="auto"/>
                                <w:left w:val="single" w:sz="4" w:space="0" w:color="auto"/>
                                <w:bottom w:val="single" w:sz="4" w:space="0" w:color="auto"/>
                                <w:right w:val="single" w:sz="4" w:space="0" w:color="auto"/>
                                <w:insideH w:val="single" w:sz="4" w:space="0" w:color="auto"/>
                                <w:insideV w:val="single" w:sz="4" w:space="0" w:color="auto"/>
                            </w:tblBorders>
                            <w:tblLook w:val="0000" w:firstRow="0" w:lastRow="0" w:firstColumn="0" w:lastColumn="0"
                                       w:noHBand="0" w:noVBand="0"/>
                        </w:tblPr>
                        <w:tblGrid>
                            <w:gridCol w:w="2582"/>
                            <w:gridCol w:w="1505"/>
                            <w:gridCol w:w="1055"/>
                            <w:gridCol w:w="934"/>
                            <w:gridCol w:w="936"/>
                            <w:gridCol w:w="1510"/>
                        </w:tblGrid>
                        <w:tr w:rsidR="00000000" w14:paraId="2DF268DE" w14:textId="77777777">
                            <w:trPr>
                                <w:trHeight w:val="90"/>
                                <w:jc w:val="center"/>
                            </w:trPr>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="1514" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="E3E3E3"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p w14:paraId="3EF8C382" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>字段名</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="882" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="E3E3E3"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p w14:paraId="68711E5A" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>数据类型</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="619" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="E3E3E3"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p w14:paraId="56A65183" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>长度</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="548" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="E3E3E3"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p w14:paraId="4592013B" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>主键</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="549" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="E3E3E3"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p w14:paraId="0D460AD4" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>非空</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="885" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="E3E3E3"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p w14:paraId="531A2B0D" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>描述</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                        </w:tr>
                        #foreach ($c in $t.columns)
                        <w:tr w:rsidR="00000000" w14:paraId="37D5DECE" w14:textId="77777777">
                            <w:trPr>
                                <w:trHeight w:val="90"/>
                                <w:jc w:val="center"/>
                            </w:trPr>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="1514" w:type="pct"/>
                                </w:tcPr>
                                <w:p w14:paraId="47ADC9A4" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>${c.columnName}</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="882" w:type="pct"/>
                                </w:tcPr>
                                <w:p w14:paraId="711D6CDF" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>${c.columnType}</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="619" w:type="pct"/>
                                </w:tcPr>
                                <w:p w14:paraId="10E68E1E" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>${c.maximumLength}</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="548" w:type="pct"/>
                                </w:tcPr>
                                <w:p w14:paraId="77E6C5F9" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>#if($c.isPk == 1) 是 #else 否 #end</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="549" w:type="pct"/>
                                </w:tcPr>
                                <w:p w14:paraId="6B45E0AF" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>#if($c.isRequired == 1) 是 #else 否 #end</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="885" w:type="pct"/>
                                </w:tcPr>
                                <w:p w14:paraId="72C983E5" w14:textId="77777777" w:rsidR="00000000"
                                     w:rsidRDefault="00000000">
                                    <w:pPr>
                                        <w:pStyle w:val="aa"/>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>${c.columnComment}</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                        </w:tr>#end
                    </w:tbl>#end
                    <w:p w14:paraId="22D14CFE" w14:textId="77777777" w:rsidR="00B42D13" w:rsidRDefault="00B42D13">
                        <w:pPr>
                            <w:rPr>
                                <w:rFonts w:hint="eastAsia"/>
                            </w:rPr>
                        </w:pPr>
                    </w:p>
                    <w:sectPr w:rsidR="00B42D13">
                        <w:pgSz w:w="11906" w:h="16838"/>
                        <w:pgMar w:top="1440" w:right="1800" w:bottom="1440" w:left="1800" w:header="851" w:footer="992"
                                 w:gutter="0"/>
                        <w:cols w:space="720"/>
                        <w:docGrid w:type="lines" w:linePitch="312"/>
                    </w:sectPr>
                </w:body>
            </w:document>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/_rels/document.xml.rels"
              pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:padding="256">
        <pkg:xmlData>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId3"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/webSettings"
                              Target="webSettings.xml"/>
                <Relationship Id="rId2"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings"
                              Target="settings.xml"/>
                <Relationship Id="rId1"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles"
                              Target="styles.xml"/>
                <Relationship Id="rId5" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme"
                              Target="theme/theme1.xml"/>
                <Relationship Id="rId4"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable"
                              Target="fontTable.xml"/>
            </Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/theme/theme1.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.theme+xml">
        <pkg:xmlData>
            <a:theme xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" name="Office 主题​​">
                <a:themeElements>
                    <a:clrScheme name="Office">
                        <a:dk1>
                            <a:sysClr val="windowText" lastClr="000000"/>
                        </a:dk1>
                        <a:lt1>
                            <a:sysClr val="window" lastClr="FFFFFF"/>
                        </a:lt1>
                        <a:dk2>
                            <a:srgbClr val="44546A"/>
                        </a:dk2>
                        <a:lt2>
                            <a:srgbClr val="E7E6E6"/>
                        </a:lt2>
                        <a:accent1>
                            <a:srgbClr val="4472C4"/>
                        </a:accent1>
                        <a:accent2>
                            <a:srgbClr val="ED7D31"/>
                        </a:accent2>
                        <a:accent3>
                            <a:srgbClr val="A5A5A5"/>
                        </a:accent3>
                        <a:accent4>
                            <a:srgbClr val="FFC000"/>
                        </a:accent4>
                        <a:accent5>
                            <a:srgbClr val="5B9BD5"/>
                        </a:accent5>
                        <a:accent6>
                            <a:srgbClr val="70AD47"/>
                        </a:accent6>
                        <a:hlink>
                            <a:srgbClr val="0563C1"/>
                        </a:hlink>
                        <a:folHlink>
                            <a:srgbClr val="954F72"/>
                        </a:folHlink>
                    </a:clrScheme>
                    <a:fontScheme name="Office">
                        <a:majorFont>
                            <a:latin typeface="等线 Light" panose="020F0302020204030204"/>
                            <a:ea typeface=""/>
                            <a:cs typeface=""/>
                            <a:font script="Jpan" typeface="游ゴシック Light"/>
                            <a:font script="Hang" typeface="맑은 고딕"/>
                            <a:font script="Hans" typeface="等线 Light"/>
                            <a:font script="Hant" typeface="新細明體"/>
                            <a:font script="Arab" typeface="Times New Roman"/>
                            <a:font script="Hebr" typeface="Times New Roman"/>
                            <a:font script="Thai" typeface="Angsana New"/>
                            <a:font script="Ethi" typeface="Nyala"/>
                            <a:font script="Beng" typeface="Vrinda"/>
                            <a:font script="Gujr" typeface="Shruti"/>
                            <a:font script="Khmr" typeface="MoolBoran"/>
                            <a:font script="Knda" typeface="Tunga"/>
                            <a:font script="Guru" typeface="Raavi"/>
                            <a:font script="Cans" typeface="Euphemia"/>
                            <a:font script="Cher" typeface="Plantagenet Cherokee"/>
                            <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
                            <a:font script="Tibt" typeface="Microsoft Himalaya"/>
                            <a:font script="Thaa" typeface="MV Boli"/>
                            <a:font script="Deva" typeface="Mangal"/>
                            <a:font script="Telu" typeface="Gautami"/>
                            <a:font script="Taml" typeface="Latha"/>
                            <a:font script="Syrc" typeface="Estrangelo Edessa"/>
                            <a:font script="Orya" typeface="Kalinga"/>
                            <a:font script="Mlym" typeface="Kartika"/>
                            <a:font script="Laoo" typeface="DokChampa"/>
                            <a:font script="Sinh" typeface="Iskoola Pota"/>
                            <a:font script="Mong" typeface="Mongolian Baiti"/>
                            <a:font script="Viet" typeface="Times New Roman"/>
                            <a:font script="Uigh" typeface="Microsoft Uighur"/>
                            <a:font script="Geor" typeface="Sylfaen"/>
                            <a:font script="Armn" typeface="Arial"/>
                            <a:font script="Bugi" typeface="Leelawadee UI"/>
                            <a:font script="Bopo" typeface="Microsoft JhengHei"/>
                            <a:font script="Java" typeface="Javanese Text"/>
                            <a:font script="Lisu" typeface="Segoe UI"/>
                            <a:font script="Mymr" typeface="Myanmar Text"/>
                            <a:font script="Nkoo" typeface="Ebrima"/>
                            <a:font script="Olck" typeface="Nirmala UI"/>
                            <a:font script="Osma" typeface="Ebrima"/>
                            <a:font script="Phag" typeface="Phagspa"/>
                            <a:font script="Syrn" typeface="Estrangelo Edessa"/>
                            <a:font script="Syrj" typeface="Estrangelo Edessa"/>
                            <a:font script="Syre" typeface="Estrangelo Edessa"/>
                            <a:font script="Sora" typeface="Nirmala UI"/>
                            <a:font script="Tale" typeface="Microsoft Tai Le"/>
                            <a:font script="Talu" typeface="Microsoft New Tai Lue"/>
                            <a:font script="Tfng" typeface="Ebrima"/>
                        </a:majorFont>
                        <a:minorFont>
                            <a:latin typeface="等线" panose="020F0502020204030204"/>
                            <a:ea typeface=""/>
                            <a:cs typeface=""/>
                            <a:font script="Jpan" typeface="游明朝"/>
                            <a:font script="Hang" typeface="맑은 고딕"/>
                            <a:font script="Hans" typeface="等线"/>
                            <a:font script="Hant" typeface="新細明體"/>
                            <a:font script="Arab" typeface="Arial"/>
                            <a:font script="Hebr" typeface="Arial"/>
                            <a:font script="Thai" typeface="Cordia New"/>
                            <a:font script="Ethi" typeface="Nyala"/>
                            <a:font script="Beng" typeface="Vrinda"/>
                            <a:font script="Gujr" typeface="Shruti"/>
                            <a:font script="Khmr" typeface="DaunPenh"/>
                            <a:font script="Knda" typeface="Tunga"/>
                            <a:font script="Guru" typeface="Raavi"/>
                            <a:font script="Cans" typeface="Euphemia"/>
                            <a:font script="Cher" typeface="Plantagenet Cherokee"/>
                            <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
                            <a:font script="Tibt" typeface="Microsoft Himalaya"/>
                            <a:font script="Thaa" typeface="MV Boli"/>
                            <a:font script="Deva" typeface="Mangal"/>
                            <a:font script="Telu" typeface="Gautami"/>
                            <a:font script="Taml" typeface="Latha"/>
                            <a:font script="Syrc" typeface="Estrangelo Edessa"/>
                            <a:font script="Orya" typeface="Kalinga"/>
                            <a:font script="Mlym" typeface="Kartika"/>
                            <a:font script="Laoo" typeface="DokChampa"/>
                            <a:font script="Sinh" typeface="Iskoola Pota"/>
                            <a:font script="Mong" typeface="Mongolian Baiti"/>
                            <a:font script="Viet" typeface="Arial"/>
                            <a:font script="Uigh" typeface="Microsoft Uighur"/>
                            <a:font script="Geor" typeface="Sylfaen"/>
                            <a:font script="Armn" typeface="Arial"/>
                            <a:font script="Bugi" typeface="Leelawadee UI"/>
                            <a:font script="Bopo" typeface="Microsoft JhengHei"/>
                            <a:font script="Java" typeface="Javanese Text"/>
                            <a:font script="Lisu" typeface="Segoe UI"/>
                            <a:font script="Mymr" typeface="Myanmar Text"/>
                            <a:font script="Nkoo" typeface="Ebrima"/>
                            <a:font script="Olck" typeface="Nirmala UI"/>
                            <a:font script="Osma" typeface="Ebrima"/>
                            <a:font script="Phag" typeface="Phagspa"/>
                            <a:font script="Syrn" typeface="Estrangelo Edessa"/>
                            <a:font script="Syrj" typeface="Estrangelo Edessa"/>
                            <a:font script="Syre" typeface="Estrangelo Edessa"/>
                            <a:font script="Sora" typeface="Nirmala UI"/>
                            <a:font script="Tale" typeface="Microsoft Tai Le"/>
                            <a:font script="Talu" typeface="Microsoft New Tai Lue"/>
                            <a:font script="Tfng" typeface="Ebrima"/>
                        </a:minorFont>
                    </a:fontScheme>
                    <a:fmtScheme name="Office">
                        <a:fillStyleLst>
                            <a:solidFill>
                                <a:schemeClr val="phClr"/>
                            </a:solidFill>
                            <a:gradFill rotWithShape="1">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="110000"/>
                                            <a:satMod val="105000"/>
                                            <a:tint val="67000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="105000"/>
                                            <a:satMod val="103000"/>
                                            <a:tint val="73000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="105000"/>
                                            <a:satMod val="109000"/>
                                            <a:tint val="81000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="0"/>
                            </a:gradFill>
                            <a:gradFill rotWithShape="1">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:satMod val="103000"/>
                                            <a:lumMod val="102000"/>
                                            <a:tint val="94000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:satMod val="110000"/>
                                            <a:lumMod val="100000"/>
                                            <a:shade val="100000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="99000"/>
                                            <a:satMod val="120000"/>
                                            <a:shade val="78000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="0"/>
                            </a:gradFill>
                        </a:fillStyleLst>
                        <a:lnStyleLst>
                            <a:ln w="6350" cap="flat" cmpd="sng" algn="ctr">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                            <a:ln w="12700" cap="flat" cmpd="sng" algn="ctr">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                            <a:ln w="19050" cap="flat" cmpd="sng" algn="ctr">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                        </a:lnStyleLst>
                        <a:effectStyleLst>
                            <a:effectStyle>
                                <a:effectLst/>
                            </a:effectStyle>
                            <a:effectStyle>
                                <a:effectLst/>
                            </a:effectStyle>
                            <a:effectStyle>
                                <a:effectLst>
                                    <a:outerShdw blurRad="57150" dist="19050" dir="5400000" algn="ctr" rotWithShape="0">
                                        <a:srgbClr val="000000">
                                            <a:alpha val="63000"/>
                                        </a:srgbClr>
                                    </a:outerShdw>
                                </a:effectLst>
                            </a:effectStyle>
                        </a:effectStyleLst>
                        <a:bgFillStyleLst>
                            <a:solidFill>
                                <a:schemeClr val="phClr"/>
                            </a:solidFill>
                            <a:solidFill>
                                <a:schemeClr val="phClr">
                                    <a:tint val="95000"/>
                                    <a:satMod val="170000"/>
                                </a:schemeClr>
                            </a:solidFill>
                            <a:gradFill rotWithShape="1">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:tint val="93000"/>
                                            <a:satMod val="150000"/>
                                            <a:shade val="98000"/>
                                            <a:lumMod val="102000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:tint val="98000"/>
                                            <a:satMod val="130000"/>
                                            <a:shade val="90000"/>
                                            <a:lumMod val="103000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:shade val="63000"/>
                                            <a:satMod val="120000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="0"/>
                            </a:gradFill>
                        </a:bgFillStyleLst>
                    </a:fmtScheme>
                </a:themeElements>
                <a:objectDefaults/>
                <a:extraClrSchemeLst/>
                <a:extLst>
                    <a:ext uri="{05A4C25C-085E-4340-85A3-A5531E510DB2}">
                        <thm15:themeFamily xmlns:thm15="http://schemas.microsoft.com/office/thememl/2012/main"
                                           name="Office Theme" id="{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}"
                                           vid="{4A3C46E8-61CC-4603-A589-7422A47A8E4A}"/>
                    </a:ext>
                </a:extLst>
            </a:theme>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/settings.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml">
        <pkg:xmlData>
            <w:settings xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                        xmlns:o="urn:schemas-microsoft-com:office:office"
                        xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                        xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                        xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
                        xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                        xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                        xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml"
                        xmlns:w16cex="http://schemas.microsoft.com/office/word/2018/wordml/cex"
                        xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid"
                        xmlns:w16="http://schemas.microsoft.com/office/word/2018/wordml"
                        xmlns:w16sdtdh="http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash"
                        xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex"
                        xmlns:sl="http://schemas.openxmlformats.org/schemaLibrary/2006/main"
                        mc:Ignorable="w14 w15 w16se w16cid w16 w16cex w16sdtdh">
                <w:zoom w:percent="100"/>
                <w:bordersDoNotSurroundHeader/>
                <w:bordersDoNotSurroundFooter/>
                <w:attachedTemplate r:id="rId1"/>
                <w:doNotTrackMoves/>
                <w:defaultTabStop w:val="420"/>
                <w:drawingGridVerticalSpacing w:val="156"/>
                <w:displayHorizontalDrawingGridEvery w:val="0"/>
                <w:displayVerticalDrawingGridEvery w:val="2"/>
                <w:characterSpacingControl w:val="compressPunctuation"/>
                <w:compat>
                    <w:spaceForUL/>
                    <w:balanceSingleByteDoubleByteWidth/>
                    <w:doNotLeaveBackslashAlone/>
                    <w:ulTrailSpace/>
                    <w:doNotExpandShiftReturn/>
                    <w:adjustLineHeightInTable/>
                    <w:useFELayout/>
                    <w:useNormalStyleForList/>
                    <w:doNotUseIndentAsNumberingTabStop/>
                    <w:useAltKinsokuLineBreakRules/>
                    <w:allowSpaceOfSameStyleInTable/>
                    <w:doNotSuppressIndentation/>
                    <w:doNotAutofitConstrainedTables/>
                    <w:autofitToFirstFixedWidthCell/>
                    <w:displayHangulFixedWidth/>
                    <w:splitPgBreakAndParaMark/>
                    <w:doNotVertAlignCellWithSp/>
                    <w:doNotBreakConstrainedForcedTable/>
                    <w:doNotVertAlignInTxbx/>
                    <w:useAnsiKerningPairs/>
                    <w:cachedColBalance/>
                    <w:compatSetting w:name="compatibilityMode" w:uri="http://schemas.microsoft.com/office/word"
                                     w:val="11"/>
                    <w:compatSetting w:name="allowHyphenationAtTrackBottom"
                                     w:uri="http://schemas.microsoft.com/office/word" w:val="1"/>
                    <w:compatSetting w:name="useWord2013TrackBottomHyphenation"
                                     w:uri="http://schemas.microsoft.com/office/word" w:val="1"/>
                </w:compat>
                <w:rsids>
                    <w:rsidRoot w:val="00B42D13"/>
                    <w:rsid w:val="001F2E0D"/>
                    <w:rsid w:val="00220EEC"/>
                    <w:rsid w:val="003A3E39"/>
                    <w:rsid w:val="003D4CE2"/>
                    <w:rsid w:val="00695CAC"/>
                    <w:rsid w:val="008C5754"/>
                    <w:rsid w:val="009A0552"/>
                    <w:rsid w:val="00B42D13"/>
                    <w:rsid w:val="00EE5595"/>
                    <w:rsid w:val="02B92F9C"/>
                    <w:rsid w:val="12832A31"/>
                    <w:rsid w:val="34D564E8"/>
                    <w:rsid w:val="3A831227"/>
                    <w:rsid w:val="6D3513AF"/>
                    <w:rsid w:val="7E4A3AE5"/>
                </w:rsids>
                <m:mathPr>
                    <m:mathFont m:val="Cambria Math"/>
                    <m:brkBin m:val="before"/>
                    <m:brkBinSub m:val="--"/>
                    <m:smallFrac m:val="0"/>
                    <m:dispDef/>
                    <m:lMargin m:val="0"/>
                    <m:rMargin m:val="0"/>
                    <m:defJc m:val="centerGroup"/>
                    <m:wrapIndent m:val="1440"/>
                    <m:intLim m:val="subSup"/>
                    <m:naryLim m:val="undOvr"/>
                </m:mathPr>
                <w:themeFontLang w:val="en-US" w:eastAsia="zh-CN"/>
                <w:clrSchemeMapping w:bg1="light1" w:t1="dark1" w:bg2="light2" w:t2="dark2" w:accent1="accent1"
                                    w:accent2="accent2" w:accent3="accent3" w:accent4="accent4" w:accent5="accent5"
                                    w:accent6="accent6" w:hyperlink="hyperlink"
                                    w:followedHyperlink="followedHyperlink"/>
                <w:shapeDefaults>
                    <o:shapedefaults v:ext="edit" spidmax="1026" fillcolor="white">
                        <v:fill color="white"/>
                    </o:shapedefaults>
                    <o:shapelayout v:ext="edit">
                        <o:idmap v:ext="edit" data="1"/>
                    </o:shapelayout>
                </w:shapeDefaults>
                <w:decimalSymbol w:val="."/>
                <w:listSeparator w:val=","/>
                <w14:docId w14:val="1A46FD83"/>
                <w15:chartTrackingRefBased/>
                <w15:docId w15:val="{03AC293F-7AC5-41D9-9788-512D6CDA509F}"/>
            </w:settings>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/_rels/settings.xml.rels"
              pkg:contentType="application/vnd.openxmlformats-package.relationships+xml">
        <pkg:xmlData>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId1"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/attachedTemplate"
                              Target="file:///C:\\Users\\yuzhanfeng\\Desktop\\新建%20Microsoft%20Word%20文档.dot"
                              TargetMode="External"/>
            </Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/styles.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml">
        <pkg:xmlData>
            <w:styles xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                      xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                      xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                      xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                      xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml"
                      xmlns:w16cex="http://schemas.microsoft.com/office/word/2018/wordml/cex"
                      xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid"
                      xmlns:w16="http://schemas.microsoft.com/office/word/2018/wordml"
                      xmlns:w16sdtdh="http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash"
                      xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex"
                      mc:Ignorable="w14 w15 w16se w16cid w16 w16cex w16sdtdh">
                <w:docDefaults>
                    <w:rPrDefault>
                        <w:rPr>
                            <w:rFonts w:ascii="等线" w:eastAsia="等线" w:hAnsi="等线" w:cs="Times New Roman"/>
                            <w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA"/>
                        </w:rPr>
                    </w:rPrDefault>
                    <w:pPrDefault/>
                </w:docDefaults>
                <w:latentStyles w:defLockedState="0" w:defUIPriority="99" w:defSemiHidden="0" w:defUnhideWhenUsed="0"
                                w:defQFormat="0" w:count="376">
                    <w:lsdException w:name="Normal" w:uiPriority="0" w:qFormat="1"/>
                    <w:lsdException w:name="heading 1" w:uiPriority="9" w:qFormat="1"/>
                    <w:lsdException w:name="heading 2" w:uiPriority="0" w:qFormat="1"/>
                    <w:lsdException w:name="heading 3" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1"
                                    w:qFormat="1"/>
                    <w:lsdException w:name="heading 4" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1"
                                    w:qFormat="1"/>
                    <w:lsdException w:name="heading 5" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1"
                                    w:qFormat="1"/>
                    <w:lsdException w:name="heading 6" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1"
                                    w:qFormat="1"/>
                    <w:lsdException w:name="heading 7" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1"
                                    w:qFormat="1"/>
                    <w:lsdException w:name="heading 8" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1"
                                    w:qFormat="1"/>
                    <w:lsdException w:name="heading 9" w:semiHidden="1" w:uiPriority="9" w:unhideWhenUsed="1"
                                    w:qFormat="1"/>
                    <w:lsdException w:name="index 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index 6" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index 7" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index 8" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index 9" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 1" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 2" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 3" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 4" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 5" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 6" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 7" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 8" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toc 9" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Normal Indent" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="footnote text" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="annotation text" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="header" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="footer" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="index heading" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="caption" w:uiPriority="35" w:qFormat="1"/>
                    <w:lsdException w:name="table of figures" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="envelope address" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="envelope return" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="footnote reference" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="annotation reference" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="line number" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="page number" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="endnote reference" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="endnote text" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="table of authorities" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="macro" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="toa heading" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Bullet" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Number" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Bullet 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Bullet 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Bullet 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Bullet 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Number 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Number 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Number 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Number 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Title" w:uiPriority="10" w:qFormat="1"/>
                    <w:lsdException w:name="Closing" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Signature" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Default Paragraph Font" w:semiHidden="1" w:uiPriority="1"
                                    w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Body Text" w:unhideWhenUsed="1" w:qFormat="1"/>
                    <w:lsdException w:name="Body Text Indent" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Continue" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Continue 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Continue 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Continue 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Continue 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Message Header" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Subtitle" w:uiPriority="11" w:qFormat="1"/>
                    <w:lsdException w:name="Salutation" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Date" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Body Text First Indent" w:unhideWhenUsed="1" w:qFormat="1"/>
                    <w:lsdException w:name="Body Text First Indent 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Note Heading" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Body Text 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Body Text 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Body Text Indent 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Body Text Indent 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Block Text" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Hyperlink" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="FollowedHyperlink" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Strong" w:uiPriority="22" w:qFormat="1"/>
                    <w:lsdException w:name="Emphasis" w:uiPriority="20" w:qFormat="1"/>
                    <w:lsdException w:name="Document Map" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Plain Text" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="E-mail Signature" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Top of Form" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Bottom of Form" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Normal (Web)" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Acronym" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Address" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Cite" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Code" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Definition" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Keyboard" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Preformatted" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Sample" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Typewriter" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="HTML Variable" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Normal Table" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="annotation subject" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="No List" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Outline List 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Outline List 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Outline List 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Simple 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Simple 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Simple 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Classic 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Classic 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Classic 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Classic 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Colorful 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Colorful 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Colorful 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Columns 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Columns 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Columns 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Columns 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Columns 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid 6" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid 7" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid 8" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table List 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table List 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table List 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table List 4" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table List 5" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table List 6" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table List 7" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table List 8" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table 3D effects 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table 3D effects 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table 3D effects 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Contemporary" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Elegant" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Professional" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Subtle 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Subtle 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Web 1" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Web 2" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Web 3" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Balloon Text" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Table Grid" w:uiPriority="39"/>
                    <w:lsdException w:name="Table Theme" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Placeholder Text" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="No Spacing" w:qFormat="1"/>
                    <w:lsdException w:name="Light Shading" w:uiPriority="60"/>
                    <w:lsdException w:name="Light List" w:uiPriority="61"/>
                    <w:lsdException w:name="Light Grid" w:uiPriority="62"/>
                    <w:lsdException w:name="Medium Shading 1" w:uiPriority="63"/>
                    <w:lsdException w:name="Medium Shading 2" w:uiPriority="64"/>
                    <w:lsdException w:name="Medium List 1" w:uiPriority="65"/>
                    <w:lsdException w:name="Medium List 2" w:uiPriority="66"/>
                    <w:lsdException w:name="Medium Grid 1" w:uiPriority="67"/>
                    <w:lsdException w:name="Medium Grid 2" w:uiPriority="68"/>
                    <w:lsdException w:name="Medium Grid 3" w:uiPriority="69"/>
                    <w:lsdException w:name="Dark List" w:uiPriority="70"/>
                    <w:lsdException w:name="Colorful Shading" w:uiPriority="71"/>
                    <w:lsdException w:name="Colorful List" w:uiPriority="72"/>
                    <w:lsdException w:name="Colorful Grid" w:uiPriority="73"/>
                    <w:lsdException w:name="Light Shading Accent 1" w:uiPriority="60"/>
                    <w:lsdException w:name="Light List Accent 1" w:uiPriority="61"/>
                    <w:lsdException w:name="Light Grid Accent 1" w:uiPriority="62"/>
                    <w:lsdException w:name="Medium Shading 1 Accent 1" w:uiPriority="63"/>
                    <w:lsdException w:name="Medium Shading 2 Accent 1" w:uiPriority="64"/>
                    <w:lsdException w:name="Medium List 1 Accent 1" w:uiPriority="65"/>
                    <w:lsdException w:name="Revision" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="List Paragraph" w:qFormat="1"/>
                    <w:lsdException w:name="Quote" w:qFormat="1"/>
                    <w:lsdException w:name="Intense Quote" w:qFormat="1"/>
                    <w:lsdException w:name="Medium List 2 Accent 1" w:uiPriority="66"/>
                    <w:lsdException w:name="Medium Grid 1 Accent 1" w:uiPriority="67"/>
                    <w:lsdException w:name="Medium Grid 2 Accent 1" w:uiPriority="68"/>
                    <w:lsdException w:name="Medium Grid 3 Accent 1" w:uiPriority="69"/>
                    <w:lsdException w:name="Dark List Accent 1" w:uiPriority="70"/>
                    <w:lsdException w:name="Colorful Shading Accent 1" w:uiPriority="71"/>
                    <w:lsdException w:name="Colorful List Accent 1" w:uiPriority="72"/>
                    <w:lsdException w:name="Colorful Grid Accent 1" w:uiPriority="73"/>
                    <w:lsdException w:name="Light Shading Accent 2" w:uiPriority="60"/>
                    <w:lsdException w:name="Light List Accent 2" w:uiPriority="61"/>
                    <w:lsdException w:name="Light Grid Accent 2" w:uiPriority="62"/>
                    <w:lsdException w:name="Medium Shading 1 Accent 2" w:uiPriority="63"/>
                    <w:lsdException w:name="Medium Shading 2 Accent 2" w:uiPriority="64"/>
                    <w:lsdException w:name="Medium List 1 Accent 2" w:uiPriority="65"/>
                    <w:lsdException w:name="Medium List 2 Accent 2" w:uiPriority="66"/>
                    <w:lsdException w:name="Medium Grid 1 Accent 2" w:uiPriority="67"/>
                    <w:lsdException w:name="Medium Grid 2 Accent 2" w:uiPriority="68"/>
                    <w:lsdException w:name="Medium Grid 3 Accent 2" w:uiPriority="69"/>
                    <w:lsdException w:name="Dark List Accent 2" w:uiPriority="70"/>
                    <w:lsdException w:name="Colorful Shading Accent 2" w:uiPriority="71"/>
                    <w:lsdException w:name="Colorful List Accent 2" w:uiPriority="72"/>
                    <w:lsdException w:name="Colorful Grid Accent 2" w:uiPriority="73"/>
                    <w:lsdException w:name="Light Shading Accent 3" w:uiPriority="60"/>
                    <w:lsdException w:name="Light List Accent 3" w:uiPriority="61"/>
                    <w:lsdException w:name="Light Grid Accent 3" w:uiPriority="62"/>
                    <w:lsdException w:name="Medium Shading 1 Accent 3" w:uiPriority="63"/>
                    <w:lsdException w:name="Medium Shading 2 Accent 3" w:uiPriority="64"/>
                    <w:lsdException w:name="Medium List 1 Accent 3" w:uiPriority="65"/>
                    <w:lsdException w:name="Medium List 2 Accent 3" w:uiPriority="66"/>
                    <w:lsdException w:name="Medium Grid 1 Accent 3" w:uiPriority="67"/>
                    <w:lsdException w:name="Medium Grid 2 Accent 3" w:uiPriority="68"/>
                    <w:lsdException w:name="Medium Grid 3 Accent 3" w:uiPriority="69"/>
                    <w:lsdException w:name="Dark List Accent 3" w:uiPriority="70"/>
                    <w:lsdException w:name="Colorful Shading Accent 3" w:uiPriority="71"/>
                    <w:lsdException w:name="Colorful List Accent 3" w:uiPriority="72"/>
                    <w:lsdException w:name="Colorful Grid Accent 3" w:uiPriority="73"/>
                    <w:lsdException w:name="Light Shading Accent 4" w:uiPriority="60"/>
                    <w:lsdException w:name="Light List Accent 4" w:uiPriority="61"/>
                    <w:lsdException w:name="Light Grid Accent 4" w:uiPriority="62"/>
                    <w:lsdException w:name="Medium Shading 1 Accent 4" w:uiPriority="63"/>
                    <w:lsdException w:name="Medium Shading 2 Accent 4" w:uiPriority="64"/>
                    <w:lsdException w:name="Medium List 1 Accent 4" w:uiPriority="65"/>
                    <w:lsdException w:name="Medium List 2 Accent 4" w:uiPriority="66"/>
                    <w:lsdException w:name="Medium Grid 1 Accent 4" w:uiPriority="67"/>
                    <w:lsdException w:name="Medium Grid 2 Accent 4" w:uiPriority="68"/>
                    <w:lsdException w:name="Medium Grid 3 Accent 4" w:uiPriority="69"/>
                    <w:lsdException w:name="Dark List Accent 4" w:uiPriority="70"/>
                    <w:lsdException w:name="Colorful Shading Accent 4" w:uiPriority="71"/>
                    <w:lsdException w:name="Colorful List Accent 4" w:uiPriority="72"/>
                    <w:lsdException w:name="Colorful Grid Accent 4" w:uiPriority="73"/>
                    <w:lsdException w:name="Light Shading Accent 5" w:uiPriority="60"/>
                    <w:lsdException w:name="Light List Accent 5" w:uiPriority="61"/>
                    <w:lsdException w:name="Light Grid Accent 5" w:uiPriority="62"/>
                    <w:lsdException w:name="Medium Shading 1 Accent 5" w:uiPriority="63"/>
                    <w:lsdException w:name="Medium Shading 2 Accent 5" w:uiPriority="64"/>
                    <w:lsdException w:name="Medium List 1 Accent 5" w:uiPriority="65"/>
                    <w:lsdException w:name="Medium List 2 Accent 5" w:uiPriority="66"/>
                    <w:lsdException w:name="Medium Grid 1 Accent 5" w:uiPriority="67"/>
                    <w:lsdException w:name="Medium Grid 2 Accent 5" w:uiPriority="68"/>
                    <w:lsdException w:name="Medium Grid 3 Accent 5" w:uiPriority="69"/>
                    <w:lsdException w:name="Dark List Accent 5" w:uiPriority="70"/>
                    <w:lsdException w:name="Colorful Shading Accent 5" w:uiPriority="71"/>
                    <w:lsdException w:name="Colorful List Accent 5" w:uiPriority="72"/>
                    <w:lsdException w:name="Colorful Grid Accent 5" w:uiPriority="73"/>
                    <w:lsdException w:name="Light Shading Accent 6" w:uiPriority="60"/>
                    <w:lsdException w:name="Light List Accent 6" w:uiPriority="61"/>
                    <w:lsdException w:name="Light Grid Accent 6" w:uiPriority="62"/>
                    <w:lsdException w:name="Medium Shading 1 Accent 6" w:uiPriority="63"/>
                    <w:lsdException w:name="Medium Shading 2 Accent 6" w:uiPriority="64"/>
                    <w:lsdException w:name="Medium List 1 Accent 6" w:uiPriority="65"/>
                    <w:lsdException w:name="Medium List 2 Accent 6" w:uiPriority="66"/>
                    <w:lsdException w:name="Medium Grid 1 Accent 6" w:uiPriority="67"/>
                    <w:lsdException w:name="Medium Grid 2 Accent 6" w:uiPriority="68"/>
                    <w:lsdException w:name="Medium Grid 3 Accent 6" w:uiPriority="69"/>
                    <w:lsdException w:name="Dark List Accent 6" w:uiPriority="70"/>
                    <w:lsdException w:name="Colorful Shading Accent 6" w:uiPriority="71"/>
                    <w:lsdException w:name="Colorful List Accent 6" w:uiPriority="72"/>
                    <w:lsdException w:name="Colorful Grid Accent 6" w:uiPriority="73"/>
                    <w:lsdException w:name="Subtle Emphasis" w:uiPriority="19" w:qFormat="1"/>
                    <w:lsdException w:name="Intense Emphasis" w:uiPriority="21" w:qFormat="1"/>
                    <w:lsdException w:name="Subtle Reference" w:uiPriority="31" w:qFormat="1"/>
                    <w:lsdException w:name="Intense Reference" w:uiPriority="32" w:qFormat="1"/>
                    <w:lsdException w:name="Book Title" w:uiPriority="33" w:qFormat="1"/>
                    <w:lsdException w:name="Bibliography" w:semiHidden="1" w:uiPriority="37" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="TOC Heading" w:semiHidden="1" w:uiPriority="39" w:unhideWhenUsed="1"
                                    w:qFormat="1"/>
                    <w:lsdException w:name="Plain Table 1" w:uiPriority="41"/>
                    <w:lsdException w:name="Plain Table 2" w:uiPriority="42"/>
                    <w:lsdException w:name="Plain Table 3" w:uiPriority="43"/>
                    <w:lsdException w:name="Plain Table 4" w:uiPriority="44"/>
                    <w:lsdException w:name="Plain Table 5" w:uiPriority="45"/>
                    <w:lsdException w:name="Grid Table Light" w:uiPriority="40"/>
                    <w:lsdException w:name="Grid Table 1 Light" w:uiPriority="46"/>
                    <w:lsdException w:name="Grid Table 2" w:uiPriority="47"/>
                    <w:lsdException w:name="Grid Table 3" w:uiPriority="48"/>
                    <w:lsdException w:name="Grid Table 4" w:uiPriority="49"/>
                    <w:lsdException w:name="Grid Table 5 Dark" w:uiPriority="50"/>
                    <w:lsdException w:name="Grid Table 6 Colorful" w:uiPriority="51"/>
                    <w:lsdException w:name="Grid Table 7 Colorful" w:uiPriority="52"/>
                    <w:lsdException w:name="Grid Table 1 Light Accent 1" w:uiPriority="46"/>
                    <w:lsdException w:name="Grid Table 2 Accent 1" w:uiPriority="47"/>
                    <w:lsdException w:name="Grid Table 3 Accent 1" w:uiPriority="48"/>
                    <w:lsdException w:name="Grid Table 4 Accent 1" w:uiPriority="49"/>
                    <w:lsdException w:name="Grid Table 5 Dark Accent 1" w:uiPriority="50"/>
                    <w:lsdException w:name="Grid Table 6 Colorful Accent 1" w:uiPriority="51"/>
                    <w:lsdException w:name="Grid Table 7 Colorful Accent 1" w:uiPriority="52"/>
                    <w:lsdException w:name="Grid Table 1 Light Accent 2" w:uiPriority="46"/>
                    <w:lsdException w:name="Grid Table 2 Accent 2" w:uiPriority="47"/>
                    <w:lsdException w:name="Grid Table 3 Accent 2" w:uiPriority="48"/>
                    <w:lsdException w:name="Grid Table 4 Accent 2" w:uiPriority="49"/>
                    <w:lsdException w:name="Grid Table 5 Dark Accent 2" w:uiPriority="50"/>
                    <w:lsdException w:name="Grid Table 6 Colorful Accent 2" w:uiPriority="51"/>
                    <w:lsdException w:name="Grid Table 7 Colorful Accent 2" w:uiPriority="52"/>
                    <w:lsdException w:name="Grid Table 1 Light Accent 3" w:uiPriority="46"/>
                    <w:lsdException w:name="Grid Table 2 Accent 3" w:uiPriority="47"/>
                    <w:lsdException w:name="Grid Table 3 Accent 3" w:uiPriority="48"/>
                    <w:lsdException w:name="Grid Table 4 Accent 3" w:uiPriority="49"/>
                    <w:lsdException w:name="Grid Table 5 Dark Accent 3" w:uiPriority="50"/>
                    <w:lsdException w:name="Grid Table 6 Colorful Accent 3" w:uiPriority="51"/>
                    <w:lsdException w:name="Grid Table 7 Colorful Accent 3" w:uiPriority="52"/>
                    <w:lsdException w:name="Grid Table 1 Light Accent 4" w:uiPriority="46"/>
                    <w:lsdException w:name="Grid Table 2 Accent 4" w:uiPriority="47"/>
                    <w:lsdException w:name="Grid Table 3 Accent 4" w:uiPriority="48"/>
                    <w:lsdException w:name="Grid Table 4 Accent 4" w:uiPriority="49"/>
                    <w:lsdException w:name="Grid Table 5 Dark Accent 4" w:uiPriority="50"/>
                    <w:lsdException w:name="Grid Table 6 Colorful Accent 4" w:uiPriority="51"/>
                    <w:lsdException w:name="Grid Table 7 Colorful Accent 4" w:uiPriority="52"/>
                    <w:lsdException w:name="Grid Table 1 Light Accent 5" w:uiPriority="46"/>
                    <w:lsdException w:name="Grid Table 2 Accent 5" w:uiPriority="47"/>
                    <w:lsdException w:name="Grid Table 3 Accent 5" w:uiPriority="48"/>
                    <w:lsdException w:name="Grid Table 4 Accent 5" w:uiPriority="49"/>
                    <w:lsdException w:name="Grid Table 5 Dark Accent 5" w:uiPriority="50"/>
                    <w:lsdException w:name="Grid Table 6 Colorful Accent 5" w:uiPriority="51"/>
                    <w:lsdException w:name="Grid Table 7 Colorful Accent 5" w:uiPriority="52"/>
                    <w:lsdException w:name="Grid Table 1 Light Accent 6" w:uiPriority="46"/>
                    <w:lsdException w:name="Grid Table 2 Accent 6" w:uiPriority="47"/>
                    <w:lsdException w:name="Grid Table 3 Accent 6" w:uiPriority="48"/>
                    <w:lsdException w:name="Grid Table 4 Accent 6" w:uiPriority="49"/>
                    <w:lsdException w:name="Grid Table 5 Dark Accent 6" w:uiPriority="50"/>
                    <w:lsdException w:name="Grid Table 6 Colorful Accent 6" w:uiPriority="51"/>
                    <w:lsdException w:name="Grid Table 7 Colorful Accent 6" w:uiPriority="52"/>
                    <w:lsdException w:name="List Table 1 Light" w:uiPriority="46"/>
                    <w:lsdException w:name="List Table 2" w:uiPriority="47"/>
                    <w:lsdException w:name="List Table 3" w:uiPriority="48"/>
                    <w:lsdException w:name="List Table 4" w:uiPriority="49"/>
                    <w:lsdException w:name="List Table 5 Dark" w:uiPriority="50"/>
                    <w:lsdException w:name="List Table 6 Colorful" w:uiPriority="51"/>
                    <w:lsdException w:name="List Table 7 Colorful" w:uiPriority="52"/>
                    <w:lsdException w:name="List Table 1 Light Accent 1" w:uiPriority="46"/>
                    <w:lsdException w:name="List Table 2 Accent 1" w:uiPriority="47"/>
                    <w:lsdException w:name="List Table 3 Accent 1" w:uiPriority="48"/>
                    <w:lsdException w:name="List Table 4 Accent 1" w:uiPriority="49"/>
                    <w:lsdException w:name="List Table 5 Dark Accent 1" w:uiPriority="50"/>
                    <w:lsdException w:name="List Table 6 Colorful Accent 1" w:uiPriority="51"/>
                    <w:lsdException w:name="List Table 7 Colorful Accent 1" w:uiPriority="52"/>
                    <w:lsdException w:name="List Table 1 Light Accent 2" w:uiPriority="46"/>
                    <w:lsdException w:name="List Table 2 Accent 2" w:uiPriority="47"/>
                    <w:lsdException w:name="List Table 3 Accent 2" w:uiPriority="48"/>
                    <w:lsdException w:name="List Table 4 Accent 2" w:uiPriority="49"/>
                    <w:lsdException w:name="List Table 5 Dark Accent 2" w:uiPriority="50"/>
                    <w:lsdException w:name="List Table 6 Colorful Accent 2" w:uiPriority="51"/>
                    <w:lsdException w:name="List Table 7 Colorful Accent 2" w:uiPriority="52"/>
                    <w:lsdException w:name="List Table 1 Light Accent 3" w:uiPriority="46"/>
                    <w:lsdException w:name="List Table 2 Accent 3" w:uiPriority="47"/>
                    <w:lsdException w:name="List Table 3 Accent 3" w:uiPriority="48"/>
                    <w:lsdException w:name="List Table 4 Accent 3" w:uiPriority="49"/>
                    <w:lsdException w:name="List Table 5 Dark Accent 3" w:uiPriority="50"/>
                    <w:lsdException w:name="List Table 6 Colorful Accent 3" w:uiPriority="51"/>
                    <w:lsdException w:name="List Table 7 Colorful Accent 3" w:uiPriority="52"/>
                    <w:lsdException w:name="List Table 1 Light Accent 4" w:uiPriority="46"/>
                    <w:lsdException w:name="List Table 2 Accent 4" w:uiPriority="47"/>
                    <w:lsdException w:name="List Table 3 Accent 4" w:uiPriority="48"/>
                    <w:lsdException w:name="List Table 4 Accent 4" w:uiPriority="49"/>
                    <w:lsdException w:name="List Table 5 Dark Accent 4" w:uiPriority="50"/>
                    <w:lsdException w:name="List Table 6 Colorful Accent 4" w:uiPriority="51"/>
                    <w:lsdException w:name="List Table 7 Colorful Accent 4" w:uiPriority="52"/>
                    <w:lsdException w:name="List Table 1 Light Accent 5" w:uiPriority="46"/>
                    <w:lsdException w:name="List Table 2 Accent 5" w:uiPriority="47"/>
                    <w:lsdException w:name="List Table 3 Accent 5" w:uiPriority="48"/>
                    <w:lsdException w:name="List Table 4 Accent 5" w:uiPriority="49"/>
                    <w:lsdException w:name="List Table 5 Dark Accent 5" w:uiPriority="50"/>
                    <w:lsdException w:name="List Table 6 Colorful Accent 5" w:uiPriority="51"/>
                    <w:lsdException w:name="List Table 7 Colorful Accent 5" w:uiPriority="52"/>
                    <w:lsdException w:name="List Table 1 Light Accent 6" w:uiPriority="46"/>
                    <w:lsdException w:name="List Table 2 Accent 6" w:uiPriority="47"/>
                    <w:lsdException w:name="List Table 3 Accent 6" w:uiPriority="48"/>
                    <w:lsdException w:name="List Table 4 Accent 6" w:uiPriority="49"/>
                    <w:lsdException w:name="List Table 5 Dark Accent 6" w:uiPriority="50"/>
                    <w:lsdException w:name="List Table 6 Colorful Accent 6" w:uiPriority="51"/>
                    <w:lsdException w:name="List Table 7 Colorful Accent 6" w:uiPriority="52"/>
                    <w:lsdException w:name="Mention" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Smart Hyperlink" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Hashtag" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Unresolved Mention" w:semiHidden="1" w:unhideWhenUsed="1"/>
                    <w:lsdException w:name="Smart Link" w:semiHidden="1" w:unhideWhenUsed="1"/>
                </w:latentStyles>
                <w:style w:type="paragraph" w:default="1" w:styleId="a">
                    <w:name w:val="Normal"/>
                    <w:next w:val="a0"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:widowControl w:val="0"/>
                        <w:spacing w:line="360" w:lineRule="auto"/>
                        <w:jc w:val="both"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="Calibri" w:eastAsia="宋体" w:hAnsi="Calibri"/>
                        <w:kern w:val="2"/>
                        <w:sz w:val="24"/>
                        <w:szCs w:val="22"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="2">
                    <w:name w:val="heading 2"/>
                    <w:basedOn w:val="a"/>
                    <w:next w:val="a"/>
                    <w:link w:val="20"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:outlineLvl w:val="1"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="Cambria" w:hAnsi="Cambria"/>
                        <w:b/>
                        <w:bCs/>
                        <w:kern w:val="0"/>
                        <w:sz w:val="30"/>
                        <w:szCs w:val="32"/>
                        <w:lang w:val="zh-CN"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:default="1" w:styleId="a1">
                    <w:name w:val="Default Paragraph Font"/>
                    <w:uiPriority w:val="1"/>
                    <w:unhideWhenUsed/>
                </w:style>
                <w:style w:type="table" w:default="1" w:styleId="a2">
                    <w:name w:val="Normal Table"/>
                    <w:uiPriority w:val="99"/>
                    <w:unhideWhenUsed/>
                    <w:tblPr>
                        <w:tblCellMar>
                            <w:top w:w="0" w:type="dxa"/>
                            <w:left w:w="108" w:type="dxa"/>
                            <w:bottom w:w="0" w:type="dxa"/>
                            <w:right w:w="108" w:type="dxa"/>
                        </w:tblCellMar>
                    </w:tblPr>
                </w:style>
                <w:style w:type="numbering" w:default="1" w:styleId="a3">
                    <w:name w:val="No List"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                </w:style>
                <w:style w:type="paragraph" w:styleId="a0">
                    <w:name w:val="Body Text First Indent"/>
                    <w:basedOn w:val="a4"/>
                    <w:next w:val="a"/>
                    <w:uiPriority w:val="99"/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:ind w:firstLineChars="100" w:firstLine="420"/>
                    </w:pPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="a4">
                    <w:name w:val="Body Text"/>
                    <w:basedOn w:val="a"/>
                    <w:next w:val="a"/>
                    <w:uiPriority w:val="99"/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:spacing w:after="120"/>
                    </w:pPr>
                </w:style>
                <w:style w:type="character" w:customStyle="1" w:styleId="20">
                    <w:name w:val="标题 2 字符"/>
                    <w:link w:val="2"/>
                    <w:rPr>
                        <w:rFonts w:ascii="Cambria" w:eastAsia="宋体" w:hAnsi="Cambria" w:cs="Times New Roman"/>
                        <w:b/>
                        <w:bCs/>
                        <w:kern w:val="0"/>
                        <w:sz w:val="30"/>
                        <w:szCs w:val="32"/>
                        <w:lang w:val="zh-CN" w:eastAsia="zh-CN"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="a5">
                    <w:name w:val="caption"/>
                    <w:basedOn w:val="a"/>
                    <w:next w:val="a"/>
                    <w:uiPriority w:val="35"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:rFonts w:ascii="等线 Light" w:eastAsia="黑体" w:hAnsi="等线 Light"/>
                        <w:sz w:val="20"/>
                        <w:szCs w:val="20"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="a6">
                    <w:name w:val="footer"/>
                    <w:basedOn w:val="a"/>
                    <w:link w:val="a7"/>
                    <w:uiPriority w:val="99"/>
                    <w:unhideWhenUsed/>
                    <w:pPr>
                        <w:tabs>
                            <w:tab w:val="center" w:pos="4153"/>
                            <w:tab w:val="right" w:pos="8306"/>
                        </w:tabs>
                        <w:snapToGrid w:val="0"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:rPr>
                        <w:sz w:val="18"/>
                        <w:szCs w:val="18"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:customStyle="1" w:styleId="a7">
                    <w:name w:val="页脚 字符"/>
                    <w:link w:val="a6"/>
                    <w:uiPriority w:val="99"/>
                    <w:rPr>
                        <w:sz w:val="18"/>
                        <w:szCs w:val="18"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="a8">
                    <w:name w:val="header"/>
                    <w:basedOn w:val="a"/>
                    <w:link w:val="a9"/>
                    <w:uiPriority w:val="99"/>
                    <w:unhideWhenUsed/>
                    <w:pPr>
                        <w:pBdr>
                            <w:bottom w:val="single" w:sz="6" w:space="1" w:color="auto"/>
                        </w:pBdr>
                        <w:tabs>
                            <w:tab w:val="center" w:pos="4153"/>
                            <w:tab w:val="right" w:pos="8306"/>
                        </w:tabs>
                        <w:snapToGrid w:val="0"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:rPr>
                        <w:sz w:val="18"/>
                        <w:szCs w:val="18"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:customStyle="1" w:styleId="a9">
                    <w:name w:val="页眉 字符"/>
                    <w:link w:val="a8"/>
                    <w:uiPriority w:val="99"/>
                    <w:rPr>
                        <w:sz w:val="18"/>
                        <w:szCs w:val="18"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:customStyle="1" w:styleId="w">
                    <w:name w:val="w表格文本居中"/>
                    <w:basedOn w:val="a"/>
                    <w:link w:val="w0"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:spacing w:line="240" w:lineRule="auto"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:cs="宋体"/>
                        <w:sz w:val="21"/>
                        <w:szCs w:val="21"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:customStyle="1" w:styleId="w0">
                    <w:name w:val="w表格文本居中 字符"/>
                    <w:link w:val="w"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体" w:cs="宋体"/>
                        <w:szCs w:val="21"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:customStyle="1" w:styleId="aa">
                    <w:name w:val="!!表格小五"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:spacing w:line="360" w:lineRule="auto"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体"/>
                        <w:kern w:val="2"/>
                        <w:sz w:val="24"/>
                        <w:szCs w:val="21"/>
                    </w:rPr>
                </w:style>
            </w:styles>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/webSettings.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml">
        <pkg:xmlData>
            <w:webSettings xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                           xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                           xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                           xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                           xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml"
                           xmlns:w16cex="http://schemas.microsoft.com/office/word/2018/wordml/cex"
                           xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid"
                           xmlns:w16="http://schemas.microsoft.com/office/word/2018/wordml"
                           xmlns:w16sdtdh="http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash"
                           xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex"
                           mc:Ignorable="w14 w15 w16se w16cid w16 w16cex w16sdtdh">
                <w:encoding w:val="x-cp20936"/>
                <w:optimizeForBrowser/>
                <w:allowPNG/>
                <w:pixelsPerInch w:val="120"/>
            </w:webSettings>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/fontTable.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml">
        <pkg:xmlData>
            <w:fonts xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                     xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                     xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                     xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                     xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml"
                     xmlns:w16cex="http://schemas.microsoft.com/office/word/2018/wordml/cex"
                     xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid"
                     xmlns:w16="http://schemas.microsoft.com/office/word/2018/wordml"
                     xmlns:w16sdtdh="http://schemas.microsoft.com/office/word/2020/wordml/sdtdatahash"
                     xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex"
                     mc:Ignorable="w14 w15 w16se w16cid w16 w16cex w16sdtdh">
                <w:font w:name="等线">
                    <w:altName w:val="DengXian"/>
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:usb0="A00002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000" w:csb0="0004000F"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Times New Roman">
                    <w:panose1 w:val="02020603050405020304"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:usb0="E0002EFF" w:usb1="C000785B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Calibri">
                    <w:panose1 w:val="020F0502020204030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:usb0="E4002EFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000" w:csb0="000001FF"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="宋体">
                    <w:altName w:val="SimSun"/>
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:usb0="00000003" w:usb1="288F0000" w:usb2="00000016" w:usb3="00000000" w:csb0="00040001"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Cambria">
                    <w:panose1 w:val="02040503050406030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:usb0="E00006FF" w:usb1="420024FF" w:usb2="02000000" w:usb3="00000000" w:csb0="0000019F"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="等线 Light">
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:usb0="A00002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000" w:csb0="0004000F"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="黑体">
                    <w:altName w:val="SimHei"/>
                    <w:panose1 w:val="02010609060101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="modern"/>
                    <w:pitch w:val="fixed"/>
                    <w:sig w:usb0="800002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000" w:csb0="00040001"
                           w:csb1="00000000"/>
                </w:font>
            </w:fonts>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/docProps/core.xml" pkg:contentType="application/vnd.openxmlformats-package.core-properties+xml"
              pkg:padding="256">
        <pkg:xmlData>
            <cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties"
                               xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/"
                               xmlns:dcmitype="http://purl.org/dc/dcmitype/"
                               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <dc:title/>
                <dc:subject/>
                <dc:creator>yuzhanfeng</dc:creator>
                <cp:keywords/>
                <cp:lastModifiedBy>zhanfeng</cp:lastModifiedBy>
                <cp:revision>1</cp:revision>
                <dcterms:created xsi:type="dcterms:W3CDTF">2023-05-11T10:52:00Z</dcterms:created>
                <dcterms:modified xsi:type="dcterms:W3CDTF">2023-05-11T10:53:00Z</dcterms:modified>
            </cp:coreProperties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/docProps/app.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.extended-properties+xml" pkg:padding="256">
        <pkg:xmlData>
            <Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"
                        xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
                <Template>新建 Microsoft Word 文档.dot</Template>
                <TotalTime>1</TotalTime>
                <Pages>1</Pages>
                <Words>10</Words>
                <Characters>63</Characters>
                <Application>Microsoft Office Word</Application>
                <DocSecurity>0</DocSecurity>
                <Lines>1</Lines>
                <Paragraphs>1</Paragraphs>
                <ScaleCrop>false</ScaleCrop>
                <Company/>
                <LinksUpToDate>false</LinksUpToDate>
                <CharactersWithSpaces>72</CharactersWithSpaces>
                <SharedDoc>false</SharedDoc>
                <HyperlinksChanged>false</HyperlinksChanged>
                <AppVersion>16.0000</AppVersion>
            </Properties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/docProps/custom.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.custom-properties+xml" pkg:padding="256">
        <pkg:xmlData>
            <Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/custom-properties"
                        xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
                <property fmtid="{D5CDD505-2E9C-101B-9397-08002B2CF9AE}" pid="2" name="KSOProductBuildVer">
                    <vt:lpwstr>2052-11.8.2.10393</vt:lpwstr>
                </property>
            </Properties>
        </pkg:xmlData>
    </pkg:part>
</pkg:package>
', 'doc', 'private', '1.0.0', 'word.docx.vm', 'word.docx', NULL, '2022-09-04 15:18:47.000', '2022-09-04 16:24:24.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(43, '物理数据模型', '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<?mso-application progid="Word.Document"?>
<pkg:package xmlns:pkg="http://schemas.microsoft.com/office/2006/xmlPackage">
    <pkg:part pkg:name="/_rels/.rels" pkg:contentType="application/vnd.openxmlformats-package.relationships+xml">
        <pkg:xmlData>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId4"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument"
                              Target="word/document.xml"/>
                <Relationship Id="rId2"
                              Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties"
                              Target="docProps/core.xml"/>
                <Relationship Id="rId1"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties"
                              Target="docProps/app.xml"/>
                <Relationship Id="rId3"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/custom-properties"
                              Target="docProps/custom.xml"/>
            </Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/_rels/document.xml.rels"
              pkg:contentType="application/vnd.openxmlformats-package.relationships+xml">
        <pkg:xmlData>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId6"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable"
                              Target="fontTable.xml"/>
                <Relationship Id="rId5"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/numbering"
                              Target="numbering.xml"/>
                <Relationship Id="rId4"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXml"
                              Target="../customXml/item1.xml"/>
                <Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme"
                              Target="theme/theme1.xml"/>
                <Relationship Id="rId2"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings"
                              Target="settings.xml"/>
                <Relationship Id="rId1"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles"
                              Target="styles.xml"/>
            </Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/document.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml">
        <pkg:xmlData>
            <w:document xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas"
                        xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                        xmlns:o="urn:schemas-microsoft-com:office:office"
                        xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                        xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                        xmlns:v="urn:schemas-microsoft-com:vml"
                        xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing"
                        xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
                        xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                        xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                        xmlns:w10="urn:schemas-microsoft-com:office:word"
                        xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml"
                        xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup"
                        xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk"
                        xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
                        xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"
                        xmlns:wpsCustomData="http://www.wps.cn/officeDocument/2013/wpsCustomData"
                        mc:Ignorable="w14 w15 wp14">
                <w:body>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="5"/>
                            <w:numPr>
                                <w:numId w:val="0"/>
                            </w:numPr>
                            <w:ind w:leftChars="0"/>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:szCs w:val="30"/>
                            </w:rPr>
                        </w:pPr>
                    </w:p>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="5"/>
                            <w:ind w:hanging="864"/>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:szCs w:val="30"/>
                            </w:rPr>
                        </w:pPr>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:szCs w:val="30"/>
                                <w:lang w:val="en-US" w:eastAsia="zh-CN"/>
                            </w:rPr>
                            <w:t>XXX</w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:szCs w:val="30"/>
                            </w:rPr>
                            <w:t>数据库表结构</w:t>
                        </w:r>
                        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
                        <w:bookmarkEnd w:id="0"/>
                    </w:p>
                    #foreach ($t in $tables)
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="6"/>
                            <w:ind w:hanging="1008"/>
                            <w:rPr>
                                <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:color w:val="000000" w:themeColor="text1"/>
                            </w:rPr>
                        </w:pPr>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                <w:lang w:eastAsia="zh-Hans"/>
                            </w:rPr>
                            <w:t>${t.tableName}</w:t>
                        </w:r>
                    </w:p>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="7"/>
                            <w:ind w:firstLine="420"/>
                            <w:jc w:val="both"/>
                            <w:rPr>
                                <w:rFonts w:hint="default" w:eastAsia="黑体"/>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                                <w:lang w:val="en-US" w:eastAsia="zh-CN"/>
                            </w:rPr>
                        </w:pPr>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:t xml:space="preserve">表 </w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:fldChar w:fldCharType="begin"/>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:instrText xml:space="preserve"> STYLEREF 3 \\s </w:instrText>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:fldChar w:fldCharType="separate"/>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:t></w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:fldChar w:fldCharType="end"/>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:noBreakHyphen/>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:fldChar w:fldCharType="begin"/>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:instrText xml:space="preserve"> SEQ 表 \\* ARABIC \\s 3 </w:instrText>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:fldChar w:fldCharType="separate"/>
                        </w:r>

                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:fldChar w:fldCharType="end"/>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                            </w:rPr>
                            <w:t xml:space="preserve"> </w:t>
                        </w:r>
                        <w:r>
                            <w:rPr>
                                <w:rFonts w:hint="eastAsia"/>
                                <w:color w:val="000000" w:themeColor="text1"/>
                                <w:sz w:val="21"/>
                                <w:szCs w:val="18"/>
                                <w:lang w:val="en-US" w:eastAsia="zh-CN"/>
                            </w:rPr>
                            <w:t>${t.tableName}</w:t>
                        </w:r>
                    </w:p>
                    <w:tbl>
                        <w:tblPr>
                            <w:tblStyle w:val="10"/>
                            <w:tblW w:w="5003" w:type="pct"/>
                            <w:tblInd w:w="0" w:type="dxa"/>
                            <w:tblBorders>
                                <w:top w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                <w:left w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                <w:bottom w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                <w:right w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                <w:insideH w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                <w:insideV w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                            </w:tblBorders>
                            <w:tblLayout w:type="autofit"/>
                            <w:tblCellMar>
                                <w:top w:w="0" w:type="dxa"/>
                                <w:left w:w="108" w:type="dxa"/>
                                <w:bottom w:w="0" w:type="dxa"/>
                                <w:right w:w="108" w:type="dxa"/>
                            </w:tblCellMar>
                        </w:tblPr>
                        <w:tblGrid>
                            <w:gridCol w:w="2496"/>
                            <w:gridCol w:w="2437"/>
                            <w:gridCol w:w="2316"/>
                            <w:gridCol w:w="1001"/>
                            <w:gridCol w:w="1536"/>
                            <w:gridCol w:w="2256"/>
                        </w:tblGrid>
                        <w:tr>
                            <w:tblPrEx>
                                <w:tblBorders>
                                    <w:top w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:left w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:bottom w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:right w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:insideH w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:insideV w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                </w:tblBorders>
                                <w:tblCellMar>
                                    <w:top w:w="0" w:type="dxa"/>
                                    <w:left w:w="108" w:type="dxa"/>
                                    <w:bottom w:w="0" w:type="dxa"/>
                                    <w:right w:w="108" w:type="dxa"/>
                                </w:tblCellMar>
                            </w:tblPrEx>
                            <w:trPr>
                                <w:trHeight w:val="300" w:hRule="atLeast"/>
                                <w:tblHeader/>
                            </w:trPr>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="549" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="EAEAEA"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="482"/>
                                        <w:jc w:val="center"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                        <w:t>序号</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="1252" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="EAEAEA"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="482"/>
                                        <w:jc w:val="center"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                        <w:t>字段名称</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="1055" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="EAEAEA"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="482"/>
                                        <w:jc w:val="center"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                        <w:t>字段类型</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="689" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="EAEAEA"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="482"/>
                                        <w:jc w:val="center"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                        <w:t>长度</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="571" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="EAEAEA"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="482"/>
                                        <w:jc w:val="center"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                        <w:t>允许空</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="882" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="EAEAEA"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="482"/>
                                        <w:jc w:val="center"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:b/>
                                            <w:bCs/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                        <w:t>缺省值</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                        </w:tr>
                        #foreach ($c in $t.columns)
                        <w:tr>
                            <w:tblPrEx>
                                <w:tblBorders>
                                    <w:top w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:left w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:bottom w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:right w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:insideH w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                    <w:insideV w:val="single" w:color="auto" w:sz="4" w:space="0"/>
                                </w:tblBorders>
                                <w:tblCellMar>
                                    <w:top w:w="0" w:type="dxa"/>
                                    <w:left w:w="108" w:type="dxa"/>
                                    <w:bottom w:w="0" w:type="dxa"/>
                                    <w:right w:w="108" w:type="dxa"/>
                                </w:tblCellMar>
                            </w:tblPrEx>
                            <w:trPr>
                                <w:trHeight w:val="225" w:hRule="atLeast"/>
                            </w:trPr>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="549" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="auto"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="480"/>
                                        <w:jc w:val="center"/>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"
                                                      w:eastAsia="宋体"/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                            <w:lang w:val="en-US" w:eastAsia="zh-CN"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia" w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                            <w:lang w:val="en-US" w:eastAsia="zh-CN"/>
                                        </w:rPr>
                                        <w:t>#if($velocityCount) $velocityCount #else 1 #end</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="1252" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="auto"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="480"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                            <w:u w:val="single"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>${c.columnName}</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="1055" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="auto"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="480"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>${c.columnType}</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="689" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="auto"/>
                                    <w:vAlign w:val="center"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:widowControl/>
                                        <w:ind w:firstLine="480"/>
                                        <w:jc w:val="right"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体"/>
                                            <w:color w:val="000000" w:themeColor="text1"/>
                                            <w:kern w:val="0"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>#if($c.maximumLength) $c.maximumLength #end</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="571" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="auto"/>
                                    <w:vAlign w:val="top"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:pStyle w:val="17"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:eastAsia="宋体"
                                                      w:cs="Times New Roman"/>
                                            <w:kern w:val="2"/>
                                            <w:sz w:val="24"/>
                                            <w:szCs w:val="21"/>
                                            <w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>#if($c.isPk == 1) 是 #else 否 #end</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                            <w:tc>
                                <w:tcPr>
                                    <w:tcW w:w="882" w:type="pct"/>
                                    <w:shd w:val="clear" w:color="auto" w:fill="auto"/>
                                    <w:vAlign w:val="top"/>
                                </w:tcPr>
                                <w:p>
                                    <w:pPr>
                                        <w:pStyle w:val="17"/>
                                        <w:rPr>
                                            <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:eastAsia="宋体"
                                                      w:cs="Times New Roman"/>
                                            <w:kern w:val="2"/>
                                            <w:sz w:val="24"/>
                                            <w:szCs w:val="21"/>
                                            <w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA"/>
                                        </w:rPr>
                                    </w:pPr>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t> #if($c.defaultValue) $c.defaultValue #end </w:t>
                                    </w:r>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                            <w:lang w:val="en-US" w:eastAsia="zh-CN"/>
                                        </w:rPr>
                                        <w:t>V</w:t>
                                    </w:r>
                                    <w:r>
                                        <w:rPr>
                                            <w:rFonts w:hint="eastAsia"/>
                                        </w:rPr>
                                        <w:t>alue}</w:t>
                                    </w:r>
                                </w:p>
                            </w:tc>
                        </w:tr>#end
                    </w:tbl>#end
                    <w:p>
                        <w:pPr>
                            <w:rPr>
                                <w:rFonts w:hint="eastAsia"/>
                            </w:rPr>
                        </w:pPr>
                    </w:p>
                    <w:sectPr>
                        <w:pgSz w:w="11906" w:h="16838"/>
                        <w:pgMar w:top="1440" w:right="1800" w:bottom="1440" w:left="1800" w:header="851" w:footer="992"
                                 w:gutter="0"/>
                        <w:cols w:space="720" w:num="1"/>
                        <w:docGrid w:type="lines" w:linePitch="312" w:charSpace="0"/>
                    </w:sectPr>
                </w:body>
            </w:document>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/customXml/_rels/item1.xml.rels"
              pkg:contentType="application/vnd.openxmlformats-package.relationships+xml">
        <pkg:xmlData>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId1"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/customXmlProps"
                              Target="itemProps1.xml"/>
            </Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/customXml/item1.xml" pkg:contentType="application/xml">
        <pkg:xmlData>
            <s:customData xmlns="http://www.wps.cn/officeDocument/2013/wpsCustomData"
                          xmlns:s="http://www.wps.cn/officeDocument/2013/wpsCustomData">
                <customSectProps>
                    <customSectPr/>
                </customSectProps>
            </s:customData>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/customXml/itemProps1.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.customXmlProperties+xml">
        <pkg:xmlData>
            <ds:datastoreItem ds:itemID="{B1977F7D-205B-4081-913C-38D41E755F92}"
                              xmlns:ds="http://schemas.openxmlformats.org/officeDocument/2006/customXml">
                <ds:schemaRefs>
                    <ds:schemaRef ds:uri="http://www.wps.cn/officeDocument/2013/wpsCustomData"/>
                </ds:schemaRefs>
            </ds:datastoreItem>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/docProps/app.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.extended-properties+xml">
        <pkg:xmlData>
            <Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"
                        xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
                <Template>新建 Microsoft Word 文档.dot</Template>
                <Pages>1</Pages>
                <Words>10</Words>
                <Characters>63</Characters>
                <Lines>1</Lines>
                <Paragraphs>1</Paragraphs>
                <TotalTime>1</TotalTime>
                <ScaleCrop>false</ScaleCrop>
                <LinksUpToDate>false</LinksUpToDate>
                <CharactersWithSpaces>72</CharactersWithSpaces>
                <Application>WPS Office_11.8.2.8808_F1E327BC-269C-435d-A152-05C5408002CA</Application>
                <DocSecurity>0</DocSecurity>
            </Properties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/docProps/core.xml"
              pkg:contentType="application/vnd.openxmlformats-package.core-properties+xml">
        <pkg:xmlData>
            <cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties"
                               xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/"
                               xmlns:dcmitype="http://purl.org/dc/dcmitype/"
                               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <dcterms:created xsi:type="dcterms:W3CDTF">2023-05-11T10:52:00Z</dcterms:created>
                <dc:creator>yuzhanfeng</dc:creator>
                <cp:lastModifiedBy>yuzhanfeng</cp:lastModifiedBy>
                <dcterms:modified xsi:type="dcterms:W3CDTF">2023-05-12T09:50:03Z</dcterms:modified>
                <cp:revision>1</cp:revision>
            </cp:coreProperties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/docProps/custom.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.custom-properties+xml">
        <pkg:xmlData>
            <Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/custom-properties"
                        xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes">
                <property fmtid="{D5CDD505-2E9C-101B-9397-08002B2CF9AE}" pid="2" name="KSOProductBuildVer">
                    <vt:lpwstr>2052-11.8.2.8808</vt:lpwstr>
                </property>
            </Properties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/fontTable.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml">
        <pkg:xmlData>
            <w:fonts xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                     xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                     xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                     xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" mc:Ignorable="w14">
                <w:font w:name="Times New Roman">
                    <w:panose1 w:val="02020603050405020304"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:usb0="20007A87" w:usb1="80000000" w:usb2="00000008" w:usb3="00000000" w:csb0="000001FF"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="宋体">
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="00000203" w:usb1="288F0000" w:usb2="00000006" w:usb3="00000000" w:csb0="00040001"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Wingdings">
                    <w:panose1 w:val="05000000000000000000"/>
                    <w:charset w:val="02"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="00000000" w:usb1="00000000" w:usb2="00000000" w:usb3="00000000" w:csb0="80000000"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Arial">
                    <w:panose1 w:val="020B0604020202020204"/>
                    <w:charset w:val="01"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="E0002EFF" w:usb1="C000785B" w:usb2="00000009" w:usb3="00000000" w:csb0="400001FF"
                           w:csb1="FFFF0000"/>
                </w:font>
                <w:font w:name="黑体">
                    <w:panose1 w:val="02010609060101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="800002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000" w:csb0="00040001"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Courier New">
                    <w:panose1 w:val="02070309020205020404"/>
                    <w:charset w:val="01"/>
                    <w:family w:val="modern"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="E0002EFF" w:usb1="C0007843" w:usb2="00000009" w:usb3="00000000" w:csb0="400001FF"
                           w:csb1="FFFF0000"/>
                </w:font>
                <w:font w:name="Symbol">
                    <w:panose1 w:val="05050102010706020507"/>
                    <w:charset w:val="02"/>
                    <w:family w:val="roman"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="00000000" w:usb1="00000000" w:usb2="00000000" w:usb3="00000000" w:csb0="80000000"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Calibri">
                    <w:panose1 w:val="020F0502020204030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="E4002EFF" w:usb1="C200247B" w:usb2="00000009" w:usb3="00000000" w:csb0="200001FF"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="等线">
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="A00002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000" w:csb0="0004000F"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Cambria">
                    <w:panose1 w:val="02040503050406030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="E00006FF" w:usb1="420024FF" w:usb2="02000000" w:usb3="00000000" w:csb0="2000019F"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="等线 Light">
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="A00002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000" w:csb0="0004000F"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="微软雅黑">
                    <w:panose1 w:val="020B0503020204020204"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="80000287" w:usb1="2ACF3C50" w:usb2="00000016" w:usb3="00000000" w:csb0="0004001F"
                           w:csb1="00000000"/>
                </w:font>
                <w:font w:name="Tahoma">
                    <w:panose1 w:val="020B0604030504040204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="E1002EFF" w:usb1="C000605B" w:usb2="00000029" w:usb3="00000000" w:csb0="200101FF"
                           w:csb1="20280000"/>
                </w:font>
                <w:font w:name="Consolas">
                    <w:panose1 w:val="020B0609020204030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="default"/>
                    <w:sig w:usb0="E00006FF" w:usb1="0000FCFF" w:usb2="00000001" w:usb3="00000000" w:csb0="6000019F"
                           w:csb1="DFD70000"/>
                </w:font>
            </w:fonts>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/numbering.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml">
        <pkg:xmlData>
            <w:numbering xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas"
                         xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                         xmlns:o="urn:schemas-microsoft-com:office:office"
                         xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                         xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                         xmlns:v="urn:schemas-microsoft-com:vml"
                         xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing"
                         xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
                         xmlns:w10="urn:schemas-microsoft-com:office:word"
                         xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                         xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                         xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup"
                         xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk"
                         xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
                         xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape"
                         mc:Ignorable="w14 wp14">
                <w:abstractNum w:abstractNumId="0">
                    <w:nsid w:val="CAC44605"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="CAC44605"/>
                    <w:lvl w:ilvl="0" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="432" w:hanging="432"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default" w:ascii="Times New Roman" w:hAnsi="Times New Roman"
                                      w:cs="Times New Roman"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="575" w:hanging="575"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="720" w:hanging="720"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="5"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="864" w:hanging="864"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default" w:ascii="Times New Roman" w:hAnsi="Times New Roman"
                                      w:cs="Times New Roman"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="6"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="1008" w:hanging="1008"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="1151" w:hanging="1151"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="1296" w:hanging="1296"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="1440" w:hanging="1440"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8" w:tentative="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:left="1583" w:hanging="1583"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:num w:numId="1">
                    <w:abstractNumId w:val="0"/>
                </w:num>
            </w:numbering>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/_rels/settings.xml.rels"
              pkg:contentType="application/vnd.openxmlformats-package.relationships+xml">
        <pkg:xmlData>
            <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
                <Relationship Id="rId1"
                              Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/attachedTemplate"
                              Target="file:///C:\\Users\\yuzhanfeng\\Desktop\\新建%20Microsoft%20Word%20文档.dot"
                              TargetMode="External"/>
            </Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/settings.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml">
        <pkg:xmlData>
            <w:settings xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                        xmlns:o="urn:schemas-microsoft-com:office:office"
                        xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                        xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                        xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
                        xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                        xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                        xmlns:sl="http://schemas.openxmlformats.org/schemaLibrary/2006/main" mc:Ignorable="w14">
                <w:zoom w:percent="110"/>
                <w:bordersDoNotSurroundHeader w:val="0"/>
                <w:bordersDoNotSurroundFooter w:val="0"/>
                <w:doNotTrackMoves/>
                <w:attachedTemplate r:id="rId1"/>
                <w:documentProtection w:enforcement="0"/>
                <w:defaultTabStop w:val="420"/>
                <w:drawingGridVerticalSpacing w:val="156"/>
                <w:displayHorizontalDrawingGridEvery w:val="1"/>
                <w:displayVerticalDrawingGridEvery w:val="1"/>
                <w:noPunctuationKerning w:val="1"/>
                <w:characterSpacingControl w:val="compressPunctuation"/>
                <w:compat>
                    <w:spaceForUL/>
                    <w:balanceSingleByteDoubleByteWidth/>
                    <w:doNotLeaveBackslashAlone/>
                    <w:ulTrailSpace/>
                    <w:doNotExpandShiftReturn/>
                    <w:adjustLineHeightInTable/>
                    <w:doNotWrapTextWithPunct/>
                    <w:doNotUseEastAsianBreakRules/>
                    <w:useFELayout/>
                    <w:useNormalStyleForList/>
                    <w:doNotUseIndentAsNumberingTabStop/>
                    <w:useAltKinsokuLineBreakRules/>
                    <w:allowSpaceOfSameStyleInTable/>
                    <w:doNotSuppressIndentation/>
                    <w:doNotAutofitConstrainedTables/>
                    <w:autofitToFirstFixedWidthCell/>
                    <w:displayHangulFixedWidth/>
                    <w:splitPgBreakAndParaMark/>
                    <w:doNotVertAlignCellWithSp/>
                    <w:doNotBreakConstrainedForcedTable/>
                    <w:doNotVertAlignInTxbx/>
                    <w:useAnsiKerningPairs/>
                    <w:cachedColBalance/>
                    <w:compatSetting w:name="compatibilityMode" w:uri="http://schemas.microsoft.com/office/word"
                                     w:val="11"/>
                </w:compat>
                <w:rsids>
                    <w:rsidRoot w:val="00B42D13"/>
                    <w:rsid w:val="001F2E0D"/>
                    <w:rsid w:val="00220EEC"/>
                    <w:rsid w:val="003A3E39"/>
                    <w:rsid w:val="003D4CE2"/>
                    <w:rsid w:val="00695CAC"/>
                    <w:rsid w:val="008C5754"/>
                    <w:rsid w:val="009A0552"/>
                    <w:rsid w:val="00B42D13"/>
                    <w:rsid w:val="00EE5595"/>
                    <w:rsid w:val="02B92F9C"/>
                    <w:rsid w:val="11421FB3"/>
                    <w:rsid w:val="12832A31"/>
                    <w:rsid w:val="1D7F2548"/>
                    <w:rsid w:val="20164D91"/>
                    <w:rsid w:val="21630423"/>
                    <w:rsid w:val="264C6312"/>
                    <w:rsid w:val="34D564E8"/>
                    <w:rsid w:val="3A831227"/>
                    <w:rsid w:val="5AFC5370"/>
                    <w:rsid w:val="6D3513AF"/>
                    <w:rsid w:val="7E4A3AE5"/>
                </w:rsids>
                <m:mathPr>
                    <m:mathFont m:val="Cambria Math"/>
                    <m:brkBin m:val="before"/>
                    <m:brkBinSub m:val="--"/>
                    <m:smallFrac m:val="0"/>
                    <m:dispDef/>
                    <m:lMargin m:val="0"/>
                    <m:rMargin m:val="0"/>
                    <m:defJc m:val="centerGroup"/>
                    <m:wrapIndent m:val="1440"/>
                    <m:intLim m:val="subSup"/>
                    <m:naryLim m:val="undOvr"/>
                </m:mathPr>
                <w:themeFontLang w:val="en-US" w:eastAsia="zh-CN"/>
                <w:clrSchemeMapping w:bg1="light1" w:t1="dark1" w:bg2="light2" w:t2="dark2" w:accent1="accent1"
                                    w:accent2="accent2" w:accent3="accent3" w:accent4="accent4" w:accent5="accent5"
                                    w:accent6="accent6" w:hyperlink="hyperlink"
                                    w:followedHyperlink="followedHyperlink"/>
            </w:settings>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/styles.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml">
        <pkg:xmlData>
            <w:styles xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                      xmlns:o="urn:schemas-microsoft-com:office:office"
                      xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                      xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                      xmlns:v="urn:schemas-microsoft-com:vml"
                      xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                      xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml"
                      xmlns:w10="urn:schemas-microsoft-com:office:word"
                      xmlns:sl="http://schemas.openxmlformats.org/schemaLibrary/2006/main" mc:Ignorable="w14">
                <w:docDefaults>
                    <w:rPrDefault>
                        <w:rPr>
                            <w:rFonts w:ascii="Times New Roman" w:hAnsi="Times New Roman" w:eastAsia="宋体"
                                      w:cs="Times New Roman"/>
                        </w:rPr>
                    </w:rPrDefault>
                </w:docDefaults>
                <w:latentStyles w:count="260" w:defQFormat="0" w:defUnhideWhenUsed="1" w:defSemiHidden="1"
                                w:defUIPriority="99" w:defLockedState="0">
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="0" w:semiHidden="0"
                                    w:name="Normal"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="9" w:semiHidden="0"
                                    w:name="heading 1"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="0" w:semiHidden="0"
                                    w:name="heading 2"/>
                    <w:lsdException w:qFormat="1" w:uiPriority="9" w:name="heading 3"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="9" w:semiHidden="0"
                                    w:name="heading 4"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="9" w:semiHidden="0"
                                    w:name="heading 5"/>
                    <w:lsdException w:qFormat="1" w:uiPriority="9" w:name="heading 6"/>
                    <w:lsdException w:qFormat="1" w:uiPriority="9" w:name="heading 7"/>
                    <w:lsdException w:qFormat="1" w:uiPriority="9" w:name="heading 8"/>
                    <w:lsdException w:qFormat="1" w:uiPriority="9" w:name="heading 9"/>
                    <w:lsdException w:uiPriority="99" w:name="index 1"/>
                    <w:lsdException w:uiPriority="99" w:name="index 2"/>
                    <w:lsdException w:uiPriority="99" w:name="index 3"/>
                    <w:lsdException w:uiPriority="99" w:name="index 4"/>
                    <w:lsdException w:uiPriority="99" w:name="index 5"/>
                    <w:lsdException w:uiPriority="99" w:name="index 6"/>
                    <w:lsdException w:uiPriority="99" w:name="index 7"/>
                    <w:lsdException w:uiPriority="99" w:name="index 8"/>
                    <w:lsdException w:uiPriority="99" w:name="index 9"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 1"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 2"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 3"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 4"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 5"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 6"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 7"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 8"/>
                    <w:lsdException w:uiPriority="39" w:name="toc 9"/>
                    <w:lsdException w:uiPriority="99" w:name="Normal Indent"/>
                    <w:lsdException w:uiPriority="99" w:name="footnote text"/>
                    <w:lsdException w:uiPriority="99" w:name="annotation text"/>
                    <w:lsdException w:uiPriority="99" w:semiHidden="0" w:name="header"/>
                    <w:lsdException w:uiPriority="99" w:semiHidden="0" w:name="footer"/>
                    <w:lsdException w:uiPriority="99" w:name="index heading"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="35" w:semiHidden="0"
                                    w:name="caption"/>
                    <w:lsdException w:uiPriority="99" w:name="table of figures"/>
                    <w:lsdException w:uiPriority="99" w:name="envelope address"/>
                    <w:lsdException w:uiPriority="99" w:name="envelope return"/>
                    <w:lsdException w:uiPriority="99" w:name="footnote reference"/>
                    <w:lsdException w:uiPriority="99" w:name="annotation reference"/>
                    <w:lsdException w:uiPriority="99" w:name="line number"/>
                    <w:lsdException w:uiPriority="99" w:name="page number"/>
                    <w:lsdException w:uiPriority="99" w:name="endnote reference"/>
                    <w:lsdException w:uiPriority="99" w:name="endnote text"/>
                    <w:lsdException w:uiPriority="99" w:name="table of authorities"/>
                    <w:lsdException w:uiPriority="99" w:name="macro"/>
                    <w:lsdException w:uiPriority="99" w:name="toa heading"/>
                    <w:lsdException w:uiPriority="99" w:name="List"/>
                    <w:lsdException w:uiPriority="99" w:name="List Bullet"/>
                    <w:lsdException w:uiPriority="99" w:name="List Number"/>
                    <w:lsdException w:uiPriority="99" w:name="List 2"/>
                    <w:lsdException w:uiPriority="99" w:name="List 3"/>
                    <w:lsdException w:uiPriority="99" w:name="List 4"/>
                    <w:lsdException w:uiPriority="99" w:name="List 5"/>
                    <w:lsdException w:uiPriority="99" w:name="List Bullet 2"/>
                    <w:lsdException w:uiPriority="99" w:name="List Bullet 3"/>
                    <w:lsdException w:uiPriority="99" w:name="List Bullet 4"/>
                    <w:lsdException w:uiPriority="99" w:name="List Bullet 5"/>
                    <w:lsdException w:uiPriority="99" w:name="List Number 2"/>
                    <w:lsdException w:uiPriority="99" w:name="List Number 3"/>
                    <w:lsdException w:uiPriority="99" w:name="List Number 4"/>
                    <w:lsdException w:uiPriority="99" w:name="List Number 5"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="10" w:semiHidden="0"
                                    w:name="Title"/>
                    <w:lsdException w:uiPriority="99" w:name="Closing"/>
                    <w:lsdException w:uiPriority="99" w:name="Signature"/>
                    <w:lsdException w:uiPriority="1" w:semiHidden="0" w:name="Default Paragraph Font"/>
                    <w:lsdException w:qFormat="1" w:uiPriority="99" w:semiHidden="0" w:name="Body Text"/>
                    <w:lsdException w:uiPriority="99" w:name="Body Text Indent"/>
                    <w:lsdException w:uiPriority="99" w:name="List Continue"/>
                    <w:lsdException w:uiPriority="99" w:name="List Continue 2"/>
                    <w:lsdException w:uiPriority="99" w:name="List Continue 3"/>
                    <w:lsdException w:uiPriority="99" w:name="List Continue 4"/>
                    <w:lsdException w:uiPriority="99" w:name="List Continue 5"/>
                    <w:lsdException w:uiPriority="99" w:name="Message Header"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="11" w:semiHidden="0"
                                    w:name="Subtitle"/>
                    <w:lsdException w:uiPriority="99" w:name="Salutation"/>
                    <w:lsdException w:uiPriority="99" w:name="Date"/>
                    <w:lsdException w:qFormat="1" w:uiPriority="99" w:semiHidden="0" w:name="Body Text First Indent"/>
                    <w:lsdException w:uiPriority="99" w:name="Body Text First Indent 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Note Heading"/>
                    <w:lsdException w:uiPriority="99" w:name="Body Text 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Body Text 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Body Text Indent 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Body Text Indent 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Block Text"/>
                    <w:lsdException w:uiPriority="99" w:name="Hyperlink"/>
                    <w:lsdException w:uiPriority="99" w:name="FollowedHyperlink"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="22" w:semiHidden="0"
                                    w:name="Strong"/>
                    <w:lsdException w:qFormat="1" w:unhideWhenUsed="0" w:uiPriority="20" w:semiHidden="0"
                                    w:name="Emphasis"/>
                    <w:lsdException w:uiPriority="99" w:name="Document Map"/>
                    <w:lsdException w:uiPriority="99" w:name="Plain Text"/>
                    <w:lsdException w:uiPriority="99" w:name="E-mail Signature"/>
                    <w:lsdException w:uiPriority="99" w:name="Normal (Web)"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Acronym"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Address"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Cite"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Code"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Definition"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Keyboard"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Preformatted"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Sample"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Typewriter"/>
                    <w:lsdException w:uiPriority="99" w:name="HTML Variable"/>
                    <w:lsdException w:uiPriority="99" w:semiHidden="0" w:name="Normal Table"/>
                    <w:lsdException w:uiPriority="99" w:name="annotation subject"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Simple 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Simple 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Simple 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Classic 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Classic 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Classic 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Classic 4"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Colorful 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Colorful 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Colorful 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Columns 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Columns 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Columns 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Columns 4"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Columns 5"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Grid 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Grid 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Grid 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Grid 4"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Grid 5"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Grid 6"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Grid 7"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Grid 8"/>
                    <w:lsdException w:uiPriority="99" w:name="Table List 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table List 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table List 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Table List 4"/>
                    <w:lsdException w:uiPriority="99" w:name="Table List 5"/>
                    <w:lsdException w:uiPriority="99" w:name="Table List 6"/>
                    <w:lsdException w:uiPriority="99" w:name="Table List 7"/>
                    <w:lsdException w:uiPriority="99" w:name="Table List 8"/>
                    <w:lsdException w:uiPriority="99" w:name="Table 3D effects 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table 3D effects 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table 3D effects 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Contemporary"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Elegant"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Professional"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Subtle 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Subtle 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Web 1"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Web 2"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Web 3"/>
                    <w:lsdException w:uiPriority="99" w:name="Balloon Text"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="39" w:semiHidden="0" w:name="Table Grid"/>
                    <w:lsdException w:uiPriority="99" w:name="Table Theme"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="60" w:semiHidden="0" w:name="Light Shading"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="61" w:semiHidden="0" w:name="Light List"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="62" w:semiHidden="0" w:name="Light Grid"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="63" w:semiHidden="0" w:name="Medium Shading 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="64" w:semiHidden="0" w:name="Medium Shading 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="65" w:semiHidden="0" w:name="Medium List 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="66" w:semiHidden="0" w:name="Medium List 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="67" w:semiHidden="0" w:name="Medium Grid 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="68" w:semiHidden="0" w:name="Medium Grid 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="69" w:semiHidden="0" w:name="Medium Grid 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="70" w:semiHidden="0" w:name="Dark List"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="71" w:semiHidden="0" w:name="Colorful Shading"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="72" w:semiHidden="0" w:name="Colorful List"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="73" w:semiHidden="0" w:name="Colorful Grid"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="60" w:semiHidden="0"
                                    w:name="Light Shading Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="61" w:semiHidden="0"
                                    w:name="Light List Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="62" w:semiHidden="0"
                                    w:name="Light Grid Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="63" w:semiHidden="0"
                                    w:name="Medium Shading 1 Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="64" w:semiHidden="0"
                                    w:name="Medium Shading 2 Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="65" w:semiHidden="0"
                                    w:name="Medium List 1 Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="66" w:semiHidden="0"
                                    w:name="Medium List 2 Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="67" w:semiHidden="0"
                                    w:name="Medium Grid 1 Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="68" w:semiHidden="0"
                                    w:name="Medium Grid 2 Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="69" w:semiHidden="0"
                                    w:name="Medium Grid 3 Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="70" w:semiHidden="0"
                                    w:name="Dark List Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="71" w:semiHidden="0"
                                    w:name="Colorful Shading Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="72" w:semiHidden="0"
                                    w:name="Colorful List Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="73" w:semiHidden="0"
                                    w:name="Colorful Grid Accent 1"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="60" w:semiHidden="0"
                                    w:name="Light Shading Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="61" w:semiHidden="0"
                                    w:name="Light List Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="62" w:semiHidden="0"
                                    w:name="Light Grid Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="63" w:semiHidden="0"
                                    w:name="Medium Shading 1 Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="64" w:semiHidden="0"
                                    w:name="Medium Shading 2 Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="65" w:semiHidden="0"
                                    w:name="Medium List 1 Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="66" w:semiHidden="0"
                                    w:name="Medium List 2 Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="67" w:semiHidden="0"
                                    w:name="Medium Grid 1 Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="68" w:semiHidden="0"
                                    w:name="Medium Grid 2 Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="69" w:semiHidden="0"
                                    w:name="Medium Grid 3 Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="70" w:semiHidden="0"
                                    w:name="Dark List Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="71" w:semiHidden="0"
                                    w:name="Colorful Shading Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="72" w:semiHidden="0"
                                    w:name="Colorful List Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="73" w:semiHidden="0"
                                    w:name="Colorful Grid Accent 2"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="60" w:semiHidden="0"
                                    w:name="Light Shading Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="61" w:semiHidden="0"
                                    w:name="Light List Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="62" w:semiHidden="0"
                                    w:name="Light Grid Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="63" w:semiHidden="0"
                                    w:name="Medium Shading 1 Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="64" w:semiHidden="0"
                                    w:name="Medium Shading 2 Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="65" w:semiHidden="0"
                                    w:name="Medium List 1 Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="66" w:semiHidden="0"
                                    w:name="Medium List 2 Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="67" w:semiHidden="0"
                                    w:name="Medium Grid 1 Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="68" w:semiHidden="0"
                                    w:name="Medium Grid 2 Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="69" w:semiHidden="0"
                                    w:name="Medium Grid 3 Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="70" w:semiHidden="0"
                                    w:name="Dark List Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="71" w:semiHidden="0"
                                    w:name="Colorful Shading Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="72" w:semiHidden="0"
                                    w:name="Colorful List Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="73" w:semiHidden="0"
                                    w:name="Colorful Grid Accent 3"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="60" w:semiHidden="0"
                                    w:name="Light Shading Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="61" w:semiHidden="0"
                                    w:name="Light List Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="62" w:semiHidden="0"
                                    w:name="Light Grid Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="63" w:semiHidden="0"
                                    w:name="Medium Shading 1 Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="64" w:semiHidden="0"
                                    w:name="Medium Shading 2 Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="65" w:semiHidden="0"
                                    w:name="Medium List 1 Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="66" w:semiHidden="0"
                                    w:name="Medium List 2 Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="67" w:semiHidden="0"
                                    w:name="Medium Grid 1 Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="68" w:semiHidden="0"
                                    w:name="Medium Grid 2 Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="69" w:semiHidden="0"
                                    w:name="Medium Grid 3 Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="70" w:semiHidden="0"
                                    w:name="Dark List Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="71" w:semiHidden="0"
                                    w:name="Colorful Shading Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="72" w:semiHidden="0"
                                    w:name="Colorful List Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="73" w:semiHidden="0"
                                    w:name="Colorful Grid Accent 4"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="60" w:semiHidden="0"
                                    w:name="Light Shading Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="61" w:semiHidden="0"
                                    w:name="Light List Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="62" w:semiHidden="0"
                                    w:name="Light Grid Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="63" w:semiHidden="0"
                                    w:name="Medium Shading 1 Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="64" w:semiHidden="0"
                                    w:name="Medium Shading 2 Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="65" w:semiHidden="0"
                                    w:name="Medium List 1 Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="66" w:semiHidden="0"
                                    w:name="Medium List 2 Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="67" w:semiHidden="0"
                                    w:name="Medium Grid 1 Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="68" w:semiHidden="0"
                                    w:name="Medium Grid 2 Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="69" w:semiHidden="0"
                                    w:name="Medium Grid 3 Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="70" w:semiHidden="0"
                                    w:name="Dark List Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="71" w:semiHidden="0"
                                    w:name="Colorful Shading Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="72" w:semiHidden="0"
                                    w:name="Colorful List Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="73" w:semiHidden="0"
                                    w:name="Colorful Grid Accent 5"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="60" w:semiHidden="0"
                                    w:name="Light Shading Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="61" w:semiHidden="0"
                                    w:name="Light List Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="62" w:semiHidden="0"
                                    w:name="Light Grid Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="63" w:semiHidden="0"
                                    w:name="Medium Shading 1 Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="64" w:semiHidden="0"
                                    w:name="Medium Shading 2 Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="65" w:semiHidden="0"
                                    w:name="Medium List 1 Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="66" w:semiHidden="0"
                                    w:name="Medium List 2 Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="67" w:semiHidden="0"
                                    w:name="Medium Grid 1 Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="68" w:semiHidden="0"
                                    w:name="Medium Grid 2 Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="69" w:semiHidden="0"
                                    w:name="Medium Grid 3 Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="70" w:semiHidden="0"
                                    w:name="Dark List Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="71" w:semiHidden="0"
                                    w:name="Colorful Shading Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="72" w:semiHidden="0"
                                    w:name="Colorful List Accent 6"/>
                    <w:lsdException w:unhideWhenUsed="0" w:uiPriority="73" w:semiHidden="0"
                                    w:name="Colorful Grid Accent 6"/>
                </w:latentStyles>
                <w:style w:type="paragraph" w:default="1" w:styleId="1">
                    <w:name w:val="Normal"/>
                    <w:next w:val="2"/>
                    <w:qFormat/>
                    <w:uiPriority w:val="0"/>
                    <w:pPr>
                        <w:widowControl w:val="0"/>
                        <w:spacing w:line="360" w:lineRule="auto"/>
                        <w:jc w:val="both"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="Calibri" w:hAnsi="Calibri" w:eastAsia="宋体" w:cs="Times New Roman"/>
                        <w:kern w:val="2"/>
                        <w:sz w:val="24"/>
                        <w:szCs w:val="22"/>
                        <w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="4">
                    <w:name w:val="heading 2"/>
                    <w:basedOn w:val="1"/>
                    <w:next w:val="1"/>
                    <w:link w:val="12"/>
                    <w:qFormat/>
                    <w:uiPriority w:val="0"/>
                    <w:pPr>
                        <w:outlineLvl w:val="1"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="Cambria" w:hAnsi="Cambria"/>
                        <w:b/>
                        <w:bCs/>
                        <w:kern w:val="0"/>
                        <w:sz w:val="30"/>
                        <w:szCs w:val="32"/>
                        <w:lang w:val="zh-CN"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="5">
                    <w:name w:val="heading 4"/>
                    <w:basedOn w:val="1"/>
                    <w:next w:val="1"/>
                    <w:qFormat/>
                    <w:uiPriority w:val="9"/>
                    <w:pPr>
                        <w:keepNext/>
                        <w:keepLines/>
                        <w:numPr>
                            <w:ilvl w:val="3"/>
                            <w:numId w:val="1"/>
                        </w:numPr>
                        <w:tabs>
                            <w:tab w:val="left" w:pos="864"/>
                        </w:tabs>
                        <w:spacing w:before="240" w:after="240" w:line="400" w:lineRule="exact"/>
                        <w:ind w:firstLine="0" w:firstLineChars="0"/>
                        <w:outlineLvl w:val="3"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="Arial" w:hAnsi="Arial"/>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="30"/>
                        <w:szCs w:val="28"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="6">
                    <w:name w:val="heading 5"/>
                    <w:basedOn w:val="1"/>
                    <w:next w:val="1"/>
                    <w:qFormat/>
                    <w:uiPriority w:val="9"/>
                    <w:pPr>
                        <w:keepNext/>
                        <w:keepLines/>
                        <w:numPr>
                            <w:ilvl w:val="4"/>
                            <w:numId w:val="1"/>
                        </w:numPr>
                        <w:tabs>
                            <w:tab w:val="left" w:pos="1008"/>
                        </w:tabs>
                        <w:spacing w:before="240" w:after="240" w:line="400" w:lineRule="exact"/>
                        <w:ind w:firstLine="0" w:firstLineChars="0"/>
                        <w:outlineLvl w:val="4"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="28"/>
                        <w:szCs w:val="28"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:default="1" w:styleId="11">
                    <w:name w:val="Default Paragraph Font"/>
                    <w:unhideWhenUsed/>
                    <w:uiPriority w:val="1"/>
                </w:style>
                <w:style w:type="table" w:default="1" w:styleId="10">
                    <w:name w:val="Normal Table"/>
                    <w:unhideWhenUsed/>
                    <w:uiPriority w:val="99"/>
                    <w:tblPr>
                        <w:tblCellMar>
                            <w:top w:w="0" w:type="dxa"/>
                            <w:left w:w="108" w:type="dxa"/>
                            <w:bottom w:w="0" w:type="dxa"/>
                            <w:right w:w="108" w:type="dxa"/>
                        </w:tblCellMar>
                    </w:tblPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="2">
                    <w:name w:val="Body Text First Indent"/>
                    <w:basedOn w:val="3"/>
                    <w:next w:val="1"/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:uiPriority w:val="99"/>
                    <w:pPr>
                        <w:ind w:firstLine="420" w:firstLineChars="100"/>
                    </w:pPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="3">
                    <w:name w:val="Body Text"/>
                    <w:basedOn w:val="1"/>
                    <w:next w:val="1"/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:uiPriority w:val="99"/>
                    <w:pPr>
                        <w:spacing w:after="120"/>
                    </w:pPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="7">
                    <w:name w:val="caption"/>
                    <w:basedOn w:val="1"/>
                    <w:next w:val="1"/>
                    <w:qFormat/>
                    <w:uiPriority w:val="35"/>
                    <w:rPr>
                        <w:rFonts w:ascii="等线 Light" w:hAnsi="等线 Light" w:eastAsia="黑体"/>
                        <w:sz w:val="20"/>
                        <w:szCs w:val="20"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="8">
                    <w:name w:val="footer"/>
                    <w:basedOn w:val="1"/>
                    <w:link w:val="13"/>
                    <w:unhideWhenUsed/>
                    <w:uiPriority w:val="99"/>
                    <w:pPr>
                        <w:tabs>
                            <w:tab w:val="center" w:pos="4153"/>
                            <w:tab w:val="right" w:pos="8306"/>
                        </w:tabs>
                        <w:snapToGrid w:val="0"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:rPr>
                        <w:sz w:val="18"/>
                        <w:szCs w:val="18"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:styleId="9">
                    <w:name w:val="header"/>
                    <w:basedOn w:val="1"/>
                    <w:link w:val="14"/>
                    <w:unhideWhenUsed/>
                    <w:uiPriority w:val="99"/>
                    <w:pPr>
                        <w:pBdr>
                            <w:bottom w:val="single" w:color="auto" w:sz="6" w:space="1"/>
                        </w:pBdr>
                        <w:tabs>
                            <w:tab w:val="center" w:pos="4153"/>
                            <w:tab w:val="right" w:pos="8306"/>
                        </w:tabs>
                        <w:snapToGrid w:val="0"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:rPr>
                        <w:sz w:val="18"/>
                        <w:szCs w:val="18"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:customStyle="1" w:styleId="12">
                    <w:name w:val="标题 2 字符"/>
                    <w:link w:val="4"/>
                    <w:uiPriority w:val="0"/>
                    <w:rPr>
                        <w:rFonts w:ascii="Cambria" w:hAnsi="Cambria" w:eastAsia="宋体" w:cs="Times New Roman"/>
                        <w:b/>
                        <w:bCs/>
                        <w:kern w:val="0"/>
                        <w:sz w:val="30"/>
                        <w:szCs w:val="32"/>
                        <w:lang w:val="zh-CN" w:eastAsia="zh-CN"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:customStyle="1" w:styleId="13">
                    <w:name w:val="页脚 字符"/>
                    <w:link w:val="8"/>
                    <w:uiPriority w:val="99"/>
                    <w:rPr>
                        <w:sz w:val="18"/>
                        <w:szCs w:val="18"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:customStyle="1" w:styleId="14">
                    <w:name w:val="页眉 字符"/>
                    <w:link w:val="9"/>
                    <w:uiPriority w:val="99"/>
                    <w:rPr>
                        <w:sz w:val="18"/>
                        <w:szCs w:val="18"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:customStyle="1" w:styleId="15">
                    <w:name w:val="w表格文本居中"/>
                    <w:basedOn w:val="1"/>
                    <w:link w:val="16"/>
                    <w:qFormat/>
                    <w:uiPriority w:val="0"/>
                    <w:pPr>
                        <w:spacing w:line="240" w:lineRule="auto"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:cs="宋体"/>
                        <w:sz w:val="21"/>
                        <w:szCs w:val="21"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="character" w:customStyle="1" w:styleId="16">
                    <w:name w:val="w表格文本居中 字符"/>
                    <w:link w:val="15"/>
                    <w:qFormat/>
                    <w:uiPriority w:val="0"/>
                    <w:rPr>
                        <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:eastAsia="宋体" w:cs="宋体"/>
                        <w:szCs w:val="21"/>
                    </w:rPr>
                </w:style>
                <w:style w:type="paragraph" w:customStyle="1" w:styleId="17">
                    <w:name w:val="!!表格小五"/>
                    <w:qFormat/>
                    <w:uiPriority w:val="0"/>
                    <w:pPr>
                        <w:spacing w:line="360" w:lineRule="auto"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="宋体" w:hAnsi="宋体" w:eastAsia="宋体" w:cs="Times New Roman"/>
                        <w:kern w:val="2"/>
                        <w:sz w:val="24"/>
                        <w:szCs w:val="21"/>
                        <w:lang w:val="en-US" w:eastAsia="zh-CN" w:bidi="ar-SA"/>
                    </w:rPr>
                </w:style>
            </w:styles>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:name="/word/theme/theme1.xml"
              pkg:contentType="application/vnd.openxmlformats-officedocument.theme+xml">
        <pkg:xmlData>
            <a:theme xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" name="Office 主题​​">
                <a:themeElements>
                    <a:clrScheme name="Office">
                        <a:dk1>
                            <a:sysClr val="windowText" lastClr="000000"/>
                        </a:dk1>
                        <a:lt1>
                            <a:sysClr val="window" lastClr="FFFFFF"/>
                        </a:lt1>
                        <a:dk2>
                            <a:srgbClr val="44546A"/>
                        </a:dk2>
                        <a:lt2>
                            <a:srgbClr val="E7E6E6"/>
                        </a:lt2>
                        <a:accent1>
                            <a:srgbClr val="4472C4"/>
                        </a:accent1>
                        <a:accent2>
                            <a:srgbClr val="ED7D31"/>
                        </a:accent2>
                        <a:accent3>
                            <a:srgbClr val="A5A5A5"/>
                        </a:accent3>
                        <a:accent4>
                            <a:srgbClr val="FFC000"/>
                        </a:accent4>
                        <a:accent5>
                            <a:srgbClr val="5B9BD5"/>
                        </a:accent5>
                        <a:accent6>
                            <a:srgbClr val="70AD47"/>
                        </a:accent6>
                        <a:hlink>
                            <a:srgbClr val="0563C1"/>
                        </a:hlink>
                        <a:folHlink>
                            <a:srgbClr val="954F72"/>
                        </a:folHlink>
                    </a:clrScheme>
                    <a:fontScheme name="Office">
                        <a:majorFont>
                            <a:latin typeface="等线 Light"/>
                            <a:ea typeface=""/>
                            <a:cs typeface=""/>
                            <a:font script="Jpan" typeface="游ゴシック Light"/>
                            <a:font script="Hang" typeface="맑은 고딕"/>
                            <a:font script="Hans" typeface="等线 Light"/>
                            <a:font script="Hant" typeface="新細明體"/>
                            <a:font script="Arab" typeface="Times New Roman"/>
                            <a:font script="Hebr" typeface="Times New Roman"/>
                            <a:font script="Thai" typeface="Angsana New"/>
                            <a:font script="Ethi" typeface="Nyala"/>
                            <a:font script="Beng" typeface="Vrinda"/>
                            <a:font script="Gujr" typeface="Shruti"/>
                            <a:font script="Khmr" typeface="MoolBoran"/>
                            <a:font script="Knda" typeface="Tunga"/>
                            <a:font script="Guru" typeface="Raavi"/>
                            <a:font script="Cans" typeface="Euphemia"/>
                            <a:font script="Cher" typeface="Plantagenet Cherokee"/>
                            <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
                            <a:font script="Tibt" typeface="Microsoft Himalaya"/>
                            <a:font script="Thaa" typeface="MV Boli"/>
                            <a:font script="Deva" typeface="Mangal"/>
                            <a:font script="Telu" typeface="Gautami"/>
                            <a:font script="Taml" typeface="Latha"/>
                            <a:font script="Syrc" typeface="Estrangelo Edessa"/>
                            <a:font script="Orya" typeface="Kalinga"/>
                            <a:font script="Mlym" typeface="Kartika"/>
                            <a:font script="Laoo" typeface="DokChampa"/>
                            <a:font script="Sinh" typeface="Iskoola Pota"/>
                            <a:font script="Mong" typeface="Mongolian Baiti"/>
                            <a:font script="Viet" typeface="Times New Roman"/>
                            <a:font script="Uigh" typeface="Microsoft Uighur"/>
                            <a:font script="Geor" typeface="Sylfaen"/>
                        </a:majorFont>
                        <a:minorFont>
                            <a:latin typeface="等线"/>
                            <a:ea typeface=""/>
                            <a:cs typeface=""/>
                            <a:font script="Jpan" typeface="游明朝"/>
                            <a:font script="Hang" typeface="맑은 고딕"/>
                            <a:font script="Hans" typeface="等线"/>
                            <a:font script="Hant" typeface="新細明體"/>
                            <a:font script="Arab" typeface="Arial"/>
                            <a:font script="Hebr" typeface="Arial"/>
                            <a:font script="Thai" typeface="Cordia New"/>
                            <a:font script="Ethi" typeface="Nyala"/>
                            <a:font script="Beng" typeface="Vrinda"/>
                            <a:font script="Gujr" typeface="Shruti"/>
                            <a:font script="Khmr" typeface="DaunPenh"/>
                            <a:font script="Knda" typeface="Tunga"/>
                            <a:font script="Guru" typeface="Raavi"/>
                            <a:font script="Cans" typeface="Euphemia"/>
                            <a:font script="Cher" typeface="Plantagenet Cherokee"/>
                            <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
                            <a:font script="Tibt" typeface="Microsoft Himalaya"/>
                            <a:font script="Thaa" typeface="MV Boli"/>
                            <a:font script="Deva" typeface="Mangal"/>
                            <a:font script="Telu" typeface="Gautami"/>
                            <a:font script="Taml" typeface="Latha"/>
                            <a:font script="Syrc" typeface="Estrangelo Edessa"/>
                            <a:font script="Orya" typeface="Kalinga"/>
                            <a:font script="Mlym" typeface="Kartika"/>
                            <a:font script="Laoo" typeface="DokChampa"/>
                            <a:font script="Sinh" typeface="Iskoola Pota"/>
                            <a:font script="Mong" typeface="Mongolian Baiti"/>
                            <a:font script="Viet" typeface="Arial"/>
                            <a:font script="Uigh" typeface="Microsoft Uighur"/>
                            <a:font script="Geor" typeface="Sylfaen"/>
                        </a:minorFont>
                    </a:fontScheme>
                    <a:fmtScheme name="Office">
                        <a:fillStyleLst>
                            <a:solidFill>
                                <a:schemeClr val="phClr"/>
                            </a:solidFill>
                            <a:gradFill rotWithShape="1">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="110000"/>
                                            <a:satMod val="105000"/>
                                            <a:tint val="67000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="105000"/>
                                            <a:satMod val="103000"/>
                                            <a:tint val="73000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="105000"/>
                                            <a:satMod val="109000"/>
                                            <a:tint val="81000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="0"/>
                            </a:gradFill>
                            <a:gradFill rotWithShape="1">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:satMod val="103000"/>
                                            <a:lumMod val="102000"/>
                                            <a:tint val="94000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:satMod val="110000"/>
                                            <a:lumMod val="100000"/>
                                            <a:shade val="100000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="99000"/>
                                            <a:satMod val="120000"/>
                                            <a:shade val="78000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="0"/>
                            </a:gradFill>
                        </a:fillStyleLst>
                        <a:lnStyleLst>
                            <a:ln w="6350" cap="flat" cmpd="sng" algn="ctr">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                            <a:ln w="12700" cap="flat" cmpd="sng" algn="ctr">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                            <a:ln w="19050" cap="flat" cmpd="sng" algn="ctr">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                        </a:lnStyleLst>
                        <a:effectStyleLst>
                            <a:effectStyle>
                                <a:effectLst/>
                            </a:effectStyle>
                            <a:effectStyle>
                                <a:effectLst/>
                            </a:effectStyle>
                            <a:effectStyle>
                                <a:effectLst>
                                    <a:outerShdw blurRad="57150" dist="19050" dir="5400000" algn="ctr" rotWithShape="0">
                                        <a:srgbClr val="000000">
                                            <a:alpha val="63000"/>
                                        </a:srgbClr>
                                    </a:outerShdw>
                                </a:effectLst>
                            </a:effectStyle>
                        </a:effectStyleLst>
                        <a:bgFillStyleLst>
                            <a:solidFill>
                                <a:schemeClr val="phClr"/>
                            </a:solidFill>
                            <a:solidFill>
                                <a:schemeClr val="phClr">
                                    <a:tint val="95000"/>
                                    <a:satMod val="170000"/>
                                </a:schemeClr>
                            </a:solidFill>
                            <a:gradFill rotWithShape="1">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:tint val="93000"/>
                                            <a:satMod val="150000"/>
                                            <a:shade val="98000"/>
                                            <a:lumMod val="102000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:tint val="98000"/>
                                            <a:satMod val="130000"/>
                                            <a:shade val="90000"/>
                                            <a:lumMod val="103000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:shade val="63000"/>
                                            <a:satMod val="120000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="0"/>
                            </a:gradFill>
                        </a:bgFillStyleLst>
                    </a:fmtScheme>
                </a:themeElements>
                <a:objectDefaults/>
            </a:theme>
        </pkg:xmlData>
    </pkg:part>
</pkg:package>', 'doc', 'public', '1.0.0', 'word.docx.vm', 'word.docx', NULL, '2023-05-15 15:25:12.000', '2023-05-15 16:49:22.000', 17, 17, '隔壁老于', 0);
INSERT INTO template_management (id, template_name, template_code, template_type, template_scope, version, file_name, file_path, description, create_time, update_time, create_by, update_by, create_name, del_flag) VALUES(45, 'plantumil-ER图', '#set($comment=$table.tableComment)
@startuml
!theme plain
usecase $comment
#foreach ($column in $columns)
:$comment: -l-> ($column.columnComment)
#end
@enduml
#end

', 'uml', 'public', '1.0.0', 'er.uml.vm', '#{tableName}.img', NULL, '2023-05-16 19:14:11.000', '2023-05-16 19:20:44.000', 17, 17, '隔壁老于', 0);
