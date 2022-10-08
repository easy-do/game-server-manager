import React from 'react';
import { Button, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import { dictLabelEnum } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('status_select','string')

export interface DataInfoVo{
    templateIds: string,
    isRemove: string,
    isUpdate: string,
    isInsert: string,
    isQuery: string,
    remark: string,
    updateTime: string,
    updateBy: string,
    createTime: string,
    createBy: string,
    options: string,
    genPath: string,
    genType: string,
    functionAuthor: string,
    functionName: string,
    businessName: string,
    moduleName: string,
    packageName: string,
    tplCategory: string,
    className: string,
    subTableFkName: string,
    subTableName: string,
    tableComment: string,
    tableName: string,
    dataSourceId: string,
    tableId: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'updateTime',
    'createTime',
    'tableComment',
    'tableName',
    'tableId',
      ];
}

//默认排序字段
export function getDefaultOrders(){
  return [{column: 'createTime', asc: false}];
}


export const yesOrNo = [{key:'否',value:0},{key:'是',value:1}]

export const queryTypeSelect = [
  { label: '=', value: SearchTypeEnum.EQ },
  { label: '!=', value: SearchTypeEnum.NE },
  { label: 'in', value: SearchTypeEnum.IN },
  { label: 'notIn', value: SearchTypeEnum.NOT_IN },
  { label: '>', value: SearchTypeEnum.GT },
  { label: '>=', value: SearchTypeEnum.GE },
  { label: '<', value: SearchTypeEnum.LT },
  { label: '<=', value: SearchTypeEnum.LE },
  { label: 'like', value: SearchTypeEnum.LIKE },
  { label: 'notLike', value: SearchTypeEnum.NOT_LIKE },
  { label: 'likeLeft', value: SearchTypeEnum.LIKE_LEFT },
  { label: 'likeRight', value: SearchTypeEnum.LIKE_RIGHT },
  { label: 'between', value: SearchTypeEnum.BETWEEN },
  { label: 'notBetween', value: SearchTypeEnum.NOT_BETWEEN },
];

export const javaTypeSelect = [
  'Long',
  'String',
  'Integer',
  'Double',
  'BigDecimal',
  'Date',
];

export const htmlTypeSelect = [
  { label: '文本框', value: 'input' },
  { label: '文本域', value: 'textarea' },
  { label: '下拉框', value: 'select' },
  { label: '单选框', value: 'radio' },
  { label: '复选框', value: 'checkbox' },
  { label: '日期控件', value: 'datetime' },
  { label: '图片上传', value: 'imageUpload' },
  { label: '文件上传', value: 'fileUpload' },
  { label: '富文本控件', value: 'editor' },
];


//表单展示字段
export function getColumns(
  t: any,
  callback: (record: Record<string, any>, type: string) => Promise<void>
) {
  return [
    {
      title: t['searchTable.columns.tableId'],
      dataIndex: 'tableId',
    },
    {
      title: t['searchTable.columns.tableName'],
      dataIndex: 'tableName',
    }, 
    {
      title: t['searchTable.columns.tableComment'],
      dataIndex: 'tableComment',
    },
    {
      title: t['searchTable.columns.createTime'],
      dataIndex: 'createTime',
    }, 
    {
      title: t['searchTable.columns.updateTime'],
      dataIndex: 'updateTime',
    },
    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <div>
          <Button
            type="text"
            size="small"
            onClick={() => callback(record, 'view')}
          >
            {t['searchTable.columns.operations.view']}
          </Button>
          <Button
            type="text"
            size="small"
            onClick={() => callback(record, 'update')}
          >
            {t['searchTable.columns.operations.update']}
          </Button>
          <Button
            type="text"
            size="small"
            onClick={() => callback(record, 'generate')}
          >
            {t['searchTable.columns.operations.generate']}
          </Button>
          <Button
            type="text"
            size="small"
            onClick={() => callback(record, 'sync')}
          >
            {t['searchTable.columns.operations.sync']}
          </Button>
          <Popconfirm
            title={t['searchTable.columns.operations.remove.confirm']}
            onOk={() => callback(record, 'remove')}
          >
            <Button type="text" status="warning" size="small">
              {t['searchTable.columns.operations.remove']}
            </Button>
          </Popconfirm>
        </div>
      ),
    },
  ];
}

