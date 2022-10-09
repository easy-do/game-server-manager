import { audit } from '@/api/audit';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import { GlobalContext } from '@/context';
import useLocale from '@/utils/useLocale';
import { Form, FormInstance, Input, Modal, Notification } from '@arco-design/web-react';
import React, { useContext, useEffect, useRef } from 'react';
import locale from './locale';


export interface Props {
    title: string;
    auditType: number;
    id: number;
    visible: boolean;
    setVisible: (value:boolean) => void;
    successCallBack: () => void;
}

function AuditModal(props: Props) {

    const TextArea = Input.TextArea;

    const { lang } = useContext(GlobalContext);

    const formRef = useRef<FormInstance>();

    useEffect(() => {
        formRef.current.clearFields()
    }, [props.id, props.visible, props.auditType, props.title]);


    const t = useLocale(locale);

    //提交请求
    const handleSubmit = () => {
        formRef.current.validate().then((values) => {
            audit({ values, auditType: props.auditType }).then((res) => {
                const { success, msg } = res.data
                if (success) {
                    Notification.success({ content: msg, duration: 300 })
                    props.successCallBack();
                }
                this.loading()
            })
        });
    };

    return (
        <Modal
            title={t['searchTable.update.title']}
            visible={props.visible}
            onOk={() => {
                handleSubmit();
            }}
            onCancel={() => {
                props.setVisible(false);
            }}
            autoFocus={false}
            focusLock={true}
            maskClosable={false}
        >
            <Form
                style={{ width: '95%', marginTop: '6px' }}
                labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
                wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
                ref={formRef}
                labelAlign="left"
            >
                <Form.Item
                    label={t['form.columns.status']}
                    field="roleKey"
                    rules={[
                        { required: true, message: t['form.columns.status.required'] },
                    ]}
                >
                    <DictDataSelect dictCode='audit_status' placeholder={t['form.columns.status.placeholder']} />
                </Form.Item>
                <Form.Item
                    label={t['form.columns.points']}
                    field="roleKey"
                    rules={[
                        { required: true, message: t['form.columns.points.required'] },
                    ]}
                >
                    <Input placeholder={t['form.columns.points.placeholder']} />
                </Form.Item>
                <Form.Item
                    label={t['form.columns.description']}
                    field="roleKey"
                    rules={[
                        { required: true, message: t['form.columns.description.required'] },
                    ]}
                >
                    <TextArea placeholder={t['form.columns.description.placeholder']} />
                </Form.Item>
            </Form>
        </Modal>
    );
}


export default AuditModal;