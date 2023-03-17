import { envDataListByScriptId } from '@/api/scriptData';
import { execScript, list } from '@/api/scriptData';
import { GlobalContext } from '@/context';
import useLocale from '@/utils/useLocale';
import { Form, FormInstance, Input, Modal, Notification, Select, Spin, Typography } from '@arco-design/web-react';
import React, { useContext, useEffect, useRef, useState } from 'react';
import locale from './locale';

function ExecScriptPage({ deviceId, deviceType, visible, setVisible, successCallBack }) {

    const t = useLocale(locale);

    const formRef = useRef<FormInstance>();

    const { lang } = useContext(GlobalContext);

    const [loading, setLoading] = useState(false);

    const [options, setOptions] = useState([]);

    const [envs, setEnvs] = useState([]);

    function scriptOnchage(value) {
        try{
            const values = value.split('--');
            if(values.length === 2){
                setLoading(true)
                envDataListByScriptId(values[0]).then((res) => {
                    const { success, data } = res.data;
                    if (success) {
                        const envs = []
                        data.map((item) => {
                            item.env.map((item1) => {
                                envs.push(
                                    <Form.Item
                                        label={item1.envName}
                                        initialValue={item1.envValue}
                                        field={'env.' + item1.envKey} >
                                        <Input />
                                    </Form.Item>)
                            })
                        })
                        setEnvs(envs)
                    }
                    setLoading(false);
                });
            }
        }catch{

        }
    }

    //加载数据
    function fetchData() {
        if (deviceId !== undefined && visible) {
            setEnvs([])
            setLoading(true)
            list().then((res) => {
                const { success, data } = res.data;
                if (success) {
                    const newOptions = [];
                    data.map((item) => {
                        newOptions.push({
                            value: item.id+'--'+item.scriptName,
                            label: item.scriptName + ':' + item.version +' by:'+ item.author,
                            key: item.id,
                        });
                    });
                    setOptions(newOptions);
                }
                setLoading(false);
            });
        }
    }

    useEffect(() => {
        fetchData();
    }, [visible]);


    //提交
    const handleSubmit = () => {
        formRef.current.validate().then((values: any) => {
            execScript({...values, deviceId:deviceId,deviceType:deviceType}).then((res) => {
                const { success, msg } = res.data;
                if (success) {
                    formRef.current.clearFields()
                    setEnvs([])
                    Notification.success(msg)
                    successCallBack()
                }
                setLoading(false);
            });
        });
    };

    return (
        <Modal
            title={t['searchTable.operations.add']}
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
                style={{ width: '95%', marginTop: '6px' }}
                labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
                wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
                labelAlign="left"
                layout='vertical'
            >
                <Form.Item
                    onChange={scriptOnchage}
                    label={t['searchTable.columns.script']}
                    field="scriptId"
                    rules={[
                        { required: true, message: t['searchTable.rules.script.required'] },
                    ]}
                >
                    <Select
                        showSearch
                        placeholder={t['searchForm.script.placeholder']}
                        options={options}
                        onChange={scriptOnchage}
                    />
                </Form.Item>

                <Typography.Title heading={6}>
                    {t['searchTable.columns.EnvInfo']}
                </Typography.Title>
                <Spin loading={loading} style={{ width: '100%' }}>
                <Form.Item
                    field={'env'}
                >
                    {
                        envs
                    }

                </Form.Item>
                </Spin>
            </Form>

        </Modal>
    )
}

export default ExecScriptPage;