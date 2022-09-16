import React, {
  useCallback,
  useContext,
  useEffect,
  useRef,
  useState,
} from 'react';
import dayjs from 'dayjs';
import {
  Form,
  Input,
  Select,
  Grid,
  Table,
  FormInstance,
  Radio,
  Switch,
} from '@arco-design/web-react';
import { GlobalContext } from '@/context';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import {
  IconDragDotVertical,
  IconRefresh,
  IconSearch,
} from '@arco-design/web-react/icon';
import { Status } from './constants';
import FormItem from '@arco-design/web-react/es/Form/form-item';
import {
  SortableContainer,
  SortableElement,
  SortableHandle,
} from 'react-sortable-hoc';
import { SearchTypeEnum } from '@/utils/systemConstant';

const { Row, Col } = Grid;
const { useForm } = Form;
const TextArea = Input.TextArea;

function EditColumnsPage({ loading, columnTableData, setColumnTableData }) {
  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const queryTypeSelect = [
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
    { label: 'LIKE', value: SearchTypeEnum.LIKE_RIGHT },
    { label: 'between', value: SearchTypeEnum.BETWEEN },
    { label: 'notBetween', value: SearchTypeEnum.NOT_BETWEEN },
  ];

  const javaTypeSelect = [
    'Long',
    'String',
    'Integer',
    'Double',
    'BigDecimal',
    'Date',
  ];

  const htmlTypeSelect = [
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

  const columnTableColumns = [
    {
      title: t['searchTable.columns.sort'],
      dataIndex: 'sort',
    },
    {
      title: t['searchTable.columns.columnName'],
      dataIndex: 'columnName',
    },
    {
      title: t['searchTable.columns.columnType'],
      dataIndex: 'columnType',
    },
    {
      title: t['searchTable.columns.columnComment'],
      dataIndex: 'columnComment',
      editable: true,
      //   render: (_, record) => <Input value={record.columnComment} />,
    },
    {
      title: t['searchTable.columns.javaField'],
      dataIndex: 'javaField',
      editable: true,
      //   render: (_, record) => <Input value={record.javaField} />,
    },
    {
      title: t['searchTable.columns.javaType'],
      dataIndex: 'javaType',
      editable: true,
        render: (_, record) => (
            <Select defaultValue={record.javaType} options={htmlTypeSelect} />
        ),
    },
    {
      title: t['searchTable.columns.htmlType'],
      dataIndex: 'htmlType',
      editable: true,
        render: (_, record) => (
          <Select defaultValue={record.htmlType} options={htmlTypeSelect} />
        ),
    },
    {
      title: t['searchTable.columns.queryType'],
      dataIndex: 'queryType',
      editable: true,
        render: (_, record) => (
          <Select defaultValue={record.queryType} options={queryTypeSelect} />
        ),
    },
    {
      title: t['searchTable.columns.isList'],
      dataIndex: 'isList',
      editable: true,
      render: (_, record) => (
        <FormItem
          style={{ marginBottom: 0 }}
          labelCol={{ span: 0 }}
          wrapperCol={{ span: 24 }}
          initialValue={record.isList}
          field="isList"
          rules={[{ required: true }]}
        >
          <Radio.Group type="button">
            <Radio value={'1'}>是</Radio>
            <Radio value={'0'}>否</Radio>
          </Radio.Group>
        </FormItem>
      ),
    },
    {
      title: t['searchTable.columns.isQuery'],
      dataIndex: 'isQuery',
      editable: true,
      render: (_, record) => (
        <FormItem
          style={{ marginBottom: 0 }}
          labelCol={{ span: 0 }}
          wrapperCol={{ span: 24 }}
          initialValue={record.isQuery}
          field="isQuery"
          rules={[{ required: true }]}
        >
          <Radio.Group type="button">
            <Radio value={'1'}>是</Radio>
            <Radio value={'0'}>否</Radio>
          </Radio.Group>
        </FormItem>
      ),
    },
    {
      title: t['searchTable.columns.isEdit'],
      dataIndex: 'isEdit',
      editable: true,
      render: (_, record) => (
        <FormItem
          style={{ marginBottom: 0 }}
          labelCol={{ span: 0 }}
          wrapperCol={{ span: 24 }}
          initialValue={record.isEdit}
          field="isEdit"
          rules={[{ required: true }]}
        >
          <Radio.Group type="button">
            <Radio value={'1'}>是</Radio>
            <Radio value={'0'}>否</Radio>
          </Radio.Group>
        </FormItem>
      ),
    },
    {
      title: t['searchTable.columns.isInsert'],
      dataIndex: 'isInsert',
      editable: true,
      render: (_, record) => (
        <FormItem
          style={{ marginBottom: 0 }}
          labelCol={{ span: 0 }}
          wrapperCol={{ span: 24 }}
          initialValue={record.isInsert}
          field="isInsert"
          rules={[{ required: true }]}
        >
          <Radio.Group type="button">
            <Radio value={'1'}>是</Radio>
            <Radio value={'0'}>否</Radio>
          </Radio.Group>
        </FormItem>
      ),
    },
    {
      title: t['searchTable.columns.isRequired'],
      dataIndex: 'isRequired',
      editable: true,
      render: (_, record) => (
        <FormItem
          style={{ marginBottom: 0 }}
          labelCol={{ span: 0 }}
          wrapperCol={{ span: 24 }}
          initialValue={record.isRequired}
          field="isRequired"
          rules={[{ required: true }]}
        >
          <Radio.Group type="button">
            <Radio value={'1'}>是</Radio>
            <Radio value={'0'}>否</Radio>
          </Radio.Group>
        </FormItem>
      ),
    },
    {
      title: t['searchTable.columns.isPk'],
      dataIndex: 'isPk',
      editable: true,
      render: (_, record) => (
        <FormItem
          style={{ marginBottom: 0 }}
          labelCol={{ span: 0 }}
          wrapperCol={{ span: 24 }}
          initialValue={record.isPk}
          field="isPk"
          rules={[{ required: true }]}
        >
          <Radio.Group type="button">
            <Radio value={'1'}>是</Radio>
            <Radio value={'0'}>否</Radio>
          </Radio.Group>
        </FormItem>
      ),
    },
    {
      title: t['searchTable.columns.dictType'],
      dataIndex: 'dictType',
      editable: true,
    },
  ];

  const arrayMoveMutate = (array, from, to) => {
    const startIndex = to < 0 ? array.length + to : to;

    if (startIndex >= 0 && startIndex < array.length) {
      const item = array.splice(from, 1)[0];
      array.splice(startIndex, 0, item);
    }
  };

  const arrayMove = (array, from, to) => {
    array = [...array];
    arrayMoveMutate(array, from, to);
    return array;
  };

  function onSortEnd({ oldIndex, newIndex }) {
    if (oldIndex !== newIndex) {
      const newData = arrayMove(
        [].concat(columnTableData),
        oldIndex,
        newIndex
      ).filter((el) => !!el);
      setColumnTableData(newData);
    }
  }

  //可编辑行
  const EditableContext = React.createContext<{ getForm?: () => FormInstance }>(
    {}
  );
  function EditableRow(props) {
    const { children, record, className, ...rest } = props;
    const refForm = useRef(null);

    const getForm = () => refForm.current;

    return (
      <EditableContext.Provider
        value={{
          getForm,
        }}
      >
        <Form
          style={{ display: 'table-row' }}
          ref={refForm}
          wrapper="tr"
          wrapperProps={rest}
          className={`${className} editable-row`}
        >
          {children}
        </Form>
      </EditableContext.Provider>
    );
  }

  //可编辑的表格
  function EditableCell(props) {
    const { children, className, rowData, column, onHandleSave } = props;
    const ref = useRef(null);
    const refInput = useRef(null);
    const { getForm } = useContext(EditableContext);
    const [editing, setEditing] = useState(false);
    const handleClick = useCallback(
      (e) => {
        if (
          editing &&
          column.editable &&
          ref.current &&
          !ref.current.contains(e.target)
        ) {
          cellValueChangeHandler(rowData[column.dataIndex]);
        }
      },
      [editing, rowData, column]
    );
    useEffect(() => {
      editing && refInput.current && refInput.current.focus();
    }, [editing]);
    useEffect(() => {
      document.addEventListener('click', handleClick, true);
      return () => {
        document.removeEventListener('click', handleClick, true);
      };
    }, [handleClick]);

    const cellValueChangeHandler = (value) => {
        console.info(value)
      if (column.dataIndex === 'queryType' ||
      column.dataIndex === 'javaType' ||
      column.dataIndex === 'htmlType') {
        const values = {
          [column.dataIndex]: value,
        };
        console.info(values)
        onHandleSave && onHandleSave({ ...rowData, ...values });
        setTimeout(() => setEditing(!editing), 300);
      } else {
        const form = getForm();
        form.validate([column.dataIndex], (errors, values) => {
          if (!errors || !errors[column.dataIndex]) {
            setEditing(!editing);
            onHandleSave && onHandleSave({ ...rowData, ...values });
          }
        });
      }
    };

    if (editing) {
      return (
        <div ref={ref}>
          {column.dataIndex === 'isPk' ||
          column.dataIndex === 'isList' ||
          column.dataIndex === 'isRequired' ||
          column.dataIndex === 'isQuery' ||
          column.dataIndex === 'isInsert' ||
          column.dataIndex === 'isEdit' ? (
            <FormItem
              style={{ marginBottom: 0 }}
              labelCol={{ span: 0 }}
              wrapperCol={{ span: 24 }}
              initialValue={rowData[column.dataIndex]}
              field={column.dataIndex}
              rules={[{ required: true }]}
            >
              <Radio.Group type="button" onChange={cellValueChangeHandler}>
                <Radio value={'1'}>是</Radio>
                <Radio value={'0'}>否</Radio>
              </Radio.Group>
            </FormItem>
          ) : column.dataIndex === 'javaType' ? (
            <Select
             labelInValue
              style={{ width: '150px' }}
              inputValue={rowData[column.dataIndex]}
              options={javaTypeSelect}
              onChange={cellValueChangeHandler}
            />
          ) : column.dataIndex === 'htmlType' ? (
            <Select
              style={{ width: '150px' }}
              defaultValue={rowData[column.dataIndex]}
              options={htmlTypeSelect}
              onChange={cellValueChangeHandler}
            />
          ) : column.dataIndex === 'queryType' ? (
            <Select
              defaultValue={rowData[column.dataIndex]}
              options={queryTypeSelect}
              onChange={cellValueChangeHandler}
            />
          ) : (
            <FormItem
              style={{ height: '100px' }}
              initialValue={rowData[column.dataIndex]}
              field={column.dataIndex}
              rules={[{ required: true }]}
            >
              <Input ref={refInput} onPressEnter={cellValueChangeHandler} />
            </FormItem>
          )}
        </div>
      );
    }

    return (
      <div
        className={column.editable ? `editable-cell ${className}` : className}
        onClick={() => column.editable && setEditing(!editing)}
      >
        {children}
      </div>
    );
  }

  //编辑表格保存回调函数
  function handleSave(row) {
    console.info(row);
    const newData = [...columnTableData];
    const index = newData.findIndex((item) => row.columnId === item.columnId);
    newData.splice(index, 1, { ...newData[index], ...row });
    setColumnTableData(newData);
  }

  //排序选择行头
  const DragHandle = SortableHandle(() => (
    <IconDragDotVertical
      style={{
        cursor: 'move',
        color: '#555',
      }}
    />
  ));

  //可排序容器
  const SortableWrapper = SortableContainer((props) => {
    return <tbody {...props} />;
  });

  //可排序行
  const SortableItem = SortableElement((props) => {
    return <EditableRow {...props} />;
  });

  //可排序组件整体
  const DraggableContainer = (props) => (
    <SortableWrapper
      useDragHandle
      onSortEnd={onSortEnd}
      helperContainer={() =>
        document.querySelector('.arco-drag-table-container-2 table tbody')
      }
      updateBeforeSortStart={({ node }) => {
        const tds = node.querySelectorAll('td');
        tds.forEach((td) => {
          td.style.width = td.clientWidth + '10px';
        });
      }}
      {...props}
    />
  );

  //可排序行
  const DraggableRow = (props) => {
    const { record, index, ...rest } = props;
    return <SortableItem index={index} {...rest} />;
  };

  //自定义可排序编辑渲染组件
  const components = {
    header: {
      operations: ({ selectionNode, expandNode }) => [
        {
          node: <th />,
          width: 40,
        },
        {
          name: 'expandNode',
          node: expandNode,
        },
        {
          name: 'selectionNode',
          node: selectionNode,
        },
      ],
    },
    body: {
      operations: ({ selectionNode, expandNode }) => [
        {
          node: (
            <td>
              <div className="arco-table-cell">
                <DragHandle />
              </div>
            </td>
          ),
          width: 40,
        },
        {
          name: 'expandNode',
          node: expandNode,
        },
        {
          name: 'selectionNode',
          node: selectionNode,
        },
      ],
      tbody: DraggableContainer,
      row: DraggableRow,
      cell: EditableCell,
    },
  };

  return (
    <Table
      className="arco-drag-table-container-2"
      rowKey="id"
      stripe
      borderCell
      loading={loading}
      pagination={false}
      columns={columnTableColumns.map((column) =>
        column.editable
          ? {
              ...column,
              onCell: () => ({
                onHandleSave: handleSave,
              }),
            }
          : column
      )}
      components={components}
      data={columnTableData}
    />
  );
}

export default EditColumnsPage;
