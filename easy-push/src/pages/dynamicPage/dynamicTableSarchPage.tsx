import { Button, ButtonGroup, Col, Form, Row } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";

const DynamicTableSarchPage = (props:any) => {
    const { key } = props;


    const getSerchFormapi = (formApi:any)=>{}
    const serchFormValueChange = (values:any)=>{}
    const serchColumns = []
    const searchButton = (key:string) =>{}
    const cleanSearchform = () =>{}
    const refreshTable = () =>{}
    return (
        <>
        <Form
        labelPosition="left"
        labelAlign="left"
        style={{ padding: "10px" }}
        getFormApi={getSerchFormapi}
        onValueChange={serchFormValueChange}
      >
        <Row>
          <Col span={4}>
            <Form.Input label="用户名" field="nickName" style={{ width: "90%" }} showClear></Form.Input>
          </Col>
          <Col span={4}>
            <Form.Input label="用户id" field="id" style={{ width: "90%" }} showClear></Form.Input>
          </Col>
          <Col span={4}>
            <Form.Select label="账户状态" field="state" style={{ width: "90%" }} showClear>
              <Form.Select.Option label="正常" value={0} />
              <Form.Select.Option label="禁用" value={1} />
            </Form.Select>
          </Col>
        </Row>
      </Form>
      <ButtonGroup theme="borderless">
          <Button onClick={() => searchButton(key)}>搜索</Button>
          <Button onClick={cleanSearchform}>重置</Button>
          <Button onClick={refreshTable}>刷新</Button>
        </ButtonGroup>
        </>
      );
};

export default observer(DynamicTableSarchPage);