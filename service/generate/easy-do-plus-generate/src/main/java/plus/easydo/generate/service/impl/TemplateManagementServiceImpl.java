package plus.easydo.generate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.auth.AuthorizationUtil;
import plus.easydo.auth.vo.SimpleUserInfoVo;
import plus.easydo.generate.dto.TemplateManagementDto;
import plus.easydo.generate.entity.TemplateManagement;
import plus.easydo.generate.mapper.TemplateManagementMapper;
import plus.easydo.generate.mapstruct.TemplateMapstruct;
import plus.easydo.generate.qo.TemplateManagementQo;
import plus.easydo.generate.service.TemplateManagementService;
import plus.easydo.generate.vo.TemplateManagementVo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**
 * 模板管理Service层
 *
 * @author gebilaoyu
 */
@Service
public class TemplateManagementServiceImpl extends ServiceImpl<TemplateManagementMapper, TemplateManagement> implements TemplateManagementService {


    /**
     * 查询模板管理
     *
     * @param id 模板管理ID
     * @return 模板管理
     */
    @Override
    public TemplateManagementVo selectById(Long id) {
        TemplateManagement templateManagement = baseMapper.selectById(id);
        return BeanUtil.copyProperties(templateManagement, TemplateManagementVo.class);
    }

    /**
     * 分页条件查询模板管理列表
     *
     * @param qo 查询条件封装
     * @return 模板管理
     */
    @Override
    public IPage<TemplateManagementVo> page(TemplateManagementQo qo) {
        IPage<TemplateManagement> page = qo.startPage();
        LambdaQueryWrapper<TemplateManagement> wrapper = Wrappers.lambdaQuery();
        if (CharSequenceUtil.isNotBlank(qo.getTemplateName())) {
            wrapper.like(TemplateManagement::getTemplateName, qo.getTemplateName());
        }
        if (CharSequenceUtil.isNotBlank(qo.getTemplateCode())) {
            wrapper.like(TemplateManagement::getTemplateCode, qo.getTemplateCode());
        }
        wrapper.select(TemplateManagement::getId,TemplateManagement::getTemplateName,
                TemplateManagement::getTemplateType,TemplateManagement::getTemplateScope,TemplateManagement::getVersion,TemplateManagement::getDescription,TemplateManagement::getCreateName);
        IPage<TemplateManagement> iPage = baseMapper.selectPage(page, wrapper);
        return iPage.convert(TemplateMapstruct.INSTANCE::entityToVo);
    }

    /**
     * 获取所有模板管理列表
     *
     * @param qo 查询条件封装
     * @return 模板管理
     */
    @Override
    public List<TemplateManagementVo> list(TemplateManagementQo qo) {
        LambdaQueryWrapper<TemplateManagement> wrapper = Wrappers.lambdaQuery();
        wrapper.select(TemplateManagement::getId,TemplateManagement::getTemplateName,
                TemplateManagement::getTemplateType,TemplateManagement::getVersion,TemplateManagement::getCreateName);
        List<TemplateManagement> list = baseMapper.selectList(wrapper);
        return TemplateMapstruct.INSTANCE.entityToVo(list);

    }

    /**
     * 新增模板管理
     *
     * @param dto 数据传输对象
     * @return 结果
     */
    @Override
    public int insert(TemplateManagementDto dto) {
        TemplateManagement templateManagement = TemplateMapstruct.INSTANCE.dtoToEntity(dto);
        if(CharSequenceUtil.isEmpty(templateManagement.getFileName())){
            templateManagement.setFileName(UUID.fastUUID()+".vm");
        }
        templateManagement.setTemplateCode(DEFAULT_TEMPLATE_CODE);
        SimpleUserInfoVo suer = AuthorizationUtil.getSimpleUser();
        templateManagement.setCreateBy(suer.getId());
        templateManagement.setUpdateBy(suer.getId());
        templateManagement.setCreateName(suer.getNickName());
        return baseMapper.insert(templateManagement);
    }

    /**
     * 修改模板管理
     *
     * @param dto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean update(TemplateManagementDto dto) {
        TemplateManagement templateManagement = TemplateMapstruct.INSTANCE.dtoToEntity(dto);
        SimpleUserInfoVo user = AuthorizationUtil.getSimpleUser();
        LambdaQueryWrapper<TemplateManagement> wrapper = Wrappers.lambdaQuery();
        if(!AuthorizationUtil.isAdmin()){
            wrapper.eq(TemplateManagement::getCreateBy,user.getId());
        }
        wrapper.eq(TemplateManagement::getId,dto.getId());
        templateManagement.setUpdateBy(user.getId());
        templateManagement.setCreateName(user.getNickName());;
        return update(templateManagement,wrapper);
    }

    /**
     * 批量删除模板管理
     *
     * @param ids 需要删除的模板管理ID
     * @return 结果
     */
    @Override
    public Boolean deleteByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids));
    }

    /**
     * 删除模板管理信息
     *
     * @param id 模板管理ID
     * @return 结果
     */
    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public List<TemplateManagementVo> selectByIds(String[] ids) {
        List<TemplateManagement> list = baseMapper.selectBatchIds(Arrays.asList(ids));
        return BeanUtil.copyToList(list, TemplateManagementVo.class);
    }

    private static final String DEFAULT_TEMPLATE_CODE = """
            ## 模板变量信息说明 --
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
            ##  子表信息  类型 GenTable subTable\s
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
             ##    查询方式 queryType（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围） \s
             ##    显示类型 htmlType（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）
             ##    字典类型 dictType
             ##    排序   sort
            """;

}

