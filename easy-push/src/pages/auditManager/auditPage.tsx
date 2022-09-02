import { Form, Modal } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";


const AuditPage = (props:any) =>{
    const { title, visible, auditId, auditType, callBack } = props;

    const { AuditStore } = useStores();

    const { initAuditEnv, auditshow, auditButtonOk, auditButtonCancel, auditshowFormOnChage} = AuditStore

      //页面渲染执行
  useEffect(() => {
      initAuditEnv(visible, auditId, auditType, callBack )
  }, [title, visible, auditId, auditType]);

    return <Modal
        title={title}
        visible={auditshow}
        onOk={auditButtonOk}
        onCancel={auditButtonCancel}
        maskClosable={false}
      >
        <Form
          onValueChange={(values) => auditshowFormOnChage(values)}
        >
          <Form.Select
            label="审核结果"
            field="status"
            style={{ width: "90%" }}
            initValue={2}
            optionList={[
              { value: 2, label: "通过", otherKey: 1 },
              { value: 3, label: "驳回", otherKey: 1 },
              { value: 4, label: "锁定", otherKey: 1 },
            ]}
          />
          <Form.InputNumber
            field="points"
            label="积分"
            style={{ width: "90%" }}
            type='number'
            initValue={10}
          />
          <Form.Input
            field="description"
            label="审核意见"
            style={{ width: "90%" }}
            initValue={'无'}
          />
        </Form>
      </Modal>
     
}

export default observer(AuditPage);