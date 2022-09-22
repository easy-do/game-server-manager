import { getInstallCmd, infoRequest } from '@/api/clientInfo';
import useLocale from '@/utils/useLocale';
import { Button, Modal, Notification, Spin } from '@arco-design/web-react';
import Paragraph from '@arco-design/web-react/es/Typography/paragraph';
import React, { useEffect, useState } from 'react';
import locale from './locale';

function InstallClientPage({ id, visible, setVisible, successCallBack }) {

    const t = useLocale(locale);

    const [loading, setLoading] = useState(false);

    const [installCmd, setInstallCmd] = useState();

    const [dataInfo, setDataInfo] = useState({
        serverId: null, status: ''
    });

    const handleSubmit = () => {
        // addRequest(values).then((res) => {
        //   const { success, msg} = res.data
        //   if(success){
        //     Notification.success({ content: msg, duration: 300 })
        //     successCallBack();
        //   }
        // });
    };

    //加载数据
    function fetchData() {
        if (id !== undefined) {
            setLoading(true);
            getInstallCmd(id).then((res) => {
                const { success, data } = res.data;
                if (success) {
                    setInstallCmd(data);
                }
            });
            infoRequest(id).then((res) => {
                const { success, data } = res.data;
                if (success) {
                    setDataInfo(data);
                }
                setLoading(false);
            });

        }

    }

    useEffect(() => {
        fetchData();
    }, [id]);

    return (
        <Modal
            title={t['searchTable.operations.install']}
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
            footer={
                <>
                    {dataInfo && dataInfo.serverId && dataInfo.status === '未部署' ? <Button type="primary" loading={loading} onClick={() => handleSubmit()} >在线安装</Button> : null}
                    {dataInfo && dataInfo.serverId && dataInfo.status !== '未部署' ? <Button type="primary" loading={loading} onClick={() => handleSubmit()} >重新在线安装</Button> : null}
                    <Button type="primary" loading={loading} onClick={() => setVisible(false)} >关闭窗口</Button>
                </>
            }
        >
            <Spin tip="loading Data..." loading={loading}>
                使用手动安装脚本需要安装wget,centos系统直接运行 yum install -y wget ,其他不支持yum的系统请自行百度安装.<br />
                <Paragraph copyable={{ text: installCmd }} >安装命令(不支持离线设备):<br />{installCmd}</Paragraph>
            </Spin>

        </Modal>
    )

}


export default InstallClientPage;