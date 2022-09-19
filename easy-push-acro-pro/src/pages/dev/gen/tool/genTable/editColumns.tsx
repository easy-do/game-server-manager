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
  Table,
  FormInstance,
  Radio,
  Button,
} from '@arco-design/web-react';
import { GlobalContext } from '@/context';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { IconDragDotVertical } from '@arco-design/web-react/icon';
import FormItem from '@arco-design/web-react/es/Form/form-item';
import {
  SortableContainer,
  SortableElement,
  SortableHandle,
} from 'react-sortable-hoc';
import EditColumn from './editColumn';
import { htmlTypeSelect, queryTypeSelect } from './constants';

function EditColumnsPage({ loading, columnTableData, setColumnTableData }) {
  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const [editColumnData, setEditColumnData] = useState(null);

  const [isEditColumn, setIsEditColumn] = useState(false);

  function editColumn(record) {
    setEditColumnData(record);
    setIsEditColumn(true);
  }

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
    },
    {
      title: t['searchTable.columns.javaField'],
      dataIndex: 'javaField',
    },
    {
      title: t['searchTable.columns.javaType'],
      dataIndex: 'javaType',
    },
    {
      title: t['searchTable.columns.htmlType'],
      dataIndex: 'htmlType',
      render: (_, record) =>
        htmlTypeSelect.map((item) => {
          if (item.value === record.htmlType) {
            return item.label;
          }
        }),
    },
    {
      title: t['searchTable.columns.queryType'],
      dataIndex: 'queryType',
      render: (_, record) =>
        queryTypeSelect.map((item) => {
          if (item.value === record.queryType) {
            return item.label;
          }
        }),
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
      title: t['searchTable.columns.isInfo'],
      dataIndex: 'isInfo',
      editable: true,
      render: (_, record) => (
        <FormItem
          style={{ marginBottom: 0 }}
          labelCol={{ span: 0 }}
          wrapperCol={{ span: 24 }}
          initialValue={record.isInfo}
          field="isInfo"
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
      title: t['searchTable.update.title'],
      render: (_, record) => (
        <div>
          <Button type="text" size="small" onClick={() => editColumn(record)}>
            {t['searchTable.columns.operations.update']}
          </Button>
        </div>
      ),
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
          !ref.current.contains(e.target) &&
          !e.target.classList.contains('js-demo-select-option')
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
      const form = getForm();
      form.validate([column.dataIndex], (errors, values) => {
        if (!errors || !errors[column.dataIndex]) {
          setEditing(!editing);
          onHandleSave && onHandleSave({ ...rowData, ...values });
        }
      });
    };

    if (editing) {
      return (
        <div ref={ref}>
          {column.dataIndex === 'isPk' ||
          column.dataIndex === 'isList' ||
          column.dataIndex === 'isRequired' ||
          column.dataIndex === 'isQuery' ||
          column.dataIndex === 'isInsert' ||
          column.dataIndex === 'isInfo' ||
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
          ) : (
            <FormItem
              style={{ width: '120px' }}
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
    console.info(row)
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
    <div>
      <Table
        style={{width:'100%'}}
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
      <EditColumn
        data={editColumnData}
        visible={isEditColumn}
        setVisible={setIsEditColumn}
        successCallBack={handleSave}
      />
    </div>
  );
}

export default EditColumnsPage;
